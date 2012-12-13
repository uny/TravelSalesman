package models;

public class LinkNode {
  
  public LinkNode prev;
  public LinkNode next;
  public int self;

  public LinkNode() {
  }
  
  public LinkNode(int self) {
    this.self = self;
  }
  
  public void link(int prev, int next) {
    
  }
}
