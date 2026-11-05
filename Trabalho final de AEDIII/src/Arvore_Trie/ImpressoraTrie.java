package Arvore_Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class ImpressoraTrie {
    private static final int ESPACO_MINIMO = 2;
 
    private ImpressoraTrie() {}
 
    private static class Bloco {
        final List<String> linhas;
        final int largura;
        final int centro; 
 
        Bloco(List<String> linhas, int largura, int centro) {
            this.linhas = linhas;
            this.largura = largura;
            this.centro = centro;
        }
    }
 
    static String desenhar(NoTrie raiz) {
        if (raiz == null) {
            return "(árvore vazia)";
        }
        
        Bloco bloco = desenharNo(raiz, '*');
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < bloco.linhas.size(); i++) {
            if (i > 0) resultado.append('\n');
            resultado.append(bloco.linhas.get(i).replaceAll("\\s+$", ""));
        }
        
        return resultado.toString();
    }
 
    private static Bloco desenharNo(NoTrie no, char charDoNo) {
        String rotulo = no.fimDePalavra ? "[" + charDoNo + "$]" : "[" + charDoNo + "]";
        int larguraRotulo = rotulo.length();
 
        if (no.filhos.isEmpty()) {
            List<String> linhas = new ArrayList<>();
            linhas.add(rotulo);
            return new Bloco(linhas, larguraRotulo, larguraRotulo / 2);
        }
 
        int quantidadeFilhos = no.filhos.size();
        Bloco[] filhos = new Bloco[quantidadeFilhos];
        int somaLarguras = 0;
        int alturaFilhos = 0;
        
        int idx = 0;
        for (Map.Entry<Character, NoTrie> entrada : no.filhos.entrySet()) {
            filhos[idx] = desenharNo(entrada.getValue(), entrada.getKey());
            somaLarguras += filhos[idx].largura;
            alturaFilhos = Math.max(alturaFilhos, filhos[idx].linhas.size());
            idx++;
        }
 
        int espaco = ESPACO_MINIMO;
        int[] deslocamentos = new int[quantidadeFilhos];
        int[] centros = new int[quantidadeFilhos];
        int inicioRotulo = 0;
        
        int largura = 0;
        int primeiro = 0;
        int ultimo = 0;

        if (quantidadeFilhos == 1) {
            deslocamentos[0] = 0;
            centros[0] = filhos[0].centro;
            inicioRotulo = centros[0] - larguraRotulo / 2;
            if (inicioRotulo < 0) {
                deslocamentos[0] = -inicioRotulo;
                centros[0] += -inicioRotulo;
                inicioRotulo = 0;
            }
            largura = Math.max(filhos[0].largura + deslocamentos[0], inicioRotulo + larguraRotulo);
            primeiro = centros[0];
            ultimo = centros[0];
        } else {
            while (true) {
                int posicao = 0;
                for (int i = 0; i < quantidadeFilhos; i++) {
                    deslocamentos[i] = posicao;
                    centros[i] = posicao + filhos[i].centro;
                    posicao += filhos[i].largura + espaco;
                }
                int primeiroCentro = centros[0];
                int ultimoCentro = centros[quantidadeFilhos - 1];
                inicioRotulo = (primeiroCentro + ultimoCentro) / 2 - larguraRotulo / 2;
                if (primeiroCentro <= inicioRotulo - 2 && ultimoCentro >= inicioRotulo + larguraRotulo + 1) {
                    break;
                }
                espaco++;
            }
            largura = somaLarguras + espaco * (quantidadeFilhos - 1);
            primeiro = centros[0];
            ultimo = centros[quantidadeFilhos - 1];
        }
 
        int fimRotulo = inicioRotulo + larguraRotulo; 
 
        char[] linhaRotulo = linhaEmBranco(largura);
        rotulo.getChars(0, larguraRotulo, linhaRotulo, inicioRotulo);
 
        char[] ligacao1 = linhaEmBranco(largura);
        char[] ligacao2 = linhaEmBranco(largura);
        
        if (quantidadeFilhos == 1) {
            ligacao1[primeiro] = '|';
            ligacao2[primeiro] = '|';
        } else {
            Arrays.fill(ligacao1, primeiro + 1, ultimo, '_');
            Arrays.fill(ligacao1, Math.max(0, inicioRotulo - 1), Math.min(largura, fimRotulo + 1), ' ');
            if (inicioRotulo - 1 >= 0) ligacao1[inicioRotulo - 1] = '/';
            if (fimRotulo < largura) ligacao1[fimRotulo] = '\\';
     
            ligacao2[primeiro] = '/';
            ligacao2[ultimo] = '\\';
            for (int i = 1; i < quantidadeFilhos - 1; i++) {
                ligacao2[centros[i]] = '|';
                if (centros[i] > inicioRotulo - 1 && centros[i] < fimRotulo) {
                    ligacao1[centros[i]] = '|';
                }
            }
        }
 
        List<String> linhas = new ArrayList<>();
        linhas.add(new String(linhaRotulo));
        linhas.add(new String(ligacao1));
        linhas.add(new String(ligacao2));
        for (int r = 0; r < alturaFilhos; r++) {
            char[] linha = linhaEmBranco(largura);
            for (int i = 0; i < quantidadeFilhos; i++) {
                if (r < filhos[i].linhas.size()) {
                    String texto = filhos[i].linhas.get(r);
                    texto.getChars(0, texto.length(), linha, deslocamentos[i]);
                }
            }
            linhas.add(new String(linha));
        }
        return new Bloco(linhas, largura, (primeiro + ultimo) / 2);
    }
 
    private static char[] linhaEmBranco(int largura) {
        char[] linha = new char[largura];
        Arrays.fill(linha, ' ');
        return linha;
    }
}
