package com.gaofeng.mapper;

import java.util.List;

import com.gaofeng.pojo.WxUser;

public interface WxUserMapper {

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public List<WxUser> findAll();

	/**
	 * 查询单个用户
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
	 * 
	 * @param user
	 */
	public void updateWxUserById(WxUser user);

	/**
	 * 条件查询
	 * 
	 * @param user
	 * @return
	 */
	public List<WxUser> findByWxUser(WxUser user);
}
