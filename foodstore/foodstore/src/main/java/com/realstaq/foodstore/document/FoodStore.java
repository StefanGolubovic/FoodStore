package com.realstaq.foodstore.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "food-store-index")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodStore {

    @JsonIgnoreProperties
    @Field(type = FieldType.Text)
    private String _class;

    @Field(type = FieldType.Text)
    private String country;

    @Id
    @Field(type = FieldType.Integer)
    private int licenceNumber;

    @Field(type = FieldType.Text)
    private String establishmentType;

    @Field(type = FieldType.Text)
    private String entityName;

    @Field(type = FieldType.Text)
    private String dbaName;

    @Field(type = FieldType.Text)
    private String streetNumber;

    @Field(type = FieldType.Text)
    private String streetName;

    @Field(type = FieldType.Text)
    private String city;

    @Field(type = FieldType.Text)
    private String stateAbbreviation;

    @Field(type = FieldType.Integer)
    private int zipCode;

    @Field(type = FieldType.Integer)
    private int squareFootage;

    @GeoPointField
    private GeoPoint geoPoint;

}