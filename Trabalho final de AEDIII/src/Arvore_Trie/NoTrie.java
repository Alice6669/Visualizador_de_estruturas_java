package Arvore_Trie;

import java.util.TreeMap;
import java.util.Map;

class NoTrie {
    Map<Character, NoTrie> filhos;
    boolean fimDePalavra;

    NoTrie() {
        this.filhos = new TreeMap<>();
        this.fimDePalavra = false;
    }
}
