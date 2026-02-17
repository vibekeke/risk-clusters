import java.util.ArrayList;

public class Main {
    public static void main (String[] args) {      
        String fileName = "data/riskgrid.txt";
        int segmentSize = 4;
        int minAverage = 70;

        if (args.length >= 1) {
            fileName = args[0];
        } 

        try {
            if (args.length >= 2) {
                segmentSize = Integer.parseInt(args[1]);
            }
            if (args.length >= 3) {
                minAverage = Integer.parseInt(args[2]);
            }
        } catch (
            NumberFormatException e) {
                throw new IllegalArgumentException("segmentSize and minAverage must be integers", e);
        }

        //verifying inputs
        if (args.length > 3) {
            throw new IllegalArgumentException("Too many arguments, expected 3 or less");
        }
        if (segmentSize < 2) {
            throw new IllegalArgumentException("SegmentSize must be >= 2, got " + segmentSize);
        }
        
        if (minAverage <= 0) {
            throw new IllegalArgumentException("minAverage must be > 0, got " + minAverage);
        }

        
        RiskMatrix riskGrid = new RiskMatrix(fileName);
        ArrayList<RiskCluster> identified = riskGrid.findSignificantRiskClusters(segmentSize, minAverage);
        if (identified.isEmpty()) {
            System.out.printf("No significant clusters found given the parameters, segmentSize = %d, minAverage = %d", segmentSize, minAverage);
        } else {
            for (RiskCluster cluster: identified) {
                System.out.println(cluster.infoString());
            }
        }
    }
}