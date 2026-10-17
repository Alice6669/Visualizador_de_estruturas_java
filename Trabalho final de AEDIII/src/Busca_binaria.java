// Classe que contém a busca binária.
import java.util.ArrayList;
import java.util.List;

public class Busca_binaria {
    // Método interno para fazer parte da busca.
    private static List<Integer> Busca_parcial_float(int inicio, int fim, int meio, String[] Lista,
                                              float procurado){
        List<Integer> resultado = new ArrayList<>();
        resultado.add(inicio);
        resultado.add(meio);
        resultado.add(fim);
        if (Float.valueOf(Lista[meio]) < procurado){
            inicio = meio;
        } else if (Float.valueOf(Lista[meio]) > procurado) {
            fim = meio;
        }
        else {
            return(resultado);
        }
        meio = ((fim - inicio) / 2) + inicio;
        resultado.set(0, inicio);
        resultado.set(1, meio);
        resultado.set(2, fim);
        return(resultado);
    }

    private static List<Integer> Busca_parcial_String(int inicio, int fim, int meio, String[] Lista,
                                               String procurado){
        List<Integer> resultado = new ArrayList<>();
        resultado.add(inicio);
        resultado.add(meio);
        resultado.add(fim);
        if (procurado.compareTo(Lista[meio]) > 0){
            inicio = meio;
        } else if (procurado.compareTo(Lista[meio]) < 0) {
            fim = meio;
        }
        else {
            return(resultado);
        }
        meio = ((fim - inicio) / 2) + inicio;
        resultado.set(0, inicio);
        resultado.set(1, meio);
        resultado.set(2, fim);
        return(resultado);
    }

    // Método para usar a busca binaria em números.
    public static void Busca_completa(String[] Lista, String procurado, String Tipo_lista){
        int inicio = 0;
        int fim = Lista.length - 1;
        int meio = fim / 2;
        int Indice_passo = 1;
        int Indice_limite = 0;
        Busca_binaria_vizualizar(Lista, meio, procurado, Indice_passo);

        while (true){
            Indice_passo++;
            List<Integer> indices = new ArrayList<>();
            if (Tipo_lista.equalsIgnoreCase("Float")){
                List<Integer> indicesTemporario = Busca_parcial_float(inicio, fim, meio, Lista,
                        Float.valueOf(procurado));
                indices = indicesTemporario;
            } else {
                List<Integer> indicesTemporario = Busca_parcial_String( inicio,  fim,  meio,
                        Lista, procurado);
                indices = indicesTemporario;
            }
            inicio = indices.get(0);
            meio = indices.get(1);
            fim = indices.get(2);
            Busca_binaria_vizualizar(Lista, meio, procurado, Indice_passo);
            if (procurado.equals(String.valueOf(Lista[meio]))){
                System.out.println("\nEncontrei " + procurado + " no indice " + meio + " no " +
                        Indice_passo + "º passo.");
                break;
            }
            if (meio == inicio || meio == fim){
                Indice_limite++;
                if(Indice_limite > 1){
                    System.out.println("\nNão encontrei " + procurado +" foram feitos " +
                            Indice_passo + " passos.");
                    break;
                }
            }
        }
    }

    // Método para ver a lista.
    private static void Busca_binaria_vizualizar(String[] Lista, int meio,
                                          String procurado, int Indice_passo){
        System.out.println("Procurado: " + procurado + "\nPasso: " + Indice_passo);
        System.out.print("[");
        for(int i = 0; i <= Lista.length - 1; i++){
            if(i == meio){
                System.out.print("<" + Lista[meio] + ">, ");
                continue;
            }
            System.out.print(", " + Lista[i]);
        }
        System.out.println("]\n");
    }
}
