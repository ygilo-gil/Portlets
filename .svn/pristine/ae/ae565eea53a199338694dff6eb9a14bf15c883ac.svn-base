package com.colsanitas.pc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.oauth2manager.token.OAuth2Token;
import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.restclient.PaymentRestClient;
import com.colsanitas.pc.restclient.TransactionsClient;
import com.colsanitas.pc.restclient.VouchersClient;
import com.colsanitas.pc.restclient.dto.PaymentResponseDTO;
import com.colsanitas.pc.restclient.dto.PaymentZpDto;
import com.colsanitas.pc.restclient.dto.Transaction;
import com.colsanitas.pc.restclient.dto.VouchersPaymentInfoDTO;
import com.colsanitas.pc.restclient.dto.VouchersPaymentInfoDTO.PaymentMethod;
import com.colsanitas.pc.util.Constants.PortletConstants;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.liferay.portal.kernel.util.StringPool;

/**
 * Servlet implementation class PaymentConfirmationServlet
 */

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
@WebServlet("/ConfirmacionPago")
public class PaymentConfirmationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(PaymentConfirmationServlet.class);
    private static final String TRANSACTION_CONFIRMED_CODE = "3";
    private static final String TRANSACTION_RECHAZADA = "7";
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentConfirmationServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
        LOG.info("Starting Servlet GET Method");
        System.out.println("______INICIANDO SERVLET CONFIRMACION PAGO RUTA GET ZP _____");
        LOG.info(">>>>>>>>>>> STATE FROM ZP :  "+request.getParameter("estado_pago") +"  >>>>>>>>>>>>>>>>");
        String estado = request.getParameter("estado_pago");
        
  	   /* Get authorization token for call protected rest apis */
        String authToken = OAuth2Token.getToken(PortletConstants.PORTLET_GROUP);
        LOG.info("Using auth token : {}", authToken);
        /* Get reques mapped object */
    
    
        if(estado != null && !estado.equals("") && !estado.equals("0")){
        
            PaymentZpDto paymentZpDto = getPaymentObjectFromRequest(request, authToken);
        	LOG.info(">>>>>>>>>>> Estado 'Exitoso' zona pagos ruta GET TEST  >>>>>>>>>>>>");
        
        	   Transaction transaction = TransactionsClient.getTransaction(paymentZpDto.getTransactionId(),
                       RestConstants.TRANSACTIONS_PATH, authToken);
        	   
        	LOG.info(">>>>>>>>>>> Estado anterior transaccion  >>>>>>>>>>>>  "+transaction.getTransactionState());
        	
        	
        	   if( !transaction.getTransactionState().equals("10") && !transaction.getTransactionState().equals("99")&& 
        			   !transaction.getTransactionState().equals("4") ){
        		   LOG.info(">>>>>>>>>>> Estado anterior transaccion != 10 !=99  != 4 >>>>>>>>>>>>  ");
        		   paymentZpDto.setTransactionState(TRANSACTION_CONFIRMED_CODE);
                   TransactionsClient.updateTransaction(paymentZpDto, RestConstants.TRANSACTIONS_PATH, authToken);   
        	   }
                
                

                /* Validate transaction concept */
                if ("MP-PIN".equals(paymentZpDto.getOptional3())) {
                	LOG.info("___payment state  mp-pin ____"+paymentZpDto.getPaymentState());
                	if(paymentZpDto.getPaymentState().equals(new Long(1))){
                		paymentZpDto.setTransactionState("4");
                		
                		//LOG.info(">>>>>>>>>>> CONTROL 3");
                		TransactionsClient.updateTransaction(paymentZpDto, RestConstants.TRANSACTIONS_PATH, authToken);
                	}
                	
                	//LOG.info(">>>>>>>>>>> CONTROL 4");
                 

                    /* Create vaouchers object */
                    VouchersPaymentInfoDTO paymentInfoDTO = new VouchersPaymentInfoDTO();
                    paymentInfoDTO.setCategory(StringPool.BLANK);
                    paymentInfoDTO.setChannel(PortletConstants.PAYMENT_CHANNEL);
                    paymentInfoDTO.setCompanyCode(transaction.getProduct());
                    paymentInfoDTO.setConcept(PortletConstants.PAYMENT_CONCEPT);
                    paymentInfoDTO.setContract(transaction.getContract());
                    paymentInfoDTO.setFamily(transaction.getFamily());
                    paymentInfoDTO.setIncomeWay(StringPool.BLANK);
                    paymentInfoDTO.setPlan(transaction.getPlan());
                    paymentInfoDTO.setProviderDocNumber(StringPool.BLANK);
                    paymentInfoDTO.setProviderDocType(StringPool.BLANK);
                    paymentInfoDTO.setQuantity(transaction.getVouchersQuantity());
                    paymentInfoDTO.setState(PortletConstants.PAYMENT_STATE);
                    paymentInfoDTO.setStationCode(PortletConstants.PAYMENT_STATION_CODE);
                    paymentInfoDTO.setTransactionNumber(paymentZpDto.getTransactionId());
                    paymentInfoDTO.setUserCode(transaction.getUserNumber());
                    
                    if(transaction.getInfo2()!= null && transaction.getInfo2().split("-").length >=4){
                    	LOG.info("_______ la transaccion "+paymentZpDto.getTransactionId()+" trae informacion del usuario logueado _____");
                    	String infoLogin [] = transaction.getInfo2().split("-");
                    	  paymentInfoDTO.setUserDocNumber(infoLogin [2]);
                          paymentInfoDTO.setUserDocType(infoLogin [3]);
                          LOG.info("_____login user transaction_____"+infoLogin [2]+"-"+infoLogin [3] +"  _______");
                    }else{
                    	LOG.info("_______ la transaccion "+paymentZpDto.getTransactionId()+" NO! trae informacion del usuario logueado _____");
                        paymentInfoDTO.setUserDocNumber(transaction.getClientId());
                        paymentInfoDTO.setUserDocType(transaction.getClientIdType());
                    }

                    paymentInfoDTO.setTotalValue(transaction.getTotal().toString());
                    paymentInfoDTO.setVouchers(StringPool.BLANK);
                    paymentInfoDTO.setService(StringPool.BLANK);
                    if (transaction.getCityCode().isEmpty()) {
                        paymentInfoDTO.setCity(PortletConstants.PAYMENT_DEFAULT_CITY_CODE);
                    } else {
                        paymentInfoDTO.setCity(transaction.getCityCode());
                    }
                    PaymentMethod method = new PaymentMethod();
                    method.setCardTax(transaction.getTax().toString());
                    method.setCardNumber(PortletConstants.PAYMENT_METHOD_CARD_NUMBER);
                    method.setType(PortletConstants.PAYMENT_METHOD_TYPE);
                    method.setValue(transaction.getTotal().toString());
                    if (paymentZpDto.getPaymentMethod().matches("4|29")) {
                        method.setCode(PortletConstants.PAYMENT_METHOD_CODE_PSE);
                        method.setAsoBancariaCode(paymentZpDto.getBankCode().toString().substring(1));
                        method.setApprovalNumber(paymentZpDto.getTicketId().toString());
                    } else if (paymentZpDto.getPaymentMethod().matches("5|31|32")) {
                        method.setCode(PortletConstants.PAYMENT_METHOD_CODE_CREDIT_CARD);
                        method.setAsoBancariaCode(getFranchiseCode(paymentZpDto.getFranchise()));
                        method.setApprovalNumber(paymentZpDto.getApprovalCode());
                    }
                    List<PaymentMethod> methods = new ArrayList<VouchersPaymentInfoDTO.PaymentMethod>();
                    methods.add(method);
                    paymentInfoDTO.setPaymentMethods(methods);
                    
//                    System.out.println("-----------------------------------------------");
//                    System.out.println("getCategory: " + paymentInfoDTO.getCategory());
//                    System.out.println("getChannel: " + paymentInfoDTO.getChannel());
//                    System.out.println("getCity: " + paymentInfoDTO.getCity());
//                    System.out.println("getCompanyCode: " + paymentInfoDTO.getCompanyCode());
//                    System.out.println("getConcept: " + paymentInfoDTO.getConcept());
//                    System.out.println("getContract: " + paymentInfoDTO.getContract());
//                    System.out.println("getFamily: " + paymentInfoDTO.getFamily());
//                    System.out.println("getIncomeWay: " + paymentInfoDTO.getIncomeWay());
//                    System.out.println("getPlan: " + paymentInfoDTO.getPlan());
//                    System.out.println("getProviderDocNumber: " + paymentInfoDTO.getProviderDocNumber());
//                    System.out.println("getProviderDocType: " + paymentInfoDTO.getProviderDocType());
//                    System.out.println("getQuantity: " + paymentInfoDTO.getQuantity());
//                    System.out.println("getState: " + paymentInfoDTO.getState());
//                    System.out.println("getStationCode: " + paymentInfoDTO.getStationCode());
//                    System.out.println("getTotalValue: " + paymentInfoDTO.getTotalValue());
//                    System.out.println("getTransactionNumber: " + paymentInfoDTO.getTransactionNumber());
//                    System.out.println("getUserCode: " + paymentInfoDTO.getUserCode());
//                    System.out.println("getUserDocNumber: " + paymentInfoDTO.getUserDocNumber());
//                    System.out.println("getUserDocType: " + paymentInfoDTO.getUserDocType());
//                    System.out.println("getVouchers: " + paymentInfoDTO.getVouchers());
//                    System.out.println("------->");
//                    for (PaymentMethod cada : paymentInfoDTO.getPaymentMethods()) {
//                    	System.out.println("------------>getApprovalNumber : "+cada.getApprovalNumber());
//                    	System.out.println("------------>getAsoBancariaCode : "+cada.getAsoBancariaCode());
//                    	System.out.println("------------>getCardNumber : "+cada.getCardNumber());
//                    	System.out.println("------------>getCardTax : "+cada.getCardTax());
//                    	System.out.println("------------>getCode : "+cada.getCode());
//                    	System.out.println("------------>getType : "+cada.getType());
//                    	System.out.println("------------>getValue : "+cada.getValue());
//    				}
                    
//                    LOG.info("-------------->");
                    LOG.info(">> VouchersClient.applyVouchersPayment: " + paymentInfoDTO.toString());
//                    LOG.info("<-------------");
                    
                    PaymentResponseDTO responseDTO = VouchersClient.applyVouchersPayment(paymentInfoDTO,
                            RestConstants.VOUCHERS_PAYMENT_PATH, authToken);
                    LOG.debug("Apply vouchers payment response : {}", responseDTO.getResponseMessage());
                    
                }else if("MP-CON".equals(paymentZpDto.getOptional3())){
                	 LOG.debug(" Payment Type MP-CON ");
                		 paymentZpDto.setTransactionState("18");
             		TransactionsClient.updateTransaction(paymentZpDto, RestConstants.TRANSACTIONS_PATH, authToken);
             		 LOG.debug(" Apply  Payment Transaction state 18  for  MP-CON ");
                }
                
      
        
        }else{
        	LOG.info(">>>>>>>>>>> Estado Null/0  - Rechazado  zona pagos ruta GET >>>>>>>>>>>>"+request.getParameter("id_pago"));
        	/*
        	LOG.info(">>>>>>>>>>> consulting Transaction "+request.getParameter("id_pago")+" >>>>>>>>>>>>");	
       Transaction transaction = TransactionsClient.getTransaction(request.getParameter("id_pago"),RestConstants.TRANSACTIONS_PATH, authToken);
       transaction.setTransactionState(TRANSACTION_RECHAZADA);
       LOG.info(">>>>>>>>>>> Updating Transaction "+request.getParameter("id_pago")+" >>>>>>>>>>>>");	
       TransactionsClient.updateDirectTransaction(request.getParameter("id_pago"), transaction, RestConstants.TRANSACTIONS_PATH, authToken);
        	
           LOG.info(">>>>>>>>>>> Success Rechazado Transaction "+request.getParameter("id_pago")+" >>>>>>>>>>>>");
           */
        }
        
        
    	  } catch (Exception e) {
              LOG.error("Servlet Error : " + e.getMessage(),e);
          }
      
        
        
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    private String getStringParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    /**
     * @param request
     * @param authToken
     * @return
     * @throws RestClientException
     */
    private PaymentZpDto getPaymentObjectFromRequest(HttpServletRequest request, String authToken)
            throws RestClientException {
        String transactionId = getStringParameter(request, "id_pago");
        String key = getStringParameter(request, "id_clave");
        String store = getStringParameter(request, "idcomercio");
        LOG.info("_____Request Serlver parameters : "+transactionId +" "+key+" "+store);
        PaymentZpDto paymentZp = PaymentRestClient.verifyPayment(transactionId, key, store,
                RestConstants.VERIFY_PAYMENT_PATH, authToken);
        return paymentZp;
    }

    /**
     * @param frachiseName
     * @return
     */
    private static String getFranchiseCode(String frachiseName) {
        String franchiseCode = StringPool.BLANK;
        for (String franchise : PortletConstants.PAYMENT_FRANCHISE_CODES) {
            String name = franchise.split("-")[0];
            if (name.equals(frachiseName)) {
                franchiseCode = franchise.split("-")[1];
            }
        }
        return franchiseCode;
    }
}
