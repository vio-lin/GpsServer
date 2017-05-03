package com.carPature.entity1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DeviceStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "device_status", catalog = "carfencing")

public class DeviceStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String imei;
	private Float battery;
	private Integer gsm;
	private Boolean oilelectirc;
	private Boolean gpsstate;
	private Boolean charging;
	private Boolean acc;
	private Boolean guard;
	private Boolean language;

	// Constructors

	/** default constructor */
	public DeviceStatus() {
	}

	/** full constructor */
	public DeviceStatus(String imei, Float battery, Integer gsm, Boolean oilelectirc, Boolean gpsstate,
			Boolean charging, Boolean acc, Boolean guard, Boolean language) {
		this.imei = imei;
		this.battery = battery;
		this.gsm = gsm;
		this.oilelectirc = oilelectirc;
		this.gpsstate = gpsstate;
		this.charging = charging;
		this.acc = acc;
		this.guard = guard;
		this.language = language;
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

	@Column(name = "IMEI", length = 15)

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "BATTERY", precision = 12, scale = 0)

	public Float getBattery() {
		return this.battery;
	}

	public void setBattery(Float battery) {
		this.battery = battery;
	}

	@Column(name = "GSM")

	public Integer getGsm() {
		return this.gsm;
	}

	public void setGsm(Integer gsm) {
		this.gsm = gsm;
	}

	@Column(name = "OILELECTIRC")

	public Boolean getOilelectirc() {
		return this.oilelectirc;
	}

	public void setOilelectirc(Boolean oilelectirc) {
		this.oilelectirc = oilelectirc;
	}

	@Column(name = "GPSSTATE")

	public Boolean getGpsstate() {
		return this.gpsstate;
	}

	public void setGpsstate(Boolean gpsstate) {
		this.gpsstate = gpsstate;
	}

	@Column(name = "Charging")

	public Boolean getCharging() {
		return this.charging;
	}

	public void setCharging(Boolean charging) {
		this.charging = charging;
	}

	@Column(name = "ACC")

	public Boolean getAcc() {
		return this.acc;
	}

	public void setAcc(Boolean acc) {
		this.acc = acc;
	}

	@Column(name = "GUARD")

	public Boolean getGuard() {
		return this.guard;
	}

	public void setGuard(Boolean guard) {
		this.guard = guard;
	}

	@Column(name = "LANGUAGE")

	public Boolean getLanguage() {
		return this.language;
	}

	public void setLanguage(Boolean language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "DeviceStatus [id=" + id + ", imei=" + imei + ", battery=" + battery + ", gsm=" + gsm + ", oilelectirc="
				+ oilelectirc + ", gpsstate=" + gpsstate + ", charging=" + charging + ", acc=" + acc + ", guard="
				+ guard + ", language=" + language + "]";
	}
	
}