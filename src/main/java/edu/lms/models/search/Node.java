package edu.lms.models.search;

import java.util.HashMap;

public class Node { //This code is nested inside the RadixTree class
    boolean isLeaf;
    HashMap<Character, Edge> edges;

    public Node(boolean isLeaf) {
        this.isLeaf = isLeaf;
        edges = new HashMap<>();
    }

    public Edge getTransition(char transitionChar) {
        return edges.get(transitionChar);
    }

    public void addEdge(String label, Node next) {
        edges.put(label.charAt(0), new Edge(label, next));
    }

    public int totalEdges() {
        return edges.size();
    }

}
