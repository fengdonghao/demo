package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date： Create in 11:02 2018/5/30
 */
public interface MessageRepo extends JpaRepository<Message,Integer> {

}
