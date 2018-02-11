package com.gaofeng.mapper;

import com.gaofeng.pojo.OrderItem;

public interface OrderItemMapper {
	
	/**
	 * 添加订单详情
	 * @param orderItem
	 */
	public void addOrderItem(OrderItem orderItem);
}
