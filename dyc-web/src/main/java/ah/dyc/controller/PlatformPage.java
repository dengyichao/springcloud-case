package ah.dyc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dengyichao
 * @Title: PlatformPage
 * @Package: com.tydic.das.controller.page
 * @Description:
 * @date 2018/12/28  9:46
 */
@Controller
@RequestMapping("platformPage")
public class PlatformPage {

    @RequestMapping("sys_management_index")
    public String page() {
        return "das-platform/bigdata/SystemManagement/sys_management_index";
    }

    @RequestMapping("userCenter")
    public String userCenter() {
        System.out.println("我进来啦~~~");
        return "das-platform/bigdata/SystemManagement/user_center";
    }

    @RequestMapping("keywordPage")
    public String keywordPage() {
        System.out.println("keywordPage");
        System.out.println("123456");
        return "das-platform/bigdata/SystemManagement/keyword";
    }

    @GetMapping("/keywordNewAndUpdatePage/{id}")
    public String keywordNewAndUpdatePage(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("method", "updateKw");
        model.addAttribute("id", id);
        return "das-platform/bigdata/SystemManagement/keywordNewAndUpdatePage";
    }


    @GetMapping("/keywordNewAndUpdatePage")
    public String keywordNewAndUpdatePage(Model model) {
        model.addAttribute("method", "insertKw");
        return "das-platform/bigdata/SystemManagement/keywordNewAndUpdatePage";
    }
}
