package net.viralpatel.model;
 
import java.util.HashSet;
import java.util.Set;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OrderBy;

import lombok.*;

@Entity
// @Data
@Table(name="TB_EMPLOYEE")
public class Employez {
     
    @Id
    @Column(name="EMPLOYEE_ID")
    @GeneratedValue
    @Getter @Setter
    private Long employeeId;
     
    @Column(name="FIRSTNAME")
    @Getter @Setter
    private String firstname;
     
    @Column(name="LASTNAME")
    @Getter @Setter
    private String lastname;
     
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="EMPLOYEE_MEETING", 
                joinColumns={@JoinColumn(name="EMPLOYEE_ID")}, 
                inverseJoinColumns={@JoinColumn(name="MEETING_ID")})
    // @OrderBy(value="subject asc")
    private Set<Meeting> meetings = new HashSet<Meeting>();
     
    public Employez() {
    }
 
    public Employez(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
         
    // Getter and Setter methods
    
    @Override
    public boolean equals(Object obj) {
      if (obj == null) return false;
      if (!this.getClass().equals(obj.getClass())) return false;

      Employez obj2 = (Employez)obj;
      if((this.employeeId == obj2.getEmployeeId()) && (this.firstname.equals(obj2.getFirstname())))
      {
         return true;
      }
      return false;
	  }
	   
	  @Override
	  public int hashCode() {
	      int tmp = 0;
	      tmp = ( employeeId + firstname ).hashCode();
	      return tmp;
	  }
	  
	  
	  public Set<Meeting> getMeetings() {
			return this.meetings;
		}

		public void setMeetings(Set<Meeting> meetings) {
			this.meetings = meetings;
		}
		
		
}
