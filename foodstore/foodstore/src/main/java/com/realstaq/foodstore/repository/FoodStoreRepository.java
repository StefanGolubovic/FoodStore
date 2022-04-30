package com.realstaq.foodstore.repository;

import com.realstaq.foodstore.document.FoodStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FoodStoreRepository extends ElasticsearchRepository<FoodStore, String> {
}
