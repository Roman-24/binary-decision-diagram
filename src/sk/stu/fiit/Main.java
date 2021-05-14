package sk.stu.fiit;

public class Main {

    final int numberOfBDD = 2000;
    final int numberOfVariable = 13;
    final int bfVectorSize = (int) Math.pow(2, numberOfVariable);

    Main(){

        // --AUTO TESTER

        // diagram a atribúty testu
        BDD bdd;
        long start = 0, end = 0;
        long createTime=0, reduceTime=0, useTime=0;
        long createTimeAverage=0, reduceTimeAverage=0, useTimeAverage=0;
        double percent=0, reductionAverage=0;

        for(int j = 0; j<numberOfBDD; j++){

            // new diagram
            bdd = new BDD();

            // vytvorenie random vektoru
            String randomVector = createBF_vector();

            // vytvorenie BDD pre prislušný náhodný vektor
            start = System.currentTimeMillis();
            bdd = bdd.BDD_create(new BF(randomVector));
            end = System.currentTimeMillis();
            createTimeAverage += createTime = end - start;

            //print(bdd);

            int numberOfNodes = bdd.numberOfNodes;
            System.out.println("Počet nodes: " + numberOfNodes);

            // redukovanie
            start = System.currentTimeMillis();
            int deletedNodes = bdd.BDD_reduce(bdd);
            end = System.currentTimeMillis();
            reduceTimeAverage += reduceTime = end - start;

            // vyhodnotenie redukovania
            System.out.println("Počet vymazaných nodes: " + deletedNodes);
            reductionAverage += percent = deletedNodes / (double)numberOfNodes * 100;
            System.out.println("Zredukované v percentách: " + String.format("%.2f",percent) + "%");

            // print(bdd);

            // otestovanie všetkých možných kombinácií vstupných premenných
            boolean flag = true;
            start = System.currentTimeMillis();
            for(int k = 0; k< bfVectorSize; k++){

                String binaryNumberString = convertDecimalToBinary(k);

                binaryNumberString = makeFullbinaryStrign(binaryNumberString);

                char BDD_useResult = bdd.BDD_use(bdd, binaryNumberString);

                if(BDD_useResult != randomVector.charAt(k))
                    flag = false;

            }
            end = System.currentTimeMillis();
            useTimeAverage += useTime = end - start;

            System.out.println("Test číslo " + j + " má výsledok *" + flag + "* s časom\n" +
                    "Vytvorenie: " + createTime + "ms " + "Redukovanie: " + reduceTime + "ms " + "Overenie správnosti " + useTime + "ms \n");
        }
        System.out.println("---------------------------------------");
        System.out.println("Počet otestovaných diagramov: " + numberOfBDD + " o " + numberOfVariable + " premenných " + "s priemerným časom\n" +
                        "Vytvorenie: " + createTimeAverage/numberOfBDD + "ms\n" +
                        "Rekudovanie: " + reduceTimeAverage/numberOfBDD + "ms\n" +
                        "Overenie správnosti: " + useTimeAverage/numberOfBDD + "ms\n" +
                        "Priemerne zredukované: " + String.format("%.2f",reductionAverage/numberOfBDD) + "%");
    }

    public static void main(String[] args) {
        new Main();
    }

    String createBF_vector(){

        StringBuilder vector = new StringBuilder();

        for(int i = 0; i< bfVectorSize; i++){

            double random = Math.random();

            if(random < 0.5)
                vector.append('1');
            else
                vector.append('0');
        }

        return vector.toString();
    }

    // funkcia convertDecimalToBinary prevzatá z https://www.baeldung.com/java-binary-numbers
    String convertDecimalToBinary(int decimalNumber) {

        if (decimalNumber == 0)
            return "0";

        StringBuilder binaryNumber = new StringBuilder();
        int quotient = decimalNumber;

        while (quotient > 0) {
            int remainder = quotient % 2;
            binaryNumber.append(remainder);
            quotient /= 2;
        }

        binaryNumber.reverse();
        return binaryNumber.toString();
    }

    String makeFullbinaryStrign(String binaryNumberString){

        StringBuilder prefix = new StringBuilder();

        if(binaryNumberString.length() != bfVectorSize){

            int missingZeros = numberOfVariable - binaryNumberString.length();

            for(int i=0; i<missingZeros; i++)
                prefix.append(0);

            prefix.append(binaryNumberString);
            return prefix.toString();
        }
        return binaryNumberString;
    }



    // funkcia na grafický výpis je prevzatá z https://www.baeldung.com/java-print-binary-tree-diagram
    public void print(BDD bdd) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", bdd.root);
        System.out.println(sb.toString());
    }
    public void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.value);
            //sb.append(node.depth);
            sb.append("\n");

            String paddingForBoth = padding + "│  ";
            String pointerForRight = "└──";
            String pointerForLeft = (node.right != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.left);
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.right);
        }
    }

}