package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.employees.service.Company;
import telran.employees.service.CompanyImpl;
import telran.employees.dto.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CompanyTest {
	private static final long ID_NOT_EXIST = 1_000_000;
	private static final long ID1 = 123;
	private static final long ID2 = 124;
	private static final long ID3 = 125;
	private static final long ID4 = 126;
	private static final long ID5 = 127;
	private static final String DEP1 = "dep1";
	private static final String DEP2 = "dep2";
	private static final String DEP3 = "dep3";
	private static final int SALARY1 = 10_000;
	private static final int SALARY2 = 5_000;
	private static final int SALARY3 = 15_000;
	private static final int YEAR1 = 2000;
	private static final int YEAR2 = 1990;
	private static final int YEAR3 = 2003;
	private static final LocalDate DATA1 = LocalDate.ofYearDay(YEAR1, 100);
	private static final LocalDate DATA2 = LocalDate.ofYearDay(YEAR2, 100);
	private static final LocalDate DATA3 = LocalDate.ofYearDay(YEAR3, 100);
	private static final String TEST_DATA = "test.data";

	Employee empl1 = new Employee(ID1, "name", DEP1, SALARY1, DATA1);
	Employee empl2 = new Employee(ID2, "name", DEP2, SALARY2, DATA2);
	Employee empl3 = new Employee(ID3, "name", DEP1, SALARY1, DATA1);
	Employee empl4 = new Employee(ID4, "name", DEP2, SALARY2, DATA2);
	Employee empl5 = new Employee(ID5, "name", DEP3, SALARY3, DATA3);
	Employee[] employees = { empl1, empl2, empl3, empl4, empl5 };
	Company company;

	@BeforeEach
	void setUp() throws Exception {
		company = new CompanyImpl();
		for (Employee empl : employees) {
			company.addEmployee(empl);
		}
	}

	@Test
	void testAddEmployee() {
		assertFalse(company.addEmployee(empl1));
		assertTrue(company.addEmployee(new Employee(ID_NOT_EXIST, "name", DEP1, SALARY1, DATA1)));
	}

	@Test
	void testRemoveEmployee() {
		assertNull(company.removeEmployee(ID_NOT_EXIST));
		assertEquals(empl1, company.removeEmployee(ID1));
		Employee[] expected = { empl2, empl3, empl4, empl5 };
		assertArrayEquals(expected, company.getEmployees().toArray(Employee[]::new));

	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, company.getEmployee(ID1));
		assertNull(company.getEmployee(ID_NOT_EXIST));
	}

	@Test
	void testGetEmployees() {
		assertArrayEquals(employees, company.getEmployees().toArray(Employee[]::new));
	}

	@Test
	void testGetDepartmentSalaryDistribution() {
		Employee[] expected = { empl1, empl3 };
		Set<Employee> expectedEmployee = new LinkedHashSet<>(Arrays.asList(expected));

		List<DepartmentSalary> actualDistribution = company.getDepartmentSalaryDistribution();

		for (Employee empl : expectedEmployee) {
			DepartmentSalary expectedSalary = new DepartmentSalary(empl.department(), empl.salary());
			assertTrue(actualDistribution.contains(expectedSalary));
		}

	}

	@Test
	void testGetSalaryDistribution() {
		company.getSalaryDistribution(2000);
		  //TODO
	}

	@Test
	@Order(2)
	void testRestore() {
		Company newCompany = new CompanyImpl();
		newCompany.restore(TEST_DATA);
		assertArrayEquals(employees, newCompany.getEmployees().toArray(Employee[]::new));

	}

	@Test
	@Order(1)
	void testSave() {
		company.save(TEST_DATA);
	}

}
