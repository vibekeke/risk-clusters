import java.util.Arrays;

public class RiskCluster implements Comparable<RiskCluster> {
    private final float average;
    private final int[] values;
    private final Coordinate[] indices;
    private final Direction direction;
    
    public RiskCluster(float average, int[] riskValues, Coordinate[] indices, Direction dir) {
        this.average = average;
        this.values = riskValues;
        this.indices = indices;
        this.direction = dir;
    }

    public float getAverage() { return average; }
    public int[] getValues() { return values.clone(); }
    public Coordinate[] getIndices() { return indices.clone(); }
    public Direction getDirection() {return direction; }

    @Override
    public int compareTo(RiskCluster other) {
        int avgComparison = Float.compare(other.average, this.average);
        
        if (avgComparison != 0) {
            return avgComparison;
        }

        int thisMax = Arrays.stream(this.values).max().getAsInt();
        int otherMax = Arrays.stream(other.values).max().getAsInt();

        int maxComparison = Integer.compare(otherMax, thisMax);
        if (maxComparison != 0) {
            return maxComparison;
        }

        //indices[0] always contains the starting position of a RiskCluster
        return this.indices[0].compareTo(other.indices[0]);
    }

    public String infoString() {
        StringBuilder sb = new StringBuilder();
        Coordinate start_pos = indices[0];
        
        sb.append("\nRisk Cluster starting at index ");
        sb.append(start_pos.toString());
        sb.append(String.format("\nAverage risk: %.1f", average));

        sb.append("\nValues: ");
        sb.append(Arrays.toString(values));

        sb.append("\nIndices: ");
        sb.append(Arrays.toString(indices));
        
        sb.append(String.format("\nDirection: " + direction));

        return sb.toString();
    }
}