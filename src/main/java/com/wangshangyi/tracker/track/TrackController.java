package com.wangshangyi.tracker.track;

import com.wangshangyi.tracker.Traceno;
import com.wangshangyi.tracker.TracenoExample;
import com.wangshangyi.tracker.dao.TracenoMapper;
import com.wangshangyi.tracker.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TrackController {

    @Resource
    private TracenoMapper tracenoMapper;

    @RequestMapping("/track")
    public String track() {
        return "Greetings from Spring Boot!";
    }



    @RequestMapping("/api/modify/change/status/headNo")
    public Object modify(@RequestParam(defaultValue = "-1") Integer wid,
                         @RequestParam(defaultValue = "packing") String status) {
        if(wid.equals(-1)){
            return ResponseUtil.badArgument();
        }else{
            TracenoExample example = new TracenoExample();
            example.or().andWidEqualTo(String.valueOf(wid));
            long result = tracenoMapper.countByExample(example);
            System.out.println("result  is  " + result);
            Traceno tcn = tracenoMapper.selectOneByExample(example);
            tcn.setStatus(status);
//            Integer result = tracenoMapper.updateByPrimaryKey(tcn);
            return ResponseUtil.ok(result);
        }

    }


}
