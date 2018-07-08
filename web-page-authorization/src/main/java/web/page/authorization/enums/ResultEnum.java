package web.page.authorization.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-07 21:17
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {
    WECHAT_ERROR(20, "微信公众账号出现异常"),
    ;

    private Integer code;
    private String msg;
}
