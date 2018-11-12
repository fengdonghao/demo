package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserRepo extends JpaRepository<User,Integer> {

    public User findByUsername(String username);
    public User findByPhoneNumber(String phoneNumber);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT username FROM user ",nativeQuery = true)
    public List<String>  findUsername();


	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update user set head_path =?1 where phone_number=?2",nativeQuery = true)
	int updateHeadByPhoneNumber(String headPath, String phoneNumber);

	/**
	 * 用户注册时，赋予用户角色：普通员工
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO user_role(user_id,role_id) VALUES (?1,?2)",nativeQuery = true)
	public int  addPermission(int userId,int roleId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO user_device(user_id,device_id) VALUES (?1,?2)",nativeQuery = true)
	public int  addDevice(int userId,int deviceId);

}
