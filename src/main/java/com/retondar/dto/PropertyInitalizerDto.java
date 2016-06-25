package com.retondar.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiagoretondar on 24/06/16.
 */
public class PropertyInitalizerDto implements Serializable {

    private static final long serialVersionUID = -4935855889891673478L;

    private int totalProperties;

    private List<PropertyCreationDto> properties;

    public int getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(int totalProperties) {
        this.totalProperties = totalProperties;
    }

    public List<PropertyCreationDto> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyCreationDto> properties) {
        this.properties = properties;
    }
}
