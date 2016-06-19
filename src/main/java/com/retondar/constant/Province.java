package com.retondar.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiagoretondar on 18/06/16.
 */
public enum Province {

    GODE("Gode", 0, 1000, 600, 500),
    RUJA("Ruja", 400, 1000, 1100, 500),
    JABY("Jaby", 1100, 1000, 1400, 500),
    SCAVY("Scavy", 0, 500, 600, 0),
    GROOLA("Groola", 600, 500, 800, 0),
    NOVA("Nova", 800, 500, 1400, 0);

    private String name;
    private int xa;
    private int xb;
    private int ya;
    private int yb;

    Province(String name, int xa, int xb, int ya, int yb) {
        this.name = name;
        this.xa = xa;
        this.xb = xb;
        this.ya = ya;
        this.yb = yb;
    }

    public String getName() {
        return name;
    }

    public List<Province> getProvincesByPosition(int x, int y) {
        List<Province> provinces = new ArrayList<>();
        for (Province province : values()) {
            if (x >= province.xa && x <= province.xb &&
                    y <= province.ya && y >= province.yb) {
                provinces.add(province);
            }
        }

        return provinces;
    }
}
