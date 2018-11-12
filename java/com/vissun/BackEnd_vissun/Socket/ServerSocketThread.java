package com.vissun.BackEnd_vissun.Socket;

import com.vissun.BackEnd_vissun.Bean.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in14:35 2018/4/11
 */
public class ServerSocketThread extends Thread{
	private SocketUser user;
	private   static List<SocketUser> list;


	public ServerSocketThread(SocketUser user, List<SocketUser> list) {
		this.user = user;
		this.list = list;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread());

			while (true) {
				// 信息的格式：(add||remove||chat),收件人,...,收件人,发件人,信息体
				//不断地读取客户端发过来的信息
				String msg = user.getBr().readLine();
				System.out.println(msg);
				String[] str = msg.split(",");
				int i=str.length;
				//转发消息
				switch (str[0]) {
					case "updateName":
						updateName(user,str[1]);
						break;
					case "remove":
						remove(user);// 移除用户
						break;
					case "chat":
						// 转发信息给特定的用户，单发或群发
						for (int a=1;a<=i-3;a++) {
							sendToClient(str[a], msg);
						}
						break;
					case "add":
						addUser(user);//添加用户
					default:
						break;
				}
			}
		} catch (Exception e) {
			System.out.println("客户端异常！");
		} finally {
			try {
				System.out.println(list);
				user.getBr().close();
				user.getSocket().close();
				list.remove(user);
				System.out.println(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//把登录账户作为唯一标识
	private void updateName(SocketUser user,String phoneNumber){
		user.setName(phoneNumber);
		System.out.println(list);
	}
	//在用户列表中添加用户信息
	private void addUser(SocketUser user) {
		list.add(user);
		System.out.println(list);
	}

	//转发消息

	//从用户列表中删除用户
	private void remove(SocketUser user2)throws IOException {
		list.remove(user2);
		System.out.println(list);
	}

	public static void sendToClient(String username, String msg) {

		for (SocketUser user : list) {
			if (user.getName().equals(username)) {
				try {
					PrintWriter pw =user.getPw();
					pw.println(msg);

					System.out.println("消息转发成功！");
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
