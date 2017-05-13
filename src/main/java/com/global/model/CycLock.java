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
	
	//����
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//γ��
	@Column(name = "lat")
	private double lat;
	
	//����
	@Column(name = "lon")
	private double lon;
	
	//����
	@Column(name = "asl")
	private double asl;
	
	//ʱ��
	@Column(name = "time")
	private Date time;
	
	//״̬
	@Column(name = "state")
	private boolean state;

	public String getState(){
		if(state){
			return "������";
		}else{
			return "δ����";
			
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
