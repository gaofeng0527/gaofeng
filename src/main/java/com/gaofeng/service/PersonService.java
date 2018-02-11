package com.gaofeng.service;

import com.gaofeng.pojo.Person;

public interface PersonService {

	public void save(Person person);

	public Person findPersonById(Long id);

}
