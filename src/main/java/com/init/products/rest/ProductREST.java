package com.init.products.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ProductsDAO;
import com.init.products.entitys.Product;


@RestController
//Le dice a Spring que va a ser un servicio Rest
@RequestMapping("products")
//En que URL se van a exponer todos los servicios o metodos  que esten en esta clase
public class ProductREST {
	
	@Autowired
	private ProductsDAO productDAO;
	
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products = productDAO.findAll();
		return ResponseEntity.ok(products);
	}
	
	@RequestMapping(value="{productId}") // /products/{productId} -> /products/1 
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		Optional<Product> optionalProduct = productDAO.findById(productId);
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping //products (POST)
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productDAO.save(product);
		return ResponseEntity.ok(newProduct);
	}
	
	@DeleteMapping(value="{productId}") //products (DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
		productDAO.deleteById(productId);
		return ResponseEntity.ok(null);
	}
	
	 @PutMapping
	 public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		 Optional<Product> optionalProduct = productDAO.findById(product.getId());
			if (optionalProduct.isPresent()) {
				Product updateProduct = optionalProduct.get();
				updateProduct.setName(product.getName());
				productDAO.save(updateProduct);
				return ResponseEntity.ok(updateProduct);
			} else {
				return ResponseEntity.notFound().build();
			}
	 }
	
	
	//@GetMapping
	//No define una URL, esta expuesto en localhost:8080 
	//@RequestMapping(value="hello", method=RequestMethod.GET)
	//Otra forma de exponer, personalizamos la URL
	public String hello() {
		return "Hello World";
	}
}

