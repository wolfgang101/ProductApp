package com.rakuten.training.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.domain.Product;

public class ProductServiceImplTest {

  @Test
  public void addNewProduct_Returns_Valid_Id_When_ProductValue_GTEQ_MinValue() {

    // Arrange
    ProductServiceImpl service = new ProductServiceImpl();
    Product toBeAdded = new Product("test", 10000, 1); // 10000x1 >= 10000
    
    ProductDAO mockDao = Mockito.mock(ProductDAO.class);
    
    Product saved = new Product("test", 10000, 1); // 10000x1 >= 10000
    saved.setId(2);
    Mockito.when(mockDao.save(toBeAdded)).thenReturn(saved);
    service.setDao(mockDao);

    // Act
    int id = service.addNewProduct(toBeAdded);

    // Assert
    assertTrue(id > 0);
  }
  
  
  @Test(expected = IllegalArgumentException.class)
  public void addNewProduct_Throws_When_ProductValue_LT_MinValue() {
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product toBeAdded = new Product("test", 9999, 1);
	  
	  service.addNewProduct(toBeAdded);
	  
	  
  }
  
  @Test
  public void removeProduct_Removes_When_ProductValue_LT_MinValue() {
	  //Arrange
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product existing = new Product("test", 9999, 1);
	  existing.setId(1);
	  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	  Mockito.when(mockDAO.findById(1)).thenReturn(existing);
	  service.setDao(mockDAO);
	  int id = 1;
	  
	  //Act
	  service.removeProduct(1);
	  
	  //Assert
	  Mockito.verify(mockDAO).deleteById(id);
	  
  }
  
  @Test(expected = IllegalStateException.class)
  public void removeProduct_Removes_When_ProductValue_GT_MinValue() {
	  //Arrange
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product existing = new Product("test", 100000, 1);
	  existing.setId(1);
	  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	  Mockito.when(mockDAO.findById(1)).thenReturn(existing);
	  service.setDao(mockDAO);
	  int id = 1;
	  
	  //Act
	  service.removeProduct(1);
	  
	  //Assert
	  Mockito.verify(mockDAO).deleteById(id);
	  
  }
  
  @Test
  public void removeProduct_Removes_When_ProductValue_Is_Null() {
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product existing = null;
	  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	  Mockito.when(mockDAO.findById(1)).thenReturn(existing);
	  service.setDao(mockDAO);
	  int id = 1;
	  
	  //Act
	  service.removeProduct(1);
	  
	  //Assert
	  Mockito.verify(mockDAO, Mockito.times(0)).deleteById(id);
  }
  
  
}
