package game;

public class Game {

    private String firstCountry;
    private String secondCountry;
    private int firstCountryScore;
    private int secondCountryScore;

    public Game(String firstCountry, String secondCountry, String firstCountryScore, String secondCountryScore) {
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
        try {
            this.firstCountryScore = Integer.parseInt(firstCountryScore);
            this.secondCountryScore = Integer.parseInt(secondCountryScore);
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
            this.firstCountryScore = 0;
            this.secondCountryScore = 0;
        }

    }

    public String whoIsTheWinner() {
        return Integer.parseInt(firstCountry) > Integer.parseInt(firstCountry) ? firstCountry : secondCountry;
    }

    public String getFirstCountry() {
        return firstCountry;
    }

    public void setFirstCountry(String firstCountry) {
        this.firstCountry = firstCountry;
    }

    public String getSecondCountry() {
        return secondCountry;
    }

    public void setSecondCountry(String secondCountry) {
        this.secondCountry = secondCountry;
    }

    public int getFirstCountryScore() {
        return firstCountryScore;
    }

    public int getSecondCountryScore() {
        return secondCountryScore;
    }
}
