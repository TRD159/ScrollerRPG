package com.company;

public enum DScale {
    Basic(5), LevelOne(10), LevelTwo(15), LevelThree(25), LevelMax(40);

    private int scale;

    private DScale (int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }
}
