package it.h2i.idservice.accountablemodel.model;
// Generated 4-apr-2018 17.24.09 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AppertainId generated by hbm2java
 */
@Embeddable
public class AppertainId  implements java.io.Serializable {


	private static final long serialVersionUID = -7326946678596127343L;
	private int appertainIduser;
     private int appertainIdorganization;

    public AppertainId() {
    }

    public AppertainId(int appertainIduser, int appertainIdorganization) {
       this.appertainIduser = appertainIduser;
       this.appertainIdorganization = appertainIdorganization;
    }
   

    @Column(name="appertain_iduser", nullable=false)
    public int getAppertainIduser() {
        return this.appertainIduser;
    }
    
    public void setAppertainIduser(int appertainIduser) {
        this.appertainIduser = appertainIduser;
    }

    @Column(name="appertain_idorganization", nullable=false)
    public int getAppertainIdorganization() {
        return this.appertainIdorganization;
    }
    
    public void setAppertainIdorganization(int appertainIdorganization) {
        this.appertainIdorganization = appertainIdorganization;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AppertainId) ) return false;
		 AppertainId castOther = ( AppertainId ) other; 
         
		 return (this.getAppertainIduser()==castOther.getAppertainIduser())
 && (this.getAppertainIdorganization()==castOther.getAppertainIdorganization());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getAppertainIduser();
         result = 37 * result + this.getAppertainIdorganization();
         return result;
   }   


}


