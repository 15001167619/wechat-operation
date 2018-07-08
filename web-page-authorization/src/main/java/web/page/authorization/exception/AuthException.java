package web.page.authorization.exception;

import web.page.authorization.enums.ResultEnum;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-07 21:18
 */
public class AuthException extends RuntimeException  {
    private Integer code;

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public AuthException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
