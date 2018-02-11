package com.gaofeng.handler;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaofeng.builder.TextBuilder;
import com.gaofeng.pojo.Company;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.WxUserService;
import com.gaofeng.service.wx.WeixinService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	
	@Autowired
	private WxUserService uservice;

	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {

		this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

		WeixinService weixinService = (WeixinService) wxMpService;

		// 获取微信用户基本信息
		WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

		if (userWxInfo != null) {
			WxUser user = createWxUser(userWxInfo);
			uservice.addWxUser(user);
			this.logger.info("添加新用户成功："+user.toString());
		}

		WxMpXmlOutMessage responseResult = null;
		try {
			responseResult = handleSpecial(wxMessage);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		if (responseResult != null) {
			return responseResult;
		}

		try {
			return new TextBuilder().build("感谢关注", wxMessage, weixinService);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}
	
	private WxUser createWxUser(WxMpUser muser) {
		WxUser wxUser = new WxUser();
		wxUser.setNickName(muser.getNickname());
		wxUser.setOpenId(muser.getOpenId());
		wxUser.setHeadImage(muser.getHeadImgUrl());
		wxUser.setProvince(muser.getProvince());
		wxUser.setSex(muser.getSex());
		wxUser.setStatus(0);
		wxUser.setRegTime(new Date());
		wxUser.setCompany(new Company(1L, "测试公司"));
		return wxUser;
	}

	/**
	 * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
	 */
	protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {

		System.out.println("扫码关注");

		return null;
	}

}
