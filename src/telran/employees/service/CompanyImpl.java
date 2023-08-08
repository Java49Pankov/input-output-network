package telran.employees.service;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import telran.employees.dto.*;

import java.io.*;

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
		List<SalaryDistribution> list = new ArrayList<>();
		int minSalary = employees.values().stream().map(e -> e.salary()).min(Comparator.naturalOrder()).get();
		int maxSalary = employees.values().stream().map(e -> e.salary()).max(Comparator.naturalOrder()).get();

		int startSalary = (minSalary / interval) * interval;
		IntStream.iterate(startSalary, i -> i + interval).limit((maxSalary - startSalary) / interval + 1).forEach(i -> {
			long counter = employees.values().stream().filter(e -> e.salary() >= i && e.salary() < i + interval)
					.count();

			list.add(new SalaryDistribution(i, Math.min(i + interval - 1, maxSalary), (int) counter));
		});
		System.out.println(list);
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void restore(String filePath) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
			employees = (LinkedHashMap<Long, Employee>) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e.toString());
		}

	}

	@Override
	public void save(String filePath) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
			output.writeObject(employees);
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}

}
