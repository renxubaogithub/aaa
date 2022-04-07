package com.service;

import java.util.List;

import com.po.Emp;
import com.po.PageBean;

public interface IEmpService {
	 public int save(Emp emp);
	  public int update(Emp emp);
	  public int delById(Integer eid);
	  public Emp findById(Integer eid);
	  public List<Emp> findPageAll(PageBean pb);
	  public int findMaxRow();
}
