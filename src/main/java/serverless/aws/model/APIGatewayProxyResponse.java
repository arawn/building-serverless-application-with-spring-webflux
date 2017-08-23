package serverless.aws.model;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class APIGatewayProxyResponse {

    private int statusCode;
    private Map<String, String> headers = new ConcurrentHashMap<>();
    private byte[] body;
    private boolean isBase64Encoded = false;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    protected void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        if (Objects.nonNull(body) && body.length > 0) {
            return new String(body, StandardCharsets.UTF_8);
        }
        return null;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isBase64Encoded() {
        return isBase64Encoded;
    }

    public void setBase64Encoded(boolean base64Encoded) {
        isBase64Encoded = base64Encoded;
    }


    @Override
    public String toString() {
        return "APIGatewayProxyResponse {" +
                "statusCode=" + statusCode +
                ", headers=" + headers +
                ", body=" + getBody() +
                ", isBase64Encoded=" + isBase64Encoded +
                '}';
    }
}
