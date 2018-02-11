package com.gaofeng.service;

import java.util.List;

import com.gaofeng.pojo.Account;;

public interface AccountService {

	/**
	 * 添加宽带信息
	 * 
	 * @param account
	 */
	public void addAccount(Account account);

	/**
	 * 查询某个用户名下安装的宽带
	 * 
	 * @param userId
	 * @return
	 */
	public List<Account> findAccountByUserId(Long userId);

	/**
	 * 查询所有宽带数据
	 * 
	 * @return
	 */
	public List<Account> findAccounts();
	
	/**
	 * 跟Id查询单个宽带信息
	 * @param accountId
	 * @return
	 */
	public Account findAccountById(Long accountId);
	
	/**
	 * 条件查询
	 * @param account
	 * @return
	 */
	public List<Account> findByAccount(Account account);
	
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	public Account findOneByAccountId(Long id);

}
