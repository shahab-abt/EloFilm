package Model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
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
                String sqlTableFilm = "CREATE TABLE  Film (" +
                        "    film_id integer PRIMARY KEY autoincrement," +
                        "    title CHARACTER(20)," +
                        "    year integer," +
                        "    photo MEDIUMBLOB," +
                        "    CONSTRAINT unique_film UNIQUE (title,year)" +
                        "                                )";
                String sqlTableUser = "create table User(" +
                        "   user_id  integer PRIMARY KEY autoincrement," +
                        "   name CHARACTER(20) NOT NULL UNIQUE" +
                        ")";
                String sqlTableRanking = "create table Ranking(" +
                        "    film_id integer," +
                        "    user_id integer," +
                        "    elo_rate integer NOT NULL ," +
                        "    FOREIGN KEY (film_id) references Film(film_id)," +
                        "    FOREIGN KEY (user_id) references User(user_id)," +
                        "    CONSTRAINT ranking_id PRIMARY KEY (film_id,user_id)" +
                        ")";
                statement.executeUpdate(sqlTableFilm);
                statement.executeUpdate(sqlTableUser);
                statement.executeUpdate(sqlTableRanking);





            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int InsertFilm(Film newFilm) {
            String insertFilmQuery = "INSERT INTO Film (title,year,photo)VALUES (?,?,?)";
            Image imageFilm = newFilm.getImage();

            BufferedImage bufferedImage =
                    new BufferedImage(
                            (int) imageFilm.getWidth(),
                            (int) imageFilm.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
            File imageFile = new File("imageFile.jpg");
            SwingFXUtils.fromFXImage(imageFilm,bufferedImage);

            try {
                ImageIO.write(bufferedImage, "jpg",imageFile );
                FileInputStream fileInputStream = new FileInputStream(imageFile);

                PreparedStatement pstmt = connection.prepareStatement(insertFilmQuery);

                pstmt.setString(1,newFilm.getTitle());
                pstmt.setInt(2,newFilm.getYear());
                //pstmt.setInt(3,newFilm.getEloRate());

                pstmt.setBinaryStream(3, fileInputStream,fileInputStream.available());
                pstmt.execute();

                return statement.executeQuery("SELECT last_insert_rowid()").getInt("last_insert_rowid()");


            } catch (SQLException | IOException e) {
                //TODO: duplicate Entry should be reported

                e.printStackTrace();
                return -1;
            }
        }

        public Image getImageById(int id){
        String query ="SELECT photo from Film where film_id=?";
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1,id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                    byte[] img = rs.getBytes(1);
                    ByteArrayInputStream bis = new ByteArrayInputStream(img);
                    Image image = new Image(bis);
                    return image;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return null;

        }

    public List<Film> GetAllFilms()
        {
            try{
            List<Film> returnValue = new ArrayList<>();
            ResultSet rs;

            rs = statement.executeQuery("SELECT * FROM Film");
            //int film_id =0;
            //String title ="";
            //int year=0;



            //int image_id=0;
            //int eloRank=0;

            while (rs.next()){
                int film_id = rs.getInt("film_id");
                String title= rs.getString("title");
                int year = rs.getInt("year");
                byte[] img = rs.getBytes("photo");
                ByteArrayInputStream bis = new ByteArrayInputStream(img);
                Image image = new Image(bis);
                //eloRank = rs.getInt("elo_rate");
                //image_id = rs.getInt("image_id");
                returnValue.add(new Film(film_id,title,year,image, -1));
            }
                return  returnValue;
            }catch (SQLException e) {
            e.printStackTrace();
            return null;
            }
        }



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
