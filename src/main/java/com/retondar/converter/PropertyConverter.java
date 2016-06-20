package com.retondar.converter;

import com.retondar.constant.Province;
import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.entity.PropertyDocument;
import org.springframework.stereotype.Component;

/**
 * Created by thiagoretondar on 19/06/16.
 */
@Component
public class PropertyConverter {

    // TODO maybe put in another converter, because its Property*Converter*Dto
    public PropertyDocument toDocument(PropertyCreationDto dto) {

        PropertyDocument document = new PropertyDocument();
        document.setX(dto.getPositionX());
        document.setY(dto.getPositionY());
        document.setBeds(dto.getAmountBeds());
        document.setBaths(dto.getAmountBaths());
        document.setSquareMeters(dto.getSquareMeters());
        document.setProvinces(Province.getProvincesByPosition(dto.getPositionX(), dto.getPositionY())); // TODO refactor this (get this info from database?)

        return document;
    }

    public PropertyDto toDto(PropertyDocument document) {

        PropertyDto dto = new PropertyDto();
        dto.setId(document.getId());
        dto.setX(document.getX());
        dto.setY(document.getY());
        dto.setBeds(document.getBeds());
        dto.setBaths(document.getBaths());
        dto.setProvinces(document.getProvinces());

        return dto;
    }
}
