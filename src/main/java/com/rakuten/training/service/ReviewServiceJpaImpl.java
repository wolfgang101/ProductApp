package com.rakuten.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.dal.ReviewDAO;
import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;

@Service
@Transactional
public class ReviewServiceJpaImpl implements ReviewService {

  ReviewDAO dao; // = new ReviewDAOInMemImpl();
  ProductDAO pDao;

  @Autowired
  public void setpDao(ProductDAO pDao) {
    this.pDao = pDao;
  }

  @Autowired
  public void setDao(ReviewDAO dao) {
    this.dao = dao;
  }

  // Failure should be returned as Exception. Absence of an object is returned as
  // empty optional
  @Override
  public int addNewReview(Review toBeAdded, int productId) {
    Product p = pDao.findById(productId);
    if (p != null) {
      toBeAdded.setProduct(p);
      Review added = dao.save(toBeAdded);
      return added.getId();
    } else {
      throw new NoSuchProductException();
    }
  }

  @Override
  public void removeReview(int id) {
    Review existing = dao.findById(id);
    if (existing != null) {
      dao.deleteById(id);
    } else {
      throw new NullPointerException("Review does not exist");
    }
  }

  @Override
  public Review findReviewById(int id) {
    Review r = dao.findById(id);
    if (r != null) {
      return r;
    } else {
      throw new NullPointerException("Review Not Found");
    }
  }
}
