package com.matheus.CoreControl.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheus.CoreControl.model.Product;
import com.matheus.CoreControl.model.User;
import com.matheus.CoreControl.model.enums.UserRole;
import com.matheus.CoreControl.service.ProductService;
import com.matheus.CoreControl.service.ReportService;
import com.matheus.CoreControl.service.UserService;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ReportService reportService;

    @MockBean
    private UserService userService;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setRole(UserRole.ADMIN);

        session = new MockHttpSession();
        session.setAttribute("user", "testuser");

        Mockito.when(userService.findUserByLogin("testuser")).thenReturn(user);
    }

    @Test
    public void testSaveNewProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("New Product");
        product.setPrice(100.0);
        product.setStock(10.0);

        Mockito.doNothing().when(productService).saveProduct(Mockito.any(Product.class));

        mockMvc.perform(post("/produtos/salvar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product))
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produtos/"));
    }
}
