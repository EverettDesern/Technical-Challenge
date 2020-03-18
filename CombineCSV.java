import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CombineCSV {

    public static void main(String[] args) {

        String bandwidth = "/Users/edesern/Downloads/bandwidth.csv";
        String netbitrate = "/Users/edesern/Downloads/netbitrate.csv";
        String rowBandwith = "";
        String rowNetBitRate = "";

        // hashmap to map (server number + network interface) with (bandwidth)
        HashMap<String, Float> map = new HashMap<>();

        // this try catch block reads the first CSV file, "Bandwidth.csv", until the end of the file.
        try(BufferedReader readBand = new BufferedReader(new FileReader(bandwidth))) {
            readBand.readLine();
            while((rowBandwith = readBand.readLine()) != null) {

                String[] lineBand = rowBandwith.split(",");
                String serverNumber = lineBand[0];
                String ethNumber = lineBand[1];
                float band = convertToFloat(lineBand[2]);
                // combining the server and eth as my hashmap key.
                String combine = serverNumber + ethNumber;

                if(!map.containsKey(combine)) {
                    map.put(combine, band);
                }
            }
            readBand.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        // this try catch block reads the second CSV file, "Netbitrate.csv", until the end of the file.
        try(BufferedReader readNet = new BufferedReader(new FileReader(netbitrate))) {
            readNet.readLine();
            while((rowNetBitRate = readNet.readLine()) != null) {

                String[] line = rowNetBitRate.split(",");
                String serverNumber = line[1];
                String ethNumber = line[2];
                float bitRate = convertToFloat(line[3]);
                String combine = serverNumber + ethNumber;

                // if the hashmap contains the serverNumber + ethNumber that we just read in the CSV file,
                // we need to extract the bandwidth value so we can divide the bitrate by the bandwidth.
                if(map.containsKey(combine)) {
                    Float val = map.get(combine);
                    Float result = bitRate/val;
                    
                    // print the results.
                    System.out.print(line[0] + " ");
                    System.out.print(serverNumber + " ");
                    System.out.print(ethNumber + " ");
                    System.out.println(result);
                }

            }
            readNet.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    // method to convert a string into a float.
    public static float convertToFloat(String s) throws NumberFormatException{
        float result = Float.parseFloat(s);
        return result;
    }

}
