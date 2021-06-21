package meetingrooms.controller;

public enum OrderingType {

    ABC("abc-"), BACK("visszafele ");

    private String word;

    OrderingType(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
