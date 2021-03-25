package com.colsanitas.pc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.pc.exception.RestClientException;
import com.colsanitas.pc.util.Constants.RestConstants;
import com.colsanitas.pc.util.RestRequest.RestRequestHeader;
import com.colsanitas.pc.util.RestRequest.RestRequestPathParameter;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public final class RestUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RestUtil.class);

    private RestUtil() {

    }

    /**
     * Call http post api
     * 
     * @param postObject
     * @return
     * @throws RestClientException
     */
    public static RestResponse httpMethodPOST(RestRequest postObject) throws RestClientException {
        try {

            URL url = new URL(createServiceUrl(postObject));
            LOG.debug("Path : {}", url);
    
            /* Create http post connection */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            /* Add connection headers */
            for (RestRequestHeader header : postObject.getHeaders()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            /* Send payload */
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream(),
                    RestConstants.ENCODING_UTF8);

            outputStream.write(postObject.getJsonPayload());
            outputStream.flush();
            outputStream.close();

            /* Check call response code */
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK
                    && connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RestClientException("Http error code : " + connection.getResponseCode());
            }

            /* Create response object */
            RestResponse response = new RestResponse();
            response.setBody(getResponseJsonString(connection.getInputStream()));
            response.setHeaders(connection.getHeaderFields());
            return response;
        } catch (IOException e) {
            throw new RestClientException("POST method error", e);
        }
    }

    /**
     * 
     * Call put http api
     * 
     * @param putRequest
     * @return
     * @throws RestClientException
     */
    public static RestResponse httpMethodPUT(RestRequest putRequest) throws RestClientException {
        try {

            

            URL url = new URL(createServiceUrl(putRequest));
            LOG.debug("Path : {}", url);

            /* Create put http connection */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            /* Add connection headers */
            for (RestRequestHeader header : putRequest.getHeaders()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            /* Send payload */
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream(),
                    RestConstants.ENCODING_UTF8);

            outputStream.write(putRequest.getJsonPayload());
            outputStream.flush();
            outputStream.close();

            /* Check connection response code */
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RestClientException("Http error code " + connection.getResponseCode());
            }

            /* Create response object */
            RestResponse response = new RestResponse();
            response.setBody(getResponseJsonString(connection.getInputStream()));
            response.setHeaders(connection.getHeaderFields());
            return response;
        } catch (IOException e) {
            throw new RestClientException("PUT method error", e);
        }
    }

    /**
     * Call's GET http api
     * 
     * @param getRequest
     * @return
     * @throws RestClientException
     */
    public static RestResponse httpMehodGET(RestRequest getRequest) throws RestClientException {
        try {

            URL url = new URL(createServiceUrl(getRequest));
            LOG.debug("Path : {}", url);

            /* Create GET connection */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            /* Add connection headers */
            for (RestRequestHeader header : getRequest.getHeaders()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            /* Check connection response code */
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RestClientException("Http error code " + connection.getResponseCode());
            }

            /* Create response object */
            RestResponse response = new RestResponse();
            response.setBody(getResponseJsonString(connection.getInputStream()));
            response.setHeaders(connection.getHeaderFields());
            return response;

        } catch (IOException e) {
            throw new RestClientException("GET method error" , e);
        }

    }

    /**
     * 
     * Create response object from connection input stream
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String getResponseJsonString(InputStream inputStream) throws IOException {
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(inputStream,
                Charset.forName(RestConstants.ENCODING_UTF8)));

        String output;
        StringBuilder sb = new StringBuilder();
        while ((output = responseBuffer.readLine()) != null) {
            sb.append(output);
        }
        responseBuffer.close();
        return sb.toString();
    }
    
    private static String createServiceUrl(RestRequest request) throws UnsupportedEncodingException{
        /* Create api url */
        StringBuilder path = new StringBuilder(request.getPath());
        /* Add path parameters */
        for (RestRequestPathParameter parameter : request.getPathParameters()) {
            path.append("/").append(parameter.getKey()).append("/")
                    .append(URLEncoder.encode(parameter.getValue(), RestConstants.ENCODING_UTF8));
        }

        /* Add query parameters */
        for (int i = 0; i < request.getQueryParameters().size(); i++) {
            path.append(i == 0 ? "?" : "&");
            path.append(request.getQueryParameters().get(i).getKey());
            path.append("=");
            path.append(URLEncoder.encode(request.getQueryParameters().get(i).getValue(),
                    RestConstants.ENCODING_UTF8));
        }
        
        return path.toString();
    }
}
