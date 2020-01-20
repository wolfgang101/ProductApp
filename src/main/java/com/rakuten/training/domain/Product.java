package com.rakuten.training.domain;

import java.util.List;

import javax.persistence.*;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  int id;

  @Column(name = "product_name")
  String name;

  @Column(name = "product_price")
  float price;

  @Column(name = "product_qoh")
  int qoh;

  public Product() {
    // Default Constructor
  }
  
  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.REMOVE)
  List<Review> reviews;
  
  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public Product(String name, float price, int qoh) {
    super();
    this.name = name;
    this.price = price;
    this.qoh = qoh;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getQoh() {
    return qoh;
  }

  public void setQoh(int qoh) {
    this.qoh = qoh;
  }
}
