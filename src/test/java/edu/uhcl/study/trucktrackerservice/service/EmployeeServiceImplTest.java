package edu.uhcl.study.trucktrackerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uhcl.study.trucktrackerservice.entity.Employee;
import edu.uhcl.study.trucktrackerservice.exception.ResourceNotFoundException;
import edu.uhcl.study.trucktrackerservice.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {
	
	@TestConfiguration
	static class EmployeeServiceImplTestConfiguration {
		
		@Bean
		public EmployeeService getService() {
			return new EmployeeServiceImpl();
		}
	}
	
	@Autowired
	private EmployeeService service; 
	
	@MockBean
	private EmployeeRepository repository;
	
	private List<Employee> employees;
	
	@Before
	public void setup() {
		System.out.println("before");
		employees = new ArrayList<Employee>();
		Employee e = new Employee("Nagarjuna","Kuppala","nagarjunakuppala07@gmail.com");
		Employee e1 = new Employee("Sweathi", "Damera", "swathi.vs113@gmail.com");
		employees.add(e);
		employees.add(e1);
		
		Mockito.when(repository.findAll()).thenReturn(employees);
		Mockito.when(repository.findById(e.getId())).thenReturn(Optional.of(e));
		Mockito.when(repository.save(e)).thenReturn(e);
	}
	@After
	public void cleanup() {
		System.out.println("after");
	}
	
	@Test
	public void findAll() {
	
		List<Employee> result = service.findAll();
		Assert.assertEquals("Should match", employees, result);
	}
	
	@Test
	public void findOne() {
		Employee result = service.findOne(employees.get(0).getId());
		Assert.assertEquals("should be equal", employees.get(0), result);
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void findOneNotFound() {
		service.findOne("nanaa");
	
	}
	
	@Test
	public void create() {
		Employee e = new Employee("Arjun", "Kuppala", "arjun@uhcl.edu");
		Employee result = service.create(e);
		
	}

}
