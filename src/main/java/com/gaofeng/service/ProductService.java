package com.gaofeng.service;

import java.util.List;

import com.gaofeng.pojo.Product;

public interface ProductService {
	
	public List<Product> findAllProduct();
	
	public void addProduct(Product product);
	
	public void deleteProduct(Long productId);
	
	public Product findOneByProductId(Long productId);
	
	public void updateProduct(Product product);
	
	public void addList(List<Product> plist);
	
	public List<Product> findProductByTypeAndArea(Product product);
	
	/**
	 * 条件查询
	 * @param product
	 * @return
	 */
	public List<Product> findByProduct(Product product);

}
