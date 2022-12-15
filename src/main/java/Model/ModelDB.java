package Model;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ModelDB {


        private  File dbFile;
        private  String dBName;
        private  Connection connection;
        private  Statement statement;

        public  ModelDB(String name) {
            this.dBName = name;
            this.dbFile = new File(name);
            // if no DataBase exist call to create new one
            if (!dbFile.exists()) {
                CreateTables();
            }
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + this.dBName);
                statement = connection.createStatement();
                ModelDB.DB.SetModel(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        private  void CreateTables() {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + dBName);
                statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Film (film_id integer PRIMARY KEY autoincrement, title CHARACTER(20),year integer,elo_rate integer," + "image_id integer, CONSTRAINT unique_film UNIQUE (title,year))");
                statement.executeUpdate("CREATE TABLE image (ID INT PRIMARY KEY NOT NULL, photo MEDIUMBLOB)");



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int InsertFilm(Film newFilm) {
            try {
                statement.executeUpdate("INSERT INTO Film " +
                        "(film_id,title,year,elo_rate ,image_id) VALUES (" +
                        "  NULL," +
                        " '" + newFilm.getTitle() + "' ," +
                        " '" + newFilm.getYear() + "' ," +
                        " '" + newFilm.getEloRate() + "' ," +
                        " '" + newFilm.getImage_id() + "'" +

                        ")"
                );
                int filmId= statement.executeQuery("SELECT last_insert_rowid()").getInt("last_insert_rowid()");
                return filmId;

            } catch (SQLException e) {
                //TODO: change function so it return a String for insert confirmation
                // or duplicate Entry Error

                e.printStackTrace();
                return -1;
            }
        }

        public void InsertImage(int filmID, Image image){

            try {
            /*
                File image = new File("samplePic.png");
            if(!image.exists()){
                System.out.println("No Image");

            }

             */
                File tempFile = new File("tempFile.jpg");
                //TODO image should be adjusted
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

                ImageIO.write(bi, "JPG", tempFile);

                
            int num_rows = 0;
            FileInputStream fis = new FileInputStream(tempFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum; (readNum = fis.read(buf)) != -1;){
                bos.write(buf, 0, readNum);
            }
            fis.close();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO image (id, photo) VALUES(?,?)");
            ps.setInt(1,filmID);
            ps.setBytes(2, bos.toByteArray());
            num_rows = ps.executeUpdate();

                if(num_rows>0){
                    System.out.println("Data has been inserted");
                }
            ps.close();

            }catch(Exception e)
            {
                System.out.println(e);
            }
        }

        public void TestInsertImage(int filmID,FileInputStream fileInputStream ){

            String query = "INSERT INTO image (id, photo) VALUES(?,?)";
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1,filmID);
                pstmt.setBinaryStream(2, fileInputStream,fileInputStream.available());
                pstmt.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public Image TestGetImage(int id){
            String query = "SELECT photo FROM image where ID=? ";

            try {
                String query2 = "SELECT photo FROM image where ID=2 ";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query2);
                while (rs.next()){
                    System.out.println("image loaded");


                    byte[] imgss = rs.getBytes("photo");
                    ByteArrayInputStream bis = new ByteArrayInputStream(imgss);
                    Image image = new Image(bis);


                    /*
                    Blob blob = rs.getBlob("photo");
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);

                     */
                    return image;

                }

                /*
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1,id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    System.out.println("image loaded");
                    Blob blob = rs.getBlob("photo");
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    return image;


                }

                 */

            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
            return null;

        }

        public void GetImage(){
            try {
                ResultSet rs;
                rs = statement.executeQuery("SELECT * FROM image");
                while (rs.next()){

                    InputStream input = rs.getBinaryStream("photo");
                    InputStreamReader inputReader = new InputStreamReader(input);
                    if(inputReader.ready()){
                        File tempFile = new File("sample.jpg");
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        byte[] buffer = new byte[1024];
                        while(input.read(buffer) > 0){
                            fos.write(buffer);
                        }
                        Image image = new Image(tempFile.toURI().toURL().toString());

                    }
                }


            }catch(Exception e)
            {
                System.out.println(e);
            }
        }

        //public List<Buch> GetAllBuecher(boolean nurVerfuegbar) throws SQLException{
        public List<Film> GetAllFilms()
        {
            try{
            List<Film> returnValue = new ArrayList<>();
            ResultSet rs;

            rs = statement.executeQuery("SELECT * FROM Film");
            int film_id =0;
            String title ="";
            int year=0;
            int image_id=0;
            int eloRank=0;

            while (rs.next()){
                film_id = rs.getInt("film_id");
                title= rs.getString("title");
                year = rs.getInt("year");
                eloRank = rs.getInt("elo_rate");
                image_id = rs.getInt("image_id");
                returnValue.add(new Film(film_id,title,year,image_id, eloRank));
            }
                return  returnValue;
            }catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
        }
/*


         while(rs.next()) {
             bcID = rs.getInt("buch_id");
             titel = rs.getString("titel");
             autor = rs.getString("autor");
             isbn = rs.getInt("isbn");
             anzahl = rs.getInt("anzahl");

             returnValue.add(new Buch(bcID,titel,autor,isbn,anzahl));
         }
 */



    public static class DB{
        private static ModelDB model;

        public static void SetModel(ModelDB db) {
            model = db;
        }

        public static ModelDB GetModel(){
            return model;
        }

    }

}
