package wechat.message.service;

import org.springframework.stereotype.Service;
import wechat.message.entity.RcvTextMessage;
import wechat.message.entity.RplTextMessage;

import java.util.Map;

/**
 * 回复信息处理，用来生成回复信息，并将回复信息生成xml格式返回给微信服务器，然后再返回到微信客户端
 RcvMsgServiceImpl
 RplMsgServiceImpl */
@Service
public interface RplMsgService {

	/**
	 * 生成回复信息xml字符串
	 */
	String genRplMsgXML(RcvTextMessage rsvMsg, Map<String, String> requestMap);

	/**
	 * 生成回复信息对象
	 */
	RplTextMessage genRplMsgObj(RcvTextMessage rsvMsg, Map<String, String> requestMap);

}
