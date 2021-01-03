package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private final Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String item: strings){
            String[] words = item.split(" ");
            for (String word: words) {
                if (word.length() > 2) {
                    this.trie.add(new Tuple(word, word.length()));
                }
            }
        }
        return 0;
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return this.trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterable<String> words = wordsWithPrefix(pref);
        int maxLen = Math.max(pref.length(), 3) + k - 1;
        ArrayList<String> finalWords = new ArrayList<>();
        for (String word: words){
            if (word.length() <= maxLen){
                finalWords.add(word);
            }
        }
        return finalWords;
    }

    public int size() {
        return this.trie.size();
    }
}
