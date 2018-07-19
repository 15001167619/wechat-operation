package wechat.message.service.impl;

import org.springframework.stereotype.Service;
import wechat.message.entity.RcvTextMessage;
import wechat.message.entity.RplTextMessage;
import wechat.message.service.RplMsgService;
import wechat.message.utils.MessageUtil;

import java.util.Map;

@Service
public class RplMsgServiceImpl implements RplMsgService {


	/**
	 * 生成查询结果返回给微信服务器，由微信服务器返回到微信客户端
	 */
	@Override
	public String genRplMsgXML(RcvTextMessage rsvMsg, Map<String, String> requestMap) {
		return this.genRplMsgObj(rsvMsg, requestMap).toXML();
	}

	/**
	 * 通过用户输入的文字返回用户要查找的文件所在位置信息对象
	 */
	@Override
	public RplTextMessage genRplMsgObj(RcvTextMessage rsvMsg, Map<String, String> requestMap) {
		RplTextMessage rplMsg = new RplTextMessage();
		rplMsg.setFromUserName(rsvMsg.getToUserName());
		rplMsg.setToUserName(rsvMsg.getFromUserName());
		rplMsg.setCreateTime(System.currentTimeMillis());
		rplMsg.setMsgType("text");
		this.fillRplContent(rsvMsg, rplMsg, requestMap);
		return rplMsg;
	}

	/**
	 * 向回复信息中添加内容
	 */
	private void fillRplContent(RcvTextMessage rsvMsg, RplTextMessage rplMsg, Map<String, String> requestMap) {
		String msgType = rsvMsg.getMsgType();
		String respContent = "";
		// 文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			respContent = "欢迎关注微信公众号";
		}
		// 图片消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			respContent = "您发送的是图片消息！";
		}
		// 地理位置消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			respContent = "您发送的是地理位置消息！";
		}
		// 链接消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			respContent = "您发送的是链接消息！";
		}
		// 音频消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			respContent = "您发送的是音频消息！";
		}
		// 事件推送
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			// 用户未关注时，进行关注后的事件推送
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				respContent = "谢谢您的关注！";
			// 用户已关注时的事件推送
			}else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
				respContent = "欢迎来到微信公众号";
			}else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

			}
		}
		rplMsg.setContent(respContent);
	}

}
