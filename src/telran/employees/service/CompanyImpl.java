package telran.employees.service;

import java.util.stream.*;

import telran.employees.dto.*;

import java.time.LocalDate;
import java.util.*;

public class CompanyImpl implements Company {
	LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
	TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
	HashMap<String, Collection<Employee>> employeesDepartment = new HashMap<>();
	TreeMap<Integer, Collection<Employee>> employeesAge = new TreeMap<>();

	@Override
	public boolean addEmployee(Employee empl) {
		boolean res = false;
		Employee emplRes = employees.putIfAbsent(empl.id(), empl);
		if (emplRes == null) {
			addEmployeeData(empl);
			res = true;
		}
		return res;
	}

	private void addEmployeeData(Employee empl) {
		int salary = empl.salary();
		String department = empl.department();
		int birthDate = empl.birthDate().getYear();
		employeesSalary.computeIfAbsent(salary, k -> new HashSet<>()).add(empl);
		employeesDepartment.computeIfAbsent(department, k -> new HashSet<>()).add(empl);
		employeesAge.computeIfAbsent(birthDate, k -> new HashSet<>()).add(empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		Employee res = employees.remove(id);
		if (res != null) {
			removeEmployeeData(res, employeesSalary, res.salary());
			removeEmployeeData(res, employeesDepartment, res.department());
			removeEmployeeData(res, employeesAge, res.birthDate().getYear());
		}
		return res;
	}

	private <T> void removeEmployeeData(Employee empl, Map<T, Collection<Employee>> collection, T key) {
		Collection<Employee> col = collection.get(key);
		col.remove(empl);
		if (col.isEmpty()) {
			collection.remove(key);
		}
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
				.collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting())).entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval - 1,
						e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary())).toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return new ArrayList<>(employeesDepartment.getOrDefault(department, Collections.emptyList()));
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return employeesSalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
				.flatMap(col -> col.stream().sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id()))).toList();
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		Employee employee = employees.get(id);
		Employee updateEmpl = null;
		if (employee != null) {
			updateEmpl = new Employee(employee.id(), employee.name(), employee.department(), newSalary,
					employee.birthDate());
			employees.put(id, updateEmpl);
			removeEmployeeData(employee, employeesSalary, employee.salary());
			addEmployeeData(updateEmpl);
		}
		return updateEmpl;
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		Employee empl = employees.get(id);
		Employee updateEmpl = null;
		if (empl != null) {
			updateEmpl = new Employee(empl.id(), empl.name(), newDepartment, empl.salary(), empl.birthDate());
			employees.put(id, updateEmpl);
			removeEmployeeData(empl, employeesDepartment, empl.department());
			addEmployeeData(updateEmpl);
		}
		return updateEmpl;
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		return employeesAge.subMap(ageFrom, true, ageTo, true).values().stream()
				.flatMap(col -> col.stream().sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id()))).toList();
	}

}
