package com.company;

import java.util.ArrayList;
import java.util.List;

public class MenuSelect<T> {
    T data;
    List<MenuSelect<T>> children;

    public MenuSelect(T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public void addChild(T d) {
        children.add(new MenuSelect<T>(d));
    }
}
