package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in14:33 2018/5/22
 */
public interface BlogUserRepo extends JpaRepository<BlogUser,Integer> {

	public BlogUser findByPhoneNumber(String phoneNumber);

}
