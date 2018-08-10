package com.nbc.app.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.nbc.app.dao.IndexRunDateRepository;
import com.nbc.app.domain.jobs.IndexRunDate;
import com.nbc.app.specification.IndexRunDateSpecifications;

@Service
public class IndexRunDateService extends AbstractService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IndexRunDateRepository indexRunDateRepository;
	
	
	public List<IndexRunDate> getData(String indexName, String machineName) throws Exception {
		List<IndexRunDate> indexRunDate=null;
		try {
			Specifications superSpec = null;
			superSpec = Specifications.where(IndexRunDateSpecifications.dummyCondition());
			superSpec = superSpec.and(IndexRunDateSpecifications.hasIndexName(indexName));
			superSpec = superSpec.and(IndexRunDateSpecifications.hasMachineName(machineName));
			indexRunDate = indexRunDateRepository.findAll(superSpec);
			
		} catch (Exception e) {
			System.out.println("Exception in getPlanData()--" + e);
			throw e;
		}
		return indexRunDate;
	}
	
	public void saveData(IndexRunDate indexRunDate) throws Exception {
		try {
			indexRunDateRepository.save(indexRunDate);
		} catch (Exception e) {
			System.out.println("Exception in getPlanData()--" + e);
			throw e;
		}
	}

}
