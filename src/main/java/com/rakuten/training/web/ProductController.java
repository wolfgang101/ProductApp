package com.rakuten.training.web;

import java.net.URI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Product;
import com.rakuten.training.service.ProductService;

@RestController
public class ProductController {

  ProductService service;

  @Autowired
  public void setService(ProductService service) {
    this.service = service;
  }

  // @RequestMapping(method = RequestMethod.GET, value = "/products") --alternative way for mapping
  // get requests to methods
  @GetMapping("/products")
  public List<Product> getAllProducts() {
    return service.findAll();
  }

  @GetMapping("/products/{id}") // URI path template, id is path variable
  public ResponseEntity<Product> getProductById(
      @PathVariable("id") int id) { // Accessing path variable ad refer it by int id
    Product p = service.findById(id);
    if (p != null) {
      return new ResponseEntity<Product>(p, HttpStatus.OK);
    }
    return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/products")
  public ResponseEntity<Product> addProduct(@RequestBody Product p) {
    try {
      int id = service.addNewProduct(p);
      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(URI.create("/products/" + id));
      return new ResponseEntity<Product>(headers, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Product> delProduct(@PathVariable("id") int id) {
    try {
      service.removeProduct(id);
      return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    } catch (NullPointerException e) {
      return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    } catch (IllegalStateException e) {
      return new ResponseEntity<Product>(HttpStatus.CONFLICT);
    }
  }

}
