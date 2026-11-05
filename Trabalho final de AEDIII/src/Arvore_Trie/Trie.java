package Arvore_Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {
    private final NoTrie raiz;

    public Trie() {
        this.raiz = new NoTrie();
    }

    public boolean inserir(String palavra) {
        if (palavra == null || palavra.isEmpty()) return false;
        NoTrie atual = raiz;
        for (char ch : palavra.toCharArray()) {
            atual.filhos.putIfAbsent(ch, new NoTrie());
            atual = atual.filhos.get(ch);
        }
        if (atual.fimDePalavra) {
            return false;
        }
        atual.fimDePalavra = true;
        return true;
    }

    public boolean buscar(String palavra) {
        NoTrie no = buscarNo(palavra);
        return no != null && no.fimDePalavra;
    }

    public boolean comecaCom(String prefixo) {
        return buscarNo(prefixo) != null;
    }

    private NoTrie buscarNo(String palavra) {
        if (palavra == null || palavra.isEmpty()) return null;
        NoTrie atual = raiz;
        for (char ch : palavra.toCharArray()) {
            NoTrie no = atual.filhos.get(ch);
            if (no == null) {
                return null;
            }
            atual = no;
        }
        return atual;
    }

    public boolean remover(String palavra) {
        if (palavra == null || palavra.isEmpty()) return false;
        return removerRecursivo(raiz, palavra, 0);
    }

    private boolean removerRecursivo(NoTrie atual, String palavra, int indice) {
        if (indice == palavra.length()) {
            if (!atual.fimDePalavra) {
                return false; 
            }
            atual.fimDePalavra = false;
            return atual.filhos.isEmpty(); 
        }
        char ch = palavra.charAt(indice);
        NoTrie no = atual.filhos.get(ch);
        if (no == null) {
            return false;
        }
        
        boolean deveDeletarNoAtual = removerRecursivo(no, palavra, indice + 1);

        if (deveDeletarNoAtual) {
            atual.filhos.remove(ch);
            return atual.filhos.isEmpty() && !atual.fimDePalavra;
        }
        return false;
    }

    public List<String> listarTodas() {
        return autoCompletar("");
    }

    public List<String> autoCompletar(String prefixo) {
        List<String> resultados = new ArrayList<>();
        NoTrie atual = raiz;
        
        if (prefixo != null && !prefixo.isEmpty()) {
            for (char ch : prefixo.toCharArray()) {
                NoTrie no = atual.filhos.get(ch);
                if (no == null) return resultados; 
                atual = no;
            }
        } else {
            prefixo = "";
        }
        
        buscarPalavrasAbaixo(atual, new StringBuilder(prefixo), resultados);
        return resultados;
    }

    private void buscarPalavrasAbaixo(NoTrie no, StringBuilder prefixoAtual, List<String> resultados) {
        if (no.fimDePalavra) {
            resultados.add(prefixoAtual.toString());
        }
        for (Map.Entry<Character, NoTrie> entrada : no.filhos.entrySet()) {
            prefixoAtual.append(entrada.getKey());
            buscarPalavrasAbaixo(entrada.getValue(), prefixoAtual, resultados);
            prefixoAtual.deleteCharAt(prefixoAtual.length() - 1);
        }
    }

    public void imprimirVisualmente() {
        System.out.println(ImpressoraTrie.desenhar(raiz)); 
    }
}
