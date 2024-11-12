
/**
 *
 * @author 0072379
 */
public class rotacoes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tree tree = new Tree();
        tree.raiz = new Node(10, null);
        insert(tree.raiz, 20);
        insert(tree.raiz, 15);
        //delete(tree, 20);
        //buscaPrint(10, tree.raiz);
        //buscaPrint(15, tree.raiz);
        //System.out.println("Soma: " + somaArvore(tree.raiz));
        System.out.println("Média: " + mediaArvore(tree.raiz));
    }

//inserir elemento na árvore
    public static void insert(Node node, int insertId) {
        if (node == null) {//raiz vazia
            Node novoNode = new Node(insertId, null);
            return;
        }
        if (insertId < node.id) {
            if (node.filhoEsquerda == null) {
                node.filhoEsquerda = new Node(insertId, node);
            } else {
                insert(node.filhoEsquerda, insertId);
            }
            return;
        } else if (insertId > node.id) {
            if (node.filhoDireita == null) {
                node.filhoDireita = new Node(insertId, node);
            } else {
                insert(node.filhoDireita, insertId);
            }
            return;
        }
    }
//deletar elemento da árvore

    public static void delete(Tree tree, int idDelete) {
        Node z = buscaNode(idDelete, tree.raiz);
        if (z.filhoDireita == null && z.filhoEsquerda == null) {
            if (z.pai == null) {//raiz deve ser eliminada
                tree.raiz = null;
                return;
            }
            if (z.id < z.pai.id) {
                z.pai.filhoEsquerda = null;
            } else {
                z.pai.filhoDireita = null;
            }
        }
//se um dos filhos for nulo basta substituir
        if (z.filhoDireita == null) {
            if (z.pai == null) {//raiz
                tree.raiz = z.filhoEsquerda;
                return;
            }
            if (z.id < z.pai.id) {
                z.pai.filhoEsquerda = z.filhoEsquerda;
            } else {
                z.pai.filhoDireita = z.filhoEsquerda;
            }
            /*
Outra forma:
z.id = z.filhoEsquerda.id;
z.filhoEsquerda = z.filhoEsquerda.filhoEsquerda;
z.filhoDireita = z.filhoEsquerda.filhoDireita;
             */
            return;
        } else if (z.filhoEsquerda == null) {
            if (z.pai == null) {//raiz
                tree.raiz = z.filhoDireita;
                return;
            }
            if (z.id < z.pai.id) {
                z.pai.filhoEsquerda = z.filhoDireita;
            } else {
                z.pai.filhoDireita = z.filhoDireita;
            }
//não basta fazer z = z.filhoEsquerda
/*z.id = z.filhoDireita.id;
z.filhoEsquerda = z.filhoDireita.filhoEsquerda;
z.filhoDireita = z.filhoDireita.filhoDireita;*/
            return;
        }
        Node y = busca_menor(z.filhoDireita);
        if (y == null) {
            return;
        }
        System.out.println("y--->" + y.id);
//atualiza possível filho direito de y
        if (y.filhoDireita != null) {
            y.pai.filhoEsquerda = y.filhoDireita;
            y.filhoDireita.pai = y.pai;
        } else {
            y.pai.filhoEsquerda = null;
        }
//--------
        z.id = y.id;
    }
//obter raiz de um elemento da árvore

    public static Node getRaiz(Node node) {
        if (node.pai == null) {
            return node;
        } else {
            return getRaiz(node.pai);
        }
    }
//busca menor elemento a partir de um nó (sub-árvore)

    public static Node busca_menor(Node n) {
        if (n == null) {
            return null;
        }
        if (n.filhoEsquerda == null) {
            return n;
        } else {
            return busca_menor(n.filhoEsquerda);
        }
    }
//busca um nó pelo id

    public static Node buscaNode(int idBusca, Node node) {
        if (node == null) {
            return null;
        }
        if (idBusca == node.id) {
            return node;
        }
        if (idBusca < node.id) {
            return buscaNode(idBusca, node.filhoEsquerda);
        } else {
            return buscaNode(idBusca, node.filhoDireita);
        }
    }
//busca um elemento pelo id para fazer sua impressão

    public static void buscaPrint(int idBusca, Node node) {
        if (node == null) {
            System.out.println("Nó não se encontra na árvore!");
            return;
        }
        if (idBusca == node.id) {
            System.out.println(node.id);
            if (node.filhoEsquerda != null) {
                System.out.println("Filho esquerda: " + node.filhoEsquerda.id);
            } else {
                System.out.println("Filho esquerda: null");
            }
            if (node.filhoDireita != null) {
                System.out.println("Filho direita: " + node.filhoDireita.id);
            } else {
                System.out.println("Filho direita: null");
            }
            return;
        }
        if (idBusca < node.id) {
            buscaPrint(idBusca, node.filhoEsquerda);
        } else {
            buscaPrint(idBusca, node.filhoDireita);
        }
    }

    //Rotações para balencear a árvore
