package com.rakuten.training.ui;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rakuten.training.domain.Product;
import com.rakuten.training.service.ProductService;

@Component
public class ProductConsoleUI {

  ProductService service; // = new ProductServiceImpl();
  
  @Autowired
  public void setService(ProductService service) {
    this.service = service;
  }
  
  Scanner in = new Scanner(System.in);
  
  public void createProductWithUI() {

    System.out.println("Enter Name : ");
    String name = in.nextLine();
    System.out.println("Enter Price : ");
    float price = Float.parseFloat(in.nextLine());
    System.out.println("Enter QoH : ");
    int qoh = Integer.parseInt(in.nextLine());
    Product p = new Product(name, price, qoh);
    int id = service.addNewProduct(p);
    System.out.println("Product Created With ID : " + id);
  }
  
  public void findProductWithUI() {
	  System.out.println("Search ID : ");
	  int id = in.nextInt();
	  Product p = service.findById(id);
	  if(p==null) {
		  System.out.println("Not Found");
		  return;
	  }
	  System.out.println(p.getId() +" "+ p.getName() + " " + p.getPrice() + p.getQoh());
  }
  
  public void removeProductWithUI() {
	  System.out.println("Delete ID : ");
	  int id = in.nextInt();
	  service.removeProduct(id);
  }

  public void findAll() {
    System.out.println(service.findAll());
  }
}
