package com.nbc.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nbc.app.domain.request.MultiMatch;
import com.nbc.app.domain.request.SearchParam;

@Service
public class ConvergenceSearchServiceImpl implements ConvergenceSearchService {

	@Value("${sort.field:cDealId}")
	public String sortField;
	
	@Value("${filter.field:enabled}")
	public String filterField;
	
	@Value("${filter.value:true}")
	public String filterValue;
	
	@Autowired
	public RestHighLevelClient restClient;

	@Override
	public Map searchByFieldName(SearchParam reqParams, Long pageNumber, Long pageSize,String index,String indexType,String scrollVal) {

		MultiMatch multiSearch = reqParams.getMultiMatch();

		String multiSearchInput = null;
		List<String> multiSearchFields = null;
		List<String> resultFields = null;

		SearchRequest searchRequest = null;
		
		// Result List & Map
		List<Map<String, Object>> convergenceList = new ArrayList<>();
		Map resultMap = null;

		if (multiSearch != null) {
			multiSearchInput = multiSearch.getInput();
			multiSearchFields = multiSearch.getFields();
		}	
		

		resultFields = reqParams.getResultFields();
		
		String[] multiSearchFieldsArray= multiSearchFields.stream().toArray(String[]::new);
		String[]  resultArray = resultFields!=null? resultFields.stream().toArray(String[]::new):null;
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(multiSearchInput,multiSearchFieldsArray);
		multiMatchQueryBuilder.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).lenient(true);
		multiMatchQueryBuilder.operator(Operator.AND);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		if (!StringUtils.isEmpty(index)) {
			searchRequest = new SearchRequest(index);
			if (!StringUtils.isEmpty(indexType)) {
				searchRequest.types(indexType);
			} else {
				searchRequest.types(indexType);
			}
		}

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		
		TermQueryBuilder termQueryBuilder =  QueryBuilders.termQuery(filterField, filterValue);
		
		queryBuilder.must(multiMatchQueryBuilder);
		queryBuilder.filter(termQueryBuilder);
		
		searchSourceBuilder.query(queryBuilder);

		if (!StringUtils.isEmpty(resultArray)) {
			searchSourceBuilder.fetchSource(resultArray, null);
		}

		searchRequest.searchType(SearchType.QUERY_THEN_FETCH);

		SortBuilder sb = SortBuilders.scoreSort();
		searchSourceBuilder.sort(sortField);
		searchSourceBuilder.sort(sb.order(SortOrder.DESC));
		
		
		

		if (!StringUtils.isEmpty(scrollVal) && "Y".equals(scrollVal)) {
			searchRequest.scroll(new TimeValue(60000));
		} else {
			searchSourceBuilder.from(pageNumber.intValue() * pageSize.intValue());
		}

		searchSourceBuilder.size(pageSize.intValue());
		searchRequest.source(searchSourceBuilder);

		SearchHit[] searchHits = null;
		String scrollId = null;
		SearchResponse scrollResp = null;

		try {
			// Search
			scrollResp = restClient.search(searchRequest);

			scrollId = scrollResp.getScrollId();
			searchHits = scrollResp.getHits().getHits();

			// Scroll until no hits are returned
			final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
			if (!StringUtils.isEmpty(scrollVal) && "Y".equals(scrollVal)) {
				while (searchHits != null && searchHits.length > 0) {
					for (SearchHit hit : searchHits) {
						Map<String, Object> map = new HashMap<>();
						hit.getSourceAsMap().put("score", hit.getScore());
						resultFields.forEach((i) -> map.put(i,
								StringUtils.isEmpty(hit.getSourceAsMap().get(i)) ? null : hit.getSourceAsMap().get(i)));
						convergenceList.add(map);
					}
					SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
					scrollRequest.scroll(scroll);
					scrollResp = restClient.searchScroll(scrollRequest);

					scrollId = scrollResp.getScrollId();
					searchHits = scrollResp.getHits().getHits();

				}

				ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
				clearScrollRequest.addScrollId(scrollId);
				ClearScrollResponse clearScrollResponse = restClient.clearScroll(clearScrollRequest);

			} else {

				for (SearchHit hit : scrollResp.getHits().getHits()) {
					Map<String, Object> map = new HashMap<String, Object>();
					hit.getSourceAsMap().put("score", hit.getScore());
					resultFields.forEach((i) -> map.put(i,
							StringUtils.isEmpty(hit.getSourceAsMap().get(i)) ? null : hit.getSourceAsMap().get(i)));
					convergenceList.add(map);
				}
			}

			if (convergenceList != null && convergenceList.size() > 0) {
				resultMap = new HashMap();
				resultMap.put("convergenceExtract", convergenceList);
				resultMap.put("total", scrollResp.getHits().getTotalHits());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

}