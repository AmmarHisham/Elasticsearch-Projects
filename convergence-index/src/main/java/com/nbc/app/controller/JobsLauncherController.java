package com.nbc.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.joda.time.LocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbc.app.domain.ResponseObject;
import com.nbc.app.domain.jobs.BatchDetail;

@RestController
public class JobsLauncherController  {

   @Autowired
   private Job convergenceJob;

   @Autowired
   private JobLauncher jobLauncher;
    
   private JobExecution jobExecution;
    
   @Autowired
   JobOperator jobOperator;
    
   @Scheduled(cron = "0 0/3 * * * ?")
   public ResponseEntity<ResponseObject> launch() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException,
            ExecutionException,
            InterruptedException, IOException, Exception {
		   Set<BatchDetail> batchDetails = new HashSet<>();
		   ResponseObject resObj = new ResponseObject();
    	try{
    		JobParameters jobParameters = new JobParametersBuilder()
            .addString("JOB_START_DATE", (new LocalDateTime()).toString())
            .toJobParameters();
     
	        jobExecution=jobLauncher.run(convergenceJob, jobParameters);
	        BatchDetail batchDetail=new BatchDetail();
	        batchDetail.setId(jobExecution.getId());
	        if(!jobExecution.getAllFailureExceptions().isEmpty()){
	        	batchDetail.setStatus(jobExecution.getAllFailureExceptions().get(0).getMessage()+""+jobExecution.getStatus().name());
	        }else{
	        	batchDetail.setStatus(jobExecution.getStatus().name());
	        }
	        
			
	        batchDetails.add(batchDetail);
	        if("COMPLETED".equals(jobExecution.getStatus().name())){
	        	resObj.setSuccess(true);	
	        }
	        resObj.setData(batchDetails);	
	        return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);	     

    	}catch (JobExecutionAlreadyRunningException e) {
    		resObj = new ResponseObject(e.getMessage());
    		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
        } catch (JobRestartException e) {          
        	resObj = new ResponseObject(e.getMessage());
    		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);

        } catch (JobInstanceAlreadyCompleteException e) {          
        	resObj = new ResponseObject(e.getMessage());
    		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);

        } catch (JobParametersInvalidException e) {        
        	resObj = new ResponseObject(e.getMessage());
    		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);

        }catch (Exception e) {        
        	resObj = new ResponseObject(e.getMessage());
    		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);

        }
    	
}
   
   @RequestMapping("/stopjob")
   public ResponseEntity<ResponseObject> stopJob() throws NoSuchJobExecutionException, JobExecutionNotRunningException, NoSuchJobInstanceException  {

	  Set<Long> executions;
	  Set<BatchDetail> batchDetails = new HashSet<>();
	  ResponseObject resObj = new ResponseObject();
	  try {
		executions = jobOperator.getRunningExecutions("convegenceJob");
		List<Long> jobIdList = new ArrayList<Long>(executions);
		Collections.sort(jobIdList, Collections.reverseOrder());
		//for(Long id:jobIdList){
		if(jobIdList!=null && jobIdList.size()>0){
			jobOperator.stop(jobIdList.get(0));
			long startTime = System.currentTimeMillis();
			boolean batchjobStatus=false;
			BatchDetail batchDetail=new BatchDetail();
			while(!batchjobStatus && (System.currentTimeMillis()-startTime)<100000)
			{	
				batchDetail.setId(jobIdList.get(0));
				batchDetail.setStatus("STOPPING");
				String s= jobOperator.getSummary(jobIdList.get(0));
				List<String> list =  
					    Stream.of(s.split("\\s*,\\s*") ).collect( Collectors.toList() );
				if(list.contains("status=STOPPED")){
					batchjobStatus=true;
					batchDetail.setStatus("STOPPED");
				}
			}
			batchDetails.add(batchDetail);	      
		}
		resObj.setSuccess(true);
		resObj.setData(batchDetails);
		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);
	} catch (NoSuchJobException e) {
		resObj = new ResponseObject(e.getMessage());
		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
	}catch (Exception e) {
		
		if("JobExecutionNotRunningException".equals(e.getClass().getSimpleName())){
			resObj = new ResponseObject("No running jobs exist");
		}else{
			resObj = new ResponseObject(e.getMessage());
		}
		return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
		
	}
	 
}

	
}
