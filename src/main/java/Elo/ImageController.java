package Elo;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.awt.*;
import java.awt.image.BufferedImage;


import static java.lang.Math.round;


public  class ImageController {


    public static class Current{
        public static boolean imageEditable =true;
        private static ImageView imageView;
        private static Image image;
        private static int widthTarget = 200 ;
        private static int heightTarget = 200;


        //apply arc (round corner) to a given Imag
        public static void MakeCornerRound(ImageView imageView, int arcWidth, int arcHeight){
            //Image returnImg =null;

            Rectangle clip = new Rectangle( imageView.getFitWidth(), imageView.getFitHeight());
            clip.setArcWidth(arcWidth);
            clip.setArcHeight(arcHeight);
            imageView.setClip(clip);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = imageView.snapshot(parameters, null);
            imageView.setClip(null);
            imageView.setImage(image);



            // return returnImg;
        }


        public static void setWidthTarget(int widthTarget) {
            Current.widthTarget = widthTarget;
        }

        public static void setHeightTarget(int heightTarget) {
            Current.heightTarget = heightTarget;
        }

        public static void setImageView(ImageView imageView) {
            Current.imageView = imageView;
        }

        public static void setImage(Image image){
            if(imageEditable){
                Current.image = CropAndResizeImage(image);
                Current.updateImageView(Current.image);
            }

        }
        public static Image getImage(){
            return Current.image;
        }

        public static void updateImageView(Image image){
            Current.imageView.setImage(image);
        }

        private static Image CropAndResizeImage(Image img){
            int width = (int) img.getWidth();
            int height = (int) img.getHeight();

            //create a BufferedImage from existing Image
            BufferedImage bufferedImage =
                    new BufferedImage(
                            width,
                            height,
                            BufferedImage.TYPE_INT_RGB);
            SwingFXUtils.fromFXImage(img,bufferedImage);

            //Ratio of width and height of target image to original
            //float widthRatio = (float)  widthTarget/width;
            float heightRatio = (float) heightTarget/height;
            float targetAspectRation = (float)widthTarget/heightTarget;

            // new value of A side based on ratio of other side
            //int newHeightByWR = round(height*widthRatio);
            int newWidthByHR = round(width*heightRatio);

            //Gap to size of ImageTarget is
            //one side is greater than or equal to zero and other side less or equal to zero
            int gapToTargetWidth =  newWidthByHR - widthTarget;


            if(gapToTargetWidth>=0){
                int newWidth =  round(height * (targetAspectRation));
                bufferedImage = CropOuterEdge( bufferedImage,newWidth, height );
            }else{
                int newHeight =  round(width * (1/targetAspectRation));
                bufferedImage = CropOuterEdge( bufferedImage,width, newHeight );
            }

            BufferedImage resizedImage = new BufferedImage(widthTarget,heightTarget, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resizedImage.createGraphics();

            graphics2D.drawImage(bufferedImage, 0, 0, widthTarget, heightTarget, null);
            graphics2D.dispose();

            return SwingFXUtils.toFXImage(resizedImage, null);
        }

        //crop Center of an Image based given values for width and height
        private static BufferedImage CropOuterEdge(BufferedImage image, int targetWidth, int targetHeight) {

            int x = round( ((float) image.getWidth() - targetWidth) /2);
            int y = round( ((float) image.getHeight() - targetHeight) /2);
            return image.getSubimage(x,y,targetWidth,targetHeight);

        }
    }

}
