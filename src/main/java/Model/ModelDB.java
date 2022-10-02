package Model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDB {


        private  File dbFile;
        private  String dBName;
        private  Connection connection;
        private  Statement statement;

        public  ModelDB(String name) {
            this.dBName = name;
            this.dbFile = new File("DB.db");
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
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
                        "Film (" +
                        "film_id integer PRIMARY KEY autoincrement, " +
                        "title CHARACTER(20)," +
                        "year integer," +
                        "elo_rate integer," +
                        "image_id integer" +
                        ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public  void InsertFilm(Film newFilm) {
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

            } catch (SQLException e) {
                e.printStackTrace();
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
