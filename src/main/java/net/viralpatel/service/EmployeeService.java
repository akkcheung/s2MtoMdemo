package net.viralpatel.service;
 
import net.viralpatel.model.*;
import net.viralpatel.util.*;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import java.util.*;
 
import org.apache.log4j.Logger; 

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Hibernate;

import com.google.gson.Gson; 
 
 
public class EmployeeService {
 
 
    Session session;   
    
    //get log4j
		private static final Logger logger = Logger.getLogger(EmployeeService.class);    
    
    
    public EmployeeService() {
        // this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }    
    
    public Employez find(Long id) {

			 Session session = HibernateUtil.getSessionFactory().getCurrentSession();    	    	     	 
    	 session.beginTransaction();
    	 Employez emp = (Employez) session.get(Employez.class, id);
    	 
    	 
    	 /*
    	 Set <Meeting> setM = null;
    	 if (emp.getMeetings().size() > 0)
    	 		setM =  emp.getMeetings();
    	 */
    	 int s = emp.getMeetings().size(); // prevent lazy intialization error
    	 
    	 session.getTransaction().commit();    	 
       
       return  emp;
    }
    
    public void update(Employez emp) {    		
    		
    		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    		session.beginTransaction();    		
        session.saveOrUpdate(emp);         
  			session.getTransaction().commit();
  
  	}
  	
  	public List<Employez> listAll() {
        
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
                   
        Query query = session.createQuery(
        
        /*
        "select distinct e from Employez e " +        
				"inner join e.meetings m " 				
				*/
				
				"select e from Employez e inner join fetch e.meetings m"   //non-lazy collection	
				// + " inner join m.employees"
				+ " order by e.firstname , m.subject"							
								
				);

				// session.createFilter(m.getEmployees(), "").list();

    		List result = null;    		
    		Set <Meeting> setM = null;        
    
    		try {
           result = query.list();
					
					/*
					 for (int i = 0; i < result.size(); i++) {
					 		Hibernate.initialize(((Employez)result.get(i)).getMeetings());
					 }
					 */
					
    		} catch (HibernateException e) {           
            System.out.println("from Employez - No Result");
            session.getTransaction().rollback();            
           
			  } finally {      
            // JpaUtil.close(entityManager) ;  // simulate lazy initialization exception
            
    		}
                
        
        
        // Employez e = null;
        List <Employez> listE = null;  
        
     		for (int i = 0; i < result.size(); i++) {
     				logger.info("row :" + i);
     			     				
						// e = (Employez) result.get(i);
						setM = ((Employez) result.get(i)).getMeetings();						
						// s0 = e.getMeetings().size();
						
						// logger.info(e.getFirstname());
		
						// int  s1 ;
						// for (Meeting m : setM) {
						for (Meeting m : setM) {
 						   logger.info(m.getSubject());
 						   // s1 = m.getEmployees().size();
 						   Hibernate.initialize(m.getEmployees()); 						  
 						   
 						   // listE = session.createFilter(m.getEmployees(), "").list();
 						}
				}
        
               
        
        session.getTransaction().commit();
       
          
	    	return result;
    }
    
   	public List<Employez> listAll2() throws Exception {
   		
   			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
                   
        Query query = session.createQuery(
        "select distinct e from Employez e inner join fetch e.meetings m"   //non-lazy collection					
				+ " order by e.firstname , m.subject"															
				);
								
        List result = query.list();
        
        session.getTransaction().commit();
          
	    	return result;
   		
  	}
 
}