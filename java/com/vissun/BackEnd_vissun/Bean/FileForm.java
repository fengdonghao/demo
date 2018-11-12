package com.vissun.BackEnd_vissun.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in11:31 2018/3/14
 */
@Entity
public class FileForm {
    @Id
    @GeneratedValue
    private Integer id;

    private String fileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
