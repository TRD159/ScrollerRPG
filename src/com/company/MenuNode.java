package com.company;

import java.util.ArrayList;
import java.util.List;

public class MenuNode<T> {
    private T data;
    private List<MenuNode<T>> children;

    public MenuNode(T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<MenuNode<T>> getChildren() {
        return children;
    }

    public void addChild(T d) {
        children.add(new MenuNode<>(d));
    }
    public void removeChild(int i) {
        children.remove(i);
    }
}
