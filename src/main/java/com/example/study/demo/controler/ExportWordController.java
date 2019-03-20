package com.example.study.demo.controler;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Controller
public class ExportWordController {

    @PostMapping(value = "/exportWord")
    public @ResponseBody
    String exportWord(Model model, HttpServletRequest request, HttpServletResponse response) {
        String url = request.getParameter("url");
        System.out.println(url);
        boolean result = writeWordFile(url);
        if (result) {
            return "success";
        } else {
            return "fail";
        }
    }

    public boolean writeWordFile(String httpPath) {
        boolean result = false;
        String path = "/Users/a610715/Desktop/";
        try {
            if (!"".equals(path)) {
                File fileDir = new File(path);
                String fileName = "record.doc";
                if (!"".equals(httpPath) && fileDir.exists()) {
                    String content = getHtmlCode(httpPath);
                    byte[] b = content.getBytes();
                    ByteArrayInputStream bais = new ByteArrayInputStream(b);
                    POIFSFileSystem poifs = new POIFSFileSystem();
                    DirectoryEntry directory = poifs.getRoot();
                    DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
                    FileOutputStream ostream = new FileOutputStream(path+ fileName);
                    poifs.writeFilesystem(ostream);
                    bais.close();
                    ostream.close();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getHtmlCode (String url) {
        String str = "";
        try {
            URL u = new URL(url);
            URLConnection uc = u.openConnection();
            InputStream raw = uc.getInputStream();
            InputStream buffer = new BufferedInputStream(raw);
            Reader r = new InputStreamReader(buffer);
            int c;

            while ((c = r.read()) != -1) {
                str += (char)c;
                //System.out.print((char)c);
            } // end while
        }
        catch (IOException e) {
            System.err.println(e);
        }// end catch
        System.out.print(str);
        return str;
    }
}
