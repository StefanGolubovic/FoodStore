package com.realstaq.foodstore.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public final class SearchUtil {

    private SearchUtil() {
    }

    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDto dto) {

        try {
            final int page = dto.getPage();
            final int size = dto.getSize();
            final int from = page <= 0 ? 0 : page * size;

            final SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(from)
                    .size(size)
                    .postFilter(getQueryBuilder(dto));

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildLocationSearchRequest(final String indexName, final double lat, final double lon) {

        try {
            GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders
                    .geoDistanceQuery("geoPoint")
                    .point(lat, lon)
                    .distance(10, DistanceUnit.KILOMETERS);

            final SearchSourceBuilder builder = new SearchSourceBuilder()
                    .postFilter(geoDistanceQueryBuilder);

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static QueryBuilder getQueryBuilder(SearchRequestDto dto) {
        if (dto == null) {
            return null;
        }

        final List<String> fields = dto.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }
        if (fields.size() > 1) {
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.OR);
            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }

        return fields.stream()
                .findFirst()
                .map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm())).orElse(null);
    }
}
