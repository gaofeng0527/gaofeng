package com.gaofeng.mapper;

import java.util.List;

import com.gaofeng.pojo.Product;

public interface ProductMapper {
	
	/**
	 * 查询所有
	 * @return 
	 */
	public List<Product> findAll();
	
	/**
	 * 查询某个公司下的产品
	 * @param companyId
	 * @return
	 */
	public List<Product> findByCompanyId(Long companyId);
	
	/**
	 * 根据状态查询某公司下的产品
	 * @param companyId
	 * @param status
	 * @return
	 */
	public List<Product> findByCompanyIdAndStatus(Long companyId,int status);
	
	/**
	 * 根据条件查询
	 * @param product
	 * @return
	 */
	public List<Product> findByProduct(Product product);
	
	/**
	 * 查询单个
	 * @param productId
	 * @return
	 */
	public Product findOneByProductId(Long productId);


	/**
	 * 添加产品
	 * @param product
	 */
	public void addProduct(Product product);


	/**
	 * 修改产品信息
	 * @param product
	 */
	public void updateProduct(Product product);
	
	/**
	 * 根据产品Id删除产品信息
	 * @param productId
	 */
	public void deleteProduct(Long productId);
	
	/**
	 * 批量添加
	 * @param plist
	 */
	public void addList(List<Product> plist);
	
	/**
	 * 用来做级联显示
	 * @param product
	 * @return
	 */
	public List<Product> findProductByTypeAndArea(Product product);
	
}
