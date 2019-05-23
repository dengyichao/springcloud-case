package ah.dyc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author dengyichao
 * @Title: DycCalculationClent
 * @Package:  ah.dyc.client
 * @Description:
 * @date 2019/5/22  16:09
 */
@FeignClient("dyc-calculation")
public interface DycCalculationClent {
    @GetMapping("test/dataList")
    public List<String> dataList();
}
