package com.vissun.BackEnd_vissun.Service;

import com.vissun.BackEnd_vissun.Bean.FileForm;
import com.vissun.BackEnd_vissun.Repository.FileFormRepo;
import com.vissun.BackEnd_vissun.Utils.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in15:06 2018/3/14
 */
@Service
public class FileManagerService {
    private static final Logger logger= LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private FileFormRepo fileFormRepo;

    /**
     * 删除文件 权限要求为 管理员 admin
     * @param fileName
     */
    public String deleteFile(String fileName){
        if (ShiroUtil.isAdmin()){
            try{
                File file =new File("/root/fengdonghao/data/"+fileName);
                file.delete();
            }catch (Exception e){
                e.printStackTrace();
            }

            logger.info(fileName+":file has already deleted!");
            FileForm fileForm=fileFormRepo.findByFileName(fileName);
            System.out.println(fileForm);
            Integer id=fileForm.getId();
            fileFormRepo.delete(id);
            return "success";
        }else{
            logger.info("current user no permission!");
            return "no permission";
        }

    }


}
