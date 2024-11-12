/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author 0072379
 */
public class Node1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Node n1 = new Node();
        n1.id = 5;

        Node n2 = new Node();
        n2.id = 3;
        n1.filhoEsquerda = n2;

        Node n2_2 = new Node();
        n2_2.id = 1;
        n1.filhoEsquerda = n2_2;

        n2 = null;
        /* coletor de lixo do java vai eliminar o objeto id = 3 */

        n1.filhoEsquerda.filhoDireita = new Node();
        n1.filhoEsquerda.filhoDireita.id = 100;

        /*filho da esquerda do n1 e filho da direita desse filho*/
        System.out.println("n1.filhoEsquerda --> " + n1.filhoEsquerda.id);
        // System.out.println("n2 --> " + n2.id);
        System.out.println("n1.filhoEsquerda.filhoDireita --> " + n1.filhoEsquerda.filhoDireita.id);
    }
}

class Node {

    public int id;
    Node filhoEsquerda;
    Node filhoDireita;
}
