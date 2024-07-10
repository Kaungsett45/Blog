package com.java.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.Blog;
import com.java.bean.User;
import com.java.dao.Userdao;

@Controller
public class Usercontroller {
	
	@Autowired
	Userdao userdao;

	//map to postBlog page
	@RequestMapping("/postBlog")
	public ModelAndView postBlog(Model m,ModelAndView model,HttpSession session,@ModelAttribute("Blog")Blog blog)throws IOException, SQLException{
		if(session.getAttribute("user") != null) {
			 int reg_id = (int) session.getAttribute("reg_id");
			 session.getAttribute("userImage");
			    session.getAttribute("user");
			    System.out.println("is that id work "+reg_id);
			    Blob chg = userdao.ChangeimageRefresh(reg_id);
				   byte[] imageByte = chg.getBytes(1,(int) chg.length());
		    	   String base64Image = Base64.getEncoder().encodeToString(imageByte);
					    m.addAttribute("userImage", "data:image/jpeg;base64," + base64Image);
		}
		
		model.setViewName("postBlog");
		return model;
	}
	//map to setting page
	@RequestMapping("/Setting")
	public ModelAndView Setting(Model m,ModelAndView model,HttpSession session,@ModelAttribute("reg")User user2)throws IOException, SQLException{
		
		if(session.getAttribute("user") != null) {
		 int reg_id = (int) session.getAttribute("reg_id");
		 session.getAttribute("userImage");
		    session.getAttribute("user");
		    System.out.println("is that id work "+reg_id);
		    Blob chg = userdao.ChangeimageRefresh(reg_id);
			   byte[] imageByte = chg.getBytes(1,(int) chg.length());
	    	   String base64Image = Base64.getEncoder().encodeToString(imageByte);
				    m.addAttribute("userImage", "data:image/jpeg;base64," + base64Image);
		    
		model.setViewName("Setting");
		}
		return model;
	}
	//map to register page
	@RequestMapping("/Register")
	public ModelAndView Register(Model m,ModelAndView model,@ModelAttribute("reg")User user)throws IOException{
		m.addAttribute("user",new User());
		model.setViewName("Register");
		return model;
	}
	
	 //Registering
	 @RequestMapping(value ="/Registerprocess" , method=RequestMethod.POST)
	 public String RegisterUser(Model m ,@RequestParam("file")MultipartFile file,@ModelAttribute("reg")User u)throws DataAccessException, IOException,NoSuchAlgorithmException {
		 m.addAttribute("reg",new User());
		 userdao.registerprocess(file, u);
		 System.out.print("inserting user work");
		 return "Registercomplete";
	 }
	 //Login
	 @RequestMapping("/Login")
		public ModelAndView Login(Model m,ModelAndView model,@ModelAttribute("Login")User user)throws IOException,NoSuchAlgorithmException{
			m.addAttribute("user",new User());
			model.setViewName("Login");
			return model;
		}
	 
	 //Loginprocesscheck
	 
	 
	 @RequestMapping(value="/validationLogin" ,method=RequestMethod.POST)
	 public ModelAndView Loginvalidation(Model m,@ModelAttribute("Login")User user,@ModelAttribute("reg")User user2,ModelAndView model,HttpSession session,HttpServletRequest req,HttpServletResponse response)throws IOException,NoSuchAlgorithmException, SQLException{
		
		 int usercheck =userdao.loginvalidation(user);
		 System.out.println(usercheck);

		 String email = req.getParameter("email");
		 String password = req.getParameter("password");
		 int emailcheck =userdao.emailcheck(email);
		 if(usercheck == 1) {
			m.addAttribute("register",new User());
			User usercheccc = userdao.getRegister(user);
			m.addAttribute("user",usercheccc);

			int regcheck = userdao.getRegister_id(user2);
			session.setAttribute("user", usercheccc);
			
			
			System.out.println("Useri_id " +regcheck);
			 Blob profileimage =userdao.profileimage(email,password);
		       if(profileimage !=null) {
		    	   byte[] imageByte = profileimage.getBytes(1,(int) profileimage.length());
		    	   String base64Image = Base64.getEncoder().encodeToString(imageByte);
					    m.addAttribute("userImage", "data:image/jpeg;base64," + base64Image);
					    m.addAttribute("reg_id",regcheck);
					    session.setAttribute("userImage", base64Image);
					    session.setAttribute("reg_id", regcheck);
						
					  model.setViewName("Setting");
		       }
		   
		    
		 }else if(emailcheck ==0) {
			 m.addAttribute("emailerror","please try email is wrong!");
			 model.setViewName("Login");
		 }else {
			 m.addAttribute("passworderror","password is wrong!");
			 model.setViewName("Login");
		 }
		 
		 return model;
		 }
	 
