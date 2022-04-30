package com.realstaq.foodstore.loader;

import com.realstaq.foodstore.document.FoodStore;
import com.realstaq.foodstore.repository.FoodStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private FoodStoreRepository repository;

    private String[] fields;

    @Autowired
    public Loader(FoodStoreRepository repository) {
        this.repository = repository;
    }

//    @PostConstruct
    public void loadData() {
        try (Scanner sc = new Scanner(new File("foodstore/src/main/resources/static/data/food_stores_ny_city.csv"))) {
            //skip first line
            sc.nextLine();
            while (sc.hasNextLine()) {
                FoodStore foodStore = generateFoodStoreDocument(sc.nextLine());
                if (foodStore != null) {
                    repository.save(foodStore);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return;
        }
    }

    private FoodStore generateFoodStoreDocument(final String line) {
        fields = line.split(",");
        if (fields.length != 13) {
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