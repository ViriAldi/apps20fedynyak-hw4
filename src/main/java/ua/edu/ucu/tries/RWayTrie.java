package ua.edu.ucu.tries;

import java.util.ArrayList;
import java.util.Collection;

public class RWayTrie implements Trie {
    private final Node<Tuple> root;
    private static final int ALPHA_SIZE = 26;
    private static final int FIRST_CHAR = 'a';
    private int size = 0;

    public RWayTrie(){
        this.root = new Node<Tuple>(new Tuple("", 0), ALPHA_SIZE);
    }

    @Override
    public void add(Tuple t) {
        Node<Tuple> lastNode = move(t.term, true);
        if (lastNode.item.weight == 0){
            lastNode.item.weight = t.term.length();
            size++;
        }
    }

    @Override
    public boolean contains(String word) {
        Node<Tuple> lastNode = move(word, false);
        return (lastNode != null && lastNode.item.weight != 0);
    }

    @Override
    public boolean delete(String word) {
        Node<Tuple> lastNode = move(word, false);
        boolean need2del = lastNode != null;
        if (need2del){
            lastNode.item.weight = 0;
            size--;
        }
        return need2del;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Node<Tuple> lastNode = move(s, false);
        if (lastNode == null){
            return new ArrayList<>();
        }
        String prefix;
        if (s.equals("")){
            prefix = " ";
        }
        else{
            prefix = s.substring(0, s.length() - 1);
        }
        return bfs(lastNode, prefix);
    }

    private Iterable<String> bfs (Node<Tuple> vertex, String prefix){
        ArrayList<String> result = new ArrayList<>();
        if (vertex.item.weight != 0){
            result.add(prefix + vertex.item.term);
        }
        for (int idx = 0; idx < ALPHA_SIZE; idx++){
            if (vertex.children[idx] == null){
                continue;
            }
            String newPrefix = prefix + vertex.item.term;
            Iterable<String> words = bfs(vertex.children[idx], newPrefix);
            result.addAll((Collection<? extends String>) words);
            }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    private Node<Tuple> move(String s, boolean fill){
        Node<Tuple> curr = root;
        for (int idx = 0; idx < s.length(); idx++){
            int char_idx = s.charAt(idx) - FIRST_CHAR;
            if (curr.children[char_idx] == null){
                if (!fill){
                    return null;
                }
                curr.children[char_idx] = new Node<>(new Tuple(s.substring(idx, idx+1), 0), ALPHA_SIZE);
            }
            curr = curr.children[char_idx];
        }
        return curr;
    }

}
