package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.FileForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in11:32 2018/3/14
 */
public interface FileFormRepo extends JpaRepository<FileForm,Integer>{

    @Transactional
    public FileForm findByFileName(String fileName);
}
