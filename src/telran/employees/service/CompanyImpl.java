package telran.employees.service;

import java.util.stream.*;

import telran.employees.dto.*;

import java.util.*;

public class CompanyImpl implements Company {
	LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();

	@Override
	public boolean addEmployee(Employee empl) {
		return employees.putIfAbsent(empl.id(), empl) == null;
	}

	@Override
	public Employee removeEmployee(long id) {
		return employees.remove(id);
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	@Override
	public List<Employee> getEmployees() {
		return new ArrayList<>(employees.values());
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department, Collectors.averagingInt(Employee::salary)))
				.entrySet().stream().map(e -> new DepartmentSalary(e.getKey(), e.getValue())).toList();
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		return employees.values().stream()
				.collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting()))
				.entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval - 1,
						e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary())).toList();
	}

}
