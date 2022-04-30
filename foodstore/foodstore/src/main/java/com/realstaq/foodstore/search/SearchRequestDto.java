package com.realstaq.foodstore.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDto {

    private List<String> fields;
    private String searchTerm;
}
