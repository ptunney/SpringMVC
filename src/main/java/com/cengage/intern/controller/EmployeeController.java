package com.cengage.intern.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hr")
public class EmployeeController {

	private Map<Integer, Employee> employees = new HashMap<Integer,Employee>();

	@RequestMapping(method=RequestMethod.GET, value="/employee/{id}")
	public @ResponseBody Employee getEmployee(@PathVariable String id) {
		Employee e = employees.get(Long.parseLong(id));
		return e;
	}

	@RequestMapping(method=RequestMethod.GET, value="/status")
	public @ResponseBody String getStatus() {
		return "V1";
	}

	@RequestMapping(method=RequestMethod.POST, value="/addEmployee")
	public @ResponseBody String addEmployee(@RequestBody String body) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Employee employee = mapper.readValue(body, Employee.class);
			employees.put(employee.getId(), employee);
			return "Employee " + employee.getName() + " added";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
	public void removeEmployee(@PathVariable String id) {
		employees.remove(Long.parseLong(id));
	}

	@RequestMapping(method=RequestMethod.GET, value="/employees")
	public @ResponseBody List<Employee> getEmployees() {
		Set<Integer> keys = employees.keySet();
		List<Employee> result = new ArrayList<Employee>(keys.size());
		for ( Integer id : keys) {
			result.add(employees.get(id));
		}
		return result;
	}

}
