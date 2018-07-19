package wechat.message.entity;

import lombok.Data;

/**
 * 回复消息</BR>
 * 文本信息
 */
@Data
public class RplTextMessage extends BaseMessage {

	private String content;

	private String agentId;

	private int funcFlag;

	public String toXML() {

		String format = "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"

				+ "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"

				+ "<CreateTime>%3$s</CreateTime>\n"

				+ "<MsgType><![CDATA[text]]></MsgType>\n"

				+ "<Content><![CDATA[%4$s]]></Content>\n"

				+ "</xml>";
		return String.format(format, super.getToUserName(), super.getFromUserName(), System.currentTimeMillis(),
				this.content);
	}

}
