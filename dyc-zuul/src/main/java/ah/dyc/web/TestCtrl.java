package ah.dyc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengyichao
 * @Title: TestCtrl
 * @Package: ah.dyc.web
 * @Description:
 * @date 2019/5/22  17:14
 */
@Controller
@RequestMapping("test")
public class TestCtrl {
    @GetMapping("dataList")
    @ResponseBody
    public String dataList(){
        return "测试";
    }
}
