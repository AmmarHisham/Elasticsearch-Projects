package com.nbc.app.jobs;

import static org.springframework.util.ClassUtils.getShortName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.nbc.app.dao.ConvergenceRepository;
import com.nbc.app.domain.convgdeal.ConvergenceDeal;

@RefreshScope
public class ConvergenceReader extends AbstractPaginatedDataItemReader<ConvergenceDeal> implements InitializingBean {

	private Pageable convergencePageable;
	private String indexDate;
	
	
	@Value("${pageStartLimit}")
	private String pageStartLimit;
	
	@Value("${pageEndLimit}")
	private String pageEndLimit;

	@Autowired
	private ConvergenceRepository convergenceRepository;

	@PostConstruct
	public void init() {
		setName(getShortName(getClass()));
		this.convergencePageable = new PageRequest(Integer.parseInt(pageStartLimit), Integer.parseInt(pageEndLimit));
	}

	@BeforeStep
	public void initializeValues(StepExecution stepExecution) {

		this.indexDate = stepExecution.getJobExecution().getExecutionContext().getString("indexDate");

	}

	@Override
	protected Iterator<ConvergenceDeal> doPageRead() {

		Page<ConvergenceDeal> convergence = null;
		boolean stop = false;

		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date date;
			date = format.parse(this.indexDate);

			//convergence = convergenceRepository.findByModifiedOnAfterAndEnabled(date,true,this.convergencePageable);
			convergence = convergenceRepository.findByModifiedOnAfter(date,this.convergencePageable);

			if (convergence == null || (convergence != null && convergence.getNumberOfElements() <= 0)) {
				setName(getShortName(getClass()));
				this.convergencePageable = new PageRequest(Integer.parseInt(pageStartLimit), Integer.parseInt(pageEndLimit));
			} else {
				convergencePageable = convergencePageable.next();
			}

		} catch (ParseException e) {
			throw new RuntimeException("Error occured while reading convergence data from DB :" + e.getMessage());
		}
		return convergence.iterator();
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
