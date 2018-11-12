package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in17:04 2018/4/20
 */
public interface DeviceRepo extends JpaRepository<Device,Integer>{


	public Device findByDeviceNumber(String deviceNumber);



}
