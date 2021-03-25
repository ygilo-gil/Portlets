package com.colsanitas.pc.restclient;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.restclient.dto.PaymentZpDto;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.colsanitas.pc.util.RestRequest;
import com.colsanitas.pc.util.RestResponse;
import com.colsanitas.pc.util.RestUtil;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public final class PaymentRestClient {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentRestClient.class);

    private PaymentRestClient() {

    }

    /**
     * Verify payment info
     * 
     * @param transactionId
     * @param key
     * @param store
     * @param serviceUrl
     * @param authToken
     * @return
     * @throws RestClientException
     */
    public static PaymentZpDto verifyPayment(String transactionId, String key, String store, String serviceUrl,
            String authToken) throws RestClientException {
    	
        LOG.info("verifyPayment: Url {}, transactionId {}, key {}, store {}", serviceUrl, transactionId, key, store);
        
        /*Create request object, set headers and path parameters*/
        RestRequest getRequest = new RestRequest();
        getRequest.setPath(serviceUrl);
        getRequest.addPathParameter("transaccionid", transactionId);
        getRequest.addPathParameter("tienda", store);
        getRequest.addPathParameter("clave", key);
        getRequest.addHeader(RestConstants.HEADER_PROPERTY_ACCEPT, RestConstants.MIME_TYPE_JSON);
        if (!authToken.isEmpty()) {
            getRequest.addHeader(RestConstants.HEADER_PROPERTY_AUTHORIZATION, authToken);
        }
        
        /*Call rest api*/
        RestResponse getResponse = RestUtil.httpMehodGET(getRequest);
        
        System.out.println("______EL RESPONSE DEL PAGO ES ..."+getResponse.getBody().toString());
        
        /*Create json object from api response*/
        JSONObject paymentInfo = new JSONObject(getResponse.getBody()).getJSONObject("informacionPago");
        LOG.info("------> paymentInfo: " + paymentInfo.toString());
        
        /*Create payment verified object*/
        PaymentZpDto dto = new PaymentZpDto();
        dto.setApprovalCode(paymentInfo.getString("codigoAprobacion"));
        dto.setBankCode(paymentInfo.getLong("codigoBanco"));
        dto.setBankName(paymentInfo.getString("nombreBanco"));
        dto.setClientId(paymentInfo.getString("idCliente"));
        dto.setFranchise(paymentInfo.getString("franquicia"));
        dto.setKey(paymentInfo.getString("clave"));
        dto.setOptional1(paymentInfo.getString("infoOpcional1"));
        dto.setOptional2(paymentInfo.getString("infoOpcional2"));
        dto.setOptional3(paymentInfo.getString("infoOpcional3"));
        dto.setPaymentMethod(paymentInfo.getString("idMetodoPago"));
        dto.setPaymentState(paymentInfo.getLong("estadoPago"));
        dto.setPaymentValue(paymentInfo.getDouble("valorPago"));
        dto.setPseDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
        dto.setServiceCode(paymentInfo.getLong("codigoServicio"));
        dto.setTicketId(paymentInfo.getLong("tickedID"));
        dto.setTransactionCode(paymentInfo.getLong("codigoTransaccion"));
        dto.setTransactionCycle(paymentInfo.getLong("cicloTransaccion"));
        dto.setTransactionId(paymentInfo.getString("idPago"));
        return dto;

    }
}
