package com.nbc.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

import com.nbc.app.domain.request.SearchParam;
import com.nbc.app.domain.response.ResponseObject;
import com.nbc.app.services.ConvergenceSearchService;


@RestController
public class ConvergenceSearchController {

	@Autowired
	private ConvergenceSearchService convergenceSearchService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/{index}/{indexType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiIgnore
	public ResponseEntity<ResponseObject> extractConvergence(@RequestBody SearchParam params,
			@RequestParam(required = false) Long pageSize, @PathVariable String index, @PathVariable String indexType) {
		List<Map<String, Object>> convergenceExtract = null;
		Map resMap = new HashMap();
		ResponseObject resObj = null;
		boolean searchFlag = true;
		int loopCount = 0;
		int totalPage = 0;
		int remainder = 0;
		int totalResult = 0;
		try {
			pageSize = pageSize != null ? pageSize : 100;
			resMap = convergenceSearchService.searchByFieldName(params, null, pageSize,index,indexType,"Y");

			if (resMap != null) {
				totalResult = Integer.parseInt(resMap.get("total").toString());
				totalPage = (totalResult / pageSize.intValue());
				remainder = totalResult % pageSize.intValue();
				if (remainder > 0) {
					totalPage = totalPage + 1;
				}
				resMap.put("totalPage", totalPage);
				convergenceExtract = (List<Map<String, Object>>) resMap.get("convergenceExtract");
			}
			if (convergenceExtract != null) {
				resObj = new ResponseObject();
				resObj.setSuccess(true);
				resObj.setTotalCount(totalResult);
				resObj.setCount(convergenceExtract.size());
				resObj.setResult(convergenceExtract);

				return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);
			} else
				return new ResponseEntity<ResponseObject>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			resObj = new ResponseObject(e.getMessage());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.PRECONDITION_FAILED);
		}
	}

	@RequestMapping(value = "/{index}/{indexType}/page/{pageNumber}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> extractConvergenceByPage(
			@ApiParam(value="Fields input values :  [\"name\", \"id\",\"agencyId\",\"agencyName\", \"advertiserName\", \"demo\"]"
					+ "  ResultFields input values :  [\"name\", \"id\", \"agencyName\", \"advertiserName\", \"demo\"]"
					+ "          (To be modified) ")@RequestBody SearchParam params,
			@ApiParam(value="Specify Page Number",required=true,defaultValue="1") @PathVariable Long pageNumber,
			@RequestParam(required = false,defaultValue="50") Long pageSize,
			@ApiParam(value="Specify Index Name",required=true,defaultValue="convergence") @PathVariable String index,
			@ApiParam(value="Specify Index Type",required=true,defaultValue="convergence")@PathVariable String indexType) {
		List<Map<String, Object>> convergenceExtract = null;
		Map resMap = new HashMap();
		ResponseObject resObj = null;
		boolean searchFlag = true;
		int loopCount = 0;
		int totalPage = 0;
		int remainder = 0;
		int totalResult = 0;
		Long zeroValue = 0L;

		try {
			if (params.getMultiMatch() != null) {
				if (StringUtils.isEmpty(params.getMultiMatch().getInput())) {
					resObj = new ResponseObject("Please enter input value");
					return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
				}

				if (params.getMultiMatch().getFields() == null || params.getMultiMatch().getFields() != null
						&& params.getMultiMatch().getFields().size() < 1) {
					resObj = new ResponseObject("Please enter the fields against which the search needs to be done");
					return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
				}

			}
			pageSize = pageSize != null ? pageSize : 100;
			if (zeroValue.equals(pageNumber)) {
				resObj = new ResponseObject("Not a valid page");
				return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
			}
			pageNumber = pageNumber - 1;
			resMap = convergenceSearchService.searchByFieldName(params, pageNumber, pageSize,index,indexType,"N");

			if (resMap != null) {
				totalResult = Integer.parseInt(resMap.get("total").toString());
				totalPage = (totalResult / pageSize.intValue());
				remainder = totalResult % pageSize.intValue();
				if (remainder > 0) {
					totalPage = totalPage + 1;
				}
				resMap.put("totalPage", totalPage);
				convergenceExtract = (List<Map<String, Object>>) resMap.get("convergenceExtract");
			}
			resObj = new ResponseObject();
			resObj.setSuccess(true);
			resObj.setTotalCount(totalResult);
			resObj.setTotalPage(totalPage);
			if (convergenceExtract != null && convergenceExtract.size() > 0) {
				resObj.setCount(convergenceExtract.size());
				resObj.setResult(convergenceExtract);
				return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);
			} else {
				resObj.setCount(0);
				resObj.setResult(new ArrayList<Map<String, Object>>());
				return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);
			}
		} catch (Exception e) {
			resObj = new ResponseObject(e.getMessage());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.PRECONDITION_FAILED);
		}
	}

}
