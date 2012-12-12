package methods;

import java.util.Arrays;
import java.util.Comparator;

import models.LinkNode;
import models.UnionFind;

public class Construction {
    private Integer[][] distances_;
    private Integer[] indexesForSort_;

    public Construction() {
        // TODO Auto-generated constructor stub
    }

    public Construction(Integer[][] distances) {
        distances_ = distances;
        final int dimension = distances_.length;
        indexesForSort_ = new Integer[dimension];
        for (int i = 0; i < dimension; i++) {
            indexesForSort_[i] = i;
        }
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

        // Nearest Neighbor
        for (int routeIndex = 1; routeIndex < dimension; routeIndex++) {
            final int curIndex = route[routeIndex];

            // Deep copy for sort
            Integer[] indexes = Arrays.copyOf(indexesForSort_, indexesForSort_.length);
            // Sort in ascending order with distance
            Arrays.sort(indexes, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return distances_[curIndex][o1].compareTo(distances_[curIndex][o2]);
                }
            });

            // Except oneself
            for (int nearIndex = 1; nearIndex < indexes.length; nearIndex++) {
                if (!unionFind.same(curIndex, indexes[nearIndex])) {
                    route[routeIndex + 1] = indexes[nearIndex];
                    unionFind.unite(curIndex, indexes[nearIndex]);
                    break;
                }
            }
        }

        return route;
    }

    public int[] solveByNearestInsertion() {
        final int dimension = distances_.length;
        // Return value
        int[] route = new int[dimension];
        // To check closed circuit
        UnionFind unionFind = new UnionFind(dimension);

        // Start with shortest edge
        int[] start = findNearestEdge();
        LinkNode first  = new LinkNode(start[0]);
        LinkNode second = new LinkNode(start[1]);
        // Loop with two
        first.link(second, second);
        second.link(first, first);
        unionFind.unite(start[0], start[1]);
        
        
        
        return route;
    }

    private int[] findNearestEdge() {
        // return value 0:from -> 1:to
        int[] edge = new int[2];
        // tmp value for searching minimum distance
        int minDistance = Integer.MAX_VALUE;  

        for (int i = 0; i < distances_.length; i++) {
            for (int j = 0; j < distances_[i].length; j++) {
                if (i != j) {
                    if (distances_[i][j] < minDistance) {
                        minDistance = distances_[i][j];
                        edge[0] = i;
                        edge[1] = j;
                    }
                }
            }
        }

        return edge;
    }

    private int findNearestNode(LinkNode first, UnionFind unionFind) {
        final int dimension = distances_.length;
        // Return value
        int nearestNodeIndex = 0;

        for (int linked = 1; linked < dimension; linked++) {
            for (int i = 0; i < array.length; i++) {
                
            }
        }

        return nearestNodeIndex;
    }
    
    private int findNearestNode(final int nodeIndex, UnionFind unionFind) {
        final int dimension = distances_.length;
        // Return value
        int nearestNodeIndex = 0;
        
        // Deep copy for sort
        Integer[] indexes = Arrays.copyOf(indexesForSort_, indexesForSort_.length);
        // Sort in ascending order with distance
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return distances_[nodeIndex][o1].compareTo(distances_[nodeIndex][o2]);
            }
        });
        
        // Except oneself
        for (int nearIndex = 1; nearIndex < indexes.length; nearIndex++) {
            if (!unionFind.same(nodeIndex, indexes[nearIndex])) {
                nearestNodeIndex = indexes[nearIndex];
                break;
            }
        }
        
        return nearestNodeIndex;
    }

}
