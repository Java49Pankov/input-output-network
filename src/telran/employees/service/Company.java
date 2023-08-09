package telran.employees.service;

import telran.employees.dto.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public interface Company {
	boolean addEmployee(Employee empl);

	Employee removeEmployee(long id);

	Employee getEmployee(long id);

	List<Employee> getEmployees();

	List<DepartmentSalary> getDepartmentSalaryDistribution(); // returns list of all departments with average salary

	List<SalaryDistribution> getSalaryDistribution(int interval); // returns salary values distribution

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
