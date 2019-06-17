package edu.uhcl.study.trucktrackerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uhcl.study.trucktrackerservice.entity.Employee;
import edu.uhcl.study.trucktrackerservice.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/employees")
@Api("employee related endpoints")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ApiOperation(value="Find All Employees", notes="returns list of all employees available in the database")
	@ApiResponses(value= {
			@ApiResponse(code=200, message="OK"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Employee findById(@PathVariable("id") String id) {
		return employeeService.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Employee create(@RequestBody Employee employee) {
		return employeeService.create(employee);
	}
	
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public Employee update(@PathVariable("id") String id, @RequestBody Employee employee) {
		return employeeService.update(id, employee);
	}
	
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void delete(@PathVariable("id") String id) {
		employeeService.delete(id);
	}
}
