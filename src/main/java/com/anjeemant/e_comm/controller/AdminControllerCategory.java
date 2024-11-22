package com.anjeemant.e_comm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.anjeemant.e_comm.model.Category;
import com.anjeemant.e_comm.service.CategoryService;

@Controller
public class AdminControllerCategory {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/admin")
	public String getAdminHome() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories") 
	public String getCategories(Model model){
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCategoriesAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCategoriesAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	/* As we are making Spring Boot and MVC project so for DELETE & UPDATE operation 
	 * we are using GET method as `form` only supports GET & POST
	 */
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id){
		categoryService.deleteCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		Optional<Category> optional = categoryService.getCategoryById(id);
		if(optional.isPresent()) {
			model.addAttribute("category", optional.get());
			return "categoriesAdd";
		}
		else
			return "404";
	}
	
	
}
