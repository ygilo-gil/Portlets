package com.colsanitas.pc.portlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.oauth2manager.token.OAuth2Token;
import com.colsanitas.pc.restclient.EquivalentDocClient;
import com.colsanitas.pc.restclient.TransactionsClient;
import com.colsanitas.pc.restclient.dto.Transaction;
import com.colsanitas.pc.util.Constants.PortletConstants;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;

/**
 * Portlet implementation class PaymentConfirmationPortlet
 */
/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public class PaymentConfirmationPortlet extends MVCPortlet {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentConfirmationPortlet.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.liferay.util.bridges.mvc.MVCPortlet#serveResource(javax.portlet.
     * ResourceRequest, javax.portlet.ResourceResponse)
     */
    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
            PortletException {
        PortletSession portletSession = resourceRequest.getPortletSession();
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
        String transactionId = (String) portletSession.getAttribute("transactionId");
        String resourceId = resourceRequest.getResourceID();
        if ("createTransactionPDF".equals(resourceId)) {
            /*
             * Generate confirmation PDF
             */

            NumberFormat numberFormat = new DecimalFormat("'$'#,##0.00");
            Transaction transaction = (Transaction) portletSession.getAttribute("transaction");
            String companyName = transaction.getProduct();
            LOG.debug("Create PDF file for transaction " + transaction.getDetail().getTransactionCode());
            try {
                /* get company image url */
                String url = PortletProps.get(companyName + "-imageUrl");

                /* create new pdf document */
                Document document = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();

                /* define font styles */
                Font h8 = new Font(FontFamily.HELVETICA, 8);
                Font h16 = new Font(FontFamily.HELVETICA, 16);
                Font h13 = new Font(FontFamily.HELVETICA, 13);
                Font h11 = new Font(FontFamily.HELVETICA, 11);

                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);
                headerTable.setSpacingAfter(30);
                headerTable.setWidths(new float[] { 40, 60 });

                PdfPCell companyInfo = new PdfPCell();
                companyInfo.setBorder(0);
                Image companyLogo = Image.getInstance(new URL(url));
                companyLogo.scaleAbsolute(200, 30);
                Paragraph companyDescription = new Paragraph(PortletProps.get(companyName + "-description"), h8);
                companyDescription.setAlignment(Element.ALIGN_CENTER);
                Paragraph companyID = new Paragraph(PortletProps.get(companyName + "-nit"), h8);
                companyID.setAlignment(Element.ALIGN_CENTER);
                Paragraph exInfo = new Paragraph("VIGILADA SUPERSALUD RESOLUCION 2028 DE 1992", h8);
                exInfo.setAlignment(Element.ALIGN_CENTER);
                Paragraph companyAddress = new Paragraph("Calle 100 No. 11B-67 PBX 6466060 Bogotá D.C.", h8);
                companyAddress.setAlignment(Element.ALIGN_CENTER);
                companyInfo.addElement(companyLogo);
                companyInfo.addElement(Chunk.NEWLINE);
                companyInfo.addElement(companyDescription);
                companyInfo.addElement(companyID);
                companyInfo.addElement(exInfo);
                companyInfo.addElement(companyAddress);
                headerTable.addCell(companyInfo);

                PdfPCell transactionInfo = new PdfPCell();
                transactionInfo.setBorder(0);
                transactionInfo.setPaddingLeft(50);
                transactionInfo.setPaddingTop(20);

                transactionInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                Paragraph title = new Paragraph("Confirmación de la transacción", h16);

                transactionInfo.addElement(title);

                if (transaction.getDetail().getPaymentMethod().matches("4|29")) {
                    Paragraph trNumber = new Paragraph("Transacción No. "
                            + transaction.getDetail().getTransactionCode(), h13);
                    transactionInfo.addElement(trNumber);
                }

                headerTable.addCell(transactionInfo);

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

                if (transaction.getInfo3().equals(PortletConstants.CONCEPT_VOUCHER)) {
                    table.addCell(new Phrase("Vales electrónicos asignados", h11));
                    table.addCell(new Phrase(transaction.getVouchersCodes(), h11));
                }

                table.addCell(new Phrase("Valor:", h11));
                table.addCell(new Phrase(numberFormat.format(transaction.getTotal()), h11));
                if (transaction.getDetail().getPaymentMethod().matches("4|29")) {
                    table.addCell(new Phrase("Fecha y hora de pago:", h11));
                    table.addCell(new Phrase(transaction.getDetail().getPseDate(), h11));
                    table.addCell(new Phrase("Banco Origen:", h11));
                    table.addCell(new Phrase(transaction.getDetail().getBankName()));
                } else if (transaction.getDetail().getPaymentMethod().matches("5|31|32")) {
                    table.addCell(new Phrase("Fecha y hora de pago:", h11));
                    table.addCell(new Phrase(transaction.getDetail().getPseDate(), h11));
                    table.addCell(new Phrase("Franquicia:", h11));
                    table.addCell(new Phrase(transaction.getDetail().getFranchise(), h11));
                }
                table.addCell(new Phrase("Tipo de transacción:", h11));
                table.addCell(new Phrase(transaction.getPaymentDecription(), h11));
                table.addCell(new Phrase("Número de referencia de pago:", h11));
                table.addCell(new Phrase(transactionId, h11));
                table.addCell(new Phrase("Dirección Ip:", h11));
                table.addCell(new Phrase(transaction.getClientIpAdress(), h11));
                table.addCell(new Phrase("Resultado de la transacción:", h11));
                table.addCell(new Phrase("1".equals(transaction.getDetail().getPaymentState()) ? "ACEPTADO"
                        : "RECHAZADO", h11));

                PdfPTable containerTable = new PdfPTable(1);
                containerTable.setWidthPercentage(100);
                PdfPCell headerCell = new PdfPCell(headerTable);
                headerCell.setBorder(0);
                PdfPCell contentCell = new PdfPCell(table);
                contentCell.setPaddingLeft(40);
                contentCell.setBorderColor(BaseColor.LIGHT_GRAY);
                contentCell.setPaddingRight(40);
                contentCell.setPaddingTop(10);
                contentCell.setPaddingBottom(10);
                containerTable.addCell(headerCell);
                containerTable.addCell(contentCell);
                document.add(containerTable);

                document.close();
                resourceResponse.setProperty("Content-Disposition",
                        "attachment;filename=" + PortletProps.get("pdf-file-name") + ".pdf");
                resourceResponse.setContentType("application/pdf");
                resourceResponse.getPortletOutputStream().write(baos.toByteArray());
                resourceResponse.getPortletOutputStream().flush();
                resourceResponse.getPortletOutputStream().close();

            } catch (DocumentException e) {
                LOG.error("Error : " + e.getMessage(), e);
            }
        } else if ("getEquivalentDocument".equals(resourceId)) {
            /*
             * Get equivalent document PDF
             */
        	JSONObject response = new JSONObject();
            try {
                /* Get authorization token for call protected rest apis */
                String authToken = OAuth2Token.getToken(PortletConstants.PORTLET_GROUP);

                /* Get transaction info from session */
                Transaction transaction = (Transaction) portletSession.getAttribute("transaction");

                /* Get document url from api */
                String url = EquivalentDocClient.getEquivalentDocUrl(transaction.getProduct(),
                        PortletConstants.PAYMENT_STATION_CODE, transaction.getCollection(),
                        RestConstants.EQUIVALENT_DOC_PATH, authToken);

                try {
					
                /* Get document bytes */
                URL docUrl = new URL(url);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                StreamUtil.transfer(docUrl.openStream(), baos);

                /* Send pdf document to view */
                response.put("message", "OK");
                response.put("fileContent", Base64.encode(baos.toByteArray()));
                response.put("fileName", "SoporteDeCompra.pdf");
                } catch (Exception e) {
                	LOG.error("Error getting pdf file",e);
                	String message = LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "equivalent-doc-error");
                	response.put("message", StringEscapeUtils.unescapeHtml(message));

                }
            } catch (Exception e) {
                LOG.error("Error : " + e.getMessage(), e);
                String message = LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(), "fatal-error-message");
                response.put("message", StringEscapeUtils.unescapeHtml(message));
            }
            writeJSON(resourceRequest, resourceResponse, response);
        }
        super.serveResource(resourceRequest, resourceResponse);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.liferay.util.bridges.mvc.MVCPortlet#processAction(javax.portlet.
     * ActionRequest, javax.portlet.ActionResponse)
     */
    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
            PortletException {
        /* remove default success and error mesages in all views */
        super.processAction(actionRequest, actionResponse);
        SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest)
                + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.liferay.util.bridges.mvc.MVCPortlet#doView(javax.portlet.RenderRequest
     * , javax.portlet.RenderResponse)
     */
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        try {
        	System.out.println("______INICIANDO DO VIEW  EN  CONFIRMACION PAGO _____"+renderRequest.getContextPath());
        	
        	
            /* Remove session values from previous calls */
            PortletSession portletSession = renderRequest.getPortletSession();
            portletSession.removeAttribute("transaction");
            portletSession.removeAttribute("backUrl");
            HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil
                    .getHttpServletRequest(renderRequest));
            String idTransaction = request.getParameter("id_pago");
            ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
            if (Validator.isNotNull(idTransaction)) {
                /* Get authorization token for call protected rest apis */
                String tokenMap = OAuth2Token.getToken(PortletConstants.PORTLET_GROUP);

                LOG.debug(">>>>>>>>>>> CONTROL 1");
                /* Get transaction object from api */
                Transaction transaction = TransactionsClient.getTransaction(idTransaction,
                        RestConstants.TRANSACTIONS_PATH, tokenMap);

                /* Generate back url for redirect to caller portlet */
                String portletId = transaction.getInfo1().split("-")[0];
                long groupId = Long.parseLong(transaction.getInfo1().split("-")[1]);
                long plid = PortalUtil.getPlidFromPortletId(groupId, portletId);
                String backUrl = PortalUtil.getLayoutFriendlyURL(LayoutLocalServiceUtil.getLayout(plid), themeDisplay);

                /* Set session values */
                
                portletSession.setAttribute("transactionCode",transaction.getDetail().getTransactionCode());
                portletSession.setAttribute("transactionId", idTransaction);
                portletSession.setAttribute("backUrl", backUrl);
                portletSession.setAttribute("transaction", transaction);
            }
        } catch (Exception e) {
            LOG.error("Render error : {}", e);
        }

        super.doView(renderRequest, renderResponse);
    }
}
