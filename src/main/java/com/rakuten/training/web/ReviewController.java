package com.rakuten.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;
import com.rakuten.training.service.ProductService;
import com.rakuten.training.service.ReviewService;

@RestController
public class ReviewController {
  ReviewService rService;

  ProductService service;

  @Autowired
  public void setService(ProductService service) {
    this.service = service;
  }

  @Autowired
  public void setrService(ReviewService rService) {
    this.rService = rService;
  }

  @GetMapping("/products/{id}/reviews")
  public ResponseEntity<List<Review>> getReviews(@PathVariable("id") int id) {
    Product p = service.findById(id);
    if (p != null) {
      List<Review> reviews = p.getReviews();
      if (!reviews.isEmpty()) {
        return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
      }
      return new ResponseEntity<List<Review>>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<List<Review>>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/products/{id}/reviews/{rid}")
  public ResponseEntity<Review> getReviewById(
      @PathVariable("id") int id, @PathVariable("rid") int rid) {
    Product p = service.findById(id);
    try {
      if (p != null) {
        Review r = rService.findReviewById(rid);
        return new ResponseEntity<Review>(r, HttpStatus.OK);
      } else {
        return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
      }
    } catch (NullPointerException e) {
      return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/products/{id}/reviews")
  public ResponseEntity<Review> addReview(@PathVariable("id") int id, @RequestBody Review r) {
    try {
      int revId = rService.addNewReview(r, id);
      HttpHeaders headers = new HttpHeaders();
      // headers.setLocation(URI.create("/products/" + id + "/reviews" + revId));
      headers.setLocation(URI.create("/products/" + id + "/reviews"));
      return new ResponseEntity<Review>(headers, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Review>(HttpStatus.CONFLICT);
    }
  }
}
