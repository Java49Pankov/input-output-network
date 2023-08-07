package telran.employees.dto;

import java.time.LocalDate;
import java.io.*;

public record Employee(long id, String name, String department, int salary, LocalDate birthDate)
		implements Serializable {
}
