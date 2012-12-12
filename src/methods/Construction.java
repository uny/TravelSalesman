package methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import models.ArrayIndex;
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
    
    public int[] solveByGreedy() {
        final int dimension = distances_.length;
        // Return value
        int[] route = new int[dimension];
        // To check closed circuit
        UnionFind unionFind = new UnionFind(dimension);
        
        final int allEdgeNum = (dimension * (dimension - 1)) / 2;
        ArrayIndex[] edges = new ArrayIndex[allEdgeNum];
        int edgeNumber = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = i + 1; j < dimension; j++) {
                ArrayIndex arrayIndex = new ArrayIndex();
                arrayIndex.row = i;
                arrayIndex.col = j;
                edges[edgeNumber++] = arrayIndex;
            }
        }
        
        Arrays.sort(edges, new Comparator<ArrayIndex>() {
            @Override
            public int compare(ArrayIndex o1, ArrayIndex o2) {
                return distances_[o1.row][o1.col].compareTo(distances_[o2.row][o2.col]);
            }
        });
        
        // Have two links for nodes (no-direction)
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] links = new ArrayList[dimension];
        for (int i = 0; i < links.length; i++) {
            links[i] = new ArrayList<Integer>();
        }
        
        // Start with shorter edges
        for (int edgeIndex = 0; edgeIndex < edges.length; edgeIndex++) {
            ArrayIndex curEdge = edges[edgeIndex];
            if (!unionFind.same(curEdge.row, curEdge.col)) {
                // Connect
                if (links[curEdge.row].size() < 2 && links[curEdge.col].size() < 2) {
                    links[curEdge.row].add(curEdge.col);
                    links[curEdge.col].add(curEdge.row);
                    unionFind.unite(curEdge.row, curEdge.col);
                }
            }
        }
        
        int start = 0;
        for (int i = 0; i < links.length; i++) {
            if (links[i].size() < 2) {
                start = i;
            }
        }
        
        int curNode = start;
        // Scan route
        for (int nodeIndex = 0; nodeIndex < dimension; nodeIndex++) {
            route[nodeIndex] = curNode;
            if (0 < links[curNode].size()) {
                int preNode = curNode;
                curNode = links[curNode].get(0);
                if (links[curNode].get(0) == preNode) {
                    links[curNode].remove(0);
                }
                else if (links[curNode].get(1) == preNode) {
                    links[curNode].remove(1);
                }
            }
        }
        
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
    
    // Does not allow to link closed circuit
    private void connectNearerEdge() {
        // return value 0:from -> 1:to
        int[] edge = new int[2];
    }

}
