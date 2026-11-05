package Arvore_Trie;

import java.util.List;
import java.util.Scanner;

public class MenuInterativo {
    private Trie trie;
    private final Scanner leitor;

    public MenuInterativo() {
        this.trie = new Trie();
        this.leitor = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== Árvore Trie (Formato Hierárquico) ===");
        mostrarArvore();

        boolean executando = true;
        while (executando) {
            exibirMenu();
            if (!leitor.hasNextLine()) break;
            String opcao = leitor.nextLine().trim();

            switch (opcao) {
                case "1": inserirPalavras(); break;
                case "2": removerPalavras(); break;
                case "3": buscarPalavras(); break;
                case "4": mostrarArvore(); break;
                case "5": autoCompletar(); break;
                case "6": listarTodas(); break;
                case "7":
                    trie = new Trie();
                    System.out.println("Árvore esvaziada.");
                    mostrarArvore();
                    break;
                case "0": executando = false; break;
                default: System.out.println("Opção inválida.");
            }
        }
        System.out.println("Encerrando. Até mais!");
    }

    private void inserirPalavras() {
        System.out.print("Digite as palavras a inserir (separadas por espaço): ");
        String[] palavras = lerLinhaETratar();
        for (String p : palavras) {
            if (trie.inserir(p)) {
                System.out.println(">>> Inserido: " + p);
            } else {
                System.out.println(">>> '" + p + "' já existe na árvore.");
            }
        }
        mostrarArvore();
    }

    private void removerPalavras() {
        System.out.print("Digite as palavras a remover (separadas por espaço): ");
        String[] palavras = lerLinhaETratar();
        for (String p : palavras) {
            if (trie.remover(p)) {
                System.out.println(">>> Removido: " + p);
            } else {
                System.out.println(">>> '" + p + "' não existe na árvore.");
            }
        }
        mostrarArvore();
    }

    private void buscarPalavras() {
        System.out.print("Digite as palavras a buscar (separadas por espaço): ");
        String[] palavras = lerLinhaETratar();
        for (String p : palavras) {
            boolean exata = trie.buscar(p);
            boolean prefixo = trie.comecaCom(p);
            
            System.out.print(p + " -> ");
            if (exata) {
                System.out.println("Encontrada (Palavra completa)");
            } else if (prefixo) {
                System.out.println("Não encontrada como palavra, mas EXISTE como prefixo!");
            } else {
                System.out.println("Não encontrada");
            }
        }
    }

    private void mostrarArvore() {
        System.out.println("\n--- Estado atual da árvore ---");
        System.out.println("(* = raiz, [$] = fim de palavra)");
        trie.imprimirVisualmente();
        System.out.println("-------------------------------");
    }

    private void autoCompletar() {
        System.out.print("Digite um prefixo para buscar: ");
        String prefixo = leitor.nextLine().trim();
        List<String> resultados = trie.autoCompletar(prefixo);
        if (resultados.isEmpty()) {
            System.out.println(">>> Nenhuma palavra encontrada com esse prefixo.");
        } else {
            System.out.println(">>> Resultados encontrados (" + resultados.size() + "):");
            System.out.println(String.join(", ", resultados));
        }
    }

    private void listarTodas() {
        List<String> resultados = trie.listarTodas();
        if (resultados.isEmpty()) {
            System.out.println(">>> A árvore está vazia.");
        } else {
            System.out.println(">>> Palavras na árvore (" + resultados.size() + "):");
            System.out.println(String.join(", ", resultados));
        }
    }

    private void exibirMenu() {
        System.out.println("\n[1] Inserir  [2] Remover  [3] Buscar  [4] Mostrar Árvore");
        System.out.println("[5] Autocompletar (Prefixo)  [6] Listar Todas  [7] Limpar  [0] Sair");
        System.out.print("Opção: ");
    }

    private String[] lerLinhaETratar() {
        if (!leitor.hasNextLine()) return new String[0];
        String linha = leitor.nextLine().trim();
        if (linha.isEmpty()) return new String[0];
        return linha.split("\\s+");
    }
}
