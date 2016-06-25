package com.retondar.converter;

import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.entity.ProvinceDocument;
import com.retondar.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by thiagoretondar on 19/06/16.
 */
@Component
public class PropertyConverter {

    private ProvinceRepository provinceRepository;

    @Autowired
    public PropertyConverter(final ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    // TODO maybe put in another converter, because its Property*Converter*Dto
    public PropertyDocument toDocument(PropertyCreationDto dto) {

        PropertyDocument document = new PropertyDocument();
        document.setX(dto.getPositionX());
        document.setY(dto.getPositionY());
        document.setBeds(dto.getAmountBeds());
        document.setBaths(dto.getAmountBaths());
        document.setSquareMeters(dto.getSquareMeters());
        document.setProvinces(getProvincesByPosition(dto));

        return document;
    }

    public PropertyDto toDto(PropertyDocument document) {

        PropertyDto dto = new PropertyDto();
        dto.setId(document.getId());
        dto.setX(document.getX());
        dto.setY(document.getY());
        dto.setBeds(document.getBeds());
        dto.setBaths(document.getBaths());
        dto.setSquareMeters(document.getSquareMeters());
        dto.setProvinces(document.getProvinces());

        return dto;
    }

    private List<String> getProvincesByPosition(PropertyCreationDto dto) {

        Stream<ProvinceDocument> provincesByPosition = provinceRepository.findProvincesByPosition(dto.getPositionX(), dto.getPositionY());

        return provincesByPosition
                .map(ProvinceDocument::getProvinceName)
                .collect(Collectors.toList());
    }
}
