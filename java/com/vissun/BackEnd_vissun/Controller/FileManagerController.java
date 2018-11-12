package com.vissun.BackEnd_vissun.Controller;


import com.vissun.BackEnd_vissun.Bean.FileForm;
import com.vissun.BackEnd_vissun.Repository.FileFormRepo;
import com.vissun.BackEnd_vissun.Service.FileManagerService;
import com.vissun.BackEnd_vissun.Utils.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in11:10 2018/3/14
 */
@Controller
public class FileManagerController {
    public static  final Logger logger= LoggerFactory.getLogger(FileManagerController.class);

    @Autowired
    private FileFormRepo fileFormRepo;

    @Autowired
    private FileManagerService fileManagerService;

    /**
     * 文件管理界面
     * @return
     */
    @GetMapping("/fileManager")
    public String fileManager(Map<String,Object> map){
        List<FileForm> list=fileFormRepo.findAll();
        map.put("fileList",list);
        return "File/fileManager";
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        String fileName=file.getOriginalFilename();
        logger.info("uploadFile's name :"+fileName);
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        String filePath="/root/fengdonghao/data/";

        File dest=new File(filePath+fileName);
        try {
            file.transferTo(dest);
            FileForm fileForm=new FileForm();
            fileForm.setFileName(fileName);
            fileFormRepo.save(fileForm);
            return "success";
        }catch (IOException e){
            e.printStackTrace();
            return "error";
        }catch (IllegalStateException e){
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletResponse res, String fileName)throws UnsupportedEncodingException{

        System.out.println(fileName);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("/root/fengdonghao/data/" + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String username= ShiroUtil.getCurrentUserName();
        logger.info(username+"--"+fileName+"--file download success");

    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteFile")
    public String deleteFile(String fileName){
        String msg=fileManagerService.deleteFile(fileName);
        return msg;
    }
}
