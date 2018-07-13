package wechat.pay.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 武海升
 * @date 2018/7/13 13:01
 */
@Controller
public class IndexController {

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("wechat/pay/")
    public String payForm(Model model) {
        return "pay/payTest";
    }
}
