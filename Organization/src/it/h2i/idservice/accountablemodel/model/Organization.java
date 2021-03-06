package it.h2i.idservice.accountablemodel.model;
// Generated 4-apr-2018 17.24.09 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Organization generated by hbm2java
 */
@Entity
@Table(name="organization"
,catalog="organization"
		)
public class Organization  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idorganization;
	private String piva;
	private String name;
	private Set<Typology> typologies = new HashSet<Typology>(0);
	private List<Appertain> appertains = new LinkedList();

	public Organization() {
	}


	public Organization(String piva, String name) {
		this.piva = piva;
		this.name = name;
	}
	public Organization(String piva, String name, Set<Typology> typologies, List<Appertain> appertains) {
		this.piva = piva;
		this.name = name;
		this.typologies = typologies;
		this.appertains = appertains;
	
	}


	@Id @GeneratedValue(strategy=IDENTITY)

	@Column(name="idorganization", unique=true, nullable=false)
	public Integer getIdorganization() {
		return this.idorganization;
	}

	public void setIdorganization(Integer idorganization) {
		this.idorganization = idorganization;
	}

	@Column(name="piva", nullable=false, unique=true,length=45)
	public String getPiva() {
		return this.piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	@Column(name="name", nullable=false, length=45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="organization")
	public Set<Typology> getTypologies() {
		return this.typologies;
	}

	public void setTypologies(Set<Typology> typologies) {
		this.typologies = typologies;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="organization")
	public List<Appertain> getAppertains() {
		return this.appertains;
	}

	public void setAppertains(List<Appertain> appertains) {
		this.appertains = appertains;
	}
	
	





}


