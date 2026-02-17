import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RiskMatrix {
    private final int[][] matrix;
    private int gridSize;

    public RiskMatrix(String fileName) {
        this.gridSize = -1;
        this.matrix = parseFile(fileName);
    }

    private int[][] parseFile(String fileName) {
        File file = new File(fileName);
        int[][] riskMatrix = null;
        int rowIndex = -1;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] rowData = sc.nextLine().trim().split(" ");
                
                //check that rows and columns are valid length
                if (gridSize == -1) {
                    gridSize = rowData.length;
                    rowIndex = 0;
                    riskMatrix = new int[gridSize][gridSize];
                } else if (rowData.length != gridSize) {
                    throw new IllegalArgumentException(String.format("Mismatched row length in file: %s on row index %d", fileName, rowIndex));
                } else if (rowIndex >= gridSize) {
                    throw new IllegalArgumentException(String.format("Too many rows compared to colums. Expected (n x n) grid file: %s", fileName));
                }

                //initialize new row
                int[] row = new int[rowData.length];
                for (int i = 0; i < rowData.length; i++) {
                    try {
                        row[i] = Integer.parseInt(rowData[i]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(String.format("Non-integer value in file: %s on row %d, colum",  fileName, i));
                    }
                }

                riskMatrix[rowIndex++] = row;
            }

        } catch (FileNotFoundException e) { 
            throw new IllegalArgumentException("File not found: " + fileName, e); }

        //final validity checks
        if (riskMatrix == null || gridSize < 2) {
            throw new IllegalArgumentException("Empty or invalid file: " + fileName);
        }

        if (rowIndex != gridSize) {
            throw new IllegalArgumentException(
                String.format("File: %s.Mismatched row to column ratio, rows = %d, columns = %d; . Excepted (n x n) grid in file: ", fileName, rowIndex, gridSize));
        }

        return riskMatrix;
    }

    /* Assignment asked to check segment sizes of 4, and keep a minimum average of 70.
    I decided to pass in these values as parameters to avoid hard coded values */
    public ArrayList<RiskCluster> findSignificantRiskClusters(int segmentSize, int minAverage) {
        ArrayList<RiskCluster> significantClusters = new ArrayList<>();
        
        if (segmentSize > gridSize) {
            throw new IllegalArgumentException(
                String.format("Tried to check segments larger than the grid in RiskGrid. %d SegmentSize and %d gridSize", segmentSize, gridSize)
            );
        }

        //Iterate through matrix, and add valid clusters
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                Coordinate startPos = new Coordinate(r, c);
                addSignificantClusters(significantClusters, startPos, segmentSize, minAverage);
            }
        }
        
        significantClusters.sort(null);
        return significantClusters;
    }

    private void addSignificantClusters(ArrayList<RiskCluster> clusterList, Coordinate startPos, int segmentSize, int minAverage) {
        //Note: No need to check out-of-bounds clusters for a given entry

        //Horizontal check
        if (startPos.c() <= gridSize - segmentSize) {
            RiskCluster cluster = evaluateSegment(startPos, Direction.HORIZONTAL, segmentSize, minAverage);
            if (cluster != null) clusterList.add(cluster);
        }
        //Vertical Check
        if (startPos.r() <= gridSize - segmentSize) {
            RiskCluster cluster = evaluateSegment(startPos, Direction.VERTICAL, segmentSize, minAverage);
        if (cluster != null) clusterList.add(cluster);
        }
        
        //Down-Left diaogonal check
        if (startPos.r() <= gridSize - segmentSize && startPos.c() >= segmentSize - 1) {
            RiskCluster cluster = evaluateSegment(startPos, Direction.LEFT_DIAGONAL, segmentSize, minAverage);
            if (cluster != null) clusterList.add(cluster);   
        }
        
        //Down-Right diagonal check
        if (startPos.r() <= gridSize - segmentSize && startPos.c() <= gridSize - segmentSize) {
            RiskCluster cluster = evaluateSegment(startPos, Direction.RIGHT_DIAGONAL, segmentSize, minAverage);
            if (cluster != null) clusterList.add(cluster);   
        }
    }

    private RiskCluster evaluateSegment(Coordinate startPos, Direction direction, int segmentSize, int minAverage) {
        Coordinate[] indices = new Coordinate[segmentSize];
        int[] values = new int[segmentSize];
        int sum = 0;

        int dr = direction.directionVectorR;  //i.e (1, 0) if VERTICAL
        int dc = direction.directionVectorC;

        for (int i = 0; i < segmentSize; i++) {
            int r = startPos.r() + dr * i;
            int c = startPos.c() + dc * i;
            indices[i] = new Coordinate(r, c);
            values[i] = matrix[r][c];
            sum += values[i];
        }

        float total = segmentSize;
        float average = sum / total;

        if (average >= minAverage) {
            RiskCluster cluster = new RiskCluster(average, values, indices, direction);
            return cluster;
        } else {
            return null;
        }
    }
}