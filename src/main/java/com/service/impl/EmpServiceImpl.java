package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Dep;
import com.po.Emp;
import com.po.EmpWelfare;
import com.po.PageBean;
import com.po.Salary;
import com.po.Welfare;
import com.service.*;
import com.util.DaoService;
@Service("EmpService")
@Transactional
public class EmpServiceImpl implements IEmpService {
	@Resource(name="DaoService")
    private DaoService daoService;
	
	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public int save(Emp emp) {
		int code=daoService.getEmpMapper().save(emp);
		//添加员工数据完成，需要获取员工Id，存储薪资和福利
		  //1.获取员工最大编号(刚才存入的编号)
		Integer eid=daoService.getEmpMapper().findMaxEid();
		/******保存薪资********/
		Salary sa=new Salary(eid,emp.getEmoney());
		daoService.getSalaryMapper().save(sa);
		/******保存薪资end********/
		/******保存福利（员工选取的福利Id）********/
		String[] wids=emp.getWids();
		if(wids!=null && wids.length>0){
			for (int i = 0; i < wids.length; i++) {
				EmpWelfare ewf=new EmpWelfare(eid,Integer.parseInt(wids[i]));
				daoService.getEmpWelfareMapper().save(ewf);
			}
		}
		/******保存福利end********/
		return code;
	}

	@Override
	public int update(Emp emp) {
		int code=daoService.getEmpMapper().update(emp);
		/******更新薪资********/
		  //1.获取原有薪资
		  Salary oldsa=daoService.getSalaryMapper().findByEid(emp.getEid());
		  if(oldsa!=null&&oldsa.getEmoney()!=null){//原来有薪资
			  oldsa.setEmoney(emp.getEmoney());
			  daoService.getSalaryMapper().updateByEid(oldsa);
		  }else{//原来没有薪资
			    Salary sa=new Salary(emp.getEid(),emp.getEmoney());
				daoService.getSalaryMapper().save(sa);
		  }
		/******更新薪资end********/
		  /******更新福利（员工选取的福利Id）********/
		     //1.获取原有福利
		   List<Welfare> lswf=daoService.getEmpWelfareMapper().findByEid(emp.getEid());
		   if(lswf!=null&&lswf.size()>0){
			   //删除原有福利
			   daoService.getEmpWelfareMapper().delByEid(emp.getEid());
		   }
		   String[] wids=emp.getWids();
			if(wids!=null && wids.length>0){
				for (int i = 0; i < wids.length; i++) {
					EmpWelfare ewf=new EmpWelfare(emp.getEid(),Integer.parseInt(wids[i]));
					daoService.getEmpWelfareMapper().save(ewf);
				}
			}
		  /******更新福利end********/
		return code;
	}

	@Override
	public int delById(Integer eid) {
		//先删子表
		daoService.getSalaryMapper().delByEid(eid);
		daoService.getEmpWelfareMapper().delByEid(eid);
		int code=daoService.getEmpMapper().delById(eid);
		return code;
	}

	@Override
	public Emp findById(Integer eid) {
		System.out.println(eid);
		//获取员工
		Emp emp=daoService.getEmpMapper().findById(eid);
		/**获取薪资**/
		System.out.println("aaa"+emp.toString());
		Salary sa=daoService.getSalaryMapper().findByEid(eid);
		System.out.println(sa.toString());
		if(sa!=null&&sa.getEmoney()!=null){
			emp.setEmoney(sa.getEmoney());
		}
		/**获取薪资end**/
		/**获取福利**/
		List<Welfare> lswf=daoService.getEmpWelfareMapper().findByEid(eid);
		System.out.println(lswf.toString());
		if(lswf!=null&&lswf.size()>0){
			String[] wids=new String[lswf.size()];
			for (int i = 0; i < lswf.size(); i++) {
				Welfare wa=lswf.get(i);
				wids[i]=wa.getWid().toString();
			}
			emp.setWids(wids);
		}
		emp.setLswf(lswf);
		/**获取福利end**/
		return emp;
	}

	@Override
	public List<Emp> findPageAll(PageBean pb) {
      if(pb==null){
    	  pb=new PageBean();
      }
		return daoService.getEmpMapper().findPageAll(pb);
	}

	@Override
	public int findMaxRow() {
		// TODO Auto-generated method stub
		return daoService.getEmpMapper().findMaxRow();
	}

	

}
