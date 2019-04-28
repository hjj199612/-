package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dao.GraduateDao;
import com.example.demo.dao.RecruiterDao;
import com.example.demo.dao.SchoolDao;
import com.example.demo.dao.SchoolmanDao;

@Service
public class FilesaveService {
	@Autowired
	SchoolDao schoolDao;
	@Autowired
	SchoolmanDao schoolmanDao;
	@Autowired
	RecruiterDao recruiterDao;
	@Autowired
	GraduateDao graduateDao;

	private boolean deleteFile(File dirFile) {
		// 如果dir对应的文件不存在，则退出
		if (!dirFile.exists()) {
			return false;
		}

		if (dirFile.isFile()) {
			return dirFile.delete();
		} else {

			for (File file : dirFile.listFiles()) {
				deleteFile(file);
			}
		}
		return dirFile.delete();
	}

	//此方法用来将图片上传到想要保存的地址
	public String savePicture(String usertype,String username, String pictureType,String suffix,MultipartFile picture)throws ServletException, IOException {
		//上面的参数分别是用户名，想要删除的图片包含的字段，文件后缀名，图片文件，文件的校验不在这里，在传参数之前一定要校验
		String path = "E:\\workplace\\j2ee\\EmploySystem\\src\\main\\resources\\static\\userFile\\"+usertype+"\\"+ username;
		//保存的地址，需要在springboot的配置里声明映射关系，浏览器才能使用
		File file = new File(path);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(path + "目录不存在，需要创建");
			// 创建目录
			file.mkdir();
		} // 已存文件夹就删除想删除的文件
		else {
			for (File fil : file.listFiles()) {
				String filname = fil.getName();
				if (filname.contains(pictureType)) {//如果文件名包含我要删除的文件的一部分，例如我想删除包含“headpic”字段的文件。
					deleteFile(fil);//那就删除它
				}
			}
		}
		// 更改为固定格式的文件名,时间+“headpic.”+后缀名
		Date now=new Date();
		String filename = pictureType+"_"+String.valueOf(now.getTime())+"." + suffix;
		picture.transferTo(new File(path + File.separator + filename));
		return "/userFile/"+usertype+"/"+username+"/"+filename;
	}

}