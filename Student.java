package sqlie.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sun.faces.context.SessionMap;

@ManagedBean(name = "student", eager = true)
@RequestScoped
public class Student {
	
	
		int id;
		 String name;
	    int averScore;
	   
	    public Student() {}

	     public Student(String name, int averScore) {
	            
		        this.averScore = averScore;
		        this.name = name;
		       
		    }

	    public Student(int id,String name, int averScore) {
            this.id = id;
	        this.averScore = averScore;
	        this.name = name;
	       
	    }
	    
	   

	    public int getId(){
	        return id;
	       }
	    public void setId(int id){
	    	 this.id = id;
	       }

	    public String getName(){
	        return name;
	    }
	    
	    public void setName(String name){
	    	this.name = name;
	       }
	    public int getaverScore(){
	        return averScore;
	    }
	    public void setaverScore(int averScore){
	    	this.averScore = averScore;
	       }
	    
	    String url = "jdbc:sqlite:C:/Users/anast/Documents/NetBeansProjects/JavaApplication1/StudentAc.db";
public  ArrayList<Student> getSelect() {
	    	
	    	
	        ArrayList<Student> students = new ArrayList<Student>();
	        try{
		           
	            Class.forName("org.sqlite.JDBC");
	            Connection conn = DriverManager.getConnection(url);
	            
	        
	                Statement statement = conn.createStatement();
	                ResultSet resultSet = statement.executeQuery("select*from students");
	                while(resultSet.next()){
	                	Student student = new Student();
	                      student.setId(resultSet.getInt("id"));
	                      student.setName(resultSet.getString("name"));
	                      student.setaverScore(resultSet.getInt("averScore"));
	                   
	                    students.add(student);
	                }
	            }
	        
	        catch(Exception ex){
	        	System.out.println("Connection failed...");
	            System.out.println(ex);
	        }
	        return students;
	    }

private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	    public String edit_One() {
	         FacesContext fc = FacesContext.getCurrentInstance();
	         Map<String, String>params= fc.getExternalContext().getRequestParameterMap();
	        String field_id = params.get("act");
	        
	        try{
	        	Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
	        	  try (Connection conn = DriverManager.getConnection(url)){
	        		  Statement statement = conn.createStatement();
	        		  ResultSet resultSet =  statement.executeQuery("SELECT * FROM students WHERE id="+field_id);
	        		  Student s = new Student();
	        		  resultSet.next();
	        		  s.setId(resultSet.getInt("id"));
	        		  s.setName(resultSet.getString("name"));
	        		  s.setaverScore(resultSet.getInt("averscore"));
	        		  sessionMap.put("edit_One",s);
	                
	            }
	        }
	        catch(Exception ex){
	            System.out.println(ex);
	        }
	        return "edit.xhtml?faces-redirect=true";
	    }
	    public void insert() {
	        
	        try{
	        	Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
	        	  try (Connection conn = DriverManager.getConnection(url)){
	        		  Student student = new Student(name, averScore);
	                String sql = "insert into students (name, averScore) Values (?, ?)";
	                PreparedStatement preparedStatement = conn.prepareStatement(sql);
	                	
	                    preparedStatement.setString(1, student.getName());
	                    preparedStatement.setInt(2, student.getaverScore());
	                      
	                     preparedStatement.executeUpdate();
	                }
	            
	        }
	        catch(Exception ex){
	            System.out.println(ex);
	        }
	        
	    }
	     
	    public String update() {
	    	FacesContext fc = FacesContext.getCurrentInstance();
	         Map<String, String>params= fc.getExternalContext().getRequestParameterMap();
	        String update_id = params.get("update_id");
	        try{
	        	Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
	        	  try (Connection conn = DriverManager.getConnection(url)){
	                  
	                String sql = "UPDATE students SET name = ?, averScore = ? WHERE id = ?";
	                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
	                	 preparedStatement.setString(3, update_id);
	                	 
	                    preparedStatement.setString(1, name);
	                    preparedStatement.setInt(2, averScore);
	                   System.out.println(preparedStatement);
	                   
	                      
	                     preparedStatement.executeUpdate();
	                }
	            }
	        }
	        catch(Exception ex){
	            System.out.println(ex);
	        }
	        return "ind.xhtml?faces-redirect=true";
	    }
	    public String delete() {
	    	FacesContext fc = FacesContext.getCurrentInstance();
	         Map<String, String>params= fc.getExternalContext().getRequestParameterMap();
	        String delete_id = params.get("delete_id");
	        try{
	        	Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
	        	  try (Connection conn = DriverManager.getConnection(url)){
	                  
	                String sql = "DELETE FROM students WHERE id = ?";
	                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
	                    preparedStatement.setString(1, delete_id);
	                      
	                     preparedStatement.executeUpdate();
	                }
	            }
	        }
	        catch(Exception ex){
	            System.out.println(ex);
	        }
	        return "ind.xhtml?faces-redirect=true";
	    }
	    
	    public void search_Key() {    
	    	  
	    	 FacesContext fc = FacesContext.getCurrentInstance();
	         Map<String, String>params= fc.getExternalContext().getRequestParameterMap();
	        String search = params.get("search_name");
	        
	    	try{
		        	Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
		        	 Connection conn = DriverManager.getConnection(url);
	       
	 
	        Statement st = conn.createStatement();
	       ResultSet res = st.executeQuery("select*from students where id="+search);
	        Student s = new Student();
	        res.next();
  		  s.setId(res.getInt("id"));
  		  s.setName(res.getString("name"));
  		  s.setaverScore(res.getInt("averscore"));
  		 sessionMap.put("search_Key",s);
  		 
	           /* if(res.next()){
	            String setname= res.getString("name");
	            jTextField1.setText(setname);
	            
	             String setpoint= res.getString("point");
	            jTextField2.setText(setpoint);
	            
	            String osn = res.getString("osnNav");
	            switch(osn){
	               case "Бюджетна":
	                 jComboBox2.setSelectedIndex(0);
	                 break;
	             case "Контрактна":
	             jComboBox2.setSelectedIndex(1);
	                 break;
	            }
	            }*/
	        }
	        
	        catch(Exception ex){}
	    	                
	    	  }
	    
	    }
	

