package models;

public class LinkNode {
    
    public LinkNode prev;
    public int self;
    public LinkNode next;
    
    public LinkNode() {
        
    }
    
    public LinkNode(int index) {
        this.self = index;
    }
    
    public void link(LinkNode prev, LinkNode next) {
        this.prev = prev;
        this.next = next;
    }
}
