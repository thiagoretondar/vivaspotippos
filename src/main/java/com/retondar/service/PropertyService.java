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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by thiagoretondar on 18/06/16.
 */
@Service
public class PropertyService {

    private static final Logger LOG = LogManager.getLogger(PropertyService.class);

    private PropertyRepository propertyRepository;

    private PropertyConverter propertyConverter;

    @Autowired
    public PropertyService(final PropertyRepository propertyRepository, final PropertyConverter propertyConverter) {
        this.propertyRepository = propertyRepository;
        this.propertyConverter = propertyConverter;
    }

    /**
     * Save a property if it's not occupied already and has characteristics
     *
     * @param propertyCreationDto DTO with property
     * @return DTO with saved informations
     * @throws PositionAlreadyOccupiedException
     * @throws RepositoryException
     */
    public PropertyDto saveProperty(PropertyCreationDto propertyCreationDto) throws PositionAlreadyOccupiedException, RepositoryException {

        verifyItsNotOccupied(propertyCreationDto);

        PropertyDocument propertyDocument = propertyConverter.toDocument(propertyCreationDto);

        try {
            LOG.info(String.format("Saving the new property in position x=%d,y=%d",
                    propertyCreationDto.getPositionX(), propertyCreationDto.getPositionY()));

            PropertyDocument insertedProperty = propertyRepository.insert(propertyDocument);

            LOG.info(String.format("Property in position x=%d,y=%d saved. ID of this property = %s",
                    propertyCreationDto.getPositionX(), propertyCreationDto.getPositionY(), insertedProperty.getId()));

            return propertyConverter.toDto(insertedProperty);
        } catch (MongoException e) {
            throw new RepositoryException("Error when trying to save the property");
        }
    }

    /**
     * Search a property by its ID. If doesn't exist, throw an Exception
     *
     * @param id It's in fact the ID =)
     * @return DTO with information about the property
     * @throws NotFoundProperty
     */
    public PropertyDto getById(String id) throws NotFoundProperty {

        Assert.hasText(id);

        LOG.info(String.format("Searching property with ID = %s", id));

        Optional<PropertyDocument> property = Optional.ofNullable(propertyRepository.findOne(id));

        return property
                .map(propertyConverter::toDto)
                .orElseThrow(() -> {
                    LOG.warn(String.format("No property found with ID = %s", id));
                    return new NotFoundProperty(String.format("No property was found with id=%s", id));
                });
    }

    /**
     * Search properties by its ID. If doesn't exist, returns empty list
     *
     * @param xa Position xa
     * @param ya Position ya
     * @param xb Position xb
     * @param yb Position yb
     * @return List with amount of properties and the properties itself
     */
    public ListPropertiesDto getListPropertiesByArea(int xa, int ya, int xb, int yb) {

        List<PropertyDto> resultProperties = new ArrayList<>();

        LOG.info("Searching properties in area.");

        Stream<PropertyDocument> propertiesInArea = propertyRepository.findByArea(xa, ya, xb, yb);

        propertiesInArea
                .map(propertyConverter::toDto)
                .forEach(propertyDto -> resultProperties.add(propertyDto));

        LOG.info(String.format("Amount of properties found: %d", resultProperties.size()));

        return new ListPropertiesDto(resultProperties.size(), resultProperties);
    }

    /**
     * Validate if a property doesn't exist in the position passed by the new property
     *
     * @param propertyCreationDto DTO of creation property
     * @throws PositionAlreadyOccupiedException
     */
    private void verifyItsNotOccupied(PropertyCreationDto propertyCreationDto) throws PositionAlreadyOccupiedException {

        LOG.info(String.format("Verifying if property is not occupied in position x=%d,y=%d",
                propertyCreationDto.getPositionX(), propertyCreationDto.getPositionY()));

        Integer positionX = propertyCreationDto.getPositionX();
        Integer positionY = propertyCreationDto.getPositionY();

        int quantityProperty = propertyRepository.getQuantityPropertyInPosition(positionX, positionY);

        if (quantityProperty > 0) {
            LOG.warn(String.format("Property in position x=%d,y=%d IS occupied",
                    propertyCreationDto.getPositionX(), propertyCreationDto.getPositionY()));
            throw new PositionAlreadyOccupiedException(String.format("Position (%d,%d) already occupied", positionX, positionY));
        }

        LOG.info(String.format("Property in position x=%d,y=%d IS NOT occupied",
                propertyCreationDto.getPositionX(), propertyCreationDto.getPositionY()));
    }

}
