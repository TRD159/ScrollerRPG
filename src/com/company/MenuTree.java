package com.company;

public class MenuTree<T> {
    MenuNode<T> root;

    public MenuTree(T data) {
        root = new MenuNode<>(data);
    }

    public MenuTree() {
        root = null;
    }
}
