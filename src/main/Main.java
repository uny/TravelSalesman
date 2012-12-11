package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import methods.Construction;

public class Main {
public static void main(String[] args) {
    
    final String[] filenames = {"eil51.tsp", "kroC100.tsp", "ts225.tsp"};
    
    for (String filename : filenames) {
        System.out.println(">" + filename);
        
        // Position of cities
        Integer[][] positions = getPositionsFromFile(filename);
        // Distances between cities
        Integer[][] distances = calcDistancesBetweenCities(positions);
        
        Construction construction = new Construction(distances);
        construction.solveByNearestNeighbor();
        
    } // for (String filename : filenames)
}

private static Integer[][] getPositionsFromFile(String filename) {
    // Dimension of TSP
    int dimension = 0;
    // Position of cities
    Integer[][] positions = null;
    
    try {
        // Get info from a file
        File file = new File("resources/" + filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            // Get dimension
            if (line.startsWith("DIMENSION")) {
                dimension = Integer.parseInt(line.split(":")[1].trim());
                positions = new Integer[dimension][2];
            }
            else {
                // ex: 1 500 500
                Pattern pattern = Pattern.compile("\\d+\\s+\\d+\\s+\\d+");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    String[] positionStrings = line.split("\\s+");
                    int cityIndex = Integer.parseInt(positionStrings[0].trim()) - 1;
                    int cityX = Integer.parseInt(positionStrings[1].trim());
                    int cityY = Integer.parseInt(positionStrings[2].trim());

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

private static Integer[][] calcDistancesBetweenCities(Integer[][] positions) {
    final int dimension = positions.length;
    // Distances between cities
    Integer[][] distances = new Integer[dimension][dimension];
    
    for (int i = 0; i < dimension; i++) {
//        System.out.println(i);
        for (int j = i; j < dimension; j++) {
//            System.out.println(j);
            int square = (positions[i][0] - positions[j][0]) * (positions[i][0] - positions[j][0])
                    + (positions[i][1] - positions[j][1]) * (positions[i][1] - positions[j][1]);
            distances[i][j] = (int) Math.round(Math.sqrt(square));
        }
    }
    
    // Lower triangle
    for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < i; j++) {
            distances[i][j] = distances[j][i];
        }
    }
    
    return distances;
}
}