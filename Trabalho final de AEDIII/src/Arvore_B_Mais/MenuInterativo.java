package Arvore_B_Mais;
import ordenacao.MergeSort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuInterativo {
    private final int grauMinimo;
    private ArvoreBPlus arvore;
    private final List<Integer> numerosInseridos;
    private final Scanner leitor;

    public MenuInterativo(int grauMinimo) {
        this.grauMinimo = grauMinimo;
        this.arvore = new ArvoreBPlus(grauMinimo);
        this.numerosInseridos = new ArrayList<>();
        this.leitor = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== Árvore B+ (grau mínimo t = " + grauMinimo + ") ===");
        mostrarArvore();

        boolean executando = true;
        while (executando) {
            exibirMenu();
            if (!leitor.hasNextLine()) {
                break;
            }
            String opcao = leitor.nextLine().trim();

            switch (opcao) {
                case "1": inserirNumeros(); break;
                case "2": removerNumeros(); break;
                case "3": buscarNumeros(); break;
                case "4": mostrarArvore(); break;
                case "5":
                    arvore = new ArvoreBPlus(grauMinimo);
                    numerosInseridos.clear();
                    System.out.println("Árvore esvaziada.");
                    mostrarArvore();
                    break;
                case "6": ordenarNumerosDaArvore(); break;
                case "7": ordenarNumerosAvulsos(); break;
                case "0": executando = false; break;
                default: System.out.println("Opção inválida.");
            }
        }
        System.out.println("Encerrando. Até mais!");
    }

    private void inserirNumeros() {
        List<Integer> numeros = lerNumeros("Digite os números a inserir (separados por espaço): ");
        for (int numero : numeros) {
            if (arvore.inserir(numero)) {
                numerosInseridos.add(numero);
                System.out.println("\n>>> Inserido: " + numero);
            } else {
                System.out.println("\n>>> " + numero + " já existe na árvore (ignorado).");
            }
            mostrarArvore();
        }
    }

    private void removerNumeros() {
        List<Integer> numeros = lerNumeros("Digite os números a remover (separados por espaço): ");
        for (int numero : numeros) {
            if (arvore.remover(numero)) {
                numerosInseridos.remove(Integer.valueOf(numero));
                System.out.println("\n>>> Removido: " + numero);
            } else {
                System.out.println("\n>>> " + numero + " não existe na árvore.");
            }
            mostrarArvore();
        }
    }

    private void buscarNumeros() {
        List<Integer> numeros = lerNumeros("Digite os números a buscar (separados por espaço): ");
        for (int numero : numeros) {
            System.out.println(numero + (arvore.buscar(numero) ? " -> encontrado" : " -> não encontrado"));
        }
    }

    private void ordenarNumerosDaArvore() {
        if (numerosInseridos.isEmpty()) {
            System.out.println("A árvore está vazia, não há números para ordenar.");
            return;
        }
        executarMergeSort(numerosInseridos);
    }

    private void ordenarNumerosAvulsos() {
        List<Integer> numeros = lerNumeros("Digite os números a ordenar (separados por espaço): ");
        if (numeros.isEmpty()) {
            System.out.println("Nenhum número informado.");
            return;
        }
        executarMergeSort(numeros);
    }

    private void executarMergeSort(List<Integer> numeros) {
        int[] vetor = numeros.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("\nAntes:  " + Arrays.toString(vetor));

        int[] ordenado;
        if (perguntarSimOuNao("Mostrar passo a passo? (s/n): ")) {
            System.out.println();
            ordenado = MergeSort.ordenar(vetor, System.out::println);
        } else {
            ordenado = MergeSort.ordenar(vetor);
        }
        System.out.println("\nDepois: " + Arrays.toString(ordenado));
    }

    private void mostrarArvore() {
        System.out.println("\n--- Estado atual da árvore ---");
        arvore.imprimir();
        System.out.println("------------------------------");
    }

    private void exibirMenu() {
        System.out.println("\n[1] Inserir  [2] Remover  [3] Buscar  [4] Mostrar  [5] Limpar");
        System.out.println("[6] Merge Sort nos números da árvore  [7] Merge Sort em números avulsos  [0] Sair");
        System.out.print("Opção: ");
    }

    private boolean perguntarSimOuNao(String mensagem) {
        System.out.print(mensagem);
        if (!leitor.hasNextLine()) return false;
        return leitor.nextLine().trim().toLowerCase().startsWith("s");
    }

    private List<Integer> lerNumeros(String mensagem) {
        System.out.print(mensagem);
        List<Integer> numeros = new ArrayList<>();
        if (!leitor.hasNextLine()) return numeros;
        String linha = leitor.nextLine().trim();
        if (linha.isEmpty()) return numeros;
        for (String parte : linha.split("[\\s,;]+")) {
            try {
                numeros.add(Integer.parseInt(parte));
            } catch (NumberFormatException e) {
                System.out.println("'" + parte + "' não é um número inteiro válido (ignorado).");
            }
        }
        return numeros;
    }
}
