package com.planitsquare.holidaykeeper.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
@Builder
@AllArgsConstructor
public record PageResponse<T>(
        List<T> data,
        boolean hasNext,
        int page,
        int totalPage
) {
    public static <U, T> PageResponse<T> from(Page<U> data, Function<U, T> converter) {
        return PageResponse.<T>builder()
                .data(data.getContent().stream().map(converter).toList())
                .page(data.getNumber())
                .hasNext(data.hasNext())
                .totalPage(data.getTotalPages())
                .build();
    }
}
