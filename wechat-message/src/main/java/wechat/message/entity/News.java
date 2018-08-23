package wechat.message.entity;

import lombok.Data;
import wechat.message.constants.WxConstants;

/**
 * @author 武海升
 * @date 2018/8/21 18:40
 */
@Data
public class News {

    private String Title = WxConstants.WX_NEWS_TITLE;//标题
    private String Description = WxConstants.WX_NEWS_DESCRIPTION;//描述
    private String PicUrl = WxConstants.WX_NEWS_PICURL;//图片地址
    private String Url = WxConstants.WX_NEWS_URL;//访问地址
}
