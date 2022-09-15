package com.woowacourse.thankoo.common.config;

import java.util.concurrent.TimeUnit;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    private static final String TIMEOUT = "timeout";
    private static final int TIME_ALIVE_MILLISECONDS = 1000;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 5000;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpClient = createHttpClient();
        HttpComponentsClientHttpRequestFactory factory = createHttpRequestFactory(httpClient);
        return new RestTemplate(new BufferingClientHttpRequestFactory(factory));
    }

    private CloseableHttpClient createHttpClient() {
        return HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .evictIdleConnections(4000, TimeUnit.MILLISECONDS)
                .build();
    }

    private ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase(TIMEOUT)) {
                    return Long.parseLong(value) * TIME_ALIVE_MILLISECONDS;
                }
            }
            return DEFAULT_KEEP_ALIVE_TIME;
        };
    }

    private HttpComponentsClientHttpRequestFactory createHttpRequestFactory(
            final HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        factory.setHttpClient(httpClient);
        return factory;
    }
}
