package gaofeng.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.gaofeng.pojo.Person;
import com.gaofeng.service.PersonService;

public class PersonTest  {

	@Resource(name="pesonService")
	private PersonService pservice;
	
	@Test
	public void saveTest() {
		Person p = new Person();
		p.setId(1L);
		p.setUname("张高峰");
		p.setEmail("gaofeng0527@163.com");
		pservice.save(p);
	}
}
