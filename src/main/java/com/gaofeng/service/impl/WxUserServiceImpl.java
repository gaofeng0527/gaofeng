package com.gaofeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.WxUserMapper;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.WxUserService;

@Service
public class WxUserServiceImpl implements WxUserService {

	@Autowired
	private WxUserMapper umapper;

	public List<WxUser> findAll() {
		return umapper.findAll();
	}

	public WxUser findOneByuId(Long wxUserId) {
		return umapper.findOneByuId(wxUserId);
	}

	public void addWxUser(WxUser user) {
		umapper.addWxUser(user);
	}

	@Override
	public WxUser findWxUserByOpenId(String openid) {
		return umapper.findWxUserByOpenId(openid);
	}

	@Override
	public void updateWxUserById(WxUser user) {
		umapper.updateWxUserById(user);
	}

	@Override
	public List<WxUser> findByWxUser(WxUser user) {
		return umapper.findByWxUser(user);
	}

}
