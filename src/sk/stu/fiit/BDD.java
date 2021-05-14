package sk.stu.fiit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BDD {

    int inputSize;
    int numberOfNodes;
    int maxDepth;
    Node root;

    BDD(String vector) {
        this.inputSize = vector.length();
        this.root = new Node(vector);
        this.numberOfNodes = 1;
    }

    BDD() {

    }

    // Funkcia vráti ľavú polku stringu
    private String bfunkciaLeftHalf(String string) {
        final int midOfString = string.length() / 2;
        return string.substring(0, midOfString);
    }

    // Funkcia vráti pravú polku stringu
    private String bfunkciaRightHalf(String string) {
        final int midOfString = string.length() / 2;
        return string.substring(midOfString);
    }

    //funkcia vytvorí strom zložený zo substringov vektoru
    private void insertSubVectors(Node node, String value, BDD root, int depth) {

        depth++; // hĺbka sa zvyšuje lebo vznikajú nové nodes

        if (node.left == null && value.length() != 1) {

            String bfunkciaLeftHalfPiece = bfunkciaLeftHalf(value);

            node.left = new Node(bfunkciaLeftHalfPiece);
            node.left.parent = node; // vytvorenie pointru na rodiča
            root.numberOfNodes++;

            insertSubVectors(node.left, bfunkciaLeftHalfPiece, root, depth);
        }

        if (node.right == null && value.length() != 1) {

            String bfunkciaRightHalfPiece = bfunkciaRightHalf(value);

            node.right = new Node(bfunkciaRightHalfPiece);
            node.right.parent = node; // vytvorenie pointru na rodiča
            root.numberOfNodes++;

            insertSubVectors(node.right, bfunkciaRightHalfPiece, root, depth);
        }

        node.depth = depth;

        if (depth > root.maxDepth) // zisťovanie najväčšej hĺbky v strome
            root.maxDepth = depth;

    }

    // funkcia vytvorí strom
    BDD BDD_create(BF bfunkcia) {

        BDD theDiagram = new BDD(bfunkcia.bfValue);

        // rekúrzívna tvorba stromu
        insertSubVectors(theDiagram.root, bfunkcia.bfValue, theDiagram, -1);

        return theDiagram;
    }

    // funkcia vráti zoznam všetkých nodes v danej hĺbke
    private ArrayList<Node> getNodesForDepth(Node root, int depth) {

        ArrayList<Node> nodesList = new ArrayList<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            Node node = queue.poll();

            if (node.depth == depth) // ak je node v požadovanej hĺbke je pridaný do zoznamu
                nodesList.add(node);

            // Zaraďovanie do poradia
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);

        }

        return nodesList;
    }

    // úlohou je zredukovať počet NODEs v strome (vymazať duplikované nodes)
    int BDD_reduce(BDD bdd) {

        int deletedNodes = 0;

        if (bdd.root == null)
            return 0;

        // ako prvá sa vezme vrstva s listami
        ArrayList<Node> temp = getNodesForDepth(bdd.root, bdd.maxDepth);

        // najskôr sa zredukujú všetky listy
        // tak aby bol iba jeden node pre 1 a jeden node pre 0
        Node one = null, zero = null;
        for (Node i : temp) {

            if (zero == null && i.value.equals("0")) {
                zero = i;
                continue;
            }
            if (one == null && i.value.equals("1")) {
                one = i;
                continue;
            }

            if (i.value.equals("0")) {
                i.parent = zero;
                deletedNodes++;
            }
            if (i.value.equals("1")) {
                i.parent = one;
                deletedNodes++;
            }
        }

        for (int j = bdd.maxDepth - 1; j > 0; j--) { // prechádzajú sa všetky hĺbky

            temp = getNodesForDepth(bdd.root, j); // zoznam nodes pre danú hĺbku

            // dvojtý loop porovnáva všetky nodes
            // ak sa nájde duplikát tak sa presmerujú pointre
            for (int indexPatter = 0; indexPatter < temp.size(); indexPatter++) {

                Node nodePatter = temp.get(indexPatter);

                for (int indexCopy = indexPatter + 1; indexCopy < temp.size(); indexCopy++) {

                    Node nodeCopy = temp.get(indexCopy);

                    if (nodePatter != nodeCopy) { // objekty nesmú byť zhodné

                        if (nodePatter.value.equals(nodeCopy.value)) { // ak majú nezhodné objekty rovnaké values

                            nodeCopy.parent = nodePatter; // presmerovanie pointrov

                            temp.remove(nodeCopy); // vymaže sa zredukovaný node zo zoznamu pre danu hĺbku
                            deletedNodes++;
                        }
                    }
                }
            }
        }

        return deletedNodes;
    }

    // prejde BDD podľa zadaného vstupu
    char BDD_use(BDD bdd, String vstupy) {

        Node node = bdd.root;

        for (int i = 0; i < vstupy.length(); i++) {

            if (node.depth == i) { // kontrola či sa rozhoduje pre správnu premennú

                if (vstupy.charAt(i) == '0')
                    if (node.left != null)
                        node = node.left;

                if (vstupy.charAt(i) == '1')
                    if (node.right != null)
                        node = node.right;
            }
        }

        if (node.value.equals("0"))
            return '0';

        else if (node.value.equals("1"))
            return '1';

        else return '-';

    }

}