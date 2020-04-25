package com.wangshangyi.tracker.track;

import com.wangshangyi.tracker.Traceno;
import com.wangshangyi.tracker.TracenoExample;
import com.wangshangyi.tracker.dao.TracenoMapper;
import com.wangshangyi.tracker.util.CharUtil;
import com.wangshangyi.tracker.util.LocalStorage;
import com.wangshangyi.tracker.util.ResponseUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.crypto.spec.PSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    // 根据传递的资源，创建excel表格对象
    public List<String> createWorkbook(String fileName, String suffix, InputStream in) throws Exception{
        InputStream is = null;
        if(in == null){
            is = new FileInputStream(fileName);
        }else{
            is = in;
        }
        List<String> result = parse(is, suffix, 1);
        return result;
    }
    // 解析文件的方法
    /*
     * in : 输入流
     * suffix : 要读取的文件的后缀 xls xlsx
     * startrow : 从第几行开始读取， 传入的是行数， 使用的时候转换为行下标
     */
    public List<String> parse(InputStream in, String suffix, int startrow) throws Exception{
        Workbook workbook = null;
        List<String> result = new ArrayList<String>();
        if(suffix.equals("xls")){
            // 2003版的Excel
            workbook = new HSSFWorkbook(in);
        }else{
            // 2007版的Excel， 需要额外的jar文件， 在poi文件夹中附带
            workbook = new XSSFWorkbook(in);
        }
        Sheet s = workbook.getSheetAt(0);
        if(s == null){
            return null;
        }
        int lastRowNum = s.getLastRowNum(); // 最后一行行数
        for(int i = startrow-1; i <= lastRowNum; i++){
            String rowStr = ""; // 每行数据保存在这个变量中
            Row row = s.getRow(i);
            if(row != null){
                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();
                if(firstCellNum != lastCellNum){
                    for(int j = firstCellNum; j <= lastCellNum; j++){
                        Cell cell = row.getCell(j);
                        if(cell == null){
                            rowStr += "";
                        }else{
                            rowStr += parseCell(cell) + ";";
                        }
                    }
                }
                result.add(rowStr.substring(0, rowStr.length()-1));
            }
        }
        return result;
    }
    // 解析单元格数据的方法
    public String parseCell(Cell cell){
        String result = "";
        switch(cell.getCellType()){
            case NUMERIC: // 数学类型
                if(HSSFDateUtil.isCellDateFormatted(cell)){ // 日期类型
                    SimpleDateFormat sdf = null;
                    if(cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")){
                        // 日期类型格式化为 小时:分钟
                        sdf = new SimpleDateFormat("HH:mm");
                    }else{
                        // 日期类型格式化为 年月日
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date d = cell.getDateCellValue(); // 获取日期类型单元格的数据
                    result = sdf.format(d);
                }else if(cell.getCellStyle().getDataFormat() == 58){
                    // 自定义日期类型， 自定义日期类型的格式化ID值为58.
                    // 2016年06月02日
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date d = DateUtil.getJavaDate(value); // POI提供的工具， 用于转换数据到日期的工具。
                    result = sdf.format(d);
                }else{
                    // 纯数学类型
                    double value = cell.getNumericCellValue();
                    DecimalFormat f = new DecimalFormat();
                    CellStyle cs = cell.getCellStyle();
                    String t = cs.getDataFormatString(); // 数据格式类型
                    if(t.equals("General")){ // 代表标准数学类型
                        f.applyPattern("#");
                    }
                    result = f.format(value);
                }
                break;
            case BOOLEAN :  // 布尔类型
                result = "";
                break;
            case STRING : // 字符串类型
                result = cell.getRichStringCellValue().toString();
                break;
            default:
                result = "";
        }

        return result;
    }

}
