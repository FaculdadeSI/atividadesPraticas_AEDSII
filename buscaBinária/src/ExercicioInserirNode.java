
/**
 *
 * @author 0072379
 */
public class ExercicioInserirNode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Node raiz = new Node(10,null);
        inserirChave(raiz, 2);
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

    public static void inserirChave(Node node, int inserir) {

        if (node == null) {
            Node novoNode = new Node(inserir, null);
            return;
        }
        if (inserir < node.id) {
            if (node.filhoEsquerda == null) {
                node.filhoEsquerda = new Node(inserir, node);
            } else {
                inserirChave(node.filhoEsquerda, inserir);
            }
            return;
        } else if (inserir > node.id) {
            if (node.filhoDireita == null) {
                node.filhoDireita = new Node(inserir, node);
            } else {
                inserirChave(node.filhoDireita, inserir);
            }

        }
    }

}

class Node {

    public int id;
    public Node pai;
    public Node filhoDireita;
    public Node filhoEsquerda;

    public Node(int id, Node pai) {
        this.id = id;
        this.pai = pai;
    }

}
