package com.realstaq.foodstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realstaq.foodstore.document.FoodStore;
import com.realstaq.foodstore.search.SearchRequestDto;
import com.realstaq.foodstore.search.SearchUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food-store")
public class FoodStoreController {

    private static final String INDEX_NAME = "food-store-index";

    private Logger LOG = LoggerFactory.getLogger(FoodStoreController.class);

    private RestHighLevelClient client;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public FoodStoreController(RestHighLevelClient client) {
        this.client = client;
    }

    @PostMapping
    public List<FoodStore> searchFoodStore(@RequestBody SearchRequestDto dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(INDEX_NAME, dto);

        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<FoodStore> foodStores = new ArrayList<>(searchHits.length);

            for (SearchHit hit : searchHits) {
                foodStores.add(MAPPER.readValue(hit.getSourceAsString(), FoodStore.class));
            }

            return foodStores;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    @GetMapping("/{lat}/{lon}")
    public List<FoodStore> getNearbyFoodStores(@PathVariable final Double lat, @PathVariable final Double lon) {
        final SearchRequest request = SearchUtil.buildLocationSearchRequest(INDEX_NAME, lat, lon);

        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<FoodStore> foodStores = new ArrayList<>(searchHits.length);

            for (SearchHit hit : searchHits) {
                foodStores.add(MAPPER.readValue(hit.getSourceAsString(), FoodStore.class));
            }

            return foodStores;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}