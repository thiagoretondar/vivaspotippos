package com.retondar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retondar.converter.PropertyConverter;
import com.retondar.dto.PropertyInitalizerDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.repository.PropertyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiagoretondar on 24/06/16.
 */
@Component
public class PropertyInitializerRunner implements CommandLineRunner {

    private static final Logger LOG = LogManager.getLogger(PropertyInitializerRunner.class);

    private PropertyRepository propertyRepository;

    private PropertyConverter propertyConverter;

    @Autowired
    public PropertyInitializerRunner(final PropertyRepository propertyRepository, final PropertyConverter propertyConverter) {
        this.propertyRepository = propertyRepository;
        this.propertyConverter = propertyConverter;
    }

    @Override
    public void run(String... strings) throws Exception {

        LOG.info("Cleaning the database!");
        propertyRepository.deleteAll();

        LOG.info("Getting properties in JSON");
        InputStream propertiesStream = VivaspotipposApplication.class.getClassLoader().getResourceAsStream("json/properties.json");

        PropertyInitalizerDto propertyInitalizerDto = new ObjectMapper().readValue(propertiesStream, PropertyInitalizerDto.class);

        List<PropertyDocument> propertyDocumentList = new ArrayList<>();
        propertyInitalizerDto.getProperties().forEach(propertyDto -> {
            PropertyDocument propertyDocument = propertyConverter.toDocument(propertyDto);
            propertyDocumentList.add(propertyDocument);
        });

        LOG.info("Well... I'm going to try save this all properties: "+ propertyDocumentList.size());
        propertyRepository.insert(propertyDocumentList);
        LOG.info("Amount of properties saved: " + propertyDocumentList.size());
    }

}