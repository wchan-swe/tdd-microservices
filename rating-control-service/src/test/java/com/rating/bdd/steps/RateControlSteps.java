package com.rating.bdd.steps;

import com.rating.RatingApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RatingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RateControlSteps {

    private String ratingControlLevel;
    private ResponseEntity<Boolean> responseEntity;

    @Autowired
    private TestRestTemplate restTemplate;

    @Given("I am a customer with a  set rating control of (.*)$")
    public void i_am_a_customer_with_a_set_rating_control_of(String customerControlLevel)  {
        this.ratingControlLevel = customerControlLevel;
    }

    @When("I request to read an equal level book (.*)$")
    public void i_request_to_read_an_equal_level_book(String bookId) {
        HttpEntity httpEntity = new HttpEntity(generateHeader());
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(String.format("/rcl/book/v1/read/eligibility/%s/%s", ratingControlLevel, bookId),
                HttpMethod.GET, httpEntity, Boolean.class);
    }

    @Then("I get the decision to read the book")
    public void i_get_the_decision_to_read_the_book() {
        assertThat(responseEntity.getStatusCode()).isEqualTo(Integer.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(true);
    }

    private MultiValueMap<String, String> generateHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        return headers;
    }
}
