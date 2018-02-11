package com.gaofeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.InstallMessageMapper;
import com.gaofeng.pojo.InstallMessage;
import com.gaofeng.service.InstallMessageService;

@Service
public class InstallMessageServiceImpl implements InstallMessageService {

	@Autowired
	private InstallMessageMapper imapper;
	
	@Override
	public void addInstall(InstallMessage install) {
		imapper.addInstall(install);
	}

	@Override
	public List<InstallMessage> findAllInstallMes(Long companyId) {
		return imapper.findAllInstallMes(companyId);
	}

	@Override
	public List<InstallMessage> findByInstall(InstallMessage install) {
		return imapper.findByInstall(install);
	}

	@Override
	public void updateStatus(InstallMessage install) {
		imapper.updateStatus(install);
	}

	@Override
	public InstallMessage findInstallById(Long id) {
		return imapper.findInstallById(id);
	}

	@Override
	public void updateInstall(InstallMessage install) {
		imapper.updateInstall(install);
	}
	
	
	
	
	
	
	
	
	

}
