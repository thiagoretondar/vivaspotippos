package com.retondar.converter;

import com.retondar.dto.PropertyCreationDto;
import com.retondar.dto.PropertyDto;
import com.retondar.entity.PropertyDocument;
import com.retondar.entity.ProvinceDocument;
import com.retondar.repository.ProvinceRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by thiagoretondar on 25/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertyConverterTest {

    @Mock
    private ProvinceRepository provinceRepository;

    @InjectMocks
    private PropertyConverter propertyConverter = new PropertyConverter(provinceRepository);

    @Test
    public void retornaPropertyDocumentDadoUmDto() {
        // GIVEN
        PropertyCreationDto dto = new PropertyCreationDto();
        dto.setPositionX(1);
        dto.setPositionY(2);
        dto.setAmountBaths(3);
        dto.setAmountBeds(4);
        dto.setSquareMeters(20);

        ProvinceDocument provinceDocument = new ProvinceDocument();
        provinceDocument.setProvinceName("Scavy");

        when(provinceRepository.findProvincesByPosition(dto.getPositionX(), dto.getPositionY())).thenReturn(Stream.of(provinceDocument));

        // WHEN
        PropertyDocument propertyDocument = propertyConverter.toDocument(dto);

        // THEN
        assertThat(propertyDocument.getX()).isEqualTo(dto.getPositionX());
        assertThat(propertyDocument.getY()).isEqualTo(dto.getPositionY());
        assertThat(propertyDocument.getBaths()).isEqualTo(dto.getAmountBaths());
        assertThat(propertyDocument.getBeds()).isEqualTo(dto.getAmountBeds());
        assertThat(propertyDocument.getSquareMeters()).isEqualTo(dto.getSquareMeters());
        assertThat(propertyDocument.getProvinces().get(0)).isEqualTo(provinceDocument.getProvinceName());
    }

    @Test
    public void retornaPropertyDtoDadoUmDocument() {
        // GIVEN
        PropertyDocument document = new PropertyDocument();
        document.setId("1a2b3c");
        document.setX(1);
        document.setY(2);
        document.setBaths(3);
        document.setBeds(4);
        document.setSquareMeters(20);
        document.setProvinces(Arrays.asList("Scavy"));

        // WHEN
        PropertyDto propertyDto = propertyConverter.toDto(document);

        // THEN
        assertThat(propertyDto.getId()).isEqualTo(document.getId());
        assertThat(propertyDto.getX()).isEqualTo(document.getX());
        assertThat(propertyDto.getY()).isEqualTo(document.getY());
        assertThat(propertyDto.getBaths()).isEqualTo(document.getBaths());
        assertThat(propertyDto.getBeds()).isEqualTo(document.getBeds());
        assertThat(propertyDto.getProvinces()).isEqualTo(document.getProvinces());
    }

}