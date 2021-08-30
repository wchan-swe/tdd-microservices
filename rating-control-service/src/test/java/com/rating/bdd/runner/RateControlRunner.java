package com.rating.bdd.runner;

import com.rating.RatingApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = { RatingApplication.class,
        RateControlRunner.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(
        plugin = "pretty",
        tags = "",
        features = "src/test/resources/features/rate_control_level.feature",
        glue = {"com.rating.bdd.steps"}
)
public class RateControlRunner {
}
