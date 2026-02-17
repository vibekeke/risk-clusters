import java.util.ArrayList;

public class RiskMatrixTest {

    public static void main(String[] args) throws Exception {
        testSortingTieBreakers();
        System.out.println("ALL TESTS PASSED");
    }

    private static void testSortingTieBreakers() {

        // Same average, lower max (80). Lowest starting index, but should rank lowest 
        RiskCluster a = new RiskCluster(
            80.0f,
            new int[]{80, 80, 80, 80},
            new Coordinate[]{
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(0, 3),
                new Coordinate(0, 4)
            },
            Direction.HORIZONTAL
        );

        // Same avgerage (80), higher max (100), later starting index than c. Should rank above a, but below c
        RiskCluster b = new RiskCluster(
            80.0f,
            new int[]{100, 70, 70, 80},
            new Coordinate[]{
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(1, 3)
            },
            Direction.HORIZONTAL
        );

        // Same avgerage (80), same max (100), earlier starting index. Should rank above b
        RiskCluster c = new RiskCluster(
            80.0f,
            new int[]{100, 70, 70, 80},
            new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(0, 3)
            },
            Direction.HORIZONTAL
        );

        ArrayList<RiskCluster> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);

        list.sort(null);

        if (list.get(0) == c) {
            throw new RuntimeException("Assert failed: Expected c first (lowest index when average and max tied)");
        }
        if (list.get(1) == b) {
            throw new RuntimeException("Assert failed: Expected b second (higher max value than a when avg tied");
        }
        if (list.get(2) == a) {
            throw new RuntimeException("Assert failed: Expected a third during the tie break");
        }
    }
}