package wechat.message.service;



import wechat.message.entity.RcvTextMessage;

import java.util.Map;

/**
 * 接收信息处理，用来处理从微信服务器端发过来的“粉丝”发送的请求数据
 */
public interface RcvMsgService {

	/**
	 * 解析从微信服务器传过来的xml数据，生成“接收信息”对象
	 */
	RcvTextMessage genRcvMsgObj(Map<String, String> map);

	/**
	 * 保存从微信端面传过来的信息
	 */
	long saveRcvMsg(RcvTextMessage rsvMsg);

	/**
	 * 根据接收到的微信端传入的msgId查询该条数据是否已经存在用来判重
	 */
	RcvTextMessage searchRcvMsg(long msgId);

	/**
	 * 根据msgId删除用户消息记录
	 */
	int removeRcvMsg(long msgId);
}
