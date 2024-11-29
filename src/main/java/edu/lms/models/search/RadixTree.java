package edu.lms.models.search;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class RadixTree {
    static class Node {
        String key;
        boolean isWord;
        Map<Character, Node> children;

        Node(String key) {
            this.key = key;
            this.isWord = false;
            this.children = new HashMap<>();
        }
    }

    private final Node root;

    public RadixTree() {
        root = new Node("");
    }

    // Insert a word into the Radix Tree
    public void insert(String word) {
        Node current = root;
        while (!word.isEmpty()) {
            boolean found = false;

            for (Map.Entry<Character, Node> entry : current.children.entrySet()) {
                Node child = entry.getValue();
                String commonPrefix = getCommonPrefix(child.key, word);

                if (!commonPrefix.isEmpty()) {
                    found = true;

                    if (commonPrefix.length() < child.key.length()) {
                        // Split the node
                        Node newChild = new Node(child.key.substring(commonPrefix.length()));
                        newChild.children.putAll(child.children);
                        newChild.isWord = child.isWord;

                        child.key = commonPrefix;
                        child.children.clear();
                        child.children.put(newChild.key.charAt(0), newChild);
                        child.isWord = false;
                    }

                    word = word.substring(commonPrefix.length());
                    current = child;
                    break;
                }
            }

            if (!found) {
                Node newNode = new Node(word);
                current.children.put(word.charAt(0), newNode);
                current = newNode;
                word = "";
            }
        }
        current.isWord = true;
    }

    //get common prefix between two strings
    private String getCommonPrefix(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                prefix.append(str1.charAt(i));
            } else {
                break;
            }
        }
        return prefix.toString();
    }

    // Suggest words based on the prefix string
    public List<String> suggestWords(String prefix) {
        Node current = root;
        List<String> suggestions = new ArrayList<>();

        while (!prefix.isEmpty()) {
            boolean found = false;
            for (Map.Entry<Character, Node> entry : current.children.entrySet()) {
                Node child = entry.getValue();
                if (prefix.startsWith(child.key)) {
                    prefix = prefix.substring(child.key.length());
                    current = child;
                    found = true;
                    break;
                }
            }
            if (!found) {
                return suggestions;
            }
        }

        collectWords(current, new StringBuilder(prefix), suggestions);
        return suggestions;
    }

    // collect all words from a given node
    private void collectWords(Node node, StringBuilder prefix, List<String> suggestions) {
        if (node.isWord) {
            suggestions.add(prefix.toString());
        }

        for (Node child : node.children.values()) {
            collectWords(child, new StringBuilder(prefix).append(child.key), suggestions);
        }
    }

    //print the words in the tree
    public void printWords() {
        printWordsHelper(root, new StringBuilder());
    }

    private void printWordsHelper(Node node, StringBuilder currentWord) {
        currentWord.append(node.key);
        if (node.isWord) {
            System.out.println(currentWord.toString());
        }
        for (Node child : node.children.values()) {
            printWordsHelper(child, new StringBuilder(currentWord));
        }
    }

    public static void main(String[] args) {
        RadixTree tree = new RadixTree();

        // Insert words into the Radix Tree
        tree.insert("cat");
        tree.insert("car");
        tree.insert("dog");
        tree.insert("dot");
        tree.insert("dove");
        tree.insert("do");
        tree.insert("done");

        // Example of word suggestion based on prefix
        String prefix = "do";
        System.out.println("Suggestions for prefix '" + prefix + "':");
        List<String> suggestions = tree.suggestWords(prefix);
        for (String word : suggestions) {
            System.out.println(word);
        }

        // Suggest for another prefix
        prefix = "ca";
        System.out.println("Suggestions for prefix '" + prefix + "':");
        suggestions = tree.suggestWords(prefix);
        for (String word : suggestions) {
            System.out.println(word);
        }

        prefix = "d";
        System.out.println("Suggestions for prefix '" + prefix + "':");
        suggestions = tree.suggestWords(prefix);
        for (String word : suggestions) {
            System.out.println(word);
        }
    }
}

