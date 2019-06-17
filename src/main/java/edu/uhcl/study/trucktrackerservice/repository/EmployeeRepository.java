package edu.uhcl.study.trucktrackerservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.uhcl.study.trucktrackerservice.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{

	Optional<Employee> findByEmail(String email);

}
