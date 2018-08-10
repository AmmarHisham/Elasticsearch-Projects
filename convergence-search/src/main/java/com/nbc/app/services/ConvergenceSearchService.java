package com.nbc.app.services;

import java.util.Map;

import com.nbc.app.domain.request.SearchParam;


public interface ConvergenceSearchService {

	Map searchByFieldName(SearchParam reqParams,Long pageNumber,Long pageSize,String index,String indexType,String scrollVal);
	
}
