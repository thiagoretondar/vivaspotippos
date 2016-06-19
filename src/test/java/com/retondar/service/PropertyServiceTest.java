package com.retondar.service;

import com.retondar.dto.PropertyDto;
import com.retondar.exception.PositionAlreadyOccupiedException;
import com.retondar.repository.PropertyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by thiagoretondar on 18/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService = new PropertyService(propertyRepository);

    @Test(expected = PositionAlreadyOccupiedException.class)
    public void lancaExceptionPorquePosicaoDeImovelJaEstaOcupada() throws PositionAlreadyOccupiedException {
        // GIVEN
        PropertyDto property = criarPropertyDto();
        int posX = property.getPositionX();
        int posY = property.getPositionY();

        when(propertyRepository.getQuantityPropertyInPosition(posX, posY)).thenReturn(1);

        // WHEN
        propertyService.saveProperty(property);

        // THEN -- expects Exception
    }

    private PropertyDto criarPropertyDto() {
        PropertyDto dto = new PropertyDto();
        dto.setPositionX(1);
        dto.setPositionY(1);

        return dto;
    }

}