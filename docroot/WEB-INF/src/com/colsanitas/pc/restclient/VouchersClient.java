package com.colsanitas.pc.restclient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.restclient.dto.PaymentResponseDTO;
import com.colsanitas.pc.restclient.dto.VouchersPaymentInfoDTO;
import com.colsanitas.pc.restclient.dto.VouchersPaymentInfoDTO.PaymentMethod;
import com.colsanitas.pc.util.RestRequest;
import com.colsanitas.pc.util.RestResponse;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.colsanitas.pc.util.RestUtil;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public final class VouchersClient {
	private static final Logger LOG = LoggerFactory.getLogger(VouchersClient.class);

	private VouchersClient() {

	}

	/**
	 * Send new vouchers payment
	 * @param paymentInfoDTO
	 * @param serviceURL
	 * @param authToken
	 * @return
	 * @throws RestClientException
	 */
	public static PaymentResponseDTO applyVouchersPayment(VouchersPaymentInfoDTO paymentInfoDTO,
			String serviceURL, String authToken) throws RestClientException {
		LOG.info("POST Call : apply vouchers payment");
		/*Create Call payload*/
		JSONObject payloadJsonObject = paymentInfoToJsonObject(paymentInfoDTO);
		LOG.debug(payloadJsonObject.toString(5));
		
		/*Create request object. set headers, payload and path parameters*/
		RestRequest postRequest = new RestRequest();
		postRequest.setPath(serviceURL);
		postRequest.setJsonPayload(payloadJsonObject.toString());
		LOG.info("POST reuquest send : "+payloadJsonObject.toString());
		
		postRequest.addHeader(RestConstants.HEADER_PROPERTY_CONTENT_TYPE, RestConstants.MIME_TYPE_JSON);
		if (authToken.isEmpty()) {
			postRequest.addHeader(RestConstants.HEADER_PROPERTY_AUTHORIZATION, authToken);
		}
		
		/*Call rest api*/
		RestResponse postResponse = RestUtil.httpMethodPOST(postRequest);
		
		/*Create json object from api response*/
		JSONObject responseJsonObject = new JSONObject(postResponse.getBody()).getJSONObject("resultado");
		PaymentResponseDTO responseDTO = new PaymentResponseDTO();
		responseDTO.setResponseCode(responseJsonObject.getString("codigoResultado"));
		responseDTO.setResponseMessage(responseJsonObject.getString("descripcionResultado"));
		LOG.info("Call success!");
		return responseDTO;
	}

	/**
	 * Create vouchers payment payload
	 * @param paymentInfoDTO
	 * @return
	 */
	private static JSONObject paymentInfoToJsonObject(VouchersPaymentInfoDTO paymentInfoDTO) {
		JSONObject paymentInfoJsonObject = new JSONObject();
		paymentInfoJsonObject.put("categoria", paymentInfoDTO.getCategory());
		paymentInfoJsonObject.put("canal", paymentInfoDTO.getChannel());
		paymentInfoJsonObject.put("ciudad", paymentInfoDTO.getCity());
		paymentInfoJsonObject.put("codigoCompania", paymentInfoDTO.getCompanyCode());
		paymentInfoJsonObject.put("concepto", paymentInfoDTO.getConcept());
		paymentInfoJsonObject.put("contrato", paymentInfoDTO.getContract());
		paymentInfoJsonObject.put("familia", paymentInfoDTO.getFamily());
		paymentInfoJsonObject.put("viaIngreso", paymentInfoDTO.getIncomeWay());
		paymentInfoJsonObject.put("plan", paymentInfoDTO.getPlan());
		paymentInfoJsonObject.put("numeroDocumentoPrestador", paymentInfoDTO.getProviderDocNumber());
		paymentInfoJsonObject.put("tipoDocumentoPrestador", paymentInfoDTO.getProviderDocType());
		paymentInfoJsonObject.put("cantidad", paymentInfoDTO.getQuantity());
		paymentInfoJsonObject.put("estado", paymentInfoDTO.getState());
		paymentInfoJsonObject.put("codigoEstacion", paymentInfoDTO.getStationCode());
		paymentInfoJsonObject.put("numeroTransaccion", paymentInfoDTO.getTransactionNumber());
		paymentInfoJsonObject.put("usuario", paymentInfoDTO.getUserCode());
		paymentInfoJsonObject.put("numeroDocumentoUsuario", paymentInfoDTO.getUserDocNumber());
		paymentInfoJsonObject.put("tipoDocumentoUsuario", paymentInfoDTO.getUserDocType());
		paymentInfoJsonObject.put("valorTotal", paymentInfoDTO.getTotalValue());
		paymentInfoJsonObject.put("vales", paymentInfoDTO.getVouchers());
		paymentInfoJsonObject.put("servicio", paymentInfoDTO.getService());
		JSONArray paymentMethods = new JSONArray();
		for (PaymentMethod paymentMethod : paymentInfoDTO.getPaymentMethods()) {
			JSONObject method = new JSONObject();
			method.put("codigoAsobancario", paymentMethod.getAsoBancariaCode());
			method.put("codigoMedio", paymentMethod.getCode());
			method.put("ivaTarjeta", paymentMethod.getCardTax());
			method.put("numeroAprobacion", paymentMethod.getApprovalNumber());
			method.put("numeroTarjeta", paymentMethod.getCardNumber());
			method.put("tipoMedio", paymentMethod.getType());
			method.put("valor", paymentMethod.getValue());
			paymentMethods.put(method);
		}
		paymentInfoJsonObject.put("metodosDePago", paymentMethods);
		return paymentInfoJsonObject;
	}
}
