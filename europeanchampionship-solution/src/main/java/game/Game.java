package game;

public class Game {

    private String firstCountry;
    private String secondCountry;
    private int firstCountryScore;
    private int secondCountryScore;

    public Game(String firstCountry, String secondCountry, int firstCountryScore, int secondCountryScore) {
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
        this.firstCountryScore = firstCountryScore;
        this.secondCountryScore = secondCountryScore;
    }

    public String getFirstCountry() {
        return firstCountry;
    }

    public String getSecondCountry() {
        return secondCountry;
    }

    public int getFirstCountryScore() {
        return firstCountryScore;
    }

    public int getSecondCountryScore() {
        return secondCountryScore;
    }

    public void setFirstCountry(String firstCountry) {
        this.firstCountry = firstCountry;
    }

    public void setSecondCountry(String secondCountry) {
        this.secondCountry = secondCountry;
    }

    public void setFirstCountryScore(int firstCountryScore) {
        this.firstCountryScore = firstCountryScore;
    }

    public void setSecondCountryScore(int secondCountryScore) {
        this.secondCountryScore = secondCountryScore;
    }
}
