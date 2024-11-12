//implementar a busca de uma chave na árvora binária!

/**
 *
 * @author 0072379
 */
public class ExercicioBuscaBinaria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Node raiz = new Node();
        raiz.id = 10;
        raiz.filhoEsquerda = new Node();
        raiz.filhoEsquerda.id = 5;
        raiz.filhoDireita = new Node();
        raiz.filhoDireita.id = 15;
        raiz.filhoEsquerda.filhoEsquerda = new Node();
        raiz.filhoEsquerda.filhoEsquerda.id = 3;
        raiz.filhoDireita.filhoDireita = new Node();
        raiz.filhoDireita.filhoDireita.id = 7;
        raiz.filhoEsquerda.filhoEsquerda.filhoEsquerda = new Node();
        raiz.filhoEsquerda.filhoEsquerda.filhoEsquerda.id = 13;
        raiz.filhoDireita.filhoDireita.filhoDireita = new Node();
        raiz.filhoDireita.filhoDireita.filhoDireita.id = 18;
        buscaChave(raiz, 17);
    }

    public static void buscaChave(Node node, int busca) {
        if (node == null) {
            System.out.println("Elemento não se encontra na árvore!");
            return;
        }
        if (node.id == busca) {
            System.out.println("Elemento encontrado!");
            return;
        }
        if (busca < node.id) {
            System.out.println("Indo para direita");
            buscaChave(node.filhoEsquerda, busca);
        } else {
            System.out.println("Indo para esquerda");
            buscaChave(node.filhoDireita, busca);
        }
    }

}

class Node {

    public int id;
    public Node filhoDireita;
    public Node filhoEsquerda;

}
