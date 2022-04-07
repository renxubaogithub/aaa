package com.mapper;

import org.springframework.stereotype.Service;

import com.po.Salary;

@Service("SalaryDAO")
public interface ISalaryMapper {
   //保存薪资
	public int save(Salary sa);
   //通过员工Id，修改薪资
	public int updateByEid(Salary sa);
   //通过员工Id，删除薪资
	public int delByEid(Integer eid);
   //通过员工Id，查询薪资
	public Salary findByEid(Integer eid);
}
