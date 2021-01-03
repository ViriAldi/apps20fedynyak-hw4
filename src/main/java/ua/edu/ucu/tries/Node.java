package ua.edu.ucu.tries;

public class Node<T> {
    public T item;
    public Node[] children;

    public Node(T item, int alphaSize){
        this.item = item;
        this.children = new Node[alphaSize];
    }
}
