package com.gaofeng.mapper;

import java.util.List;

import com.gaofeng.pojo.InstallMessage;

public interface InstallMessageMapper {

	/**
	 * 添加装机信息
	 * 
	 * @param install
	 */
	public void addInstall(InstallMessage install);

	/**
	 * 查询该公司下的所有报装信息
	 * 
	 * @param companyId
	 * @return
	 */
	public List<InstallMessage> findAllInstallMes(Long companyId);

	/**
	 * 条件查询
	 * 
	 * @param install
	 * @return
	 */
	public List<InstallMessage> findByInstall(InstallMessage install);

	/**
	 * 修改数据状态
	 * 
	 * @param install
	 */
	public void updateStatus(InstallMessage install);

	/**
	 * 查询单个信息
	 * 
	 * @param id
	 * @return
	 */
	public InstallMessage findInstallById(Long id);

	/**
	 * 修改信息
	 * 
	 * @param install
	 */
	public void updateInstall(InstallMessage install);
}
