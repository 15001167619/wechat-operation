package wechat.pay.config;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import wechat.pay.utils.ToolUtil;

/**
 * @author 武海升
 * @date 2018/7/13 13:45
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
    }

}
