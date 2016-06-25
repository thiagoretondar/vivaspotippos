package com.retondar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retondar.entity.ProvinceDocument;
import com.retondar.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thiagoretondar on 24/06/16.
 */
@Component
public class ProvinceInitalizerRunner implements CommandLineRunner {

    private ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceInitalizerRunner(final ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        // limpa a base para os testes
        provinceRepository.deleteAll();

        List<ProvinceDocument> provinceDocuments = new ArrayList<>();

        InputStream provincesStream = VivaspotipposApplication.class.getClassLoader().getResourceAsStream("json/provinces.json");
        JsonNode provincesNodes = new ObjectMapper().readTree(provincesStream);
        Iterator<String> provincesIterator = provincesNodes.fieldNames();
        while (provincesIterator.hasNext()) {

            String provinceName = provincesIterator.next();
            JsonNode upperLeftNode = provincesNodes.path(provinceName).path("boundaries").path("upperLeft");
            JsonNode rightBottomNode = provincesNodes.path(provinceName).path("boundaries").path("bottomRight");

            ProvinceDocument provinceDocument = new ProvinceDocument();
            provinceDocument.setProvinceName(provinceName);
            provinceDocument.setUpperLeftX(upperLeftNode.path("x").asInt());
            provinceDocument.setUpperLeftY(upperLeftNode.path("y").asInt());
            provinceDocument.setBottomRightX(rightBottomNode.path("x").asInt());
            provinceDocument.setBottomRightY(rightBottomNode.path("y").asInt());

            provinceDocuments.add(provinceDocument);
        }

        provinceRepository.insert(provinceDocuments);
    }
}
