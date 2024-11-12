
import java.util.LinkedList;

public class tableaHash {

    static int HASHMOD = 5;

    public static void main(String args[]) {
        LinkedList<Integer>[] hashTable = new LinkedList[HASHMOD];
        for (int i = 0;
                i < 20; i++) {
            int idR = (int) (100 * Math.random());
            insertHash(idR, hashTable);
        }

        printHash(hashTable);
    }

    public static void insertHash(int id, LinkedList<Integer>[] hashTable) {
        int hashValue = hashFunction(id);

        if (hashTable[hashValue] == null) {
            hashTable[hashValue] = new LinkedList<Integer>();
        }

        hashTable[hashValue].add(id);
    }

    public static void searchHash(int id, LinkedList<Integer>[] hashTable) {
        int hashValue = hashFunction(id);

        if (hashTable[hashValue] == null) {
            System.out.println("o elemento não se encontra");
            return;
        }

        for (Integer elemento : hashTable[hashValue]) {
            if (id == elemento) {
                System.out.println("elemento encontrado");
                return;
            }
        }
        System.out.println("o elemento não se encontra");
    }

    public static int hashFunction(int id) {
        return id % HASHMOD;
    }

    public static void printHash(LinkedList<Integer>[] hashTable) {

        int c = 0;
        for (LinkedList<Integer> list : hashTable) {
            System.out.print("[" + c + "] ");
            c++;
            if (list == null) {
                System.out.println();
                continue;
            }
            for (Integer i : list) {
                System.out.print(i + "->");
            }
            System.out.println();
        }
    }
}
