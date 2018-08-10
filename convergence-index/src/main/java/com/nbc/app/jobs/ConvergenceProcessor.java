package com.nbc.app.jobs;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.nbc.app.domain.convgdeal.ConvergenceDeal;
import com.nbc.app.domain.index.ConvergenceIndex;

public class ConvergenceProcessor implements ItemProcessor<ConvergenceDeal, ConvergenceIndex> {

	private Logger logger = getLogger(getClass());

	@Override
	public ConvergenceIndex process(ConvergenceDeal convergenceDeal) throws Exception {

		try {
			ConvergenceIndex convergenceIndex = new ConvergenceIndex();
			
			// ConvergenceDeal
			convergenceIndex.setId(convergenceDeal.getId());
			convergenceIndex.setName(convergenceDeal.getName());
			// Agency
			convergenceIndex.setAgencyId(convergenceDeal.getAgency().getId());
			convergenceIndex.setAgencySFId(convergenceDeal.getAgency().getSfId());
			convergenceIndex.setAgencyName(convergenceDeal.getAgency().getName());
			// Advertiser
			convergenceIndex.setAdvertiserId(convergenceDeal.getAdvertiser().getId());
			convergenceIndex.setAdvertiserSFId(convergenceDeal.getAdvertiser().getSfId());
			convergenceIndex.setAdvertiserName(convergenceDeal.getAdvertiser().getName());
			// Demo
			convergenceIndex.setDemoId(convergenceDeal.getDemo().getId());
			convergenceIndex.setDemo(convergenceDeal.getDemo().getName());
			
			//enabled
			convergenceIndex.setEnabled(convergenceDeal.getEnabled());
			
			return convergenceIndex;

		} catch (Exception e) {
			throw new RuntimeException("Error occured while processing the convergence data to ES :" + e.getMessage());
		}
	}

}
