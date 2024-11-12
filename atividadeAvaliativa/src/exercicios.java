/*
Código: Árvore Binária - v1
Disciplina: AEDII - Sistemas de Informação, IFMG Ouro Branco
 */
public class exercicios {

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.raiz = new Node(10, null);
        insert(tree.raiz, 15);
        insert(tree.raiz, 20);
        insert(tree.raiz, 12);

        insert(tree.raiz, 5);
        insert(tree.raiz, 2);
        insert(tree.raiz, 6);
        //delete(tree, 20);
        //buscaPrint(10, tree.raiz);
        //buscaPrint(15, tree.raiz);
        //trocaNode(15, 18, tree.raiz);
        //encontra2(12, tree.raiz);
        //buscaApaga(2, tree.raiz);
        //buscaPrint(5, tree.raiz);
        buscaImprime(10, tree.raiz);
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

    //exercício 1
    public static void trocaNode(int id, int valorNovo, Node node) {
        Node x = buscaNode(id, node);
        System.out.println("Node procurado: " + x.id);

        if (x.filhoDireita == null && valorNovo > x.filhoEsquerda.id) {

            x.id = valorNovo;
            System.out.println("Node novo: " + x.id);
        } else if (x.filhoEsquerda == null && valorNovo < x.filhoDireita.id) {
            x.id = valorNovo;
            System.out.println("Node novo: " + x.id);
        } else if (x.filhoDireita != null && x.filhoEsquerda != null && valorNovo < x.filhoDireita.id && valorNovo > x.filhoEsquerda.id) {
            x.id = valorNovo;
            System.out.println("Node novo: " + x.id);
        } else {
            System.out.println("Valor não pode ser trocado!");
        }

    }

    //exercício 2
    public static void encontra2(int idBusca, Node node) {
        int x = 0;
        int y = 0;
        Node a = buscaNode(idBusca, node);
        System.out.println("Número analisado: " + a.id);

        System.out.println("Números encontrados: ");

        if (a.filhoDireita == null) {
            System.out.println("É um nó folha! O maior número é seu pai: " + a.pai.id + "\nE o filho da direita de seu pai: " + a.pai.filhoDireita.id);
            return;
        }
        if (a.filhoDireita.id > idBusca) {
            y = a.filhoDireita.id;
            System.out.println(y);
        }

        if (a.pai.id > idBusca) {
            x = a.pai.id;
            System.out.println(x);
        }

    }

    //exercício 3
    public static void buscaApaga(int idBusca, Node node) {
        Node a = buscaNode(idBusca, node);
        System.out.println("Número analisado: " + a.id);

        if (a.filhoDireita == null && a.filhoEsquerda == null) {
            System.out.println("É um nó folha!Número apagado!");
            a.pai.filhoEsquerda = null;
            System.out.println(a.pai.filhoEsquerda);
            return;
        }
    }

    //exercício 4
    public static void buscaImprime(int idBusca, Node node) {
        Node a = buscaNode(idBusca, node);
        System.out.println("Número buscado: " + a.id);

        if (a.filhoDireita == null && a.filhoEsquerda == null) {
            System.out.println("É um nó folha!");
            System.out.println(a.filhoDireita.id + " " + a.filhoEsquerda.id);
            return;
        } else {
            System.out.println("Nós folhas: " + a.filhoDireita.filhoDireita.id + " " +a.filhoDireita.filhoEsquerda.id+ " " +a.filhoEsquerda.filhoDireita.id + " " + a.filhoEsquerda.filhoEsquerda.id);
        }
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
