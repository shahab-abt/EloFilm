package Model;

import javafx.scene.image.Image;

public class Film {
    private String title;
    private int year, film_id,  eloRate;
    private  Image image;

    //TODO: EloRate should be removed from here
    public Film(String title, int year, Image image, int eloRate){
        this.film_id = 0;
        this.title =title;
        this.year= year;
        this.image =image;
        this.eloRate=eloRate;
    }
    public Film(int id,  String title, int year, Image image,int eloRate){
        this.film_id = id;
        this.title =title;
        this.year= year;
        this.image =image;
        this.eloRate=eloRate;

    }

//(film_id,title,year, image_id)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getEloRate() {
        return eloRate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setEloRate(int eloRate) {
        this.eloRate = eloRate;
    }

    public int getFilm_id() {
        return film_id;
    }
}
