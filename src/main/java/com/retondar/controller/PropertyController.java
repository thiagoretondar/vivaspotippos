package com.retondar.controller;

import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.exception.NotFoundProperty;
import com.retondar.exception.PositionAlreadyOccupiedException;
import com.retondar.exception.RepositoryException;
import com.retondar.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by thiagoretondar on 18/06/16.
 */
@RestController
@RequestMapping(value = "/properties", produces = APPLICATION_JSON_UTF8_VALUE)
public class PropertyController {

    private PropertyService propertyService;

    @Autowired
    public PropertyController(final PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public PropertyDto create(@RequestBody @Valid PropertyCreationDto property) throws PositionAlreadyOccupiedException, RepositoryException {

        return propertyService.saveProperty(property);
    }

    @RequestMapping(method = GET, value = "/{id}")
    public PropertyDto getById(@PathVariable("id") String id) throws NotFoundProperty {

        return propertyService.getById(id);
    }

}
