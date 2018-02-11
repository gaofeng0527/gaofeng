package com.gaofeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.ProductMapper;
import com.gaofeng.pojo.Product;
import com.gaofeng.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductMapper pmapper;

	public List<Product> findAllProduct() {
		return pmapper.findAll();
	}

	public void addProduct(Product product) {
		pmapper.addProduct(product);
	}

	public void deleteProduct(Long productId) {
		pmapper.deleteProduct(productId);
	}

	public Product findOneByProductId(Long productId) {
		return pmapper.findOneByProductId(productId);
	}

	public void updateProduct(Product product) {
		pmapper.updateProduct(product);
	}

	public void addList(List<Product> plist) {
		pmapper.addList(plist);
	}

	@Override
	public List<Product> findProductByTypeAndArea(Product product) {
		return pmapper.findProductByTypeAndArea(product);
	}

	@Override
	public List<Product> findByProduct(Product product) {
		return pmapper.findByProduct(product);
	}
	
	
	
	
	
	
	
	
	
	
	

}
