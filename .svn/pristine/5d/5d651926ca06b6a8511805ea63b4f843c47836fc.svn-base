package com.colsanitas.pc.util;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public class RestRequest {
    private String path;
    private String jsonPayload;
    private List<RestRequestHeader> headers;
    private List<RestRequestPathParameter> pathParameters;
    private List<RestRequestQueryParameter> queryParameters;

    public RestRequest() {
        headers = new ArrayList<RestRequestHeader>();
        pathParameters = new ArrayList<RestRequestPathParameter>();
        queryParameters = new ArrayList<RestRequestQueryParameter>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

    public List<RestRequestHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<RestRequestHeader> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        this.headers.add(new RestRequestHeader(key, value));
    }

    public List<RestRequestPathParameter> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(List<RestRequestPathParameter> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public void addPathParameter(String key, String value) {
        this.pathParameters.add(new RestRequestPathParameter(key, value));
    }
    

    public List<RestRequestQueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<RestRequestQueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public void addQueryParameter(String key, String value){
        this.queryParameters.add(new RestRequestQueryParameter(key, value));
    }

    public static class RestRequestHeader {
        private String key;
        private String value;

        public RestRequestHeader() {
            super();
        }

        public RestRequestHeader(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public static class RestRequestPathParameter {
        private String key;
        private String value;

        public RestRequestPathParameter() {
            super();
        }

        public RestRequestPathParameter(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * @author AndresFelipe
     *
     */
    public static class RestRequestQueryParameter {
        private String key;
        private String value;

        public RestRequestQueryParameter() {
            super();
        }

        public RestRequestQueryParameter(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}
