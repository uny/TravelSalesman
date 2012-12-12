package models;

public class LinkNode {
    
    public int self;
    public LinkNode next;
    
    public LinkNode() {
        
    }
    
    public LinkNode(int index) {
        this.self = index;
    }
    
    public void link(LinkNode next) {
        this.next = next;
    }
}
