package com.gaofeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.AccountMapper;
import com.gaofeng.pojo.Account;
import com.gaofeng.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper amapper;

	@Override
	public void addAccount(Account account) {
		amapper.addAccount(account);
	}

	@Override
	public List<Account> findAccountByUserId(Long userId) {
		return amapper.findAccountByUserId(userId);
	}

	@Override
	public List<Account> findAccounts() {
		return amapper.findAccounts();
	}

	@Override
	public Account findAccountById(Long accountId) {
		return amapper.findAccountById(accountId);
	}

	@Override
	public List<Account> findByAccount(Account account) {
		return amapper.findByAccount(account);
	}

	@Override
	public Account findOneByAccountId(Long id) {
		return amapper.findOneByAccountId(id);
	}
	
	
	
	
	

}
