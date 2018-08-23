package wechat.message.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wechat.message.entity.RcvTextMessage;
import wechat.message.service.RcvMsgService;

import java.util.Map;

@Service
public class RcvMsgServiceImpl implements RcvMsgService {

	private static Logger log = LoggerFactory.getLogger(RcvMsgServiceImpl.class);

	@Override
	public RcvTextMessage genRcvMsgObj(Map<String, String> map) {
		RcvTextMessage rcvMsg = new RcvTextMessage();
		try {
			String fromName = map.get("FromUserName");
			if (null != fromName)
				rcvMsg.setFromUserName(fromName);

			String toName = map.get("ToUserName");
			if (null != toName)
				rcvMsg.setToUserName(toName);

			String ctTime = map.get("CreateTime");
			if (null != ctTime)
				rcvMsg.setCreateTime(Long.valueOf(ctTime));

			String msgType = map.get("MsgType");
			if (null != msgType)
				rcvMsg.setMsgType(msgType);

			String content = map.get("Content");
			if (null != content)
				rcvMsg.setContent(content);

			String msgId = map.get("MsgId");
			if (null != msgId)
				rcvMsg.setMsgId(Long.valueOf(msgId));

		} catch (Exception e) {
			e.printStackTrace();
			log.error("解析接收信息失败 ，失败原因:{}", e);
		}
		return rcvMsg;
	}

	@Override
	public long saveRcvMsg(RcvTextMessage rsvMsg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RcvTextMessage searchRcvMsg(long msgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int removeRcvMsg(long msgId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
