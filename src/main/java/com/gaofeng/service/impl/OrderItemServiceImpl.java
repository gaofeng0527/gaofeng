package com.gaofeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.OrderItemMapper;
import com.gaofeng.pojo.OrderItem;
import com.gaofeng.service.OrderItemService;
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemMapper omapper;

	@Override
	public void addOrderItem(OrderItem orderItem) {
		omapper.addOrderItem(orderItem);
	}

}
