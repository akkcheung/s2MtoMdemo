package net.viralpatel.action ;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Action;

import lombok.*;

import org.apache.log4j.Logger;

import net.viralpatel.service.*;
import net.viralpatel.model.*;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger; 

import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletResponse;  

import java.io.PrintWriter;  

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder; 
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class MtoMAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    
    @Getter @Setter
    private long inputA;
    
    @Getter @Setter
    private long inputB;
        
    // @Getter @Setter
    private Employez employee;
    
    // @Getter @Setter
    private Meeting meeting;
        
   	private List <Employez> employezList = new ArrayList <Employez>();
    
    private List resultList = new ArrayList();
           
    private EmployeeService employeeService = new EmployeeService();    
    private MeetingService meetingService = new MeetingService();
    
    //get log4j
		private static final Logger logger = Logger.getLogger(MtoMAction.class); 
    
   	
   	 
    // @Override
    public String execute() {    	   
    			
    		this.employezList = employeeService.listAll() ;    	                   		 	    		    		
    		return Action.SUCCESS;
    }
    
    public String add() throws Exception {
         
        //call Service class to store personBean's state in database
        logger.info("call add()");
                
	     	employee = employeeService.find(inputA);
	     	meeting = meetingService.find(inputB);
        
        employee.getMeetings().add(meeting);        
                
        employeeService.update(employee);
                
        logger.info(employee.getFirstname() + " - meeting(s) : " + employee.getMeetings().size());

        return Action.SUCCESS;
         
    }
    
    public List <Employez> getEmployezList() throws Exception {   	
  	  	// this.employezList = employeeService.listAll() ;    	               
        return employezList;
  	}
  	  	
  	
    public void validate(){
    
    		/*
        if ( firstName.length() == 0 ){  
            addFieldError( "firstName", "First name is required." );         
        }
        if ( lastName.length() == 0 ){  
            addFieldError( "lastName", "Last name is required." );         
        }
        */
    }
  	
  	public void gsonOut() throws Exception {
  		
  			List employezList = employeeService.listAll2() ;
  			
  			Gson gson = new GsonBuilder()
  				.setPrettyPrinting()
  				.setExclusionStrategies(new EmpExclStrat())
  				.create();
  			
  			String result = gson.toJson(employezList);  
  			  			  			
  			HttpServletResponse response = ServletActionContext.getResponse();    			 
  			response.setContentType("application/json;charset=utf-8");            
        response.setHeader("caChe-Control", "no-cache"); 
        
        System.out.println("");          
         
        PrintWriter out =response.getWriter();            
        out.print(result);
        out.flush(); 
        out.close();         
        	        	
  	}
		
		private class EmpExclStrat implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {
            return (            	
            	f.getDeclaringClass() == Meeting.class && f.getName().equals("employees")
            );
        }

    }
}    
