package com.retondar.service;

import com.mongodb.MongoException;
import com.retondar.converter.PropertyConverter;
import com.retondar.dto.ListPropertiesDto;
import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.exception.NotFoundProperty;
import com.retondar.exception.PositionAlreadyOccupiedException;
import com.retondar.exception.RepositoryException;
import com.retondar.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by thiagoretondar on 18/06/16.
 */
@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    private PropertyConverter propertyConverter;

    @Autowired
    public PropertyService(final PropertyRepository propertyRepository, final PropertyConverter propertyConverter) {
        this.propertyRepository = propertyRepository;
        this.propertyConverter = propertyConverter;
    }

    public PropertyDto saveProperty(PropertyCreationDto propertyCreationDto) throws PositionAlreadyOccupiedException, RepositoryException {

        Integer positionX = propertyCreationDto.getPositionX();
        Integer positionY = propertyCreationDto.getPositionY();

        int quantityProperty = propertyRepository.getQuantityPropertyInPosition(positionX, positionY);

        if (quantityProperty > 0) {
            throw new PositionAlreadyOccupiedException(String.format("Position (%d,%d) already occupied", positionX, positionY));
        }

        PropertyDocument propertyDocument = propertyConverter.toDocument(propertyCreationDto);

        try {
            PropertyDocument insertedProperty = propertyRepository.insert(propertyDocument);

            return propertyConverter.toDto(insertedProperty);
        } catch (MongoException e) {
            throw new RepositoryException("Error when trying to save the property");
        }
    }

    public PropertyDto getById(String id) throws NotFoundProperty {

        Assert.hasText(id);

        PropertyDocument propertyDocument = propertyRepository.findOne(id);

        if (isNull(propertyDocument)) {
            throw new NotFoundProperty(String.format("No property was found with id=%s", id));
        }

        return propertyConverter.toDto(propertyDocument);
    }

    public ListPropertiesDto getListPropertiesByArea(int xa, int ya, int xb, int yb) {

        List<PropertyDto> resultProperties = new ArrayList<>();
        int quantity = 0;

        List<PropertyDocument> propertiesInArea = propertyRepository.findByArea(xa, ya, xb, yb);

        if (nonNull(propertiesInArea)) {
            for (PropertyDocument propertyDocument : propertiesInArea) {
                resultProperties.add(propertyConverter.toDto(propertyDocument));
            }
            quantity = propertiesInArea.size();
        }

        return new ListPropertiesDto(quantity, resultProperties);
    }

}
