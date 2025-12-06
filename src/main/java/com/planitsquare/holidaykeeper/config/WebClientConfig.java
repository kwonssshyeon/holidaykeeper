package com.planitsquare.holidaykeeper.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    private static final String HOLIDAY_API_BASE_URL = "https://date.nager.at/api/v3";
    private static final int CONNECT_TIMEOUT_MS = 5_000;
    private static final int TIMEOUT_SECONDS = 5;
    private static final int MAX_CONNECTIONS = 50;
    private static final Duration MAX_IDLE_TIME = Duration.ofSeconds(20);

    @Bean
    public WebClient holidayWebClient() {
        return WebClient.builder()
            .baseUrl(HOLIDAY_API_BASE_URL)
            .clientConnector(new ReactorClientHttpConnector(httpClient()))
            .filter(errorHandlingFilter())
            .build();
    }

    private static HttpClient httpClient() {
        ConnectionProvider provider = ConnectionProvider.builder("holiday-pool")
                .maxConnections(MAX_CONNECTIONS)
                .pendingAcquireMaxCount(1000)
                .maxIdleTime(MAX_IDLE_TIME)
                .build();

        return HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MS)
                .responseTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT_SECONDS)));
    }

    // 응답 상태 코드가 에러일 때 예외를 발생
    private static ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (response.statusCode().isError()) {
                return response.bodyToMono(String.class)
                        .defaultIfEmpty("")
                        .flatMap(body -> Mono.error(
                                new RuntimeException("Holiday API Error: " + response.statusCode() + " Body: " + body)
                        ));
            }
            return Mono.just(response);
        });
    }
}
