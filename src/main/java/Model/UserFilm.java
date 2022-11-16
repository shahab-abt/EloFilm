package Model;

public class UserFilm {

    private String filmTile;
    private int filmYear;
    private int filmElo;

    public UserFilm(String filmTile, int filmYear, int filmElo) {
        this.filmTile = filmTile;
        this.filmYear = filmYear;
        this.filmElo = filmElo;
    }

    public String getFilmTile() {
        return filmTile;
    }

    public int getFilmYear() {
        return filmYear;
    }

    public int getFilmElo() {
        return filmElo;
    }
}
