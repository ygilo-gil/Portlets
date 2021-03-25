<%@include file="/html/init.jsp"%>
<c:if test="${!empty portletSessionScope.transaction}">
<div>
	<div class="titulo_contenido_verde">
		<liferay-ui:message key="new-contract-payment" />
	</div>
		<div class="subtitulo_gris_normal"><liferay-ui:message key="title-payment-information"/></div>
		<c:choose>
		  <c:when test="${(portletSessionScope.transaction.transactionState == '4' || portletSessionScope.transaction.transactionState == '14') && portletSessionScope.transaction.info3 == 'MP-PIN'}">
		  	<div class="alert alert-warning subazul">
				<liferay-ui:message key="payment-vouchers-pending" arguments="${portletSessionScope.transaction.clientEmail}" />
		  	</div>
		  </c:when>
		  <c:when test="${portletSessionScope.transaction.detail.paymentState == '1'}">
			<div class="alert alert-success subazul">
				<liferay-ui:message key="payment-success" />
			</div>
		  </c:when>
		  <c:when test="${portletSessionScope.transaction.transactionState == '7'}">
		  	<div class="alert alert-error subazul">
				<liferay-ui:message key="payment-error" />
		  	</div>
		  </c:when>
		  <c:otherwise>
			<div class="alert alert-error">
<%-- 		<liferay-ui:message key="payment-pending" arguments="${portletSessionScope.transactionId}"/> --%>

             <liferay-ui:message key="payment-pending" arguments='<%=new String[] {portletSessionScope.get("transactionId").toString(),portletSessionScope.get("transactionCode").toString()}%>'/> 
			</div>
		  </c:otherwise>
		</c:choose>
		<div id="docEqError" class="alert alert-error" style="display: none;"></div>
	<div class="content">
		
		<table style="width: 100%" id="confirmationTable">
			<c:if test="${portletSessionScope.transaction.info3 == 'MP-PIN'}">
				<tr>
					<td><liferay-ui:message key="field-asigned-vouchers"/></td>
					<td>${portletSessionScope.transaction.vouchersCodes}</td>
				</tr>
			</c:if>
			<tr>
				<td><liferay-ui:message key="field-value"/></td>
				<td><fmt:formatNumber type="currency"
						value="${portletSessionScope.transaction.total}"
						maxFractionDigits="0" /></td>
			</tr>
					<c:choose>
						<c:when test="${portletSessionScope.transaction.detail.paymentMethod == '4' || portletSessionScope.transaction.detail.paymentMethod == '29'}">
							<tr>
								<td><liferay-ui:message key="field-transaction-date"/></td>
								<td>${portletSessionScope.transaction.detail.pseDate}</td>
							</tr>
							<tr>
								<td><liferay-ui:message key="field-bank-code"/></td>
								<td>${portletSessionScope.transaction.detail.bankName}</td>
							</tr>
						</c:when>
						<c:when test="${portletSessionScope.transaction.detail.paymentMethod == '100' || portletSessionScope.transaction.detail.paymentMethod == '31' || portletSessionScope.transaction.detail.paymentMethod == '32'}">
							<tr>
								<td><liferay-ui:message key="field-transaction-date"/></td>
								<td>${portletSessionScope.transaction.detail.pseDate}</td>
							</tr>
							<tr>
								<td><liferay-ui:message key="field-franchise"/></td>
								<td>${portletSessionScope.transaction.detail.franchise}</td>
							</tr>
						</c:when>
					</c:choose>
			<tr>
				<td><liferay-ui:message key="field-transaction-type"/></td>
				<td>${portletSessionScope.transaction.paymentDecription}</td>
			</tr>
			<tr>
				<td><liferay-ui:message key="field-reference-number"/></td>
				<td>${portletSessionScope.transactionId}</td>
			</tr>
			<tr>
				<td><liferay-ui:message key="field-ip-address"/></td>
				<td>${portletSessionScope.transaction.clientIpAdress}</td>
			</tr>
		</table>
		
		<div style="text-align: right;">
		<liferay-portlet:resourceURL id="createTransactionPDF"
			var="createVoucher" />
			
		<c:if test="${portletSessionScope.transaction.detail.paymentState == '1' && portletSessionScope.transaction.info3 == 'MP-PIN' && portletSessionScope.transaction.transactionState == '10'}">
				<liferay-portlet:resourceURL id="getEquivalentDocument"
					var="getEquivalentDocumentURL" />
				<aui:button type="button" value="button-equivalent-doc" name="docEqButton"/>
				<aui:script use="aui-base, aui-io-request, event-node-simulate">
					A.one("#<portlet:namespace/>docEqButton").on('click',function(){
						A.io.request('${getEquivalentDocumentURL}',{
							method : 'POST',
							dataType : 'JSON',
							on : {
								success : function(){
									var response = this.get('responseData');
									if(response.message == 'OK'){
										A.one('#docEqError').hide();
										var docNode = A.Node.create('<a></a>');
										docNode.setAttribute('href', 'data:application/pdf;base64,' + response.fileContent);
										docNode.setAttribute('download' , response.fileName);
										docNode.simulate('click');
									}else{
										A.one('#docEqError').set('text',response.message);
										A.one('#docEqError').show();
									}
								}
							}
						});
					});
				</aui:script>
		</c:if>	
		
		<c:if test="${(portletSessionScope.transaction.detail.paymentState == '1' && portletSessionScope.transaction.transactionState != '14') || portletSessionScope.transaction.transactionState == '7'}">
			<aui:button type="button" value="button-print" href="${createVoucher}"/>
		</c:if>
		<aui:button type="button" value="button-finish" cssClass="btn btn-primary" name="finishButton"/>
		<aui:script use="aui-base">
			A.one('#<portlet:namespace/>finishButton').on('click',function(){
				window.location = '${portletSessionScope.backUrl}';
			});
		</aui:script>
		</div>
	</div>
</div>
</c:if>