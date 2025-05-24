package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.nt.dto.StudentOrders;

public interface StudentrRepo extends JpaRepository<StudentOrders, Integer> {
	public StudentOrders  findByRazorpayOrderId(String orderId);

}
