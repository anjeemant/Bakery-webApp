package com.anjeemant.e_comm.controller;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anjeemant.e_comm.dto.ProductDTO;
import com.anjeemant.e_comm.model.Product;
import com.anjeemant.e_comm.service.CategoryService;
import com.anjeemant.e_comm.service.ProductService;

@Controller
public class AdminControllerProduct {
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String getProductAdd(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String postProductAdd(@ModelAttribute("productDTO")ProductDTO productDTO,
								 @RequestParam("productImage")MultipartFile file,
								 @RequestParam("imgName")String imgName
								) throws IOException  {
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setWeight(productDTO.getWeight());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		
		String imageUUID;
		if(file.isEmpty()) {
			imageUUID = imgName;
		}else {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
//		productService.deleteProductById(id);
		
		Optional<Product> productOptional = productService.getProductById(id);
	    if (productOptional.isPresent()) {
	        Product product = productOptional.get();
	        String imageName = product.getImageName();
	        
	        // Delete the product from the database
	        productService.deleteProductById(id);
	        
	        // Delete the corresponding image file
	        if (imageName != null && !imageName.isEmpty()) {
	            try {
	                Path imagePath = Paths.get(uploadDir, imageName);
	                Files.delete(imagePath); // Files throws an IOException so surrounded with try/catch
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		
		return "productsAdd";
	}
	
	
	
	
	
}
