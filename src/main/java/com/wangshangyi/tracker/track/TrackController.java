package com.wangshangyi.tracker.track;

import com.wangshangyi.tracker.Traceno;
import com.wangshangyi.tracker.TracenoExample;
import com.wangshangyi.tracker.dao.TracenoMapper;
import com.wangshangyi.tracker.util.CharUtil;
import com.wangshangyi.tracker.util.LocalStorage;
import com.wangshangyi.tracker.util.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.crypto.spec.PSource;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class TrackController {

    @Resource
    private TracenoMapper tracenoMapper;

    LocalStorage localStorage = new LocalStorage();

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
            Traceno tcn = tracenoMapper.selectOneByExample(example);
            tcn.setStatus(status);
            Integer result = tracenoMapper.updateByPrimaryKey(tcn);
            return ResponseUtil.ok(result);
        }

    }

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        try{
            localStorage.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
            return ResponseUtil.ok();
        }catch (Exception e){
            System.out.println(e);
            return ResponseUtil.fail(400, e.toString());
        }

    }


}
