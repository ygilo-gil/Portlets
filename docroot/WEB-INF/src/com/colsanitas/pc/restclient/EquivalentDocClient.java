package com.colsanitas.pc.restclient;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.util.RestRequest;
import com.colsanitas.pc.util.RestUtil;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.colsanitas.pc.util.RestResponse;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public final class EquivalentDocClient {
    private static final Logger LOG = LoggerFactory.getLogger(EquivalentDocClient.class);
	private EquivalentDocClient() {
	}
	/**
	 * 
	 * Get equivalent pdf
	 * @param company
	 * @param station
	 * @param transactionNumber
	 * @param serviceUrl
	 * @param authToken
	 * @return
	 * @throws RestClientException
	 */
	public static String getEquivalentDocUrl(String company, String station, String transactionNumber,
			String serviceUrl, String authToken) throws RestClientException {
	        LOG.info("GET Call : Get equivalent document url");
	        LOG.info("Getting url document for company {}, station {} and transaction number {}", company, station, transactionNumber);
	        /*Create request object, set headers and path parameters*/
			RestRequest getRequest = new RestRequest();
			getRequest.setPath(serviceUrl);
			getRequest.addPathParameter("codigocompania", company );
			getRequest.addPathParameter("estacion", station);
			getRequest.addPathParameter("numerotransaccion", transactionNumber);
			getRequest.addHeader(RestConstants.HEADER_PROPERTY_ACCEPT, RestConstants.MIME_TYPE_JSON);
			if (!authToken.isEmpty()) {
				getRequest.addHeader(RestConstants.HEADER_PROPERTY_AUTHORIZATION, authToken);
			}
			
			/*Call rest api*/
			RestResponse getResponse = RestUtil.httpMehodGET(getRequest);
			/*Create json object from api response*/
			JSONObject responseObject = new JSONObject(getResponse.getBody());
			/*Get document url*/
			String url = responseObject.getJSONObject("documentoEquivalente").getString("url");
			return url;
	}
}
