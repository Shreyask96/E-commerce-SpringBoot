package com.jsp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.ecommerce.dto.Customer;
import com.jsp.ecommerce.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/customer")
@Controller
public class CustomerController {

	@Autowired
	Customer customer;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("customer",customer);
		return "Signup";
	}
	@PostMapping("/signup")
	public String signup(@Valid Customer customer, BindingResult result,ModelMap map) {
		if (result.hasErrors()) {
			return "Signup";
		} else {
			return customerService.signup(customer,map);
		}
	}
	
	@GetMapping("/home")
	public String loadHome(HttpSession session, ModelMap map) {
		if (session.getAttribute("customer") != null)
			return "CustomerHome";
		else {
			map.put("fail", "Session Expired, Login Again");
			return "Home";
		}
	}
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam int otp,@RequestParam int id,ModelMap map)
	{
		return customerService.verifyOtp(id,otp,map);
	}
	
	
	@GetMapping("/fetch-products")
	public String fetchProducts(HttpSession session,ModelMap map)
	{
		if (session.getAttribute("customer") != null) {
			return customerService.fetchProducts(map);
		} else {
			map.put("fail", "Session Expired, Login Again");
			return "Home";
		}
	}
	
	@GetMapping("/cart-add/{id}")
	public String addToCart(HttpSession session, ModelMap map, @PathVariable int id) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null) {
			return customerService.addToCart(customer, id, map);
		} else {
			map.put("fail", "Session Expired, Login Again");
			return "Home";
		}
	}

}
