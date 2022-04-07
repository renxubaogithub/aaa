package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Welfare;
import com.service.IWelfareService;
import com.util.DaoService;
@Service("WelfareService")
@Transactional
public class WelfareServiceImpl implements IWelfareService {
	@Resource(name="DaoService")
    private DaoService daoService;
	
	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}
	@Override
	public List<Welfare> findAll() {
		// TODO Auto-generated method stub
		return daoService.getWelfareMapper().findAll();
	}

}
