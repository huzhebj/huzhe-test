package com.hualala.controller.file;

import com.hualala.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
public class UploadAndDownload {

    public static Logger logger = LoggerFactory.getLogger(UploadAndDownload.class);

    // 单文件上传
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();//包含后缀名
            logger.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")+1);
            logger.info("文件的后缀名为：" + suffixName);

            // 设置文件存储路径
            String filePath = "D://aim//";
            String path = filePath + fileName;

            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "上传成功,保存路径是:"+path;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    //多文件上传
    @RequestMapping(value = "/uploadMore", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            String filePath = "D://aim//";
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(filePath + file.getOriginalFilename())));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "第 " + i + " 个文件上传失败  ==> "
                            + e.getMessage();
                }
            } else {
                return "第 " + i
                        + " 个文件上传失败因为文件为空";
            }
        }
        return "上传成功";
    }

    //文件下载相关代码
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        //适合Word,excel,pdf,xmind,jpg,bmp等格式
        String fileName = "git分支管理及代码提交.xmind";// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://aim//";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + parseGBK(fileName));// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    // 将GBK字符转化为ISO码
    public static String parseGBK(String sIn) {
        if (sIn == null || sIn.equals(""))
            return sIn;
        try {
            return new String(sIn.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException usex) {
            return sIn;
        }
    }

    /**
     * 下载资料
     * url 资源文件路径，比如：http://res.hualala.com/group3/M00/F9/C6/wKgVwlsHZ0TtE538ADu_wWpq_kI540.jpg
     * fileName 下载后文件名，比如：笔记本键盘布局.jpg
     * 访问此接口的url示例：
     * http://localhost/download2?url=http://res.hualala.com/group3/M00/F9/C6/wKgVwlsHZ0TtE538ADu_wWpq_kI540.jpg&fileName=笔记本键盘布局.jpg
     */
    @RequestMapping("/download2")
    public Map<String, Object> downloadFile2(HttpServletRequest request,
                                             @RequestParam String url,
                                             @RequestParam String fileName,
                                             HttpServletResponse response) {
        logger.info("请求参数->url["+url+"];fileName["+fileName+"]");
        if(StringUtils.isBlank(url) || StringUtils.isBlank(fileName))
            return DataUtil.sendResponse("001","url和fileName都不能为空");
        OutputStream os = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        try {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();//找不到文件时此处会抛异常
            bis = new BufferedInputStream(inputStream);
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + parseGBK(fileName));// 设置文件名，此处不使用parseGBK下载后的文件名中的汉字会乱码
            byte[] buffer = new byte[1024];
            os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            logger.info("文件下载成功");
            return DataUtil.sendResponse("000",null);
        }catch (Exception e){
            logger.error("文件下载异常",e);
        }finally {
            try {
                if(os!=null){
                    os.flush();
                    os.close();
                }
                if(bis!=null){
                    bis.close();
                }
                if(conn!=null){
                    conn.disconnect();
                }
            } catch (IOException e) {
                logger.error("关闭流异常",e);
            }
        }
        logger.error("文件下载失败");
        return DataUtil.sendResponse("002","文件下载失败");
    }

}
