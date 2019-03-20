package com.example.study.demo.controler;

import com.example.study.demo.bean.JSONResult;
import org.apache.poi.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Controller
public class UploadController {

    private static final int BUFFER_SIZE = 2048;

    @RequestMapping(value = "/uploadGCNO", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONResult upload(HttpServletRequest request, HttpServletResponse response) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("zipFile");
        String version = request.getParameter("version");
        if (file.isEmpty()) {
            JSONResult result = new JSONResult();
            result.msg = "uploadFile is Empty";
            result.status = 400;
            return result;
        } else {
            String savePath = "/Users/a610715/Desktop/uploadFiles";
            String fileName = UUID.randomUUID().toString().replace("-","");
            String fileSuffix = getFileSuffix(file);
            if (fileSuffix != null) {
                fileName += fileSuffix;
            }
            if (fileSuffix.equals(".zip")) {
                savePath = savePath + "/" + version;
                //解压文件并保存在版本号路径里面
                File targetFile = new File(savePath + File.separator + fileName);
                FileOutputStream fileOutputStream = null;
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                try {
                    fileOutputStream = new FileOutputStream(targetFile);
                    IOUtils.copy(file.getInputStream(), fileOutputStream);
                    unZip(targetFile, savePath);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            JSONResult result = new JSONResult();
            result.msg = "uploadFile Success";
            result.status = 200;
            return result;
        }
    }

    private String getFileSuffix(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        int suffixIndex = fileName.lastIndexOf(".");
        if (suffixIndex == -1) {
            return null;
        } else {
            return fileName.substring(suffixIndex);
        }
    }

    /**
     2
     * zip解压
     3
     * @param srcFile        zip源文件
    4
     * @param destDirPath     解压后的目标文件夹
    5
     * @throws RuntimeException 解压失败会抛出运行时异常
    6
     */
    public static void unZip (File srcFile, String destDirPath) throws RuntimeException {
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)entries.nextElement();
                System.out.println("解压" + entry.getName());
                if (entry.isDirectory()) {
                    // 如果是文件夹， 先创建文件夹
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdir();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    if (!targetFile.getParentFile().isDirectory()) {
                        targetFile.getParentFile().mkdir();
                    }
                    targetFile.createNewFile();
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    is.close();
                    srcFile.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SpringBootApplication
    public static class DemoApplication {

        public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);
        }

    }
}
