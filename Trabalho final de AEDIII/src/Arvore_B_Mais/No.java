package Arvore_B_Mais;

class No {
    private final int[] chaves;
    private final No[] filhos;
    private int quantidadeChaves;
    private final boolean folha;
    private No proximo;
 
    No(int grauMinimo, boolean folha) {
        this.chaves = new int[2 * grauMinimo];
        this.filhos = new No[2 * grauMinimo + 1];
        this.quantidadeChaves = 0;
        this.folha = folha;
        this.proximo = null;
    }
 
    int obterChave(int indice) { return chaves[indice]; }
    void definirChave(int indice, int valor) { chaves[indice] = valor; }
 
    No obterFilho(int indice) { return filhos[indice]; }
    void definirFilho(int indice, No filho) { filhos[indice] = filho; }
 
    int obterQuantidadeChaves() { return quantidadeChaves; }
    void definirQuantidadeChaves(int quantidade) { this.quantidadeChaves = quantidade; }
 
    boolean ehFolha() { return folha; }
    
    No obterProximo() { return proximo; }
    void definirProximo(No proximo) { this.proximo = proximo; }
 
    int indicePrimeiraChaveMaiorOuIgual(int valor) {
        int indice = 0;
        while (indice < quantidadeChaves && chaves[indice] < valor) {
            indice++;
        }
        return indice;
    }
}
