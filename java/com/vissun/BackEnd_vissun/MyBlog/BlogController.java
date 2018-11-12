package com.vissun.BackEnd_vissun.MyBlog;

import com.vissun.BackEnd_vissun.Bean.Blog;
import com.vissun.BackEnd_vissun.Bean.Message;
import com.vissun.BackEnd_vissun.Repository.BlogRepo;
import com.vissun.BackEnd_vissun.Repository.MessageRepo;
import com.vissun.BackEnd_vissun.Utils.RedisUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in11:25 2018/4/27
 */
@Controller
public class BlogController {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private RedisUtil redisUtil;

	@Autowired
	private BlogRepo blogRepo;

    private static  final Logger logger = LoggerFactory.getLogger(BlogController.class);

	/**
	 * 主页
	 * @param request
	 * @param map
	 * @return
	 */
	@GetMapping("blog/index")
	public String index(HttpServletRequest request,Map<String,String > map){
		HttpSession session=request.getSession(false);
		if (session!=null){
			String phoneNumber = (String) session.getAttribute("phoneNumber");
			logger.info(phoneNumber);
			map.put("phoneNumber",phoneNumber);
		}
		return "MyBlog/index";
	}

	@GetMapping("blog/top")
	public String top(){
		return "MyBlog/top";
	}

	/**
	 *
	 * @param map
	 * @return
	 */
	@GetMapping("blog/mid")
	public String mid(HttpServletRequest request,Map<String,Long> map){
		Long articleCount=blogRepo.count();
        //访问人数：在一小时之内 ，同一个浏览器访问，只会增加一个访问量
        HttpSession session = request.getSession();
        if(session!=null){
            if (!redisUtil.exists(session.getId())){
                redisUtil.set(session.getId(),"",60*60L); //有效时间一小时
                logger.info(session.getId());
                //访问人数
                Integer count = Integer.valueOf(redisUtil.get("count"));
                Integer tmp = count+1;
                redisUtil.set("count",tmp.toString());
            }
		}
        map.put("total",Integer.valueOf(redisUtil.get("count")).longValue());
        map.put("articleCount",articleCount);
		return "MyBlog/mid";
	}
	@ResponseBody
	@GetMapping("/blog/test")
	public String test(){
	    redisUtil.set("count","0");
	    return "success";
    }





	@GetMapping("blog/main")
	public String main(){
		return "MyBlog/main";
	}

	@GetMapping("blog/msg")
	public String msgG(Map<String,List> map){
	    List<Message> list=new ArrayList<>();
	    list=messageRepo.findAll();
	    map.put("list",list);
		return "MyBlog/msg";
	}
	@ResponseBody
	@PostMapping("blog/msgP")
	public String msgP(Message msg,HttpServletRequest request){
	    HttpSession session=request.getSession(false);
	    if (session.getAttribute("phoneNumber") == null){
	        return "登录后才可留言！谢谢";
        }
        String phoneNumber=(String)session.getAttribute("phoneNumber");
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(day);
        Message message=new Message();
        message.setMsg(msg.getMsg());
        message.setTime(time);
        message.setUsername(phoneNumber);
        messageRepo.save(message);
        return "success";
    }


	@GetMapping("blog/aboutMe")
	public String aboutMe(){
		return "MyBlog/aboutMe";
	}

	/**
	 * 弹出新的显示文章的网页
	 * @param map
	 * @param id
	 * @return
	 */
	@GetMapping("blog/mid1/{id}")
	public String mid1(Map<String,Integer> map,@PathVariable("id") int id){
		Long articleCount=blogRepo.count();
		map.put("articleCount",articleCount.intValue());
		map.put("id",id);
		return "MyBlog/mid1";
	}

	@GetMapping("blog/blog")
	public String blog(Map<String,List> map){
		List<Blog> list=new ArrayList<>();
		list=blogRepo.findAll();
		map.put("list",list);
		return "MyBlog/blogList";
	}


	@ResponseBody
	@PostMapping("blog/addBlog")
	public String addBlog(Blog blog){
		blogRepo.save(blog);
		return "success";
	}


	/**
	 * 查看文章详情
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping("blog/skip/{id}")
	public String   skip(HttpServletRequest request,Map<String ,Object> map, @PathVariable("id")int id){
		HttpSession session=request.getSession(false);
		if (session!=null){
			String phoneNumber = (String) session.getAttribute("phoneNumber");
			System.out.println(phoneNumber);
			map.put("phoneNumber",phoneNumber);
		}
		map.put("id",id);
		return "MyBlog/index1";
	}

	/**
	 * 获取整片文章
	 * @param response
	 * @param model
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping("blog/getBlog/{id}")
	public String   getBlog(HttpServletResponse response, Model model, Map<String ,String> map, @PathVariable("id")int id){
		response.setHeader("Content-Type", "text/plain");
		Blog blog=blogRepo.findOne(id);
		map.put("title",blog.getTitle());
		map.put("content",blog.getContent());
		return "MyBlog/blogPage";
	}


	@ResponseBody
	@GetMapping("blog/ajaxTest")
	public String ajaxTest(){
		System.out.println("ajax 请求过来啦");
		return "test is success";
	}

	@GetMapping("blog/ajaxTest1")
	public String ajaxTest1(){
		return "Register1";
	}
}

