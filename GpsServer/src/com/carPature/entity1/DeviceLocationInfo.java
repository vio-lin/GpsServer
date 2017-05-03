package com.carPature.entity1;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DeviceLocationInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "device_location_info", catalog = "carfencing")

public class DeviceLocationInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private byte[] gpssstat;
	private Double longtitude;
	private Double latitude;
	private Float speed;
	private String orienStat;
	private Integer mcc;
	private Integer mnc;
	private Integer lac;
	private Integer cellId;
	private Boolean acc;
	private String oploadMode;
	private Boolean realNot;
	private Timestamp date;

	// Constructors

	/** default constructor */
	public DeviceLocationInfo() {
	}

	/** full constructor */
	public DeviceLocationInfo(byte[] gpssstat, Double longtitude, Double latitude, Float speed, String orienStat,
			Integer mcc, Integer mnc, Integer lac, Integer cellId, Boolean acc, String oploadMode, Boolean realNot,
			Timestamp date) {
		this.gpssstat = gpssstat;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.speed = speed;
		this.orienStat = orienStat;
		this.mcc = mcc;
		this.mnc = mnc;
		this.lac = lac;
		this.cellId = cellId;
		this.acc = acc;
		this.oploadMode = oploadMode;
		this.realNot = realNot;
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

	@Column(name = "GPSSSTAT")

	public byte[] getGpssstat() {
		return this.gpssstat;
	}

	public void setGpssstat(byte[] gpssstat) {
		this.gpssstat = gpssstat;
	}

	@Column(name = "LONGTITUDE", precision = 22, scale = 0)

	public Double getLongtitude() {
		return this.longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

	@Column(name = "LATITUDE", precision = 22, scale = 0)

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "SPEED", precision = 12, scale = 0)

	public Float getSpeed() {
		return this.speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	@Column(name = "ORIEN_STAT", length = 4)

	public String getOrienStat() {
		return this.orienStat;
	}

	public void setOrienStat(String orienStat) {
		this.orienStat = orienStat;
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

	@Column(name = "LAC")

	public Integer getLac() {
		return this.lac;
	}

	public void setLac(Integer lac) {
		this.lac = lac;
	}

	@Column(name = "CELL_ID")

	public Integer getCellId() {
		return this.cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	@Column(name = "ACC")

	public Boolean getAcc() {
		return this.acc;
	}

	public void setAcc(Boolean acc) {
		this.acc = acc;
	}

	@Column(name = "OPLOAD_MODE", length = 4)

	public String getOploadMode() {
		return this.oploadMode;
	}

	public void setOploadMode(String oploadMode) {
		this.oploadMode = oploadMode;
	}

	@Column(name = "REAL_NOT")

	public Boolean getRealNot() {
		return this.realNot;
	}

	public void setRealNot(Boolean realNot) {
		this.realNot = realNot;
	}

	@Column(name = "DATE", length = 19)

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}