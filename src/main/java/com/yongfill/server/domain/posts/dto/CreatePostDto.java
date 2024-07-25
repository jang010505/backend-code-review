package com.yongfill.server.domain.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
public class CreatePostDto {

    @Data
    @Builder
    @AllArgsConstructor
    public static class RequestDto {
        @NotBlank
        private String title;
        @NotBlank
        private String category;
        @NotBlank
        private String content;

    }


    @Builder
    @Getter
    public static class ResponseDto{
        private HttpStatus status;
        private String message;
    }


}
