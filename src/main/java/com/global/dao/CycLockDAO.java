package com.global.dao;

import java.util.List;

import com.global.model.CycLock;


public interface CycLockDAO {
	void save(CycLock cyc);
	
	List<CycLock> findAll();
	CycLock findLastet();
	
	void delete(long id);	
}
