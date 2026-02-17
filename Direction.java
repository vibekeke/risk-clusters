public enum Direction {
    HORIZONTAL(0, 1),
    VERTICAL(1, 0),
    LEFT_DIAGONAL(1, -1),
    RIGHT_DIAGONAL(1, 1);

    public final int directionVectorR;
    public final int directionVectorC;

    Direction(int directionR, int directionC) {
        this.directionVectorR = directionR;
        this.directionVectorC = directionC; 
    }
}
