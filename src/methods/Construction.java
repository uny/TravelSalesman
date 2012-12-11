package methods;

import java.util.Arrays;
import java.util.Comparator;

import models.UnionFind;

public class Construction {
    private Integer[][] distances_;

    public Construction() {
        // TODO Auto-generated constructor stub
    }

    public Construction(Integer[][] distances) {
        distances_ = distances;
    }

    public int[] solveByNearestNeighbor() {
        final int dimension = distances_.length;
        // Return value
        int[] route = new int[dimension];
        // To check closed circuit
        UnionFind unionFind = new UnionFind(dimension);
        
        // Start with shortest edge
        int[] start = findNearestEdge();
        route[0] = start[0];
        route[1] = start[1];
        unionFind.unite(start[0], start[1]);
        
        Integer[] indexesForSort = new Integer[dimension];
        for (int i = 0; i < dimension; i++) {
            indexesForSort[i] = i;
        }
        
        // Nearest Neighbor
        for (int route_i = 1; route_i < dimension; route_i++) {
            final int cur_i = route[route_i];
            
            // Deep copy for sort
            Integer[] indexes = Arrays.copyOf(indexesForSort, indexesForSort.length);
            Arrays.sort(indexes, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return distances_[cur_i][o1].compareTo(distances_[cur_i][o2]);
                }
            });
            
            // Except oneself
            for (int near_i = 1; near_i < indexes.length; near_i++) {
                if (!unionFind.same(cur_i, indexes[near_i])) {
                    route[route_i + 1] = indexes[near_i];
                    unionFind.unite(cur_i, indexes[near_i]);
                    break;
                }
            }
        }
        
        return route;
    }

    private int[] findNearestEdge() {
        // return value 0:from -> 1:to
        int[] edge = new int[2];
        // tmp value for searching minimum distance
        int min_distance = Integer.MAX_VALUE;  

        for (int i = 0; i < distances_.length; i++) {
            for (int j = 0; j < distances_[i].length; j++) {
                if (i != j) {
                    if (distances_[i][j] < min_distance) {
                        min_distance = distances_[i][j];
                        edge[0] = i;
                        edge[1] = j;
                    }
                }
            }
        }

        return edge;
    }

}
