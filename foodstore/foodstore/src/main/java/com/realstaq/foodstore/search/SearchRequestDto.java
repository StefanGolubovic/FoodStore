package com.realstaq.foodstore.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchRequestDto extends PageRequestDto {

    private List<String> fields;
    private String searchTerm;
}
