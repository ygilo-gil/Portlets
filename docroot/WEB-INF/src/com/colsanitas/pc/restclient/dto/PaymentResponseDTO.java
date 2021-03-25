package com.colsanitas.pc.restclient.dto;

import java.io.Serializable;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public class PaymentResponseDTO implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String responseCode;
	private String responseMessage;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
