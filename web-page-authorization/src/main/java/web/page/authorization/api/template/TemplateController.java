package web.page.authorization.api.template;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.page.authorization.constants.WxConstants;
import web.page.authorization.enums.ResultEnum;
import web.page.authorization.exception.AuthException;
import web.page.authorization.result.WxOAuth2AccessToken;
import web.page.authorization.result.WxUserInfo;
import web.page.authorization.service.IAuthorizationService;
import web.page.authorization.template.TemplateData;
import web.page.authorization.template.WeChatTemplate;
import web.page.authorization.utils.WeChatUtil;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 武海升
 * @version 2.0
 * @description
 * 接口请求地址 http://localhost/wechat/getTemplate
 * @date 2018-07-07 21:06
 */
@RestController
@RequestMapping(value = "wechat")
@Slf4j
public class TemplateController {



    /**
     *  通过openId 发送 模板消息
     * @return
     */
    @GetMapping(value = "getTemplate")
    public String getTemplate() {

        String color = "#FF0000";
        String url = "https://www.etycx.com";
        String templateId = "dlj13fpybBA4sWqvbm4Y836OOC_XQSmbLd-h_lVNooE";
        String openId = "oKuUM6PzUjicwPplHo9Hu990bTsw";
        //封装模板消息
        WeChatTemplate tmpl = new WeChatTemplate();
        tmpl.setTopcolor(color);
        tmpl.setUrl(url);
        //模板ID,需先建立模板
        tmpl.setTemplate_id(templateId);
        //模板数据
        Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("测试标题", color));
        data.put("keyword1", new TemplateData("关键词一", color));
        data.put("keyword2", new TemplateData("关键词二", color));
        data.put("keyword3", new TemplateData("关键词三", color));
        data.put("keyword4", new TemplateData(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()), color));
        data.put("remark", new TemplateData("这是备注信息", color));

        //关注用户的openid
        tmpl.setTouser(openId);
        tmpl.setData(data);

        //发送模板数据并记录回执
        JSONObject object = WeChatUtil.sendTmplMsg(tmpl);


        return object.toJSONString();
    }

}
