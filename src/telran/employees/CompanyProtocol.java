package telran.employees;

import java.io.Serializable;
import java.util.ArrayList;

import telran.employees.dto.Employee;
import telran.employees.dto.FromTo;
import telran.employees.dto.UpdateData;
import telran.employees.service.Company;
import telran.net.*;

public class CompanyProtocol implements ApplProtocol {
	private Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Response response = null;
		String requestType = request.requestType();
		Serializable data = request.requestData();
		try {
			Serializable responseData = switch (requestType) {
			case "employee/add" -> employee_add(data);
			case "employee/remove" -> employee_remove(data);
			case "employee/get" -> employee_get(data);
			case "employees/get" -> employees_get(data);
			case "department/salary/distribution" -> department_salary_distribution(data);
			case "salary/distribution" -> salary_distribution(data);
			case "employees/department" -> employees_department(data);
			case "employees/salary" -> employees_salary(data);
			case "employees/age" -> employees_age(data);
			case "salary/update" -> salary_update(data);
			case "department/update" -> department_update(data);

			default -> new Response(ResponseCode.WRONG_TYPE, requestType + "is unsupported in the Company Protocol");
			};
			response = (responseData instanceof Response) ? (Response) responseData
					: new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_DATA, e.toString());
		}
		return response;
	}

	private Serializable salary_update(Serializable data) {
		@SuppressWarnings("unchecked")
		UpdateData<Integer> updateData = (UpdateData<Integer>) data;
		Integer newSalary = updateData.data();
		long id = updateData.id();
		return company.updateSalary(id, newSalary);
	}

	private Serializable employees_age(Serializable data) {
		FromTo fromTo = (FromTo) data;
		int ageFrom = fromTo.from();
		int ageTo = fromTo.to();
		return (Serializable) company.getEmployeesByAge(ageFrom, ageTo);
	}

	private Serializable employees_salary(Serializable data) {
		FromTo fromTo = (FromTo) data;
		int salaryFrom = fromTo.from();
		int salaryTo = fromTo.to();
		return (Serializable) company.getEmployeesBySalary(salaryFrom, salaryTo);
	}

	private Serializable employees_department(Serializable data) {
		String department = (String) data;
		return (Serializable) company.getEmployeesByDepartment(department);
	}

	private Serializable salary_distribution(Serializable data) {
		int interval = (int) data;
		return new ArrayList<>(company.getSalaryDistribution(interval));
	}

	private Serializable department_salary_distribution(Serializable data) {
		return (Serializable) company.getDepartmentSalaryDistribution();
	}

	private Serializable employee_remove(Serializable data) {
		long id = (long) data;
		return company.removeEmployee(id);
	}

	private Serializable department_update(Serializable data) {
		@SuppressWarnings("unchecked")
		UpdateData<String> updateData = (UpdateData<String>) data;
		long id = updateData.id();
		String department = updateData.data();
		return company.updateDepartment(id, department);
	}

	private Serializable employees_get(Serializable data) {
		return (Serializable) company.getEmployees();
	}

	private Serializable employee_get(Serializable data) {
		long id = (long) data;
		return company.getEmployee(id);
	}

	private Serializable employee_add(Serializable data) {
		Employee empl = (Employee) data;
		return company.addEmployee(empl);
	}

}
