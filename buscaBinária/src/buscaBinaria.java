
/**
 *
 * @author 0072379
 */
public class buscaBinaria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int[] vetor = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24};
        int fim = vetor.length - 1;
        buscaBinaria(vetor, 0, fim, 22);
    }

    public static void buscaBinaria(int[] vetor, int inicio, int fim, int busca) {
        int iMeio = (inicio + fim) / 2;
        if (vetor[iMeio] == busca) {
            System.out.println("Encontrado na posição: " + iMeio);
            return;
        }
        if (inicio == fim) {
            System.out.println("O elemento não se encontra no vetor.");
            return;
        }
        if (busca > vetor[iMeio]) { //vai para a direita
            buscaBinaria(vetor, iMeio + 1, fim, busca);
        } else { //vai para a esquerda
            buscaBinaria(vetor, inicio, iMeio - 1, busca);
        }
    }

    class Node {

        int id;
        Node pai;
        Node filhoEsquerda;
        Node filhoDireita;

    }

}
