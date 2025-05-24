package com.nt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.dto.StudentOrders;
import com.nt.service.StudentService;

@Controller
public class StudentController {
	@Autowired
	private StudentService service;	
	@GetMapping("/")
	public String init() {
		return "index";
	}
	
	@PostMapping(value="/create-order",produces="application/json")
	@ResponseBody
	public ResponseEntity<StudentOrders>createOrder(@RequestBody StudentOrders studentOrder)throws Exception{
		StudentOrders createdOrder=service.createOrder(studentOrder);
		return new ResponseEntity<>(createdOrder,HttpStatus.CREATED);
		
	}
	@PostMapping("/handle-payment-callback")
	public String handlePaymentCallback(@RequestParam Map<String,String>respPayload) {
		System.out.println(respPayload);
		service.upadteOrders(respPayload);
		return "success";
	}
	

}
