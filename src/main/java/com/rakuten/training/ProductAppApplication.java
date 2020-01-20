package com.rakuten.training;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.dal.ReviewDAO;
import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;
import com.rakuten.training.ui.ProductConsoleUI;

@SpringBootApplication
public class ProductAppApplication {

  public static void main(String[] args) {
    ApplicationContext springContainer = SpringApplication.run(ProductAppApplication.class, args);
    /*		ProductConsoleUI ui = springContainer.getBean(ProductConsoleUI.class);
      ui.createProductWithUI();
      ui.findProductWithUI();
      ui.removeProductWithUI();
      ui.findProductWithUI();
      ReviewDAO rDao = springContainer.getBean(ReviewDAO.class);
      Review sample = new Review("Name1", "Good1", 5);
      Review saved = rDao.save(sample, 1);
      System.out.println("Created review with ID : "+saved.getId());
    ProductDAO pDao = springContainer.getBean(ProductDAO.class);
    //ui.findProductWithUI();
    Product p = pDao.findById(1);
    List<Product> pl = pDao.findAll();
    for(Product pp : pl){
    	System.out.println(pp.getName());
    }
    pDao.deleteById(1);
    System.out.println("This Product has " + p.getReviews().size() + "reviews");*/
  }
}
