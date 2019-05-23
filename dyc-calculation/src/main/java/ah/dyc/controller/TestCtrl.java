package ah.dyc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author dengyichao
 * @Title: TestCtrl
 * @Package: ah.dyc.controller
 * @Description:
 * @date 2019/5/22  15:23
 */
@RestController
@RequestMapping("test")
public class TestCtrl {
    @GetMapping("/dataList")
    public List<String> dataList(){
        List<String> list = Arrays.asList("我是数据计算微服务  001","我是数据计算微服务  002","我是数据计算微服务  003");
        return list;
    }
}
