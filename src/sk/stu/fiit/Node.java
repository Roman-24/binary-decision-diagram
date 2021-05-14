package sk.stu.fiit;

public class Node {
    int depth = 0;
    String value;
    Node left, right;
    Node parent = null;
    Node(String value){
        this.value = value;
        left = right = null;
    }
}
