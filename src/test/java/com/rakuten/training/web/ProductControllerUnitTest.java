package com.rakuten.training.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuten.training.domain.Product;
import com.rakuten.training.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest({ProductController.class})
public class ProductControllerUnitTest {

  @Autowired MockMvc mockMvc;

  @MockBean ProductService service;

  @Test
  public void getProductById_Returns_OK_With_Correct_Product_If_Found() throws Exception {

    // Arrange
    Product found = new Product("test", 10000, 2);
    int id = 1;
    found.setId(id);
    Mockito.when(service.findById(id)).thenReturn(found);

    // Act and Assert
    mockMvc
        .perform(get("/products/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)));
  }

  @Test
  public void getProductById_Returns_NOT_FOUND_If_Not_Found() throws Exception {
    // Arrange
    int id = 1;
    Mockito.when(service.findById(id)).thenReturn(null);

    // Act and Assert
    mockMvc
        .perform(get("/products/{id}", id))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void addProduct_Returns_CREATED_If_Successful() throws Exception {

    // Arrange
    Product add = new Product("test", 10000, 2);
    Mockito.when(service.addNewProduct(Mockito.any(Product.class))).thenReturn(3);

    // Act and Assert
    // String json = "{\"name\" : \"test\", \"price\" : 10000, \"qoh\" : 2})";
    String json2 = toJson(add);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/products/")
                .contentType("application/json")
                .content(json2))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.header().string("location", "/products/3"));
	  Mockito.verify(service).addNewProduct(Mockito.any(Product.class));
  }

  @Test
  public void addProduct_Returns_BAD_REQUEST_If_Unsuccessful() throws Exception {

    // Arrange
    Product add = new Product("test", 90, 10);
    Mockito.when(service.addNewProduct(Mockito.any(Product.class)))
        .thenThrow(new IllegalArgumentException());

    // Act and Assert
    String json2 = toJson(add);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/products/")
                .contentType("application/json")
                .content(json2))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
  
  @Test
  public void delProduct_Returns_NO_CONTENT_If_Successful() throws Exception {
	  int id = 1;
	  Mockito.doNothing().when(service).removeProduct(id);
	  mockMvc.perform(MockMvcRequestBuilders.delete("/products/"+id)).andExpect(MockMvcResultMatchers.status().isNoContent());
	  Mockito.verify(service).removeProduct(id);
  }
  
  @Test
  public void delProduct_Returns_CONFLICT_If_Unsuccessful() throws Exception {
	  int id = 1;
	  Mockito.doThrow(new IllegalStateException()).when(service).removeProduct(id);
	  mockMvc.perform(MockMvcRequestBuilders.delete("/products/"+id)).andExpect(MockMvcResultMatchers.status().isConflict());
	  Mockito.verify(service).removeProduct(id);
  }
  
  @Test
  public void delProduct_Returns_NOT_FOUND_If_Unsuccessful() throws Exception {
	  int id = 1;
	  Mockito.doThrow(new NullPointerException()).when(service).removeProduct(id);
	  mockMvc.perform(MockMvcRequestBuilders.delete("/products/"+id)).andExpect(MockMvcResultMatchers.status().isNotFound());
	  Mockito.verify(service).removeProduct(id);
  }
  
  public static String toJson(Object o) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();

    try {
      // convert user object to json string and return it
      return mapper.writeValueAsString(o);
    } catch (JsonGenerationException | JsonMappingException e) {
      // catch various errors
      e.printStackTrace();
    }
    return null;
  }
}
