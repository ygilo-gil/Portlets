package com.colsanitas.pc.restclient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.restclient.dto.PaymentZpDto;
import com.colsanitas.pc.restclient.dto.Transaction;
import com.colsanitas.pc.restclient.dto.Transaction.TransactionDetail;
import com.colsanitas.pc.util.RestRequest;
import com.colsanitas.pc.util.RestRequest.RestRequestHeader;
import com.colsanitas.pc.util.RestResponse;
import com.colsanitas.pc.util.RestUtil;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public final class TransactionsClient {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionsClient.class);
    private static final String TRANSANTION_CREDIT_CART = "5|31|32";
    private static final String TRANSANTION_PSE = "4|29";

    private TransactionsClient() {
    }

    /**
     * Update payment transaction
     * 
     * @param paymentVoucher
     * @param serviceUrl
     * @param authToken
     * @throws RestClientException
     */
    public static void updateTransaction(PaymentZpDto paymentVoucher, String serviceUrl, String authToken)
            throws RestClientException {
        LOG.info("PUT Call : Update transaction call");
        /* Validate Payment method and create put payload */
        String jsonPayload = "";

        if (paymentVoucher.getPaymentMethod().matches(TRANSANTION_PSE)) {
            jsonPayload += "{" + "\"informacionPago\": {" + "\"estadoTransaccion\": \""
                    + paymentVoucher.getTransactionState() + "\"" + "}," + "\"comprobantePago\": {"
                    + "\"codigoBanco\": \"" + paymentVoucher.getBankCode() + "\"," + "\"nombreBanco\": \""
                    + paymentVoucher.getBankName() + "\"," + "\"numeroDocumentoPagador\": \""
                    + paymentVoucher.getClientId() + "\"," + "\"fechaPSE\": \"" + paymentVoucher.getPseDate() + "\","
                    + "\"infoOpcional1\": \"" + paymentVoucher.getOptional1() + "\"," + "\"infoOpcional2\": \""
                    + paymentVoucher.getOptional2() + "\"," + "\"infoOpcional3\": \"" + paymentVoucher.getOptional3()
                    + "\"," + "\"claveNueva\": \"" + paymentVoucher.getKey() + "\"," + "\"metodoPago\": \""
                    + paymentVoucher.getPaymentMethod() + "\"," + "\"estadoPago\": \""
                    + paymentVoucher.getPaymentState() + "\"," + "\"codigoServicioDetalle\": \""
                    + paymentVoucher.getServiceCode() + "\"," + "\"identificadorTicket\": \""
                    + paymentVoucher.getTicketId() + "\"," + "\"codigoTransaccion\": \""
                    + paymentVoucher.getTransactionCode() + "\"," + "\"valorPagado\": "
                    + paymentVoucher.getPaymentValue() + "," + "\"cicloTransaccion\": \""
                    + paymentVoucher.getTransactionCycle() + "\"" + "}" + "}";
        } else if (paymentVoucher.getPaymentMethod().matches(TRANSANTION_CREDIT_CART)) {
        	
            jsonPayload += "{" + "\"informacionPago\": {" + "\"estadoTransaccion\": \""
                    + paymentVoucher.getTransactionState() + "\"" + "}," + "\"comprobantePago\": {"
                    + "\"numeroDocumentoPagador\": \"" + paymentVoucher.getClientId() + "\"," + "\"fechaPSE\": \""
                    + paymentVoucher.getPseDate() + "\"," + "\"infoOpcional1\": \"" + paymentVoucher.getOptional1()
                    + "\"," + "\"infoOpcional2\": \"" + paymentVoucher.getOptional2() + "\"," + "\"infoOpcional3\": \""
                    + paymentVoucher.getOptional3() + "\"," + "\"franquicia\": \"" + paymentVoucher.getFranchise()
                    + "\"," + "\"claveNueva\": \"" + paymentVoucher.getKey() + "\"," + "\"metodoPago\": \""
                    + paymentVoucher.getPaymentMethod() + "\"," + "\"estadoPago\": \""
                    + paymentVoucher.getPaymentState() + "\"," + "\"identificadorTicket\": \""
                    + paymentVoucher.getTicketId() + "\"," + "\"valorPagado\": " + paymentVoucher.getPaymentValue()
                    + "," + "\"codigoAprobacion\": \"" + paymentVoucher.getApprovalCode() + "\""
                    + "," + "\"cicloTransaccion\": \"" + paymentVoucher.getTransactionCycle() + "\""
                    + "," + "\"codigoTransaccion\": \"" + paymentVoucher.getTransactionCode() + "\""
                    + "," + "\"codigoBanco\": \"" + paymentVoucher.getBankCode() + "\""
                    + "}" + "}";
        }

        /* Create request object, set headers and path parameters */
        RestRequest putRequest = new RestRequest();
        putRequest.setPath(serviceUrl);
        putRequest.addPathParameter("transaccionidentificador", paymentVoucher.getTransactionId());
        putRequest.setJsonPayload(jsonPayload);
        putRequest.addHeader(RestConstants.HEADER_PROPERTY_CONTENT_TYPE, RestConstants.MIME_TYPE_JSON);
        if (!authToken.isEmpty()) {
            putRequest.addHeader(RestConstants.HEADER_PROPERTY_AUTHORIZATION, authToken);
        }

        /* Call rest api */
        RestResponse putResponse = RestUtil.httpMethodPUT(putRequest);

        /* Create json object from api response */
        String out = putResponse.getBody().replace("\"{\"@nil\":\"true\"}\"", "\"\"");

        JSONObject responseObject = new JSONObject(out);
        JSONObject resultObject = responseObject.getJSONObject("resultado");
        Integer resultCode = resultObject.getInt("codigoResultado");
        if (resultCode == -1) {
            throw new RestClientException(resultObject.getString("descripcionResultado"));
        } else {
            LOG.info("Update transaction success");
        }
    }

    
    
    
    
    
    
    
    
    public static void updateDirectTransaction (String id ,Transaction transaction, String serviceUrl, String authToken){
    	
    	LOG.info("_____________ Update direct transaction Method _______________ ");
    	
    	String jsonPayload="";
    	
    	   jsonPayload += "{" + "\"informacionPago\": {" + "\"estadoTransaccion\": \""
                   + transaction.getTransactionState() + "\"" + "}," + "\"comprobantePago\": {"
                   + "\"numeroDocumentoPagador\": \"" + transaction.getClientId() + "\"," + "\"fechaPSE\": \""
                   + transaction.getRegDate() + "\"," + "\"infoOpcional1\": \"" + transaction.getInfo1()
                   + "\"," + "\"infoOpcional2\": \"" + transaction.getInfo2() + "\"," + "\"infoOpcional3\": \""
                   + transaction.getInfo3() + "\"," + "\"franquicia\": \"" + transaction.getDetail().getFranchise()
                   + "\"," + "\"claveNueva\": \"" + transaction.getDetail().getTransactionCode() + "\"," + "\"metodoPago\": \""
                   + transaction.getDetail().getPaymentMethod() + "\"," + "\"estadoPago\": \""
                   + transaction.getDetail().getPaymentState() + "\"," + "\"identificadorTicket\": \""
                   + transaction.getDetail().getTicketId() + "\"," + "\"valorPagado\": " + transaction.getDetail().getTotalPayed()
                   + "," + "\"codigoAprobacion\": \"" + transaction.getDetail().getSuccessCode() + "\""
                   + "," + "\"cicloTransaccion\": \"" + transaction.getDetail().getTransactionCycle() + "\""
                   + "," + "\"codigoTransaccion\": \"" + transaction.getDetail().getTransactionCode() + "\""
                   + "," + "\"codigoBanco\": \"" + transaction.getDetail().getBankCode() + "\""
                   + "}" + "}";
    	
    	
    	   /* Create request object, set headers and path parameters */
           RestRequest putRequest = new RestRequest();
           putRequest.setPath(serviceUrl);
           putRequest.addPathParameter("transaccionidentificador",id);
           putRequest.setJsonPayload(jsonPayload);
           putRequest.addHeader(RestConstants.HEADER_PROPERTY_CONTENT_TYPE, RestConstants.MIME_TYPE_JSON);
           if (!authToken.isEmpty()) {
               putRequest.addHeader(RestConstants.HEADER_PROPERTY_AUTHORIZATION, authToken);
           }

           /* Call rest api */
           RestResponse putResponse;
		try {
			putResponse = RestUtil.httpMethodPUT(putRequest);
			
		    /* Create json object from api response */
	           String out = putResponse.getBody().replace("\"{\"@nil\":\"true\"}\"", "\"\"");

	           JSONObject responseObject = new JSONObject(out);
	           JSONObject resultObject = responseObject.getJSONObject("resultado");
	           Integer resultCode = resultObject.getInt("codigoResultado");
	           if (resultCode == -1) {
	               throw new RestClientException(resultObject.getString("descripcionResultado"));
	           } else {
	               LOG.info("_____________ Update direct transaction success _______________ ");
	           }
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
		
    	   
    	
    }
    
    
    
    /**
     * Get transaction by id
     * 
     * @param id
     * @param serviceUrl
     * @param authToken
     * @return
     * @throws RestClientException
     */
    public static Transaction getTransaction(String id, String serviceUrl, String authToken) throws RestClientException {
        LOG.info("Rest call : Get transaction");
        LOG.debug("Getting trasaction for id : {}", id);
        try {
            /* Create request object. Set headers and path parameters */
            RestRequest getRequest = new RestRequest();
            getRequest.setPath(serviceUrl);
            getRequest.addPathParameter("idtransaccion", id);
            getRequest.addHeader(RestConstants.HEADER_PROPERTY_ACCEPT, RestConstants.MIME_TYPE_JSON);
            if (!authToken.isEmpty()) {
                getRequest.addHeader(RestConstants.HEADER_PROPERTY_AUTHORIZATION, authToken);
            }
            LOG.debug("---- : "+getRequest.getJsonPayload());
            LOG.debug("---- : "+getRequest.getPath());
            LOG.debug("---- : "+RestConstants.HEADER_PROPERTY_ACCEPT);
            LOG.debug("---- : "+RestConstants.HEADER_PROPERTY_AUTHORIZATION+" :::authToken::: "+authToken);
            
            
           
            
            /* Call rest api */
            RestResponse getResponse = RestUtil.httpMehodGET(getRequest);

            /* Create json object from api response */
            String out = getResponse.getBody().replace("\"{\"@nil\":\"true\"}\"", "\"\"")
                    .replace("{\"@nil\":\"true\"}", "0");
            
            LOG.debug("#### out: "+out);
            JSONObject responseObject = new JSONObject(out);
            
            LOG.debug("#### responseObject: "+responseObject.toString());
            
            /* Create transaction object */
            JSONObject paymentInfo = responseObject.getJSONObject("informacionPago");
            JSONObject payerInfo = responseObject.getJSONObject("informacionPagador");
            Transaction transaction = new Transaction();
            transaction.setContract(paymentInfo.getString("contrato"));
            transaction.setFamily(paymentInfo.getString("familia"));
            transaction.setUserNumber(paymentInfo.getString("numeroUsuario"));
            transaction.setPlan(paymentInfo.getString("plan"));
            transaction.setProduct(paymentInfo.getString("producto"));
            transaction.setTotal(paymentInfo.getDouble("ivaTotal"));
            transaction.setTax(paymentInfo.getDouble("ivaValor"));
            transaction.setPaymentDecription(paymentInfo.getString("descripcionPago"));
            transaction.setPaymentReference(paymentInfo.getString("referenciaPago"));
            transaction.setPaymentSource(paymentInfo.getString("origenPago"));
            transaction.setPaymentType(paymentInfo.getString("tipoPago"));
            transaction.setInfo1(paymentInfo.getString("infoOpcional1"));
            transaction.setInfo2(paymentInfo.getString("infoOpcional2"));
            transaction.setInfo3(paymentInfo.getString("infoOpcional3"));
            transaction.setRegDate(paymentInfo.getString("fechaRegistro"));
            transaction.setTransactionState(paymentInfo.getString("estadoTransaccion"));
            transaction.setTransactionReason(paymentInfo.getString("motivoTransaccion"));
            transaction.setCollection(paymentInfo.getString("recaudo"));
            transaction.setVouchersCodes(paymentInfo.getString("valesAsignados").replace(StringPool.COMMA,
                    StringPool.COMMA + StringPool.SPACE));
            transaction.setCityCode(paymentInfo.getString("ciudadpago"));
            transaction.setVouchersQuantity(paymentInfo.getString("cantidadVales"));
            transaction.setClientId(payerInfo.getString("numeroDocumentoPagador"));
            transaction.setClientIdType(payerInfo.getString("tipoDocumentoPagador"));
            transaction.setClientName(payerInfo.getString("nombrePagador"));
            transaction.setClientLastname(payerInfo.getString("apellidoCliente"));
            transaction.setClientPhone(payerInfo.getString("telefonoPagador"));
            transaction.setClientEmail(payerInfo.getString("email"));
            transaction.setClientIpAdress(payerInfo.getString("ipAddres"));

            JSONObject detailInfo = responseObject.getJSONObject("comprobantePago");
            TransactionDetail detail = new TransactionDetail();
            detail.setSuccessCode(detailInfo.getString("codigoAprobacion"));
            detail.setBankCode(detailInfo.getString("codigoBanco"));
            detail.setBankName(detailInfo.getString("nombreBanco"));
            detail.setClientId(detailInfo.getString("numeroDocumentoPagador"));
            detail.setPseDate(detailInfo.getString("fechaPSE"));
            detail.setInfo1(detailInfo.getString("infoOpcional1"));
            detail.setInfo2(detailInfo.getString("infoOpcional2"));
            detail.setInfo3(detailInfo.getString("infoOpcional3"));
            detail.setFranchise(detailInfo.getString("franquicia"));
            detail.setPaymentMethod(detailInfo.getString("metodoPago"));
            detail.setPaymentState(detailInfo.getString("estadoPago"));
            detail.setTicketId(detailInfo.getString("identificadorTicket"));
            detail.setTransactionCode(detailInfo.getString("codigoTransaccion"));
            detail.setTotalPayed(detailInfo.getDouble("valorPagado"));
            detail.setTransactionCycle(detailInfo.getString("cicloTransaccion"));

            if (!detail.getPseDate().isEmpty()) {
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
                DateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                detail.setPseDate(dateFormat.format(inFormat.parse(detail.getPseDate())));
            }

            transaction.setDetail(detail);

            LOG.info("Call success");
            return transaction;
        } catch (ParseException e) {
            LOG.error("Get Transaction error : {}", e.getMessage());
            throw new RestClientException(e);
        }

    }

}
