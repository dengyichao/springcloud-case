package ah.dyc.service.impl;

import ah.dyc.client.DycCalculationClient;
import ah.dyc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dengyichao
 * @Title: TestServiceImpl
 * @Package: ah.dyc.service.impl
 * @Description:
 * @date 2019/5/22  15:45
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private DycCalculationClient dycCalculationClient;

    @Override
    public List<String> dataList() {
        List<String> list = Arrays.asList("我是数据查询微服务  001","我是数据查询微服务  002","我是数据查询微服务  003");
        return list;
    }

    @Override
    public List<String> searchAndCalculation() {
        List<String> dataList = new ArrayList<>();
        List<String> list = this.dataList();
        List<String> warMethodModelList = dycCalculationClient.dataList();
        dataList.addAll(list);
        dataList.addAll(warMethodModelList);
        /*for (String str : warMethodModelList){
            dataList.add(str);
        }
        for (String str : list){
            dataList.add(str);
        }*/
        return dataList;
    }
}
