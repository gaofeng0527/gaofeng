package com.gaofeng.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gaofeng.builder.AbstractBuilder;
import com.gaofeng.builder.ImageBuilder;
import com.gaofeng.builder.TextBuilder;
import com.gaofeng.dto.WxMenuKey;
import com.gaofeng.service.wx.WeixinService;

import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class MenuHandler extends AbstractHandler {

	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		WeixinService weixinService = (WeixinService) wxMpService;

		String key = wxMessage.getEventKey();
		WxMenuKey menuKey;
		try {
			menuKey = JSON.parseObject(key, WxMenuKey.class);
		} catch (Exception e) {
			return WxMpXmlOutMessage.TEXT().content(key).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
					.build();
		}

		AbstractBuilder builder = null;
		String mtype = menuKey.getType();
		if (XmlMsgType.TEXT.equals(mtype)) {
			builder = new TextBuilder();
		} else if (XmlMsgType.IMAGE.equals(mtype)) {
			builder = new ImageBuilder();
		} else if (XmlMsgType.VOICE.equals(mtype)) {

		} else if (XmlMsgType.VIDEO.equals(mtype)) {

		} else if (XmlMsgType.NEWS.equals(mtype)) {

		} else {

		}

		if (builder != null) {
			try {
				return builder.build(menuKey.getContent(), wxMessage, weixinService);
			} catch (Exception e) {
				this.logger.error(e.getMessage(), e);
			}
		}

		return null;

	}

}
