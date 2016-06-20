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
    private int ya;
    private int xb;
    private int yb;

    Province(String name, int xa, int ya, int xb, int yb) {
        this.name = name;
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
    }

    public String getName() {
        return name;
    }

    public static List<Province> getProvincesByPosition(int x, int y) {
        List<Province> provinces = new ArrayList<>();
        for (Province p : Province.values()) {
            if (x >= p.xa && x <= p.xb && y <= p.ya && y >= p.yb) {
                provinces.add(p);
            }
        }

        return provinces;
    }
}
