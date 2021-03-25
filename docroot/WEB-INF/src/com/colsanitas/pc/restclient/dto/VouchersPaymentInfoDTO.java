package com.colsanitas.pc.restclient.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public class VouchersPaymentInfoDTO {
	private String category;
	private String channel;
	private String city;
	private String companyCode;
	private String concept;
	private String contract;
	private String family;
	private String incomeWay;
	private String plan;
	private String providerDocType;
	private String providerDocNumber;
	private String quantity;
	private String state;
	private String stationCode;
	private String transactionNumber;
	private String userCode;
	private String userDocType;
	private String userDocNumber;
	private String totalValue;
	private String vouchers;
	private String service;
	private List<PaymentMethod> paymentMethods;
	

	
	
	
	
	
	
	@Override
	public String toString() {
		return "VouchersPaymentInfoDTO [category=" + category + ", channel="
				+ channel + ", city=" + city + ", companyCode=" + companyCode
				+ ", concept=" + concept + ", contract=" + contract
				+ ", family=" + family + ", incomeWay=" + incomeWay + ", plan="
				+ plan + ", providerDocType=" + providerDocType
				+ ", providerDocNumber=" + providerDocNumber + ", quantity="
				+ quantity + ", state=" + state + ", stationCode="
				+ stationCode + ", transactionNumber=" + transactionNumber
				+ ", userCode=" + userCode + ", userDocType=" + userDocType
				+ ", userDocNumber=" + userDocNumber + ", totalValue="
				+ totalValue + ", vouchers=" + vouchers + ", service="
				+ service + ", paymentMethods=" + paymentMethods + "]";
	}





	public String getService() {
		return service;
	}





	public void setService(String service) {
		this.service = service;
	}





	public VouchersPaymentInfoDTO() {
		this.paymentMethods = new ArrayList<VouchersPaymentInfoDTO.PaymentMethod>();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getIncomeWay() {
		return incomeWay;
	}

	public void setIncomeWay(String incomeWay) {
		this.incomeWay = incomeWay;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getProviderDocType() {
		return providerDocType;
	}

	public void setProviderDocType(String providerDocType) {
		this.providerDocType = providerDocType;
	}

	public String getProviderDocNumber() {
		return providerDocNumber;
	}

	public void setProviderDocNumber(String providerDocNumber) {
		this.providerDocNumber = providerDocNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserDocType() {
		return userDocType;
	}

	public void setUserDocType(String userDocType) {
		this.userDocType = userDocType;
	}

	public String getUserDocNumber() {
		return userDocNumber;
	}

	public void setUserDocNumber(String userDocNumber) {
		this.userDocNumber = userDocNumber;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getVouchers() {
		return vouchers;
	}

	public void setVouchers(String vouchers) {
		this.vouchers = vouchers;
	}

	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public void addPaymentMethod(PaymentMethod paymentMethod) {
	    this.paymentMethods.add(paymentMethod);
	}

	public static class PaymentMethod {
		private String code;
		private String value;
		private String asoBancariaCode;
		private String cardNumber;
		private String type;
		private String cardTax;
		private String approvalNumber;
		
		@Override
		public String toString() {
			return "PaymentMethod [code=" + code + ", value=" + value
					+ ", asoBancariaCode=" + asoBancariaCode + ", cardNumber="
					+ cardNumber + ", type=" + type + ", cardTax=" + cardTax
					+ ", approvalNumber=" + approvalNumber + "]";
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getAsoBancariaCode() {
			return asoBancariaCode;
		}

		public void setAsoBancariaCode(String asoBancariaCode) {
			this.asoBancariaCode = asoBancariaCode;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getCardTax() {
			return cardTax;
		}

		public void setCardTax(String cardTax) {
			this.cardTax = cardTax;
		}

		public String getApprovalNumber() {
			return approvalNumber;
		}

		public void setApprovalNumber(String approvalNumber) {
			this.approvalNumber = approvalNumber;
		}

	}
}
