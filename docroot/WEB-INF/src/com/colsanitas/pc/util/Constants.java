package com.colsanitas.pc.util;

import java.util.Arrays;
import java.util.List;

import com.liferay.util.portlet.PortletProps;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public final class Constants {
	public static final class RestConstants {
		public static final String TRANSACTIONS_PATH = PortletProps.get("transactions-path");
		public static final String EQUIVALENT_DOC_PATH = PortletProps.get("equivalent-doc-path");
		public static final String VOUCHERS_PAYMENT_PATH = PortletProps.get("vouchers-payment-path");
		public static final String VERIFY_PAYMENT_PATH = PortletProps.get("verify-payment-path");
		public static final String ENCODING_UTF8 = "UTF-8";
		public static final String HEADER_PROPERTY_AUTHORIZATION = "Authorization";
		public static final String HEADER_PROPERTY_CONTENT_TYPE = "Content-Type";
		public static final String HEADER_PROPERTY_ACCEPT = "Accept";
		public static final String MIME_TYPE_JSON = "application/json";

		private RestConstants() {
		}
	}
	
	public static final class PortletConstants{
		public static final String PORTLET_GROUP = PortletProps.get("portlet-group");
		public static final String CONCEPT_VOUCHER = "MP-PIN";
		
		public static final String PAYMENT_CHANNEL=PortletProps.get("channel");
		public static final String PAYMENT_CONCEPT=PortletProps.get("concept");
		public static final String PAYMENT_STATE=PortletProps.get("state");
		public static final String PAYMENT_STATION_CODE=PortletProps.get("station-code");
		public static final String PAYMENT_DEFAULT_CITY_CODE=PortletProps.get("default-city-code");
		
		
		public static final List<String> PAYMENT_FRANCHISE_CODES = Arrays.asList(PortletProps.getArray("franchise-code"));
		public static final String PAYMENT_METHOD_TYPE = PortletProps.get("method-type");
		public static final String PAYMENT_METHOD_CARD_NUMBER = PortletProps.get("card-number");
		
		
		public static final String PAYMENT_METHOD_CODE_PSE = PortletProps.get("method-code-pse");
		public static final String PAYMENT_METHOD_CODE_CREDIT_CARD = PortletProps.get("method-code-credit-card");
		
		public static final String PAYMENT_METHOD_CODE_PSE_CORE = PortletProps.get("method-code-pse-core");
		public static final String PAYMENT_METHOD_CODE_CREDIT_CARD_CORE = PortletProps.get("method-code-credit-card-core");
		
		
		private PortletConstants(){
		}
	}
	
	private Constants(){
	}
}
