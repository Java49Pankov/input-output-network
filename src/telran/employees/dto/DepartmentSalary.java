package telran.employees.dto;

import java.io.*;

public record DepartmentSalary(String department, double salary) implements Serializable {

}
