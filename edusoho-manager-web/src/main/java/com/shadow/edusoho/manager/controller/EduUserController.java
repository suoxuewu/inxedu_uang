package com.shadow.edusoho.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.edusoho.bean.EduUser;
import com.edusoho.service.EduUserService;
import com.edusoho.util.ExcelUtils;
import com.edusoho.vo.Term;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/7/27.
 */
@Controller
public class EduUserController {

    @Reference
    private EduUserService eduUserService;


    @RequestMapping(value = "user",method = RequestMethod.GET)
    public String getAllEduUser(){
         return "user";
    }

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping(value = "batchUser",method = RequestMethod.GET)
    public String batchUser(){
        return "batchUser";
    }

    @RequestMapping(value = "getPage",method = RequestMethod.POST)
    @ResponseBody
    public String getPage(Term termParams){
         Map<String, Object> pages = eduUserService.getPage(termParams);
        return JSON.toJSONString(pages);
    }
    /**a
     * 修改用户密码
     * @param request
     * @return
     */
    @RequestMapping(value = "updateEduUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public String updateEduUserInfo(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String enterPasswd = request.getParameter("enterPasswd");
        String userId = request.getParameter("userId");

        if(enterPasswd == null || enterPasswd.length() == 0){
            throw  new NullPointerException("enterPasswd == null");
        }
        if(userId == null || userId.length() == 0){
            throw  new NullPointerException("userId == null");
        }
        map.put("enterPasswd",enterPasswd);
        map.put("userId",userId);
        boolean b = eduUserService.updateEduUserInfo(map);
        HashMap<String, Object> result = new HashMap<String,Object>();
        result.put("result", b);
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "updateEduUserInfoStatus",method = RequestMethod.POST)
    @ResponseBody
    public String updateEduUserInfoStatus(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        String isAvalible = request.getParameter("isAvalible");
        if(userId == null || userId.length() == 0){
            throw  new NullPointerException("userId == null");
        }
        if(isAvalible == null || isAvalible.length() == 0){
            throw  new NullPointerException("isAvalible == null");
        }
        map.put("isAvalible",isAvalible);
        map.put("userId",userId);
        boolean b = eduUserService.updateEduUserInfoStatus(map);
        HashMap<String, Object> result = new HashMap<String,Object>();
        result.put("result", b);
        return JSON.toJSONString(result);
    }
    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response){
        try {
            //文件所在的绝对路径
            String filePath = "C:/Users/Administrator/Desktop/批量开通学员模版.xlsx";
            //1.文件的输入流
            FileInputStream fis = new FileInputStream(new File(filePath));

            //2.根据文件名获取文件的媒体类型
            String mimeType = request.getServletContext().getMimeType(filePath);
            String contentDisposition = "attachment;filename="+new String("批量开通模版.xlsx".getBytes("UTF-8"),"ISO-8859-1");

            //3.设置响应头
            response.setHeader("Content-Type",mimeType);
            response.setHeader("Content-Disposition",contentDisposition);
            //获取绑定了客户端的流
            ServletOutputStream outputStream = response.getOutputStream();
            org.apache.tomcat.util.http.fileupload.IOUtils.copy(fis,outputStream);
            fis.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

    /**
     * 上传Excel
     * Excel共有两种格式:xls(03版本)和xlsx(07及之后版本)
     * POI提供了两个对应接口类:
     *                      HSSFWorkbook  xls(03版本)
     *                      XSSFWorkbook  xlsx(07及之后版本)
     * @param request
     * @param response
     */
   @RequestMapping("uploadExcel")
    public void uploadExcel(HttpServletRequest request,HttpServletResponse response){
       HashMap<String, Object> map = new HashMap<String, Object>();

       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       List<EduUser> eduUsers = new ArrayList<EduUser>();
       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
       for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
           String key = entry.getKey();
           MultipartFile file = entry.getValue();
           //将Excel中的数据存储到集合中

           Workbook workbook = null;
           //判断是xls和xlsx
           try {
               boolean flag = ExcelUtils.isExcel2003(file.getOriginalFilename());
               if(!flag){
                   boolean flag2007 = ExcelUtils.isExcel2007(file.getOriginalFilename());
                   if(flag2007){
                       workbook= new XSSFWorkbook(file.getInputStream());
                   }
               }else{
                   workbook= new HSSFWorkbook(file.getInputStream());
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
           if(workbook == null)
               throw new NullPointerException("file == null");
            //获取一共有多少sheet,遍历
           int numberOfSheets = workbook.getNumberOfSheets();
           for (int i = 0; i < numberOfSheets; i++) {
               EduUser eduUser = new EduUser();
               Sheet sheet = workbook.getSheetAt(i);
               //获取Sheet中有多少行,遍历每一行
               int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
               for (int j = 0; j < physicalNumberOfRows; j++) {
                    if(j == 0){
                        continue;//标题行
                    }
                   Row row = sheet.getRow(j);
                   String email =  row.getCell(0).getStringCellValue();
                    //强制设置成String类型
                   row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                   String phone = row.getCell(1).getStringCellValue();
                   row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                   String passwd =  row.getCell(2).getStringCellValue();
                   eduUser.setEmail(email);
                   eduUser.setMobile(phone);
                   eduUser.setPassword(passwd);
                   eduUser.setIsAvalible(1);
                   eduUser.setAge(0);
                   eduUser.setSex(0);
                   eduUser.setMsgNum(String.valueOf(0));
                   eduUser.setSysMsgNum(String.valueOf(0));
                   eduUser.setCreateTime(new Date());

                   eduUsers.add(eduUser);
               }
           }

           Boolean aBoolean = eduUserService.batchSaveEduUserInfo(eduUsers);
           /*if(aBoolean){
               map.put("flag",true);
           }else{
               map.put("flag",false);
           }*/

       }
/*       return JSON.toJSONString(map);*/
   }

   @RequestMapping("exportExcel")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response){

       List<EduUser> allEduUser = eduUserService.getAllEduUser();
       XSSFWorkbook wb = null;
       String contextPath = request.getContextPath();
       String bastPath = "C:/Users/Administrator/Desktop/Portrait-master/WEB-INF/upload";
      /* if(contextPath.length()>0){
           bastPath = request.getScheme()+"://192.168.25.38:"+request.getServerPort()+"/"+contextPath+"/upload/";
       }else{
           bastPath = request.getScheme()+"://192.168.25.38:"+request.getServerPort()+"/upload/";
       }*/
       String excelPath = bastPath + "/7677bf9c4edf4a658bf80bec4855e5af-导出学员数据.xlsx";


       try {
           File file =  new File(excelPath);

           wb = new XSSFWorkbook(new FileInputStream(file));
           // 读取了模板内所有sheet内容
           XSSFSheet sheet = wb.getSheetAt(0);

           XSSFCellStyle style = wb.createCellStyle();//设置列样式
           style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
           style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中



           // 在相应的单元格进行赋值
           int rowIndex = 1;
           int j = 1;
           for (EduUser eduUser : allEduUser) {
               XSSFRow row = sheet.getRow(rowIndex);
               sheet.autoSizeColumn((short)rowIndex);
               if (null == row) {
                   row = sheet.createRow(rowIndex);
               }
               XSSFCell cell0 = row.getCell(0);
               if (null == cell0) {
                   cell0 = row.createCell(0);
               }
               cell0.setCellValue(eduUser.getUserId()); //用户ID

               XSSFCell cell1 = row.getCell(1);
               if (null == cell1) {
                   cell1 = row.createCell(1);
               }
               cell1.setCellValue(eduUser.getMobile());//手机号

               XSSFCell cell2 = row.getCell(2);
               if (null == cell2) {
                   cell2 = row.createCell(2);
               }
               cell2.setCellValue(eduUser.getEmail());//邮箱

               XSSFCell cell3 = row.getCell(3);
               if (null == cell3) {
                   cell3 = row.createCell(3);
               }
               cell3.setCellValue(eduUser.getPassword());//密码

               XSSFCell cell4 = row.getCell(4);
               if (null == cell4) {
                   cell4 = row.createCell(4);
               }
               cell4.setCellValue(eduUser.getUserName());//用户名


               XSSFCell cell5 = row.getCell(5);
               if (null == cell5) {
                   cell5 = row.createCell(5);
               }
               cell5.setCellValue(eduUser.getShowName());//昵称


               XSSFCell cell6 = row.getCell(6);
               if (null == cell6) {
                   cell6 = row.createCell(6);
               }
               Integer sex = eduUser.getSex();
               if(sex == 0){
                   cell6.setCellValue("男");//性别
               }else{
                   cell6.setCellValue("女");//性别
               }

               XSSFCell cell7 = row.getCell(7);
               if (null == cell7) {
                   cell7 = row.createCell(7);
               }
               cell7.setCellValue(eduUser.getAge());//年龄


               XSSFCell cell8 = row.getCell(8);
               if (null == cell8) {
                   cell8 = row.createCell(8);
               }

               if(eduUser.getCreateTime() == null){
                   cell8.setCellValue("");//lastSystemTime
               }else{
                   SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String format = foramt.format(eduUser.getCreateTime());
                   cell8.setCellValue(format);//创建时间
               }


               XSSFCell cell9 = row.getCell(9);
               if (null == cell9) {
                   cell9 = row.createCell(9);
               }
               if(sex == 1){
                   cell9.setCellValue("正常");//状态
               }else{
                   cell9.setCellValue("冻结");//状态
               }

                 XSSFCell cell10 = row.getCell(10);
               if (null == cell10) {
                   cell10 = row.createCell(10);
               }
               cell10.setCellValue(eduUser.getPicImg());//PIC_img

               XSSFCell cell11 = row.getCell(11);
               if (null == cell11) {
                   cell11 = row.createCell(11);
               }
               cell11.setCellValue(eduUser.getBannerUrl());//bannar_url




               XSSFCell cell12 = row.getCell(12);
               if (null == cell12) {
                   cell12 = row.createCell(12);
               }
               cell12.setCellValue(eduUser.getMsgNum());//msg_num


               XSSFCell cell13 = row.getCell(13);
               if (null == cell13) {
                   cell13 = row.createCell(13);
               }
               cell13.setCellValue(eduUser.getSysMsgNum());//sysMsgNum


               XSSFCell cell14 = row.getCell(14);
               if (null == cell14) {
                   cell14 = row.createCell(14);
               }
               if(eduUser.getLastSystemTime() == null){
                   cell14.setCellValue("");//lastSystemTime
               }else{
                   SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String format = foramt.format(eduUser.getLastSystemTime());
                   cell14.setCellValue(format);//lastSystemTime
               }
               rowIndex++;
           }
           String fileName = "学员信息";
           //ByteArrayOutputStream os = new ByteArrayOutputStream();
           ByteArrayOutputStream os = new ByteArrayOutputStream();

           wb.write(os);
           byte[] content = os.toByteArray();
           InputStream is = new ByteArrayInputStream(content);
           // 设置response参数，可以打开下载页面
           response.reset();
           String mimeType = request.getServletContext().getMimeType(excelPath);
           response.setContentType(mimeType);
          // String contentDisposition = "attachment;filename="+new String("批量开通模版.xlsx".getBytes("UTF-8"),"ISO-8859-1");
           response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
           ServletOutputStream sout = response.getOutputStream();
           BufferedInputStream bis = null;
           BufferedOutputStream bos = null;



           try {
               bis = new BufferedInputStream(is);
               bos = new BufferedOutputStream(sout);
               byte[] buff = new byte[2048];
               int bytesRead;
               // Simple read/write loop.
               while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                   bos.write(buff, 0, bytesRead);
               }
           } catch (Exception e) {
               System.out.println("导出excel出现异常");
               e.printStackTrace();
           } finally {
               if (bis != null)
                   bis.close();
               if (bos != null)
                   bos.close();
           }
       } catch (Exception e) {
           System.out.println("导出excel出现异常");
           e.printStackTrace();
       }


   }

}
