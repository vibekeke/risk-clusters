public class Coordinate implements Comparable<Coordinate> {
    private final int r;
    private final int c;

    public Coordinate(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int r() { return r;}
    public int c() { return c;}

    @Override
    public String toString() {
        return String.format("[%d][%d]", r, c);
    }

    @Override
    public int compareTo(Coordinate other) {
        //sort by smallest index first
        int comparison = Integer.compare(this.r, other.r);

        if (comparison != 0) {
            return comparison;
        }
        return Integer.compare(this.c, other.c);
    }
}


/*
Initially used record before I realized it's nice to make it printable and comparable.
record Coordinate(int r, int c) {}

*/