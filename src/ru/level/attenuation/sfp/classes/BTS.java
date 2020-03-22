package ru.level.attenuation.sfp.classes;

abstract class BTS {

    private String BTS_name;

    public String getBTS_name() {
        return BTS_name;
    }

    BTS(String BTS_name) {
        this.BTS_name = BTS_name;
    }

    public abstract String display();
}
