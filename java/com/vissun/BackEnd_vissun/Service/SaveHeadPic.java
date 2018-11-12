package com.vissun.BackEnd_vissun.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date：Create in9:32 2018/3/31
 */
@Service
public class SaveHeadPic {

	public String saveHeadPic(MultipartFile file, String username) throws IOException {
		if (!file.isEmpty()) {
			try {
				BufferedOutputStream out1 = new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\Administrator\\IdeaProjects\\FDH_CRM\\src\\main\\resources\\static\\" + username + ".jpg")));
				out1.write(file.getBytes());
				out1.flush();
				out1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			InputStream input1 = null;
			OutputStream output1 = null;
			try {
				input1 = new FileInputStream("C:\\Users\\Administrator\\IdeaProjects\\FDH_CRM\\src\\main\\resources\\static\\noHeadPic.jpg");
				output1 = new FileOutputStream("C:\\Users\\Administrator\\IdeaProjects\\FDH_CRM\\src\\main\\resources\\static\\" + username + ".jpg");
				byte[] buf1 = new byte[1024];
				int bytesRead;
				while ((bytesRead = input1.read(buf1)) > 0) {
					output1.write(buf1, 0, bytesRead);
				}
			} finally {
				input1.close();
				output1.close();
			}

		}
		return "success";
	}
}
