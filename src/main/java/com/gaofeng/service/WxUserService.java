package com.gaofeng.service;

import java.util.List;

import com.gaofeng.pojo.WxUser;

public interface WxUserService {

	/**
	 * 获取所有
	 * 
	 * @return
	 */
	public List<WxUser> findAll();

	/**
	 * 根据id查询单个用户信息
	 * 
	 * @param wxUserId
	 * @return
	 */
	public WxUser findOneByuId(Long wxUserId);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void addWxUser(WxUser user);

	/**
	 * 根据openid查询单个
	 * 
	 * @param openid
	 * @return
	 */
	public WxUser findWxUserByOpenId(String openid);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateWxUserById(WxUser user);
	
	/**
	 * 条件查询
	 * @param user
	 * @return
	 */
	public List<WxUser> findByWxUser(WxUser user);

}
