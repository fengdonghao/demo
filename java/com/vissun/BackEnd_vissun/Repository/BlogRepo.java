package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:42 2018/5/10
 */
public interface BlogRepo extends JpaRepository<Blog,Integer>{

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "SELECT title,id,author FROM blog  ",nativeQuery = true)
	public List<String> findAllTitle();


	@Query(value = "select count(*) from blog",nativeQuery = true)
	int getAllBlogNumber();


}
