package web.page.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 武海升
 * @version 2.0
 * @description
 *  访问地址 http://mxq.natapp4.cc/wechat/authorize?returnUrl=https://weibo.com/0926whs
 * @date 2018-07-07 20:05
 */
@SpringBootApplication
@Slf4j

public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
        log.info("Application  Wechat Operation AuthorizationApplication start-up is success!");
    }

}
