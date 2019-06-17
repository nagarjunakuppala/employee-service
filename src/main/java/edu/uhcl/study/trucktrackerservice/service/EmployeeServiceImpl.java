package edu.uhcl.study.trucktrackerservice.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uhcl.study.trucktrackerservice.entity.Employee;
import edu.uhcl.study.trucktrackerservice.exception.ResourceAlreadyExistsException;
import edu.uhcl.study.trucktrackerservice.exception.ResourceNotFoundException;
import edu.uhcl.study.trucktrackerservice.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository repository;
	
	@Override
	@Transactional(readOnly =true)
	public List<Employee> findAll() {
		Iterable<Employee> employees= repository.findAll();
		return (List<Employee>) employees;
	}

	@Override
	@Transactional(readOnly =true)
	public Employee findOne(String id) {
		Optional<Employee> object = repository.findById(id);
		if(!object.isPresent()) {
			throw new ResourceNotFoundException("Employee is not Avaiable with the following ID: "+id);
		}
		return object.get();
	}

	@Override
	@Transactional
	public Employee create(Employee emp) {
		Optional<Employee> employee = repository.findByEmail(emp.getEmail());
		if(employee.isPresent()) {
			throw new ResourceAlreadyExistsException("Employee already available in database with email: "+emp.getEmail());
		}
		return repository.save(emp);
	}

	@Override
	@Transactional
	public Employee update(String id, Employee emp) {
		findOne(id);
		return repository.save(emp);
	}

	@Transactional
	@Override
	public void delete(String id) {
		Employee emp = findOne(id);
		repository.delete(emp);;
	}

}
