package com.gaofeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaofeng.mapper.PersonMapper;
import com.gaofeng.pojo.Person;
import com.gaofeng.service.PersonService;

@Service(value="pesonService")
public class PersonServiceImpl implements PersonService {
	
	/**
	 * 这个地方因为之前在spring-data.xml配置文件中使用
	 * MapperScannerConfigurer 扫描 mapper包，并创建了bean
	 * 因此这里可以自动注入
	 */
	@Autowired
	private PersonMapper pmapper;

	public void save(Person person) {
		System.out.println("save peson :" + person.toString());
	}
	
	public Person findPersonById(Long id) {
		return pmapper.findPersonById(id);
	}

}
