package com.nbc.app.dao;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nbc.app.domain.convgdeal.ConvergenceDeal;

@Repository
public interface ConvergenceRepository extends MongoRepository<ConvergenceDeal, Serializable> {

	@Query(value = "{'modifiedOn' : {$gt : ?0}}")
	Page<ConvergenceDeal> findByModifiedOnAfter(Date date, Pageable pageable);
	
	@Query(value = "{'modifiedOn' : {$gt : ?0}},{'enabled' : true")
	Page<ConvergenceDeal> findByModifiedOnAfterAndEnabled(Date date,Boolean enabled, Pageable pageable);
	



}