package serverless.aws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.CollectionUtils;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIGatewayProxyRequest {

    static final String HEADER_VALUE_SEPARATOR = ";";
    static final String HEADER_KEY_VALUE_SEPARATOR = "=";

    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Map<String, String> pathParameters;
    private Map<String, String> stageVariables;
    private Map<String, Object> requestContext;
    private String body;
    private boolean isBase64Encoded;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeaders() {
        if (Objects.nonNull(headers)) {
            return headers;
        }
        return Collections.emptyMap();
    }

    public List<Map.Entry<String, String>> getHeaders(String name) {
        String headerValue = getHeaders().getOrDefault(name, null);
        if (Objects.nonNull(headerValue)) {
            return parseHeaderValue(headerValue);
        }
        return Collections.emptyList();
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getStageVariables() {
        return stageVariables;
    }

    public void setStageVariables(Map<String, String> stageVariables) {
        this.stageVariables = stageVariables;
    }

    public Map<String, Object> getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(Map<String, Object> requestContext) {
        this.requestContext = requestContext;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isBase64Encoded() {
        return isBase64Encoded;
    }

    public void setBase64Encoded(boolean base64Encoded) {
        isBase64Encoded = base64Encoded;
    }

    public String getQueryString() {
        StringBuilder queryString = new StringBuilder("");
        if (!CollectionUtils.isEmpty(queryStringParameters)) {
            for (String key : queryStringParameters.keySet()) {
                String value = queryStringParameters.get(key);
                String separator = queryString.length() == 0 ? "?" : "&";
                queryString.append(separator).append(key).append("=").append(value);
            }
        }
        return queryString.toString();
    }

    private List<Map.Entry<String, String>> parseHeaderValue(String headerValue) {
        List<Map.Entry<String, String>> values = new ArrayList<>();
        if (headerValue == null) {
            return values;
        }

        for (String kv : headerValue.split(HEADER_VALUE_SEPARATOR)) {
            String[] kvSplit = kv.split(HEADER_KEY_VALUE_SEPARATOR);

            if (kvSplit.length != 2) {
                values.add(new AbstractMap.SimpleEntry<>(null, kv.trim()));
            } else {
                values.add(new AbstractMap.SimpleEntry<>(kvSplit[0].trim(), kvSplit[1].trim()));
            }
        }
        return values;
    }

    @Override
    public String toString() {
        return "APIGatewayProxyRequest {" +
                "resource='" + resource + '\'' +
                ", path='" + path + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", headers=" + headers +
                ", queryStringParameters=" + queryStringParameters +
                ", pathParameters=" + pathParameters +
                ", stageVariables=" + stageVariables +
                ", requestContext=" + requestContext +
                ", body='" + body + '\'' +
                ", isBase64Encoded=" + isBase64Encoded +
                '}';
    }

}
