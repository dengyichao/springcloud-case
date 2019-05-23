package ah.dyc.web;

import ah.dyc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dengyichao
 * @Title: TestCtrl
 * @Package: ah.dyc.web
 * @Description:
 * @date 2019/5/22  15:45
 */
@RestController
@RequestMapping("test")
public class TestCtrl {

    @Autowired
    private TestService testService;

    @GetMapping("/dataList")
    public List<String> dataList() {

        return testService.dataList();
    }

    @GetMapping("/searchAndCalculation")
    public List<String> searchAndCalculation() {
        return testService.searchAndCalculation();
    }
}
