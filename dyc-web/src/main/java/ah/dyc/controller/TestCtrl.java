package ah.dyc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dengyichao
 * @Title: TestCtrl
 * @Package: com.tydic.das.controller
 * @Description:
 * @date 2018/12/18  11:04
 */
@Controller
@RequestMapping("TestCtrl")
public class TestCtrl {

    @RequestMapping("testPage")
    public String testPage(){
        return "test/index";
    }
}
