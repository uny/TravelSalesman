package models;

public class UnionFind {
    
    private int[] parent;
    private int[] rank;
    
    public UnionFind() {
        // TODO Auto-generated constructor stub
    }
    
    public UnionFind(int numNodes) {
        parent = new int[numNodes];
        rank = new int[numNodes];
        
        for (int i = 0; i < numNodes; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }
    
    public void unite(int node_i, int node_j) {
        node_i = findParent(node_i);
        node_j = findParent(node_j);
        
        if (node_i == node_j) {
            return;
        }
        
        if (rank[node_i] < rank[node_j]) {
            parent[node_i] = node_j;
        }
        else {
            parent[node_j] = node_i;
            if (rank[node_i] == rank[node_j]) {
                rank[node_i]++;
            }
        }
    }
    
    public boolean same(int node_i, int node_j) {
        return findParent(node_i) == findParent(node_j);
    }
    
    private int findParent(int node_i) {
        if (parent[node_i] == node_i) {
            return node_i;
        }
        else {
            return parent[node_i] = findParent(parent[node_i]);
        }
    }
}
