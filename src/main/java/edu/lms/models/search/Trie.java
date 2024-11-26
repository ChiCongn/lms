package edu.lms.models.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Trie {
    private static TrieNode root;

    private Trie() {}

    public static void initialize() {
        root = new TrieNode();
    }

    // Insert a word into the Trie
    public static void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    // Search for a word in the Trie
    public static boolean search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isEndOfWord;
    }

    // Search for a prefix in the Trie
    public static boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return true;
    }

    // Find all words with a given prefix
    public static void autocomplete(List<String> guesses, String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                System.out.println("No words found with prefix: " + prefix);
                return;
            }
        }
        collectWords(guesses, current, prefix);
    }

    public static List<String> autocomplete(String prefix) {
        TrieNode current = root;
        List<String> guesses = new ArrayList<>();
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                System.out.println("No words found with prefix: " + prefix);
                return null;
            }
        }
        collectWords(guesses, current, prefix);
        return guesses;
    }

    // Helper method to collect all words starting from the given node
    private static void collectWords(List<String> guesses, TrieNode node, String prefix) {
        if (node.isEndOfWord) {
            //System.out.println(prefix);
            guesses.add(prefix);
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectWords(guesses, entry.getValue(), prefix + entry.getKey());
        }
    }

}

