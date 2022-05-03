package com.realstaq.foodstore.loader;

import com.realstaq.foodstore.document.FoodStore;
import com.realstaq.foodstore.repository.FoodStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Service
public class Loader {

    public static final Logger LOG = LoggerFactory.getLogger(Loader.class);

    private static final int NUMBER_OF_COLUMNS = 13;

    private FoodStoreRepository repository;

    @Value("${data.location}")
    private String path;

    private String[] fields;

    @Autowired
    public Loader(FoodStoreRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadData() {
        try (Scanner sc = new Scanner(new File(path))) {
            //skip first line
            sc.nextLine();
            while (sc.hasNextLine()) {
                FoodStore foodStore = generateFoodStoreDocument(sc.nextLine());
                if (foodStore != null) {
                    repository.save(foodStore);
                } else {
                    LOG.info("Document not created");
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return;
        }
    }

    private FoodStore generateFoodStoreDocument(final String line) {
        if (line != null) {
            fields = line.split(",");
        } else {
            return null;
        }
        if (fields.length != NUMBER_OF_COLUMNS) {
            return null;
        }
        return FoodStore.builder()
                .country(fields[0])
                .licenceNumber(parseInt(fields[1]))
                .establishmentType(fields[2])
                .entityName(fields[3])
                .dbaName(fields[4])
                .streetNumber(fields[5])
                .streetName(fields[6])
                .city(fields[7])
                .stateAbbreviation(fields[8])
                .zipCode(parseInt(fields[9]))
                .squareFootage(parseInt(fields[10]))
                .geoPoint(new GeoPoint(parseDouble(fields[11]), parseDouble(fields[12])))
                .build();
    }
}