package serverless.aws.springframework.http.server.reactive;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import serverless.aws.model.APIGatewayProxyRequest;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

public class SimpleAPIGatewayProxyServerHttpRequestTest {

    @Test
    public void body() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        APIGatewayProxyRequest proxyRequest = new APIGatewayProxyRequest();
        proxyRequest.setPath("/");
        proxyRequest.setHeaders(headers);

        SimpleAPIGatewayProxyServerHttpRequest requestAdapter = SimpleAPIGatewayProxyServerHttpRequest.of(proxyRequest);
        Assert.assertThat(requestAdapter.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON));
    }


}