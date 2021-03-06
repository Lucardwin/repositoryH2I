package it.h2i.idservice.accountablemodel.model;
// Generated 4-apr-2018 17.24.09 by Hibernate Tools 3.2.2.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Function generated by hbm2java
 */
@Entity
@Table(name="function"
    ,catalog="organization"
)
public class Function  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 6195011577375009053L;
	private Integer idfunction;
     private Transaction transaction;
     private String name;
     private Set<Role> roles = new HashSet<Role>(0);
     private Set<Typology> typologies = new HashSet<Typology>(0);

    public Function() {
    }

	
    public Function(String name) {
        this.name = name;
    }
    public Function(Transaction transaction, String name, Set<Role> roles, Set<Typology> typologies) {
       this.transaction = transaction;
       this.name = name;
       this.roles = roles;
       this.typologies = typologies;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="idfunction", unique=true, nullable=false)
    public Integer getIdfunction() {
        return this.idfunction;
    }
    
    public void setIdfunction(Integer idfunction) {
        this.idfunction = idfunction;
    }
    @OneToOne(mappedBy = "function", cascade = CascadeType.ALL)
    public Transaction getTransaction() {
        return this.transaction;
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    @Column(name="name", nullable=false, length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="function")
    public Set<Role> getRoles() {
        return this.roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="function")
    public Set<Typology> getTypologies() {
        return this.typologies;
    }
    
    public void setTypologies(Set<Typology> typologies) {
        this.typologies = typologies;
    }




}


