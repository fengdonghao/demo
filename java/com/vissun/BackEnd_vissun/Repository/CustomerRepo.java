package com.vissun.BackEnd_vissun.Repository;

import com.vissun.BackEnd_vissun.Bean.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer>{

    List<Customer> findByUserId(Integer param);
}
