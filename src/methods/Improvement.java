package methods;

import java.util.Arrays;
import java.util.Comparator;

import models.LinkNode;

public class Improvement {
  private LinkNode first_;
  private Integer[][] distances_;
  private Integer[][] nearer_;
  private Integer[] indexesForSort_;

  public Improvement() {
    // TODO Auto-generated constructor stub
  }

  public Improvement(int[] route, Integer[][] distances) {
    final int dimension = route.length;
    
    // Make node links
    first_ = new LinkNode(route[0]);
    LinkNode linkNode = first_;
    for (int nodeIndex = 1; nodeIndex < dimension; nodeIndex++) {
      LinkNode next = new LinkNode(route[nodeIndex]);
      linkNode.next = next;
      LinkNode prev = linkNode;
      linkNode = next;
      linkNode.prev = prev;
    }
    linkNode.next = first_;
    first_.prev = linkNode;
    
    distances_ = distances;
    indexesForSort_ = new Integer[dimension];
    for (int i = 0; i < dimension; i++) {
      indexesForSort_[i] = i;
    }
    
    // Make neaer array
    nearer_ = new Integer[dimension][dimension];
    for (int nodeIndex = 0; nodeIndex < dimension; nodeIndex++) {
      final int fixedIndex = nodeIndex;
      // Deep copy for sort
      Integer[] indexes = Arrays.copyOf(indexesForSort_, indexesForSort_.length);
      // Sort in ascending order with distance
      Arrays.sort(indexes, new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
          return distances_[fixedIndex][o1].compareTo(distances_[fixedIndex][o2]);
        }
      });
      nearer_[fixedIndex] = indexes;
    }
  }
  
  public int[] improveByLinKernighan() {
    final int dimension = distances_.length;
    // Return value
    int[] route = new int[dimension];
    
    // Count when not connect
    int cnt = 0;
    LinkNode linkNode = first_;
    while (cnt < dimension) {
      if (reconnect(linkNode)) {
        cnt = 0;
      }
      else {
        cnt++;
      }
      linkNode = linkNode.next;
    }
    
    return route;
  }
  
  private boolean reconnect(LinkNode a) {
    // Return value
    boolean reconnected = false;
    
    LinkNode b = a.next;
    LinkNode c;
    Integer[] nearerToB = nearer_[b.self];
    // For nearer
    for (Integer nearIndex : nearerToB) {
      // Find C
      for (c = first_; c.self == nearIndex; c = c.next);
      // There should not be b on both-sides of c
      if (c.prev.self == b.self) {
        continue;
      }
      LinkNode d = c.prev;
    }
    
    return reconnected;
  }
  
  private boolean checkCircuit(LinkNode d) {
    // Return value
    boolean isClosed = false;
    
    LinkNode linkNode = first_;
    for (int nodeIndex = 0; nodeIndex < distances_.length; nodeIndex++) {
      
    }
    
    return isClosed;
  }
}
