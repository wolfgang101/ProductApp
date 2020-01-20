package com.rakuten.training.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rakuten.training.domain.Product;

@Repository
@Transactional
public class ProductDAOJpaImpl implements ProductDAO {

  @Autowired EntityManager em;

  @Override
  public Product save(Product toBeSaved) {
    em.persist(toBeSaved);
    return toBeSaved;
  }

  @Override
  public Product findById(int id) {
    return em.find(Product.class, id);
  }

  @Override
  public List<Product> findAll() {
	  Query q = em.createQuery("SELECT p from Product as p");
	  List<Product> pList = q.getResultList();
	  return pList;
  }

  @Override
  public void deleteById(int id) {
    Product p = em.getReference(Product.class, id);
    em.remove(p);
    /*Query q = em.createQuery("DELETE FROM Product as p where p.id=:idParam");
    q.setParameter("idParam", id);
    q.executeUpdate();*/
  }
}
