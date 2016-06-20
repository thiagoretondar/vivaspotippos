package com.retondar.service;

import com.mongodb.MongoException;
import com.retondar.converter.PropertyConverter;
import com.retondar.dto.PropertyCreationDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.exception.PositionAlreadyOccupiedException;
import com.retondar.repository.PropertyRepository;
import com.retondar.repository.RepositoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.retondar.constant.Province.SCAVY;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

/**
 * Created by thiagoretondar on 18/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyConverter propertyConverter;

    @InjectMocks
    private PropertyService propertyService = new PropertyService(propertyRepository, propertyConverter);

    @Test(expected = PositionAlreadyOccupiedException.class)
    public void lancaExceptionPorquePosicaoDeImovelJaEstaOcupada() throws PositionAlreadyOccupiedException, RepositoryException {
        // GIVEN
        PropertyCreationDto property = criarPropertyDto();
        int posX = property.getPositionX();
        int posY = property.getPositionY();

        when(propertyRepository.getQuantityPropertyInPosition(posX, posY)).thenReturn(1);

        // WHEN
        propertyService.saveProperty(property);

        // THEN -- expects Exception
    }

    @Test(expected = RepositoryException.class)
    public void lancaExceptionPorqueNaoConseguiuSalvarNoBanco() throws PositionAlreadyOccupiedException, RepositoryException {
        // GIVEN
        PropertyDocument document = criarPropertyDocument();
        PropertyCreationDto property = criarPropertyDto();
        int posX = property.getPositionX();
        int posY = property.getPositionY();

        when(propertyRepository.getQuantityPropertyInPosition(posX, posY)).thenReturn(0);
        when(propertyConverter.toDocument(property)).thenReturn(document);
        when(propertyRepository.insert(document)).thenThrow(new MongoException("Erro"));

        // WHEN
        propertyService.saveProperty(property);

        // THEN -- expects Exception
    }

    private PropertyCreationDto criarPropertyDto() {
        PropertyCreationDto dto = new PropertyCreationDto();
        dto.setPositionX(1);
        dto.setPositionY(1);
        dto.setAmountBeds(5);
        dto.setAmountBaths(4);
        dto.setSquareMeters(240);

        return dto;
    }

    private PropertyDocument criarPropertyDocument() {
        PropertyDocument document = new PropertyDocument();
        document.setX(1);
        document.setY(1);
        document.setBeds(5);
        document.setBaths(4);
        document.setSquareMeters(240);
        document.setProvinces(asList(SCAVY));

        return document;
    }
}
