package com.retondar.dto;

import java.io.Serializable;

/**
 * Created by thiagoretondar on 24/06/16.
 */
public class ProvinceDto implements Serializable {

    private static final long serialVersionUID = 2028096811873520828L;

    private String id;

    private String provinceName;

    private int upperLeftX;

    private int uppeLeftY;

    private int bottomRightX;

    private int bottomRightY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getUpperLeftX() {
        return upperLeftX;
    }

    public void setUpperLeftX(int upperLeftX) {
        this.upperLeftX = upperLeftX;
    }

    public int getUppeLeftY() {
        return uppeLeftY;
    }

    public void setUppeLeftY(int uppeLeftY) {
        this.uppeLeftY = uppeLeftY;
    }

    public int getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(int bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public int getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(int bottomRightY) {
        this.bottomRightY = bottomRightY;
    }
}
