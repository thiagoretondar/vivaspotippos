package com.retondar.converter;

import com.retondar.constant.Province;
import com.retondar.dto.PropertyCreationDto;
import com.retondar.entity.PropertyDocument;
import org.springframework.stereotype.Component;

/**
 * Created by thiagoretondar on 19/06/16.
 */
@Component
public class PropertyConverter {

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

}
