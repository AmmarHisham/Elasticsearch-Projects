package com.nbc.app.specification;

import java.text.ParseException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nbc.app.domain.jobs.IndexRunDate;

public class IndexRunDateSpecifications {
	
	
	public static Specification<IndexRunDate> dummyCondition() {
		return new Specification<IndexRunDate>() {
			@Override
			public Predicate toPredicate(Root<IndexRunDate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(root.get("indexName").isNotNull());
				return query.getRestriction();
			}
		};
	}
	
	public static Specification<IndexRunDate> hasIndexName(String indexName) throws ParseException {
		return new Specification<IndexRunDate>() {
			@Override
			public Predicate toPredicate(Root<IndexRunDate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(root.get("indexName").in(indexName));
				return query.getRestriction();
			}
		};
	}
	
	public static Specification<IndexRunDate> hasMachineName(String machineName) throws ParseException {
		return new Specification<IndexRunDate>() {
			@Override
			public Predicate toPredicate(Root<IndexRunDate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.where(root.get("machineName").in(machineName));
				return query.getRestriction();
			}
		};
	}


}