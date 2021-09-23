package com.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.ProductDTO;
import com.project.model.Category;
import com.project.model.Product;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.CategoryService;
import com.project.service.ProductService;

@Controller
public class AdminController {
	public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
 @Autowired(required=true)
 CategoryService categoryService;
 @Autowired
 private ProductService productService;
 @Autowired
 private UserRepository userRepository;
  
	@RequestMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}
	
	@RequestMapping("/admin/users")
	public String getUsers(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "users";
	}
	
	@RequestMapping("/admin/findUser")
	public String getUser() {
		
  		
  		return "userSearch";
   		 
	} 
   	
	
	@PostMapping("/admin/findUser")
	public String getUserByFirstName(Model model,@RequestParam(name ="firstName") String firstName) {
		Optional<User> user=userRepository.findUserByFirstName(firstName);
  	 if(!user.isPresent()) {
   		return "404";
   	 }else {
   		 
   		 model.addAttribute("users",user.get());
   		 return "users";
   	 }
   	 
    }

	

	
//	@RequestMapping("/admin/users/{firstName}")
//	public String getUserByFirstName(Model model,@PathVariable String firstName) {
//		Optional<User> user=userRepository.findUserByFirstName(firstName);
//   	 if(!user.isPresent()) {
//   		return "404";
//   	 }else {
//   		 
//   		 model.addAttribute("users",user.get());
//   		 return "users";
//   	 }
//   	 
//    }
	
	@RequestMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	
	@RequestMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	@GetMapping("admin/categories/delete/{id}")
	public String deleteCatageoryById(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
		
	}
     @GetMapping("admin/categories/update/{id}")
     public String updateCat(@PathVariable int id, Model model ) {
    	 Optional<Category> category=categoryService.getCategoryById(id);
    	 if(category.isPresent()) {
    		 model.addAttribute("category",category.get());
    		 return "categoriesAdd";
    	 }else {
    		 return "404";
    	 }
    	 
     }
     //Products
     @GetMapping("admin/products")
     public String products(Model model) {
    	 model.addAttribute("products",productService.getAllProducts() );
    	 return "products";
    	 
     }
     @GetMapping("/admin/products/add")
     public String getProductAdd(Model model) {
    	 model.addAttribute("productDTO", new ProductDTO());
    	 model.addAttribute("categories", categoryService.getAllCategory());
    	 return "productsAdd";
     }
     @PostMapping("/admin/products/add")
     public String getProductAdd(@ModelAttribute("ProductDTO") ProductDTO productDTO,
    		                     @RequestParam("productImage")MultipartFile file,
    		                    @RequestParam("imgName") String imgName)throws IOException {
    	 Product product=new Product();
    	 product.setId(productDTO.getId());
    	 product.setName(productDTO.getName());
    	 product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
    	 product.setPrice(productDTO.getPrice());
    	 product.setDescription(productDTO.getDescription());
    	 String imageUUID;
    	 if(!file.isEmpty()) {
    		 imageUUID=file.getOriginalFilename();
    		 Path fileNameAndPath=Paths.get(uploadDir, imageUUID) ;
    		 Files.write(fileNameAndPath,file.getBytes());
    	 } else {
    		 imageUUID=imgName;
    	 }
    	 product.setImageName(imageUUID);
    	 productService.addProdct(product);
    	 
    	 return "redirect:/admin/products";
     }
    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
    	productService.removeById(id);
    	 return "redirect:/admin/products";
    }
    @GetMapping("admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id,Model model) {
    	Product product=productService.getProductById(id).get();
    	ProductDTO productDTO=new ProductDTO();
    	productDTO.setId(product.getId());
    	productDTO.setName(productDTO.getName());
    	productDTO.setPrice(product.getPrice());
    	productDTO.setDescription(product.getDescription());
    	productDTO.setCategoryId(product.getCategory().getId());
    	productDTO.setImageName(product.getImageName());
    	
    	model.addAttribute("categories", categoryService.getAllCategory());
    	model.addAttribute("productDTO", productDTO);
    	
    	return "productsAdd";
    }
     
     
     
     
     
     
     
     
     
}
