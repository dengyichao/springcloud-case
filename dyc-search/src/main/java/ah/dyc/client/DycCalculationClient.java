package ah.dyc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author dengyichao
 * @Title: DycCalculation
 * @Package: ah.dyc.client
 * @Description:
 * @date 2019/5/22  15:39
 */
@FeignClient("dyc-calculation")
public interface DycCalculationClient {
    @GetMapping("test/dataList")
    public List<String> dataList();
}
