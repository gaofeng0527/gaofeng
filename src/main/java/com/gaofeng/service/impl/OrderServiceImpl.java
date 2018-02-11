package com.gaofeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.OrderMapper;
import com.gaofeng.pojo.Order;
import com.gaofeng.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper omapper;

	@Override
	public void addOrder(Order order) {
		omapper.addOrder(order);
	}

}
