package wechat.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 武海升
 * @date 2018/7/17 14:32
 */
@SpringBootApplication
@Slf4j
public class MessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
        log.info("Application  Wechat Message start-up is success!");
    }

}
