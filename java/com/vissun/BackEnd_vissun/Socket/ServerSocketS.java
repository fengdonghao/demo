package com.vissun.BackEnd_vissun.Socket;

import com.vissun.BackEnd_vissun.Bean.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in14:28 2018/4/11
 */
@Service
public class ServerSocketS {

	public static List<SocketUser> list;

	private ServerSocket serverSocket;

	public void serverSocket(){
		int count=0;
		try{
			//存放 连接上服务器的用户 列表
			 list= new ArrayList<SocketUser>();
			serverSocket=new ServerSocket(5555);//开启服务的端口，需和客户端一致
			System.out.println("服务端已经启动，等待客户端连接");
			while (true){
				Socket socket=serverSocket.accept();
				count++;
				//Math.round(Math.random() * 100)
				SocketUser user = new SocketUser("user" + count,socket);
				System.out.println(user.getName() + "正在登录。。。");
				list.add(user);//把新增的用户添加到list里面

				System.out.println(list);
				System.out.println("客户端的IP："+socket.getInetAddress().getHostAddress());
				Thread thread=new ServerSocketThread(user,list);
				thread.setName(user.getName());
				thread.start();
//				new ServerSocketThread(user,list).start();//开启输入和输出的多线程
				ServerSocketThread.sendToClient(user.getName(),"state,连接服务器success");
			}
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}
}
