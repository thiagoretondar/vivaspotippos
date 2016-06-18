package com.retondar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by thiagoretondar on 18/06/16.
 */
public class PropertyDto implements Serializable {

    private static final long serialVersionUID = 7163410250242677783L;

    @NotNull
    @JsonProperty("id")
    private Integer id;

    @NotNull(message = "Position x cannot be null")
    @Max(value = 1400, message = "Position x maximum is 1400")
    @Min(value = 0, message = "Position x minimum is 0")
    @JsonProperty("x")
    private Integer positionX;

    @NotNull(message = "Position y cannot be null")
    @Max(value = 1000, message = "Position y maximum is 1000")
    @Min(value = 0, message = "Position y minimum is 1000")
    @JsonProperty("y")
    private Integer positionY;

    @NotNull(message = "Amount of beds cannot be null")
    @Max(value = 5, message = "Maximum quantity of beds is 5")
    @Min(value = 1, message = "Minimum quantity of beds is 1")
    @JsonProperty("beds")
    private Integer amountBeds;

    @NotNull(message = "Amount of baths cannot be null")
    @Max(value = 4, message = "Maximum quantity of baths is 4")
    @Min(value = 1, message = "Minimum quantity of baths is 1")
    @JsonProperty("baths")
    private Integer amountBaths;

    @NotNull(message = "Square meters cannot be null")
    @Max(value = 240, message = "Square meters maximum values is 240")
    @Min(value = 20, message = "Square meters minimum values is 20")
    @JsonProperty("squareMeters")
    private Integer squareMeters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Integer getAmountBeds() {
        return amountBeds;
    }

    public void setAmountBeds(Integer amountBeds) {
        this.amountBeds = amountBeds;
    }

    public Integer getAmountBaths() {
        return amountBaths;
    }

    public void setAmountBaths(Integer amountBaths) {
        this.amountBaths = amountBaths;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

}
