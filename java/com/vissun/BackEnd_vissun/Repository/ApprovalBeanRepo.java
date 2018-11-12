package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.ApprovalBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in10:34 2018/4/28
 */
public interface ApprovalBeanRepo extends JpaRepository<ApprovalBean,Integer>{

	public List<ApprovalBean> findByCreator(String creator);

	public List<ApprovalBean> findByCarbonCopy(String carbonCopy);

}
