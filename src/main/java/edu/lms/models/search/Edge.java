package edu.lms.models.search;

public class Edge { //This code is nested inside the RadixTree class
    String label;
    Node next;

    public Edge(String label) {
        this(label, new Node(true));
    }

    public Edge(String label, Node next) {
        this.label = label;
        this.next = next;
    }
}
