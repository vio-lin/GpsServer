package com.carPature.entity1;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DeviceLocationLbsInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "device_location_lbs_info", catalog = "carfencing")

public class DeviceLocationLbsInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mcc;
	private Integer mnc;
	private Integer lac1;
	private Integer ci1;
	private Integer rssi;
	private Integer lac2;
	private Integer ci2;
	private Integer rssi2;
	private Integer lac3;
	private Integer ci3;
	private Integer rssi3;
	private Integer lac4;
	private Integer ci4;
	private Integer rssi4;
	private Integer lac5;
	private Integer ci5;
	private Integer rssi5;
	private Integer lac6;
	private Integer ci6;
	private Integer rssi6;
	private Integer lac7;
	private Integer ci7;
	private Integer rssi7;
	private Integer timebefore;
	private Short language;
	private Timestamp date;

	// Constructors

	/** default constructor */
	public DeviceLocationLbsInfo() {
	}

	/** full constructor */
	public DeviceLocationLbsInfo(Integer mcc, Integer mnc, Integer lac1, Integer ci1, Integer rssi, Integer lac2,
			Integer ci2, Integer rssi2, Integer lac3, Integer ci3, Integer rssi3, Integer lac4, Integer ci4,
			Integer rssi4, Integer lac5, Integer ci5, Integer rssi5, Integer lac6, Integer ci6, Integer rssi6,
			Integer lac7, Integer ci7, Integer rssi7, Integer timebefore, Short language, Timestamp date) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.lac1 = lac1;
		this.ci1 = ci1;
		this.rssi = rssi;
		this.lac2 = lac2;
		this.ci2 = ci2;
		this.rssi2 = rssi2;
		this.lac3 = lac3;
		this.ci3 = ci3;
		this.rssi3 = rssi3;
		this.lac4 = lac4;
		this.ci4 = ci4;
		this.rssi4 = rssi4;
		this.lac5 = lac5;
		this.ci5 = ci5;
		this.rssi5 = rssi5;
		this.lac6 = lac6;
		this.ci6 = ci6;
		this.rssi6 = rssi6;
		this.lac7 = lac7;
		this.ci7 = ci7;
		this.rssi7 = rssi7;
		this.timebefore = timebefore;
		this.language = language;
		this.date = date;
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

	@Column(name = "MCC")

	public Integer getMcc() {
		return this.mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	@Column(name = "MNC")

	public Integer getMnc() {
		return this.mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	@Column(name = "LAC1")

	public Integer getLac1() {
		return this.lac1;
	}

	public void setLac1(Integer lac1) {
		this.lac1 = lac1;
	}

	@Column(name = "CI1")

	public Integer getCi1() {
		return this.ci1;
	}

	public void setCi1(Integer ci1) {
		this.ci1 = ci1;
	}

	@Column(name = "RSSI")

	public Integer getRssi() {
		return this.rssi;
	}

	public void setRssi(Integer rssi) {
		this.rssi = rssi;
	}

	@Column(name = "LAC2")

	public Integer getLac2() {
		return this.lac2;
	}

	public void setLac2(Integer lac2) {
		this.lac2 = lac2;
	}

	@Column(name = "CI2")

	public Integer getCi2() {
		return this.ci2;
	}

	public void setCi2(Integer ci2) {
		this.ci2 = ci2;
	}

	@Column(name = "RSSI2")

	public Integer getRssi2() {
		return this.rssi2;
	}

	public void setRssi2(Integer rssi2) {
		this.rssi2 = rssi2;
	}

	@Column(name = "LAC3")

	public Integer getLac3() {
		return this.lac3;
	}

	public void setLac3(Integer lac3) {
		this.lac3 = lac3;
	}

	@Column(name = "CI3")

	public Integer getCi3() {
		return this.ci3;
	}

	public void setCi3(Integer ci3) {
		this.ci3 = ci3;
	}

	@Column(name = "RSSI3")

	public Integer getRssi3() {
		return this.rssi3;
	}

	public void setRssi3(Integer rssi3) {
		this.rssi3 = rssi3;
	}

	@Column(name = "LAC4")

	public Integer getLac4() {
		return this.lac4;
	}

	public void setLac4(Integer lac4) {
		this.lac4 = lac4;
	}

	@Column(name = "CI4")

	public Integer getCi4() {
		return this.ci4;
	}

	public void setCi4(Integer ci4) {
		this.ci4 = ci4;
	}

	@Column(name = "RSSI4")

	public Integer getRssi4() {
		return this.rssi4;
	}

	public void setRssi4(Integer rssi4) {
		this.rssi4 = rssi4;
	}

	@Column(name = "LAC5")

	public Integer getLac5() {
		return this.lac5;
	}

	public void setLac5(Integer lac5) {
		this.lac5 = lac5;
	}

	@Column(name = "CI5")

	public Integer getCi5() {
		return this.ci5;
	}

	public void setCi5(Integer ci5) {
		this.ci5 = ci5;
	}

	@Column(name = "RSSI5")

	public Integer getRssi5() {
		return this.rssi5;
	}

	public void setRssi5(Integer rssi5) {
		this.rssi5 = rssi5;
	}

	@Column(name = "LAC6")

	public Integer getLac6() {
		return this.lac6;
	}

	public void setLac6(Integer lac6) {
		this.lac6 = lac6;
	}

	@Column(name = "CI6")

	public Integer getCi6() {
		return this.ci6;
	}

	public void setCi6(Integer ci6) {
		this.ci6 = ci6;
	}

	@Column(name = "RSSI6")

	public Integer getRssi6() {
		return this.rssi6;
	}

	public void setRssi6(Integer rssi6) {
		this.rssi6 = rssi6;
	}

	@Column(name = "LAC7")

	public Integer getLac7() {
		return this.lac7;
	}

	public void setLac7(Integer lac7) {
		this.lac7 = lac7;
	}

	@Column(name = "CI7")

	public Integer getCi7() {
		return this.ci7;
	}

	public void setCi7(Integer ci7) {
		this.ci7 = ci7;
	}

	@Column(name = "RSSI7")

	public Integer getRssi7() {
		return this.rssi7;
	}

	public void setRssi7(Integer rssi7) {
		this.rssi7 = rssi7;
	}

	@Column(name = "TIMEBEFORE")

	public Integer getTimebefore() {
		return this.timebefore;
	}

	public void setTimebefore(Integer timebefore) {
		this.timebefore = timebefore;
	}

	@Column(name = "LANGUAGE")

	public Short getLanguage() {
		return this.language;
	}

	public void setLanguage(Short language) {
		this.language = language;
	}

	@Column(name = "DATE", length = 19)

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}