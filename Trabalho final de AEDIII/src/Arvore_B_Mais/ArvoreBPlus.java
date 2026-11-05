package Arvore_B_Mais;

public class ArvoreBPlus {
    
    private final int grauMinimo;
    private final int maximoChaves;
    private final int minimoChaves;
    private No raiz;
 
    public ArvoreBPlus() {
        this(2);
    }
 
    public ArvoreBPlus(int grauMinimo) {
        if (grauMinimo < 2) {
            throw new IllegalArgumentException("O grau mínimo deve ser >= 2");
        }
        this.grauMinimo = grauMinimo;
        this.maximoChaves = 2 * grauMinimo - 1;
        this.minimoChaves = grauMinimo - 1;
        this.raiz = null;
    }
 
    public boolean buscar(int chave) {
        return buscarNoNo(raiz, chave);
    }
 
    private boolean buscarNoNo(No no, int chave) {
        if (no == null) {
            return false;
        }
        int indice = no.indicePrimeiraChaveMaiorOuIgual(chave);
        
        if (no.ehFolha()) {
            return indice < no.obterQuantidadeChaves() && no.obterChave(indice) == chave;
        }
        
        if (indice < no.obterQuantidadeChaves() && no.obterChave(indice) == chave) {
            indice++;
        }
        
        return buscarNoNo(no.obterFilho(indice), chave);
    }
 
    public boolean inserir(int chave) {
        if (buscar(chave)) {
            return false;
        }
        if (raiz == null) {
            raiz = new No(grauMinimo, true);
        }
        inserirNoNo(raiz, chave);
 
        if (raiz.obterQuantidadeChaves() > maximoChaves) {
            No novaRaiz = new No(grauMinimo, false);
            novaRaiz.definirFilho(0, raiz);
            quebrarFilho(novaRaiz, 0);
            raiz = novaRaiz;
        }
        return true;
    }
 
    private void inserirNoNo(No no, int chave) {
        if (no.ehFolha()) {
            int i = no.obterQuantidadeChaves() - 1;
            while (i >= 0 && chave < no.obterChave(i)) {
                no.definirChave(i + 1, no.obterChave(i));
                i--;
            }
            no.definirChave(i + 1, chave);
            no.definirQuantidadeChaves(no.obterQuantidadeChaves() + 1);
            return;
        }
 
        int indice = no.indicePrimeiraChaveMaiorOuIgual(chave);
        if (indice < no.obterQuantidadeChaves() && no.obterChave(indice) == chave) {
            indice++;
        }
        
        inserirNoNo(no.obterFilho(indice), chave);
 
        if (no.obterFilho(indice).obterQuantidadeChaves() > maximoChaves) {
            quebrarFilho(no, indice);
        }
    }
 
    private void quebrarFilho(No pai, int indice) {
        No esquerdo = pai.obterFilho(indice);
        int meio = grauMinimo - 1; 
        
        No direito = new No(grauMinimo, esquerdo.ehFolha());
        int chaveSubindo;

        if (esquerdo.ehFolha()) {
            chaveSubindo = esquerdo.obterChave(meio + 1);
            int quantidadeDireita = esquerdo.obterQuantidadeChaves() - meio - 1;
            
            for (int j = 0; j < quantidadeDireita; j++) {
                direito.definirChave(j, esquerdo.obterChave(meio + 1 + j));
            }
            direito.definirQuantidadeChaves(quantidadeDireita);
            esquerdo.definirQuantidadeChaves(meio + 1);
            
            direito.definirProximo(esquerdo.obterProximo());
            esquerdo.definirProximo(direito);
        } else {
            chaveSubindo = esquerdo.obterChave(meio);
            int quantidadeDireita = esquerdo.obterQuantidadeChaves() - meio - 1;
            
            for (int j = 0; j < quantidadeDireita; j++) {
                direito.definirChave(j, esquerdo.obterChave(meio + 1 + j));
            }
            for (int j = 0; j <= quantidadeDireita; j++) {
                direito.definirFilho(j, esquerdo.obterFilho(meio + 1 + j));
                esquerdo.definirFilho(meio + 1 + j, null);
            }
            direito.definirQuantidadeChaves(quantidadeDireita);
            esquerdo.definirQuantidadeChaves(meio);
        }
 
        for (int j = pai.obterQuantidadeChaves(); j >= indice + 1; j--) {
            pai.definirFilho(j + 1, pai.obterFilho(j));
        }
        pai.definirFilho(indice + 1, direito);
 
        for (int j = pai.obterQuantidadeChaves() - 1; j >= indice; j--) {
            pai.definirChave(j + 1, pai.obterChave(j));
        }
        pai.definirChave(indice, chaveSubindo);
        pai.definirQuantidadeChaves(pai.obterQuantidadeChaves() + 1);
    }
 
    public boolean remover(int chave) {
        if (raiz == null || !buscar(chave)) {
            return false;
        }
        removerDoNo(raiz, chave);
 
        if (raiz.obterQuantidadeChaves() == 0) {
            raiz = raiz.ehFolha() ? null : raiz.obterFilho(0);
        }
        return true;
    }
 
    private void removerDoNo(No no, int chave) {
        int indice = no.indicePrimeiraChaveMaiorOuIgual(chave);
        
        if (no.ehFolha()) {
            if (indice < no.obterQuantidadeChaves() && no.obterChave(indice) == chave) {
                removerChaveDeFolha(no, indice);
            }
            return;
        }
        
        if (indice < no.obterQuantidadeChaves() && no.obterChave(indice) == chave) {
            indice++;
        }
        
        removerDoNo(no.obterFilho(indice), chave);
    }
 
    private void removerChaveDeFolha(No folha, int indice) {
        for (int i = indice + 1; i < folha.obterQuantidadeChaves(); i++) {
            folha.definirChave(i - 1, folha.obterChave(i));
        }
        folha.definirQuantidadeChaves(folha.obterQuantidadeChaves() - 1);
    }

    public boolean estaVazia() { return raiz == null; }
    
    public void imprimir() { System.out.println(ImpressoraArvore.desenhar(raiz)); }
}
