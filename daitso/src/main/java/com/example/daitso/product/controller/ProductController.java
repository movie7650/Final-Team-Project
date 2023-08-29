package com.example.daitso.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.daitso.category.model.Category;
import com.example.daitso.category.sevice.ICategoryService;
import com.example.daitso.product.model.Product;
import com.example.daitso.product.service.IProductService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	IProductService productService;

	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("")
	public String main() {
		return "/main/main";
	}
	
//	@GetMapping("/admin")
//	public String selectAllProducts(Model model) {
//		List<Product> products = productService.selectAllProducts();
//		model.addAttribute("products",products);
//		return "admin/product/admin-product";
//	}
	
//	@GetMapping("/admin/register")
//	public String addForm(Model model) {
//		List<Category> categories = categoryService.getAllFirstCategoryIdAndName();
//		model.addAttribute("categories",categories);
//		List<Product> products = productService.selectAllProducts();
//		model.addAttribute("products",products);
//		return "admin/product/admin-register";
//	}

//	@PostMapping("/admin/register")
//	public String registerProducts(Product product) {
//		productService.registerProducts(product);
//		return "redirect:/product/admin";
//	}
	
	@GetMapping("/admin")
	public String selectAllProducts(Model model) {
		List<Product> products = productService.selectAllProducts();
		model.addAttribute("products",products);
		List<Category> categories = categoryService.getAllFirstCategoryIdAndName();
		model.addAttribute("categories",categories);
		
		return "admin/product/productRegister";
	}
	
	@PostMapping("/admin")
	public String registerProducts(Product product, Model model) {
		productService.registerProducts(product);
		model.addAttribute("message","상품이 등록되었습니다.");
	    model.addAttribute("searchUrl","/product/admin");
//		return "redirect:/product/admin";
	    return "admin/product/message";
	}
	
	@GetMapping("/subCategories/{categoryId}")
    @ResponseBody
    public List<Category> getSubCategories(@PathVariable int categoryId) {
        List<Category> subCategories = categoryService.getSecondCategoryIdAndNameByFirstCategoryId(categoryId);
        return subCategories;
    }
	
	@PostMapping("/admin/delete")
    public String deleteProduct(@RequestParam int productId, Model model) {
	    productService.deleteProduct(productId);
	    model.addAttribute("message","상품이 삭제되었습니다.");
	    model.addAttribute("searchUrl","/product/admin");
//	    String message = "상품이 삭제되었습니다.";
//	    redirectAttributes.addFlashAttribute("message",message);
//	    return "redirect:/product/admin";
	    return "admin/product/message";
    }
	
	@GetMapping("/update/{productId}")
	public String selectProductId(@PathVariable int productId, Model model) {
		Product product = productService.selectProductId(productId);
		model.addAttribute("product", product);
		return "admin/product/productRegister";
	}
	
	@PostMapping("/update")
	public String updateProduct(Product product, Model model, HttpSession session) {
		productService.updateProduct(product);
		model.addAttribute("product", product);
		session.setAttribute("productId", product.getProductId());
		session.setAttribute("productCode", product.getProductCode());
		session.setAttribute("productNm", product.getProductNm());
		session.setAttribute("productPrice", product.getProductPrice());
		session.setAttribute("productStock", product.getProductStock());
		return "admin/product/message";
	}
	
	@GetMapping("/{categoryId}")
	public String selectProduct(@PathVariable int categoryId, Model model) {
		Product product = productService.selectProduct(categoryId);
		System.out.println(product);
		return "/main/productDetail";
	}

}
