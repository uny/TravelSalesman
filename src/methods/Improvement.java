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
    
    linkNode = first_;
    for (int i = 0; i < route.length; i++) {
      route[i] = linkNode.self;
      linkNode = linkNode.next;
    }
    
    return route;
  }
  
  private boolean reconnect(LinkNode a) {
    // b is after a
    LinkNode b = a.next;
    LinkNode c;
    Integer[] nearerToB = nearer_[b.self];
    // For nearer = c
    for (Integer nearIndex : nearerToB) {
      // Find c
      for (c = first_; c.self != nearIndex; c = c.next);
      // There should not be b on both-sides of c
      if (c.prev.self == b.self) {
        continue;
      }
      // d is before c
      LinkNode d = c.prev;
      if (d.self == a.self) {
        continue;
      }
      
      int ab = distances_[a.self][b.self];
      int cd = distances_[c.self][d.self];
      int ad = distances_[a.self][d.self];
      int bc = distances_[b.self][c.self];
      
      // ad + bc shorter, then reconnect
      if (ad + bc < ab + cd) {
        // Turn over the route between b and d
        // Turn over in for loop, so we use next to go back
        for (LinkNode linkNode = d; linkNode != a; linkNode = linkNode.next) {
          LinkNode tmp = linkNode.next;
          linkNode.next = linkNode.prev;
          linkNode.prev = tmp;
        }
        // a-d
        a.next = d;
        d.prev = a;
        // b-c
        b.next = c;
        c.prev = b;
        
        return true;
      } // if (ad + bc < ab + cd)
    } // for (Integer nearIndex : nearerToB)
    
    return false;
  }
}
