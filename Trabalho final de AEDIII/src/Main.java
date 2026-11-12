import ordenacao.MergeSort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== Sistema de Estruturas de Dados ===");
            System.out.println("[1] Usar Árvores (B, B+ ou Trie)");
            System.out.println("[2] Usar Lista (Merge Sort e Busca Binária)");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = leitor.nextLine().trim();

            switch (opcao) {
                case "1":
                    menuArvores();
                    break;
                case "2":
                    menuListaEBusca();
                    break;
                case "0":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        System.out.println("Encerrando o programa. Até mais!");
    }

    // ===================== ROTEADOR DE ÁRVORES =====================

    private static void menuArvores() {
        System.out.println("\n--- Qual Árvore deseja usar? ---");
        System.out.println("[1] Árvore B");
        System.out.println("[2] Árvore B+");
        System.out.println("[3] Árvore Trie (Palavras)");
        System.out.println("[0] Voltar");
        System.out.print("Escolha uma opção: ");

        String opcao = leitor.nextLine().trim();
        int t;

        switch (opcao) {
            case "1":
                t = lerGrauMinimo();
                new Arvore_B.MenuInterativo(t).iniciar();
                break;
            case "2":
                t = lerGrauMinimo();
                new Arvore_B_Mais.MenuInterativo(t).iniciar();
                break;
            case "3":
                new Arvore_Trie.MenuInterativo().iniciar();
                break;
            case "0":
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ===================== FLUXO DE LISTA (ORDENAÇÃO E BUSCA) =====================

    private static void menuListaEBusca() {
        System.out.println("\n--- Lista (Merge Sort & Busca Binária) ---");
        List<Integer> numeros = lerNumerosInt("Digite os números iniciais (separados por espaço): ");
        if (numeros.isEmpty()) {
            System.out.println("Nenhum número informado. Retornando ao menu principal.");
            return;
        }

        int[] ordenado = realizarMergeSort(numeros);
        boolean operandoLista = true;

        while (operandoLista) {
            System.out.println("\n--- Opções da Lista ---");
            System.out.println("Lista atual ordenada: " + Arrays.toString(ordenado));
            System.out.println("[1] Buscar número (Busca Binária)");
            System.out.println("[2] Adicionar novos números (Reordenar)");
            System.out.println("[0] Voltar ao Menu Principal");
            System.out.print("Opção: ");

            String op = leitor.nextLine().trim();

            switch (op) {
                case "1":
                    System.out.print("Digite o número que deseja buscar: ");
                    String procurado = leitor.nextLine().trim();

                    // Converte para String[] para compatibilidade com o método da Busca_binaria
                    String[] listaConvertida = new String[ordenado.length];
                    for (int i = 0; i < ordenado.length; i++) {
                        listaConvertida[i] = String.valueOf(ordenado[i]);
                    }
                    System.out.println();
                    Busca_binaria.Busca_completa(listaConvertida, procurado, "Float");
                    break;

                case "2":
                    List<Integer> novosNumeros = lerNumerosInt("Digite os novos números a inserir (separados por espaço): ");
                    if (!novosNumeros.isEmpty()) {
                        numeros.addAll(novosNumeros);
                        System.out.println("\nReordenando a lista com os novos elementos...");
                        ordenado = realizarMergeSort(numeros);
                    }
                    break;

                case "0":
                    operandoLista = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static int[] realizarMergeSort(List<Integer> numeros) {
        int[] vetor = numeros.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("Vetor original (desordenado):  " + Arrays.toString(vetor));

        System.out.print("Mostrar passo a passo da ordenação? (s/n): ");
        boolean passoAPasso = leitor.nextLine().trim().toLowerCase().startsWith("s");

        int[] ordenado;
        if (passoAPasso) {
            System.out.println();
            ordenado = MergeSort.ordenar(vetor, System.out::println);
        } else {
            ordenado = MergeSort.ordenar(vetor);
        }
        System.out.println("Vetor final (ordenado): " + Arrays.toString(ordenado));
        return ordenado;
    }

    // ===================== AUXILIARES =====================

    private static int lerGrauMinimo() {
        while (true) {
            System.out.print("Digite o grau mínimo (t >= 2) para a árvore: ");
            try {
                int t = Integer.parseInt(leitor.nextLine().trim());
                if (t >= 2) {
                    return t;
                } else {
                    System.out.println("O grau mínimo deve ser maior ou igual a 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static List<Integer> lerNumerosInt(String mensagem) {
        System.out.print(mensagem);
        List<Integer> numeros = new ArrayList<>();
        String linha = leitor.nextLine().trim();
        if (linha.isEmpty()) return numeros;

        for (String parte : linha.split("[\\s,;]+")) {
            try {
                numeros.add(Integer.parseInt(parte));
            } catch (NumberFormatException e) {
                System.out.println("'" + parte + "' não é um inteiro válido (ignorado).");
            }
        }
        return numeros;
    }
}