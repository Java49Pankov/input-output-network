package telran.employees.service;

import telran.employees.dto.*;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public interface Company {
	boolean addEmployee(Employee empl);

	Employee removeEmployee(long id);

	Employee getEmployee(long id);

	Employee updateSalary(long id, int newSalary);

	Employee updateDepartment(long id, String newDepartment);

	List<Employee> getEmployees();

	List<DepartmentSalary> getDepartmentSalaryDistribution(); // returns list of all departments with average salary

	List<SalaryDistribution> getSalaryDistribution(int interval); // returns salary values distribution

	List<Employee> getEmployeesByDepartment(String department);

	List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);

	List<Employee> getEmployeesByAge(int ageFrom, int ageTo);

	default public void restore(String filePath) {
		if (Files.exists(Path.of(filePath))) {
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
				@SuppressWarnings("unchecked")
				List<Employee> employeesRestore = (List<Employee>) input.readObject();
				employeesRestore.forEach(this::addEmployee);
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
	}

	default public void save(String filePath) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
			output.writeObject(getEmployees());
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}

}
