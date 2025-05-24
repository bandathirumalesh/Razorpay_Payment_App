package com.nt.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nt.dto.StudentOrders;
import com.nt.repo.StudentrRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class StudentService {
	
	@Autowired
	private StudentrRepo studentRepo;
	
	@Value("${razorpay.key.id}")
	private String razorPayKey;
	@Value("${razorpay.secret.key}")
	private String razorPaySecret;
	
	private RazorpayClient client;
	
	public StudentOrders createOrder(StudentOrders stuOrder) throws Exception{
		JSONObject orderReq=new JSONObject();
		
		orderReq.put("amount", stuOrder.getAmount()*100);
        orderReq.put("currency", "INR");
		orderReq.put("receipt",stuOrder.getEmail());
		
		this.client=new RazorpayClient(razorPayKey,razorPaySecret);
		//cerate  order in razorpay
		Order razorPayOrder=client.orders.create(orderReq);
		
		System.out.println(razorPayOrder);
		stuOrder.setRazorpayOrderId(razorPayOrder.get("id"));
		stuOrder.setOrderStatus(razorPayOrder.get("status"));
		
		studentRepo.save(stuOrder);
		return stuOrder;
	}
    public StudentOrders upadteOrders(Map<String,String> responsePayLoad) {
		
		String razorPayOrderId=responsePayLoad.get("razorpay_order_id");
		StudentOrders order=studentRepo.findByRazorpayOrderId( razorPayOrderId);
		order.setOrderStatus("PAYMENT_COMPLETED");
		StudentOrders upadteOrder=studentRepo.save(order);
		return upadteOrder;
				
	}
	
	

}
