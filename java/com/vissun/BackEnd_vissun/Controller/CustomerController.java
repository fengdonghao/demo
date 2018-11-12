package com.vissun.BackEnd_vissun.Controller;

import com.vissun.BackEnd_vissun.Bean.Customer;
import com.vissun.BackEnd_vissun.Bean.User;
import com.vissun.BackEnd_vissun.Repository.CustomerRepo;
import com.vissun.BackEnd_vissun.Repository.UserRepo;
import com.vissun.BackEnd_vissun.Service.CustomerService;
import com.vissun.BackEnd_vissun.Utils.ShiroUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CustomerService customerService;


    /**
     * @description: 查找客户
     * @param map
     * @return
     */
    @GetMapping("/custmList")
    public String getCustmList(Map<String,Object> map){
        List<Customer> userList = customerService.findUserByParam();
        System.out.println(userList);
        map.put("userList",userList);
        return "Customer/list";
    }

    /**
     * 编辑客户资料 返回 编辑客户资料的界面
     * @param id 客户ID
     * @param map
     * @param map1
     * @return
     */
    @RequestMapping("/custEdit")
    public String custEdit(Integer id,Map<String,List<String>> map,Map<String,Customer> map1){
        Customer customer=customerRepo.findOne(id);

        map1.put("customer",customer);

        return "Customer/custEdit";
    }

    /**
     * 转移客户资料 返回转移客户资料的界面
     * @param map
     * @param id
     * @return
     */
    @RequestMapping("/custTrans1")
    public String custTrans(Map<String,Object> map,Integer id){
        List<String > usernameList=userRepo.findUsername();
        System.out.println(id);
        map.put("usernameList",usernameList);
        map.put("custId",id);

        return "Customer/custTrans";
    }

    /**
     * 转移客户资料
     * @param username
     * @param custId
     * @return
     * @throws NotFoundException
     */
    @ResponseBody
    @PostMapping("/custTrans")
    public String custTrans1(String username,Integer custId) throws NotFoundException{
        User user=userRepo.findByUsername(username);
        if (user==null){
            return "没有这个接口人！";
        }
        Integer userId=user.getId();
        customerService.tranCustomer(custId,userId);
        return "success";
    }


    @ResponseBody
    @PostMapping("/custSave")
    public String custSave(Customer customer){
       String msg=customerService.saveCustomer(customer);
        return msg;
    }


    @ResponseBody
    @RequestMapping("custDel")
    public String custDel(Integer id){
        String msg=customerService.delCustomer(id);
        if (msg=="success"){
            return "redirect:/custmList";
        }else{
            return msg;
        }


    }

    @GetMapping("/custAdd")
    public String custAdd(){
        return "Customer/custAdd";
    }
    @ResponseBody
    @PostMapping("/custAdd")
    public String custAdd1(Customer customer){
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
        customer1.setContact(ShiroUtil.getCurrentUserName());
        customer1.setUserId(ShiroUtil.getCurrentUserId());
        customerService.saveCustomer(customer1);
        return "success";
    }


}
