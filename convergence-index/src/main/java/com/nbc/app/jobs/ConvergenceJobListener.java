package com.nbc.app.jobs;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nbc.app.domain.jobs.IndexRunDate;
import com.nbc.app.services.IndexRunDateService;

@Component
@RefreshScope
public class ConvergenceJobListener extends JobExecutionListenerSupport {

    @Autowired
    private IndexRunDateService indexRunDateService;
    
    @Value("${uniqueIndexName}")
    private String uniqueIndexName;
    
    public String getMac()
    {
    	String macAddr =null;
	    try
	    {
		    InetAddress address = InetAddress.getLocalHost();
		    NetworkInterface nwi = NetworkInterface.getByInetAddress(address);
		    byte mac[] = nwi.getHardwareAddress();
		    
		    if(mac != null && mac.length > 0){
		    	StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				macAddr = sb.toString();
		    }
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
		return !StringUtils.isEmpty(uniqueIndexName)?uniqueIndexName:macAddr;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    	List<IndexRunDate> indexRunDateList;
		try {
			indexRunDateList = indexRunDateService.getData("elasticsearchconvergence_index", getMac());
			if(indexRunDateList==null || (indexRunDateList!=null && indexRunDateList.size()<=0)){				
				Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("America/New_York"));
	        	cal.add(Calendar.YEAR, -15);
				Timestamp someOldDate = new Timestamp(cal.getTimeInMillis());
				System.out.println("someOldDate : "+someOldDate);
				
				svaeorupdate(someOldDate);
				indexRunDateList = indexRunDateService.getData("elasticsearchconvergence_index", getMac());
			}
			Timestamp lastIndexDate = null;
	    	for(IndexRunDate indexRunDate:indexRunDateList){
	    		lastIndexDate=indexRunDate.getRunDate();
	    	}
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	    	String string  = dateFormat.format(lastIndexDate);
		    	
	    	jobExecution.getExecutionContext().putString("indexDate", string);
		} catch (Exception e) {
			throw new RuntimeException("Issue while parsing date - beforeJob");
		}
    	

    }
    
    @Override
    public void afterJob(JobExecution jobExecution)  {
    	try {
			
			if (jobExecution.getStatus() == BatchStatus.COMPLETED){
				Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("America/New_York"));
				calendar.add(Calendar.HOUR, -10);
				Timestamp justLastRunDate = new Timestamp(calendar.getTimeInMillis());
				svaeorupdate(justLastRunDate);
			}
		} catch (Exception e) {
			throw new RuntimeException("Issue while parsing date - afterJob");
		}

    }
    
    
    public void svaeorupdate(Timestamp someOldDate) {
    	IndexRunDate indexRunDate=new IndexRunDate();
		indexRunDate.setIndexName("elasticsearchconvergence_index");
		indexRunDate.setMachineName(getMac());
		indexRunDate.setRunDate(someOldDate);
		try {
			indexRunDateService.saveData(indexRunDate);
		} catch (Exception e) {
			throw new RuntimeException("Issue while saving date - lastrunjob");
		}

    }
    
    private Calendar getNYCTime(){
    	
    	  TimeZone tz1 = TimeZone.getTimeZone("America/New_York");
		  Calendar cal = Calendar.getInstance(tz1); 
		  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss z YYYY");
		  format.setCalendar(cal); 
		  String nycDate=format.format(cal.getTime());
		  
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		  Date date1 = null;
		  try{
			  date1 = sdf.parse(nycDate);
		  }catch(ParseException e){
			  throw new RuntimeException("Issue while parsing");
		  }
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTimeZone(tz1);
		  calendar.setTime(date1);
		  return calendar;
    }

}
