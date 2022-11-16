package Model;
public class Film {
    private String title;
    private int year, film_id, image_id, eloRate;

    //Create a new Object 
    public Film(String title, int year, int image_id,int eloRate){
        this.film_id = 0;
        this.title =title;
        this.year= year;
        this.image_id =image_id;
        this.eloRate=eloRate;
    }
    public Film(int id,  String title, int year, int image_id,int eloRate){
        this.film_id = id;
        this.title =title;
        this.year= year;
        this.image_id =image_id;
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

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getEloRate() {
        return eloRate;
    }

    public void setEloRate(int eloRate) {
        this.eloRate = eloRate;
    }

    public int getFilm_id() {
        return film_id;
    }
}
