package telran.employees.controller;

import java.time.LocalDate;
import java.util.*;

import telran.employees.dto.Employee;
import telran.employees.service.*;
import telran.view.*;

public class CompanyController {

	private static final long MIN_ID = 100_000;
	private static final long MAX_ID = 999_999;
	private static final int MIN_SALARY = 6_000;
	private static final int MAX_SALARY = 50_000;
	private static final int MAX_AGE = 75;
	private static final int MIN_AGE = 20;
	static Company company;

	public static ArrayList<Item> getCompanyItems(Company company) {
		CompanyController.company = company;
		ArrayList<Item> res = new ArrayList<>(Arrays.asList(getItems()));
		return res;
	}

	private static Item[] getItems() {
		return new Item[] { Item.of("Add new Employee", CompanyController::addEmployeeItem),
				Item.of("Remove Employee", CompanyController::removeEmployeeItem),
				Item.of("All Employee", CompanyController::getEmployeesItem),
				Item.of("Data about Employee", CompanyController::getEmployeeItem),
				Item.of("Employees by Salary", CompanyController::getEmployeesBySalaryItem),
				Item.of("Employees by Department", CompanyController::getEmployeesByDepartmentItem),
				Item.of("Update Salary", CompanyController::updateSalaryItem),
				Item.of("Deparments & Salary", CompanyController::getDepartmentSalaryDistributionItem),
				Item.of("Distribution by Salary", CompanyController::getSalaryDistributionItem),
				Item.of("Update Department", CompanyController::updateDepartmentItem),
				Item.of("Employees by Age", CompanyController::getEmployeesByAgeItem), };
	}

	static private Set<String> departments = new HashSet<>(
			Arrays.asList(new String[] { "QA", "Development", "Audit", "Management", "Accouting" }));

	static void addEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter id Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		String name = io.readString("Enter name", "Wrong name", str -> str.matches("[A-Z][a-z]+"));
		String department = io.readString("Enter department", "Wrong department", departments);
		int salary = io.readInt("Enter salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
		LocalDate birthDate = io.readDate("Enter birth date", "Wrong birth date entered", getBirthDate(MAX_AGE),
				getBirthDate(MIN_AGE));
		boolean res = company.addEmployee(new Employee(id, name, department, salary, birthDate));
		io.writeLine(res ? String.format("Employee with id %d has been added", id)
				: String.format("Employee with id %d already exists", id));
	}

	private static LocalDate getBirthDate(int age) {
		return LocalDate.now().minusYears(age);
	}

	static void removeEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter id Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		Employee employee = company.getEmployee(id);
		io.writeLine(employee != null ? company.removeEmployee(id) : "Employee by ID does not exist");
	}

	static void getEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter id Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		Employee employee = company.getEmployee(id);
		io.writeLine(employee != null ? employee : "Employee by ID does not exist");
	}

	static void getEmployeesItem(InputOutput io) {
		List<Employee> employees = company.getEmployees();
		io.writeLine(employees);
	}

	static void getDepartmentSalaryDistributionItem(InputOutput io) {
		company.getDepartmentSalaryDistribution().forEach(io::writeLine);
	}

	static void getSalaryDistributionItem(InputOutput io) {
		int interval = io.readInt("Enter salary range", "Wrong value");
		company.getSalaryDistribution(interval).forEach(io::writeLine);
		;
	}

	static void getEmployeesByDepartmentItem(InputOutput io) {
		String department = io.readString("Enter department", "Wrong department", departments);
		company.getEmployeesByDepartment(department).forEach(io::writeLine);
	}

	static void getEmployeesBySalaryItem(InputOutput io) {
		int salaryFrom = io.readInt("Enter min salary", "Wrong salary");
		int salaryTo = io.readInt("Enter max salary", "Wrong salary");
		io.writeLine(salaryFrom < salaryTo ? company.getEmployeesBySalary(salaryFrom, salaryTo)
				: "the salary from should be less than the salary to");
	}

	static void getEmployeesByAgeItem(InputOutput io) {
		int ageFrom = io.readInt("Enter min age", "Wrong age");
		int ageTo = io.readInt("Enter max age", "Wrong age");
		io.writeLine(ageFrom < ageTo ? company.getEmployeesByAge(ageFrom, ageTo) : "age from must be less than age to");
	}

	static void updateSalaryItem(InputOutput io) {
		long id = io.readLong("Enter id Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		int salary = io.readInt("Enter new salary", "Wrong salary");
		Employee employee = company.getEmployee(id);
		io.writeLine(employee != null ? company.updateSalary(id, salary) : "Employee by ID does not exist");
	}

	static void updateDepartmentItem(InputOutput io) {
		long id = io.readLong("Enter id Employee identity", "Wrong identity value", MIN_ID, MAX_ID);
		String department = io.readString("Enter new department", "Wrong department", departments);
		Employee employee = company.getEmployee(id);
		io.writeLine(employee != null ? company.updateDepartment(id, department) : "Employee by ID does not exist");
	}
}
