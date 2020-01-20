package com.rakuten.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {

  ProductDAO dao; // = new ProductDAOInMemImpl();

  @Autowired
  public void setDao(ProductDAO dao) {
    this.dao = dao;
  }

  @Override
  public int addNewProduct(Product toBeAdded) {

    if (toBeAdded.getPrice() * toBeAdded.getQoh() >= 10000) {
      Product added = dao.save(toBeAdded);
      return added.getId();
    } else {
      throw new IllegalArgumentException("Monetary Value < 10000");
    }
  }

  @Override
  public void removeProduct(int id) {
    Product toBeRemoved = dao.findById(id);
    if (toBeRemoved != null) {
      if (toBeRemoved.getPrice() * toBeRemoved.getQoh() >= 100000) {
        throw new IllegalStateException("Monetary Value >= 100000");
      } else {
        dao.deleteById(id);
      }
      throw new NullPointerException("Product Doesn't Exist");
    }
  }

  @Override
  public List<Product> findAll() {
    return dao.findAll();
  }

  @Override
  public Product findById(int id) {
    return dao.findById(id);
  }
}
