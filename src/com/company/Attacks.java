package com.company;

public enum Attacks {
    Cut("ZZ"), Stab("ZX"), Punch("ZC"), Fire("XZ"), Freeze("XX"), Shock("XC"), Pois("XV");

    private String code;

    private Attacks(String code) {
        this.code = code;
    }
}