//Método Auxiliar igual ao insert já visto, porém insere o Node pronto
    public static void insert(Node node, Node nodeInsert) {
        if (nodeInsert == null) {
            return;
        }
        if (node == null) {//raiz vazia
            Node novoNode = nodeInsert;
            return;
        }

        if (nodeInsert.id < node.id) {
            if (node.filhoEsquerda == null) {
                node.filhoEsquerda = nodeInsert;
                nodeInsert.pai = node;
                return;
            } else {
                insert(node.filhoEsquerda, nodeInsert);
            }
            return;
        } else if (nodeInsert.id > node.id) {
            if (node.filhoDireita == null) {
                node.filhoDireita = nodeInsert;
                nodeInsert.pai = node;
                return;
            } else {
                insert(node.filhoDireita, nodeInsert);
            }
            return;
        }
    }

    public static void rotacaoRR(Node node) {

        //separa os filhos para inserir depois
        Node i1 = node.filhoEsquerda;
        node.filhoEsquerda = null;
        Node i2 = node.filhoDireita.filhoEsquerda;
        node.filhoDireita.filhoEsquerda = null;
        Node i3 = node.filhoDireita.filhoDireita.filhoEsquerda;
        node.filhoDireita.filhoDireita.filhoEsquerda = null;
        Node i4 = node.filhoDireita.filhoDireita.filhoDireita;
        node.filhoDireita.filhoDireita.filhoDireita = null;

        //Faz a rotação:
        Node central = node.filhoDireita;
        node.filhoDireita = null;
        //pega o que estava no topo e coloca como filho do novo central
        central.filhoEsquerda = node;
        if (node.pai != null) {
            central.pai = node.pai;
            if (central.id < central.pai.id) {
                central.pai.filhoEsquerda = central;
            } else {
                central.pai.filhoDireita = central;
            }
        }
        node.pai = central;

        //insere os nós filhos novamente:
        insert(central, i1);
        insert(central, i2);
        insert(central, i3);
        insert(central, i4);

    }

    public static void rotacaoLL(Node node) {

        //separa os filhos para inserir depois
        Node i1 = node.filhoDireita;
        node.filhoDireita = null;
        Node i2 = node.filhoEsquerda.filhoDireita;
        node.filhoEsquerda.filhoDireita = null;
        Node i3 = node.filhoEsquerda.filhoEsquerda.filhoDireita;
        node.filhoEsquerda.filhoEsquerda.filhoDireita = null;
        Node i4 = node.filhoEsquerda.filhoEsquerda.filhoEsquerda;
        node.filhoEsquerda.filhoEsquerda.filhoEsquerda = null;

        //Faz a rotação:
        Node central = node.filhoEsquerda;
        node.filhoEsquerda = null;
        //pega o que estava no topo e coloca como filho do novo central
        central.filhoDireita = node;
        if (node.pai != null) {
            central.pai = node.pai;
            if (central.id < central.pai.id) {
                central.pai.filhoDireita = central;
            } else {
                central.pai.filhoEsquerda = central;
            }
        }
        node.pai = central;

        //insere os nós filhos novamente:
        insert(central, i1);
        insert(central, i2);
        insert(central, i3);
        insert(central, i4);
    }

    public static void rotacaoRL(Node node) {
        //separa os filhos para inserir depois
        Node i1 = node.filhoEsquerda;
        node.filhoEsquerda = null;
        Node i2 = node.filhoDireita.filhoDireita;
        node.filhoDireita.filhoDireita = null;
        Node i3 = node.filhoDireita.filhoEsquerda.filhoEsquerda;
        node.filhoDireita.filhoEsquerda.filhoEsquerda = null;
        Node i4 = node.filhoDireita.filhoEsquerda.filhoDireita;
        node.filhoDireita.filhoEsquerda.filhoDireita = null;

        //Faz a rotação:
        Node central = node.filhoDireita.filhoEsquerda;
        central.filhoDireita.filhoEsquerda = null;
        central.pai = node.pai;
        central.filhoDireita = node.filhoDireita;
        node.filhoDireita = null;       
        central.filhoDireita.pai = central;

        //pega o que estava no topo e coloca como filho do novo central
        central.filhoEsquerda = node;

        if (node.pai != null) {
            central.pai = node.pai;
            if (central.id < central.pai.id) {
                central.pai.filhoEsquerda = central;
            } else {
                central.pai.filhoDireita = central;
            }
        }
        node.pai = central;

        //insere os nós filhos novamente:
        insert(central, i1);
        insert(central, i2);
        insert(central, i3);
        insert(central, i4);

    }

    //para iniciar balaceamento
    public static int getFatorBalanceamento(Node node) {
        if (node == null) {
            return 0;
        }

        return getAltura(node.filhoEsquerda) - getAltura(node.filhoDireita);
    }

    public static int getAltura(Node node) {
        if (node == null) {
            return -1;//pq não conta a raiz
        }
        int alturaEsquerda = getAltura(node.filhoEsquerda);
        int alturaDireita = getAltura(node.filhoDireita);
        if (alturaEsquerda > alturaDireita) {
            return 1 + alturaEsquerda;
        } else {
            return 1 + alturaDireita;
        }

    }

    public static int somaArvore(Node node) {
        if (node == null) {
            return 0;
        }
        int somaEsquerda = somaArvore(node.filhoEsquerda);
        int somaDireita = somaArvore(node.filhoDireita);

        return node.id + somaEsquerda + somaDireita;

    }

    public static int qntdNodes(Node node) {
        if (node == null) {
            return 0;
        }
        int qntdEsquerda = qntdNodes(node.filhoEsquerda);
        int qntdDireita = qntdNodes(node.filhoDireita);
        return 1 + qntdEsquerda + qntdDireita;
    }

    public static double mediaArvore(Node node) {
        return somaArvore(node) / qntdNodes(node);
    }
}

class Tree {

    public Node raiz;
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
