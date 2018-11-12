package com.vissun.BackEnd_vissun.Service;

import com.vissun.BackEnd_vissun.Bean.Customer;
import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Exception.ForbiddenException;
import com.vissun.BackEnd_vissun.Repository.CustomerRepo;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Utils.ShiroUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger= LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private UserRepo userRepo;
    /**
     * 保存新客户
     * @param customer
     */
    public String  saveCustomer(Customer customer) {
        Integer num=customer.getUserId();
        if (num.equals(ShiroUtil.getCurrentUserId())){
            Customer customer1=new Customer();
            customer1.setId(customer.getId());
            customer1.setContact(customer.getContact());
            customer1.setUserId(customer.getUserId());
            customer1.setMark(customer.getMark());
            customer1.setAddress(customer.getAddress());
            customer1.setCreatetime(customer.getCreatetime());
            customer1.setCustname(customer.getCustname());
            customer1.setEmail(customer.getEmail());
            customer1.setProgress(customer.getProgress());
            customer1.setProgresstime(customer.getProgresstime());
            customer1.setQq(customer.getQq());
            customer1.setTel(customer.getTel());
            customer1.setWechar(customer.getWechar());
            customerRepo.save(customer1);
            logger.info("{}-添加了新客户-{}",ShiroUtil.getCurrentUserName(),customer.getCustname());
            return "success";
        }else{
            return "不是该客户的负责人，不能修改，请联系该客户的负责人！";
        }

    }

    /**
     * 根据DataTables的查询方式获取 客户列表
     * @ param 如果权限是管理员 则查找全部 如果不是管理员  则只能查找自己的客户
     * @return
     */
    public List<Customer> findUserByParam( ) {
        //如果当前登录的用户是普通员工，那么只能看到自己的客户
        //如果当前登录的用户是经理，那么可以看到所有的客户
        Integer param=null;
        if(!ShiroUtil.isAdmin()&&!ShiroUtil.isManager()) {//不是管理员
            System.out.println("不是管理员或经理");
             param=ShiroUtil.getCurrentUserId();
            System.out.println(param);
            return  customerRepo.findByUserId(param);
        }else{
            //是管理员
            System.out.println("是管理员或经理");
            return customerRepo.findAll();
        }

    }

 /*   *//**
     * 获取客户的总数量
     * @return
     *//*
    public Long findCustomerCount() {
        return  customerRepo.count();
    }

    *//**
     * 根据DataTables的查询方式获取客户总数量
     * @param param
     * @return
     *//*
    public Integer findUserCountByParam(Map<String, Object> param) {
        //如果当前登录的用户是普通员工，那么只能看到自己的客户
        //如果当前登录的用户是经理，那么可以看到所有的客户
        if(!ShiroUtil.isManager()) {
            param.put("userid",ShiroUtil.getCurrentUserId());
        }
        return customerRepo.countByParam(param).intValue();
    }

    *//**
     * 根据客户ID查询对应的客户
     * @param id
     * @return
     *//*
    public Customer findCustomerById(Integer id) {
        Customer customer = customerMapper.findById(id);
        if(customer == null) {
            throw new NotFoundException();
        } else {
            if(ShiroUtil.isManager()) {
                return customer;
            } else {
                if(customer.getUserid() == null || customer.getUserid().equals(ShiroUtil.getCurrentUserId())) {
                    return customer;
                } else {
                    throw new ForbiddenException();
                }
            }
        }
    }

    *//**
     * 根据主键删除客户对象
     * @param id
     */
    public String  delCustomer(Integer id) {
        if(ShiroUtil.isManager()) {
            //删除跟进记录

            //删除客户对象
            customerRepo.delete(id);

            logger.info("{}删除了客户{}",ShiroUtil.getCurrentUserName(),id);
            return "success";
        } else {
            System.out.println("没有权限 不是Manager");
//            throw new ForbiddenException();
            return "不是经理，没有权限,请联系项目经理！";
        }
    }

    /**
     * 公开客户
     * @param
     *//*
    public void publicCustomer(Integer id) {
        Customer customer = customerMapper.findById(id);
        if(customer == null) {
            throw new NotFoundException();
        } else {
            if(customer.getUserid().equals(ShiroUtil.getCurrentUserId())) {
                //公开客户
                customer.setUserid(null);
                customerMapper.update(customer);

                logger.info("{}将客户{}进行了公开",ShiroUtil.getCurrentUserName(),customer.getCustname());
            } else {
                throw new ForbiddenException();
            }
        }

    }

    /**
     * 转交客户
     * @param custId 客户ID
     * @param userId 转入用户ID
     */
    public void tranCustomer(Integer custId, Integer userId) throws NotFoundException{
        Customer customer = customerRepo.findOne(custId);
        if(customer == null) {
            System.out.println("--------客户不存在");
            throw new NotFoundException("客户不存在");
        } else {
            if(customer.getUserId().equals(ShiroUtil.getCurrentUserId())) {
                User user = userRepo.findOne(userId);
                if(user == null) {
                    System.out.println("--------yonghu不存在");
                    throw new NotFoundException("用户不存在");
                } else {
                    customer.setId(custId);
                    customer.setUserId(user.getId());
                    customer.setContact(user.getUsername());
                    customer.setMark(customer.getMark() + "   " + ShiroUtil.getCurrentUserName()+"转交过来的客户");
                    customerRepo.save(customer);

                    logger.info("{}把客户{}转交给了{}",ShiroUtil.getCurrentUserName(),customer.getCustname(),user.getUsername());

                }
            } else {
                System.out.println("没有权限");
                throw new ForbiddenException();
            }
        }
    }

    /**
     * 根据当前登录的用户获取对应的客户
     * @return
     *//*
    public List<Customer> findCustomerByCurrentUser() {
        return customerRepo.findByUserIdAndEmptyUserId(ShiroUtil.getCurrentUserId());
    }

    *//**
     * 首页的统计柱状图
     * @return
     *//*
    public List<Map<String, Object>> homeTotal() {
        return customerRepo.findTotal();
    }

    *//**
     * 获取userId对应客户
     * @param id
     * @return
     *//*
    public List<Customer> findCustomerByUserId(Integer id) {
        return customerRepo.findByUserIdAndEmptyUserId(id);
    }

    *//**
     * 在微信中查询客户
     * @param id
     * @return
     *//*
    public Customer findCustById(Integer id) {
        return  customerRepo.findById(id);
    }*/

}
