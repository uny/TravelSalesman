package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
public static void main(String[] args) {
    
    final String[] filenames = {"eil51.tsp", "kroC100.tsp", "ts225.tsp"};
    
    for (String filename : filenames) {
        System.out.println(">" + filename);
        
        // Position of cities
        int[][] positions = getPositionsFromFile(filename);
        // Distances between cities
        int[][] distances = calcDistancesBetweenCities(positions);
        
        
    } // for (String filename : filenames)
}

private static int[][] getPositionsFromFile(String filename) {
    // Dimension of TSP
    int dimension = 0;
    // Position of cities
    int[][] positions = null;
    
    try {
        // Get info from a file
        File file = new File("resources/" + filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            // Get dimension
            if (line.startsWith("DIMENSION")) {
                dimension = Integer.parseInt(line.split(":")[1].trim());
                positions = new int[dimension][2];
            }
            else {
                // ex: 1 500 500
                Pattern pattern = Pattern.compile("\\d+ \\d+ \\d+");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String[] positionStrings = line.split(" ");
                    int cityIndex = Integer.parseInt(positionStrings[0]);
                    int cityX = Integer.parseInt(positionStrings[1]);
                    int cityY = Integer.parseInt(positionStrings[2]);

                    positions[cityIndex][0] = cityX;
                    positions[cityIndex][1] = cityY;
                }
            }
        }
        bufferedReader.close();
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (NumberFormatException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
    return positions;
}

private static int[][] calcDistancesBetweenCities(int[][] positions) {
    final int dimension = positions.length;
    // Distances between cities
    int[][] distances = new int[dimension][dimension];
    
    for (int i = 0; i < dimension; i++) {
        for (int j = i; j < dimension; j++) {
            int square = (positions[i][0] - positions[j][0]) * (positions[i][0] - positions[j][0])
                    + (positions[i][1] - positions[j][1]) * (positions[i][1] - positions[j][1]);
            distances[i][j] = (int) Math.round(Math.sqrt(square));
        }
    }
    
    return distances;
}
}