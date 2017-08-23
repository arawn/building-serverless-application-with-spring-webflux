package serverless.aws.springframework.http.server.reactive;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import serverless.aws.model.APIGatewayProxyRequest;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class SimpleAPIGatewayProxyServerHttpRequest extends AbstractServerHttpRequest {

    private final APIGatewayProxyRequest proxyRequest;
    private final DataBufferFactory dataBufferFactory;

    private SimpleAPIGatewayProxyServerHttpRequest(URI uri, String contextPath, HttpHeaders headers, APIGatewayProxyRequest proxyRequest, DataBufferFactory dataBufferFactory) {
        super(uri, contextPath, headers);
        this.proxyRequest = proxyRequest;
        this.dataBufferFactory = dataBufferFactory;
    }

    @Override
    protected MultiValueMap<String, String> initQueryParams() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        proxyRequest.getQueryStringParameters().forEach(queryParams::add);
        return queryParams;
    }

    @Override
    protected MultiValueMap<String, HttpCookie> initCookies() {
        MultiValueMap<String, HttpCookie> httpCookies = new LinkedMultiValueMap<>();

        proxyRequest.getHeaders(HttpHeaders.COOKIE)
                    .stream()
                    .map(cookie -> new HttpCookie(cookie.getKey(), cookie.getValue()))
                    .forEach(httpCookie -> httpCookies.add(httpCookie.getName(), httpCookie));



        return httpCookies;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public String getMethodValue() {
        return proxyRequest.getHttpMethod();
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return Flux.just(dataBufferFactory.wrap(proxyRequest.getBody().getBytes(StandardCharsets.UTF_8)));
    }


    public static SimpleAPIGatewayProxyServerHttpRequest of(APIGatewayProxyRequest proxyRequest) {
        try {
            URI uri = new URI(proxyRequest.getPath());
            String contextPath = "/";
            HttpHeaders headers = new HttpHeaders();
            proxyRequest.getHeaders()
                        .entrySet()
                        .stream()
                        .filter(entry -> !HttpHeaders.COOKIE.equals(entry.getKey()))
                        .filter(entry -> !HttpHeaders.ACCEPT_LANGUAGE.equals(entry.getKey()))
                        .forEach(entry -> {
                            List<Map.Entry<String, String>> entries = proxyRequest.getHeaders(entry.getKey());
                            entries.stream().map(Map.Entry::getValue).forEach(headerValue -> headers.add(entry.getKey(), headerValue));
                        });

            return new SimpleAPIGatewayProxyServerHttpRequest(uri, contextPath, headers, proxyRequest, new DefaultDataBufferFactory());
        } catch (URISyntaxException error) {
            throw new URISyntaxExceptionWrapper(error);
        }
    }

    public static class URISyntaxExceptionWrapper extends RuntimeException {
        URISyntaxExceptionWrapper(Throwable cause) {
            super(cause);
        }
    }

}
