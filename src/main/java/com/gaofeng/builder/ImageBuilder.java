package com.gaofeng.builder;


import com.gaofeng.service.wx.WeixinService;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
public class ImageBuilder extends AbstractBuilder {

  @Override
  public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage,
      WeixinService service) {

    WxMpXmlOutImageMessage m = WxMpXmlOutMessage.IMAGE().mediaId(content)
        .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
        .build();

    return m;
  }

}
