package com.retondar.service;

import com.retondar.constant.Province;
import com.retondar.dto.PropertyDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.exception.PositionAlreadyOccupiedException;
import com.retondar.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.retondar.constant.Province.GODE;
import static com.retondar.constant.Province.GROOLA;

/**
 * Created by thiagoretondar on 18/06/16.
 */
@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(final PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public void saveProperty(PropertyDto propertyDto) throws PositionAlreadyOccupiedException {

        Integer positionX = propertyDto.getPositionX();
        Integer positionY = propertyDto.getPositionY();

        int quantityProperty = propertyRepository.findPropertyInPosition(positionX, positionY);

        if (quantityProperty > 0) {
            throw new PositionAlreadyOccupiedException(String.format("Position (%d,%d) already occupied", positionX, positionY));
        }

        PropertyDocument property = new PropertyDocument();
        property.setBaths(4);
        property.setBeds(5);
        property.setProvinces(Arrays.asList(GODE, GROOLA));
        property.setSquareMeters(230);
        property.setX(positionX);
        property.setY(positionY);

        propertyRepository.insert(property);

    }

}
