package ah.dyc.controller;

import ah.dyc.client.DycSearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author dengyichao
 * @Title: DycSearchCtrl
 * @Package: ah.dyc.controller
 * @Description:
 * @date 2019/5/22  16:12
 */
@Controller
@RequestMapping("dyc-search")
public class DycSearchCtrl {

    @Autowired
    private DycSearchClient dycSearchClient;

    @RequestMapping("/index")
    public String index(Model model){
        List<String> dataList = dycSearchClient.dataList();
        model.addAttribute("dataList",dataList);
        return "dyc-search/index";
    }

}
