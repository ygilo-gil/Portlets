package com.colsanitas.pc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.restclient.TransactionsClient;
import com.colsanitas.pc.restclient.dto.Transaction;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 * 
 */
public class PaymentConfirmationTest {
    private static final String HOST = "http://wso2desa.colsanitas.com:8283";
    private static final String CONTEXT_TRANSACTIONS = "/osi/api/afiliado/mp/transacciones";

    @Test
    public void getTransactionSuccesCase() throws RestClientException {
        String transactionId = "2183";
        String path = HOST + CONTEXT_TRANSACTIONS;
        Transaction transaction = TransactionsClient.getTransaction(
                transactionId, path, StringPool.BLANK);
        assertNotNull(transaction);
    }

    @Test(expected = RestClientException.class)
    public void getTransactionErrorCase() throws RestClientException {
        String transactionId = StringPool.BLANK;
        String path = HOST + CONTEXT_TRANSACTIONS;
        TransactionsClient.getTransaction(
                transactionId, path, StringPool.BLANK);
    }
    
}
