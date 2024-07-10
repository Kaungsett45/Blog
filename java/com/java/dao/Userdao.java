package com.java.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.java.bean.Blog;
import com.java.bean.User;

public class Userdao {
	
JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
	this.template = template;
	}

	
			public void registerprocess(MultipartFile image,User u)throws DataAccessException,IOException{
				
				byte[] photoBytes = image.getBytes();
				String sql="insert into register(username,image,email,password,gender,passport_id) values('"+u.getUsername()+"',?,'"+u.getEmail()+"','"+u.getPassword()+"','"+u.getGender()+"','"+u.getPassport_id()+"')";
				template.update(sql,new Object[] {photoBytes});
			}
			
			
			public int loginvalidation(User p) {
				String sql="select count(*) from register where email=? and password=?";
				System.out.println(sql);
				return template.queryForObject(sql,Integer.class,p.getEmail(),p.getPassword());
			}
			
			
			public int emailcheck(String email) {
				String sql="select count(email) from register  where email= ?";
		
				return template.queryForObject(sql,Integer.class,email);
			}
			public int passwordcheck(String  password) {
				String sql="select count(password) from register    where password= ?";
		
				return template.queryForObject(sql,Integer.class,password);
			}
			
			public User getRegister(User register) throws NoSuchAlgorithmException, IOException {
				
				String sql = "select * from register where email='"+register.getEmail()+"' and password='"+register.getPassword()+"' ";
				return template.queryForObject(sql, new RowMapper<User>() {
					public User mapRow(ResultSet rs, int row) throws SQLException {
						User m= new User();
						m.setReg_id(rs.getInt(1));
				        m.setUsername(rs.getString(2));
				        m.setImage(rs.getBlob(3));
				        m.setEmail(rs.getString(4));
				        m.setPassword(rs.getString(5));
				        m.setChgpassword(rs.getString(6));
				        m.setGender(rs.getString(7));
				        m.setPassport_id(rs.getString(8));
				       
				        
				  		return m;
					}
				});
			}
			
	
			
		public int getRegister_id(User register) throws NoSuchAlgorithmException, IOException {
				
				String sql = "select reg_id from register where email='"+register.getEmail()+"' and password='"+register.getPassword()+"' ";
				return template.queryForObject(sql,Integer.class);
			}
			
			public Blob profileimage(String email,String  password) {
				String sql="select image from register    where email=? and password= ?";
		
				return template.queryForObject(sql,Blob.class,email,password);
			}
			
			public void Changeimage(MultipartFile image,int reg_id)throws DataAccessException,IOException{
				
				byte[] photoBytes = image.getBytes();
				String sql="update register set image = ? where reg_id=?";
				template.update(sql,new Object[] {photoBytes,reg_id});
			}
			public Blob ChangeimageRefresh(int reg_id) {
				String sql="select image from register    where reg_id= ?";
		
				return template.queryForObject(sql,Blob.class,reg_id);
			}
			
		
			

			public  void postBlog(MultipartFile image,Blog b,int reg_id)throws DataAccessException,IOException{
				
				 
				
				byte[] photoBytes = image.getBytes();
				String sql="insert into Blog(Blog_image,description,reg_id) values (?,?,? )";
				template.update(sql,new Object[] {photoBytes,b.getDescription(),reg_id});
			}
	
	
	public List<Blog> Blog(){
		String sql="select Blog_image,description,time,register.username,register.image,Blog.reg_id from Blog,register where register.reg_id = Blog.reg_id";
		//String sql="select register.username,user_review,comment_review,user_time,user_day from review,movie,register where register.user_id=review.user_id and movie.movei_id=review.movei_id and movie.movei_id='"+id+"' order by user_review desc";
		List<Blog> list =template.query(sql ,new RowMapper<Blog>() {
			

			@Override
		public Blog mapRow(ResultSet rs, int arg1) throws SQLException {
			
			Blog reg = new Blog();
			reg.setBlog_image(rs.getBlob("Blog_image"));
			reg.setDescription(rs.getString("description"));
			reg.setTime(rs.getString("time"));
			reg.setUsername(rs.getString("username"));
			reg.setImage(rs.getBlob("image"));
			reg.setReg_id(rs.getInt("reg_id"));
			
			return reg;
		}
		});
		return list;
	}
	
	public List<Blog> profile(){
		String sql="select * from Blog,register where register.reg_id = Blog.reg_id";
		//String sql="select register.username,user_review,comment_review,user_time,user_day from review,movie,register where register.user_id=review.user_id and movie.movei_id=review.movei_id and movie.movei_id='"+id+"' order by user_review desc";
		List<Blog> list =template.query(sql ,new RowMapper<Blog>() {
			

			@Override
			public Blog mapRow(ResultSet rs, int arg1) throws SQLException {
			    Blog reg = new Blog();

				   reg.setBlog_id(rs.getInt("Blog_id"));
			    reg.setBlog_image(rs.getBlob("Blog_image"));
			    reg.setDescription(rs.getString("description"));
			    reg.setTime(rs.getString("time"));
				   reg.setReg_id(rs.getInt("reg_id"));
			    reg.setUsername(rs.getString("username"));
			   reg.setImage(rs.getBlob("image"));
			    // Retrieve the Blob image
			
			    return reg;
			}

		
		});
		return list;
	}
	public int callid(Blog b) throws NoSuchAlgorithmException, IOException {
		
		String sql = "select Blog_id from Blog";
		return template.queryForObject(sql,Integer.class);
	}
	
	
				
	public Blob getprofileimg(int id) {
		// TODO Auto
		String query="select image from register,Blog where  register.reg_id=Blog.reg_id and Blog.Blog_id=?";

		Blob photo= template.queryForObject(query, new Object[]{id}, Blob.class);

		return photo;
		}	
	
	public Blob getBlogimg(int id) {
		// TODO Auto
		String query="select Blog_image from register,Blog where  register.reg_id=Blog.reg_id and Blog.Blog_id=?";

		Blob photo= template.queryForObject(query, new Object[]{id}, Blob.class);

		return photo;
		}	

	public List<Blog> imageprfretrieve(int Blog_id){
		String sql="select  * from Blog";
		//String sql="select register.username,user_review,comment_review,user_time,user_day from review,movie,register where register.user_id=review.user_id and movie.movei_id=review.movei_id and movie.movei_id='"+id+"' order by user_review desc";
		List<Blog> list =template.query(sql ,new RowMapper<Blog>() {
			

			@Override
		public Blog mapRow(ResultSet rs, int arg1) throws SQLException {
			
			Blog reg = new Blog();
			reg.setBlog_id(rs.getInt("Blog_id"));
			reg.setBlog_image(rs.getBlob("Blog_image"));
			reg.setDescription(rs.getString("description"));
			reg.setTime(rs.getString("time"));
			reg.setUsername(rs.getString("username"));
			reg.setImage(rs.getBlob("image"));
			reg.setReg_id(rs.getInt("reg_id"));
			
			return reg;
		}
		});
		return list;
	}	
}
