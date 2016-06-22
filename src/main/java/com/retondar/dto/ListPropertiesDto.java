package com.retondar.dto;

import com.retondar.entity.PropertyDocument;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiagoretondar on 21/06/16.
 */
public class ListPropertiesDto implements Serializable {

    private static final long serialVersionUID = -8922734025815622668L;

    private int foundProperties;

    private List<PropertyDto> properties;

    public ListPropertiesDto(int quantity, List<PropertyDto> properties) {
        this.foundProperties = quantity;
        this.properties = properties;
    }

    public int getFoundProperties() {
        return foundProperties;
    }

    public void setFoundProperties(int foundProperties) {
        this.foundProperties = foundProperties;
    }

    public List<PropertyDto> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDto> properties) {
        this.properties = properties;
    }
}
