package com.rating.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rcl/book/v1/read")
public class RatingControlLevelController {

    @GetMapping(value = "/eligibility/{control_level}/{book_id}")
    public ResponseEntity<Boolean> getControlAccess(@PathVariable("control_level") String controlLevel,
                                                    @PathVariable("book_id") String bookId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
