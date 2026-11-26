package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Team {
    final String name;
    final int points;
    final int goalDifference;

    Team(String name, int points, int goalDifference) {
        this.name = name;
        this.points = points;
        this.goalDifference = goalDifference;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDifference + ")";
    }
}

class Leaderboard<T> {
    private final List<T> items;
    private final Comparator<? super T> order;

    Leaderboard(List<T> items, Comparator<? super T> order) {
        this.items = items;
        this.order = order;
    }

    List<T> ranking() {
        List<T> sortedItems = new ArrayList<>(items);
        Collections.sort(sortedItems, order);
        return sortedItems;
    }
}

public class task5 {
    public static void main(String[] args) {
        List<Team> teams = new ArrayList<>();

        teams.add(new Team("Eagles", 9, 6));
        teams.add(new Team("Bisons", 9, 4));
        teams.add(new Team("Panthers", 9, 4));
        teams.add(new Team("Cobras", 6, 1));

        Comparator<Team> teamOrder = Comparator
                .comparingInt((Team t) -> t.points).reversed()
                .thenComparing(Comparator.comparingInt(
                        (Team t) -> t.goalDifference).reversed())
                .thenComparing(t -> t.name);

        Leaderboard<Team> board = new Leaderboard<>(teams, teamOrder);

        System.out.println(board.ranking());
    }
}
