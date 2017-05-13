package com.global.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cycLock")
public class CycLock {

	
	public CycLock() {
		// TODO Auto-generated constructor stub
	}
	
	//主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//纬度
	@Column(name = "lat")
	private double lat;
	
	//经度
	@Column(name = "lon")
	private double lon;
	
	//海拔
	@Column(name = "asl")
	private double asl;
	
	//时间
	@Column(name = "time")
	private Date time;
	
	//状态
	@Column(name = "state")
	private boolean state;

	public String getState(){
		if(state){
			return "已上锁";
		}else{
			return "未上锁";
			
		}		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public double getAsl() {
		return asl;
	}
	public void setAsl(double asl) {
		this.asl = asl;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	
}