	 @RequestMapping("/Logout")
	 public String Logout(Model m,HttpServletRequest req) {
		  HttpSession session=req.getSession();  
	         session.invalidate();
			return "Setting"; 
	        
	 }
	 
	 @RequestMapping(value="/Changephoto",method=RequestMethod.POST)
	 public ModelAndView ChangePhoto(@RequestParam("file")MultipartFile file,Model m,ModelAndView model,@ModelAttribute("reg")User u,HttpSession session)throws IOException, NoSuchAlgorithmException, SQLException {
		System.out.println("working??" +u.getReg_id());
		System.out.println(session.getAttribute("user"));
		 userdao.Changeimage(file,u.getReg_id());
		 Blob chg = userdao.ChangeimageRefresh(u.getReg_id());
		   byte[] imageByte = chg.getBytes(1,(int) chg.length());
    	   String base64Image = Base64.getEncoder().encodeToString(imageByte);
			    m.addAttribute("userImage", "data:image/jpeg;base64," + base64Image);
			    session.setAttribute("userImage", base64Image);
		 model.setViewName("Setting");
		 return model;
	 }
	 
	 
	 
	 @RequestMapping(value ="/Blogmoment",method=RequestMethod.POST)
		public ModelAndView Setting(@ModelAttribute("Blog")Blog blog,@RequestParam("file")MultipartFile file,Model m,ModelAndView model,HttpSession session,@ModelAttribute("reg")User user2)throws IOException, SQLException, NoSuchAlgorithmException{
			
			if(session.getAttribute("user") != null) {
			 int reg_id = (int) session.getAttribute("reg_id");
			 session.getAttribute("userImage");
			    session.getAttribute("user");
			    System.out.println("is that id work "+reg_id);
			    Blob chg = userdao.ChangeimageRefresh(reg_id);
				   byte[] imageByte = chg.getBytes(1,(int) chg.length());
		    	   String base64Image = Base64.getEncoder().encodeToString(imageByte);
					    m.addAttribute("userImage", "data:image/jpeg;base64," + base64Image);
			userdao.postBlog(file, blog, reg_id);
			model.setViewName("Blog");
			}
			return model;
		}
		
	 @RequestMapping("/Blog")
		public ModelAndView Blog(Model m,ModelAndView model,HttpSession session,@ModelAttribute("reg")User user2,Blog bg)throws IOException, SQLException{
		 List<Blog> blog=userdao.profile();
				if(session.getAttribute("user") != null) {
			 int reg_id = (int) session.getAttribute("reg_id");
			 session.getAttribute("userImage");
			 session.getAttribute("user");
			    System.out.println("id should not be same "+reg_id);
			
			     
					m.addAttribute("postblog",blog);
			model.setViewName("Blog");
			}
			return model;
		}
	 
	
		@RequestMapping(value = "/getprofileimg/{blog_id}" )
		public void getMoviePhoto(Model m,HttpServletResponse response, @PathVariable("blog_id") int blog_id) throws Exception {
			response.setContentType("image/jpeg");
			System.out.println(blog_id);
			Blob ph =  userdao.getprofileimg(blog_id);

			byte[] bytes = ph.getBytes(1, (int) ph.length());

			InputStream inputStream = new ByteArrayInputStream(bytes);
			IOUtils.copy(inputStream, response.getOutputStream());
				
		}
		
		@RequestMapping(value = "/getuploadimg/{blog_id}" )
		public void getUploadimg(Model m,HttpServletResponse response, @PathVariable("blog_id") int blog_id) throws Exception {
			response.setContentType("image/jpeg");
			System.out.println(blog_id);
			Blob ph =  userdao.getBlogimg(blog_id);

			byte[] bytes = ph.getBytes(1, (int) ph.length());

			InputStream inputStream = new ByteArrayInputStream(bytes);
			IOUtils.copy(inputStream, response.getOutputStream());
				
		}
		
		
}

