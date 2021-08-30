package com.rating.common;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class RatingControlServiceConfig {

    private String bookServiceEndpoint;

}
