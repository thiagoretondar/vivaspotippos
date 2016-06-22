package com.retondar.service;

import com.mongodb.MongoException;
import com.retondar.converter.PropertyConverter;
import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.exception.NotFoundProperty;
import com.retondar.exception.PositionAlreadyOccupiedException;
import com.retondar.repository.PropertyRepository;
import com.retondar.exception.RepositoryException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.retondar.constant.Province.SCAVY;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
        PropertyCreationDto property = criarPropertyCreationDto();
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
        PropertyCreationDto property = criarPropertyCreationDto();
        int posX = property.getPositionX();
        int posY = property.getPositionY();

        when(propertyRepository.getQuantityPropertyInPosition(posX, posY)).thenReturn(0);
        when(propertyConverter.toDocument(property)).thenReturn(document);
        when(propertyRepository.insert(document)).thenThrow(new MongoException("Erro"));

        // WHEN
        propertyService.saveProperty(property);

        // THEN -- expects Exception
    }

    @Test
    public void retornaDtoCompletoPorqueImovelFoiSalvoComSucesso() throws PositionAlreadyOccupiedException, RepositoryException {
        // GIVEN
        PropertyDocument document = criarPropertyDocument();
        PropertyCreationDto property = criarPropertyCreationDto();
        PropertyDocument insertedProperty = criarInsertedPropertyDocument();
        PropertyDto resultProperty = criarPropertyDto();
        int posX = property.getPositionX();
        int posY = property.getPositionY();

        when(propertyRepository.getQuantityPropertyInPosition(posX, posY)).thenReturn(0);
        when(propertyConverter.toDocument(property)).thenReturn(document);
        when(propertyRepository.insert(document)).thenReturn(insertedProperty);
        when(propertyConverter.toDto(insertedProperty)).thenReturn(resultProperty);

        // WHEN
        PropertyDto result = propertyService.saveProperty(property);

        // THEN
        verify(propertyConverter, times(1)).toDto(insertedProperty);
        assertEquals(result.getId(), resultProperty.getId());
    }

    @Test
    public void retornarDtoDoImovelQuandoEncontrarImovelNaBuscaPorId() throws NotFoundProperty {
        // GIVEN
        String id = "1a2b";
        PropertyDocument foundProperty = criarInsertedPropertyDocument();
        PropertyDto resultProperty = criarPropertyDto();

        when(propertyRepository.findOne(id)).thenReturn(foundProperty);
        when(propertyConverter.toDto(foundProperty)).thenReturn(resultProperty);

        // WHEN
        PropertyDto propertyResult = propertyService.getById(id);

        // THEN
        assertNotNull(propertyResult);
    }

    @Test(expected = NotFoundProperty.class)
    public void lancaExcecaoQuandoNaoEncontrarImovelNaBuscaPorId() throws NotFoundProperty {
        // GIVEN
        String id = "1a2b";

        when(propertyRepository.findOne(id)).thenReturn(null);

        // WHEN
        propertyService.getById(id);

        // THEN -- expects Exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void lancaExcecaoPorqueIdPassadoEhVazio() throws NotFoundProperty {
        // GIVEN
        String id = "";

        // WHEN
        propertyService.getById(id);

        // THEN -- expects Exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void lancaExcecaoPorqueIdPassadoEhNulo() throws NotFoundProperty {
        // GIVEN
        String id = null;

        // WHEN
        propertyService.getById(id);

        // THEN -- expects Exception

    }

    private PropertyCreationDto criarPropertyCreationDto() {
        PropertyCreationDto dto = new PropertyCreationDto();
        dto.setPositionX(1);
        dto.setPositionY(1);
        dto.setAmountBeds(5);
        dto.setAmountBaths(4);
        dto.setSquareMeters(240);

        return dto;
    }

    private PropertyDto criarPropertyDto() {
        PropertyDto dto = new PropertyDto();
        dto.setId("1a2b");
        dto.setX(1);
        dto.setY(1);
        dto.setBeds(5);
        dto.setBaths(4);
        dto.setSquareMeters(240);
        dto.setProvinces(asList(SCAVY));

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

    private PropertyDocument criarInsertedPropertyDocument() {
        PropertyDocument document = new PropertyDocument();
        document.setId("1a2b");
        document.setX(1);
        document.setY(1);
        document.setBeds(5);
        document.setBaths(4);
        document.setSquareMeters(240);
        document.setProvinces(asList(SCAVY));

        return document;
    }
}
