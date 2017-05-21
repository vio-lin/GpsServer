package com.carPature.entity1;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "users", catalog = "carfencing")
public class Users implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String platenumber;
	private String imei;
	private Timestamp startdate;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** full constructor */
	public Users(String name, String platenumber, String imei,
			Timestamp startdate) {
		this.name = name;
		this.platenumber = platenumber;
		this.imei = imei;
		this.startdate = startdate;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PLATENUMBER", length = 10)
	public String getPlatenumber() {
		return this.platenumber;
	}

	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}

	@Column(name = "IMEI", length = 15)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "STARTDATE", length = 0)
	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

}