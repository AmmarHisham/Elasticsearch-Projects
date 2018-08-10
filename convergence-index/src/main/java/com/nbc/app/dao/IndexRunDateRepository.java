package com.nbc.app.dao;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nbc.app.domain.jobs.IndexRunDate;

@Repository
public interface IndexRunDateRepository  extends JpaRepository<IndexRunDate, Serializable>, JpaSpecificationExecutor{
	
	 @Query("UPDATE IndexRunDate c SET c.runDate = :runDate WHERE c.machineName = :machineName and  c.indexName = :indexName")
    int updateIndexRunDate(@Param("machineName")  String machineName,@Param("indexName")  String indexName,@Param("runDate")  Timestamp runDate);
	
}