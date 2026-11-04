package ordenacao;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Merge Sort de inteiros.
 * Tempo O(n log n), memória extra O(n), ordenação estável.
 * Não altera o vetor recebido: devolve uma cópia ordenada.
 */
public final class MergeSort {

    private MergeSort() {
    }

    public static int[] ordenar(int[] numeros) {
        return ordenar(numeros, null);
    }

    /**
     * @param registrador recebe uma linha a cada divisão e intercalação; pode ser null.
     * @return novo vetor em ordem crescente.
     */
    public static int[] ordenar(int[] numeros, Consumer<String> registrador) {
        int[] resultado = Arrays.copyOf(numeros, numeros.length);
        if (resultado.length > 1) {
            // Um único vetor auxiliar, reaproveitado em todas as intercalações.
            int[] auxiliar = new int[resultado.length];
            dividir(resultado, auxiliar, 0, resultado.length - 1, 0, registrador);
        }
        return resultado;
    }

    /** Divide [inicio, fim] ao meio, ordena cada metade e intercala. */
    private static void dividir(int[] vetor, int[] auxiliar, int inicio, int fim,
                                int nivel, Consumer<String> registrador) {
        if (inicio >= fim) {
            return;
        }
        int meio = inicio + (fim - inicio) / 2; // evita overflow de (inicio + fim)

        registrar(registrador, nivel, "Dividir " + trecho(vetor, inicio, fim)
                + " em " + trecho(vetor, inicio, meio)
                + " e " + trecho(vetor, meio + 1, fim));

        dividir(vetor, auxiliar, inicio, meio, nivel + 1, registrador);
        dividir(vetor, auxiliar, meio + 1, fim, nivel + 1, registrador);
        intercalar(vetor, auxiliar, inicio, meio, fim);

        registrar(registrador, nivel, "Intercalar -> " + trecho(vetor, inicio, fim));
    }

    /** Junta [inicio, meio] e [meio+1, fim], que já estão ordenados. */
    private static void intercalar(int[] vetor, int[] auxiliar, int inicio, int meio, int fim) {
        for (int k = inicio; k <= fim; k++) {
            auxiliar[k] = vetor[k];
        }
        int esquerda = inicio;
        int direita = meio + 1;

        for (int k = inicio; k <= fim; k++) {
            if (esquerda > meio) {
                vetor[k] = auxiliar[direita++];
            } else if (direita > fim) {
                vetor[k] = auxiliar[esquerda++];
            } else if (auxiliar[direita] < auxiliar[esquerda]) {
                vetor[k] = auxiliar[direita++];
            } else {
                vetor[k] = auxiliar[esquerda++]; // "<" estrito mantém a estabilidade
            }
        }
    }

    private static String trecho(int[] vetor, int inicio, int fim) {
        return Arrays.toString(Arrays.copyOfRange(vetor, inicio, fim + 1));
    }

    private static void registrar(Consumer<String> registrador, int nivel, String texto) {
        if (registrador != null) {
            registrador.accept("  ".repeat(nivel) + texto);
        }
    }
}