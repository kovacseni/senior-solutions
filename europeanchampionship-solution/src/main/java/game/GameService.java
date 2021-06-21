package game;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GameService {

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game getGameWithMostGoalDifference() {
        return gameRepository.getGames().stream()
                .max(Comparator.comparing(game -> Math.abs((game.getFirstCountryScore()) - (game.getSecondCountryScore()))))
                .get();
    }

    public String getCountryWithMostGoals() {
        Map<String, Integer> result = new HashMap<>();
        int actual;
        int maxBattle = 0;
        String country = "Nincs";
        for (Game game : gameRepository.getGames()) {
            if (!result.containsKey(game.getFirstCountry())) {
                result.put(game.getFirstCountry(), game.getFirstCountryScore());
            } else {
                actual = result.get(game.getFirstCountry());
                result.replace(game.getFirstCountry(), actual + game.getFirstCountryScore());
                if (actual > maxBattle) {
                    maxBattle = actual + game.getFirstCountryScore();
                    country = game.getFirstCountry();
                }
            }
            if (!result.containsKey(game.getSecondCountry())) {
                result.put(game.getSecondCountry(), game.getSecondCountryScore());
            } else {
                actual = result.get(game.getSecondCountry());
                result.replace(game.getSecondCountry(), actual + game.getSecondCountryScore());
                if (actual > maxBattle) {
                    maxBattle = actual + game.getSecondCountryScore();
                    country = game.getSecondCountry();
                }
            }
        }
        return country;
    }

    public String getCountryWithMostGoals2() {
        Map<String, Integer> result = new HashMap<>();
        for (Game game : gameRepository.getGames()) {
            counter(game.getFirstCountry(), game.getFirstCountryScore(), result);
            counter(game.getSecondCountry(), game.getSecondCountryScore(), result);
        }
        return result.entrySet().stream()
                .max(Comparator.comparing(Map.Entry<String, Integer>::getValue))
                .map(Map.Entry<String, Integer>::getKey)
                .get();
    }

    private void counter(String name, int goal, Map<String, Integer> result) {
        int actual = 0;
        if (!result.containsKey(name)) {
            result.put(name, goal);
        } else {
            actual = result.get(name);
            result.replace(name, actual + goal);
        }
    }

    public Game largestGoalDiff() {
        return gameRepository.getGames().stream()
                .max(Comparator.comparing(game -> Math.abs(game.getFirstCountryScore() - game.getSecondCountryScore()))).get();
    }

    public int getGoals(String country) {
        int goals = 0;
        for (Game g : gameRepository.getGames()) {
            if (g.getFirstCountry().equals(country)) {
                goals += g.getFirstCountryScore();
            } else if (g.getSecondCountry().equals(country)) {
                goals += g.getSecondCountryScore();
            }
        }
        return goals;
    }

    public String mostGoalsCountry() {
        return gameRepository.getGames().stream()
                .flatMap(game -> Stream.of(game.getFirstCountry(), game.getSecondCountry()))
                .max(Comparator.comparing(this::getGoals)).get();
    }
}
