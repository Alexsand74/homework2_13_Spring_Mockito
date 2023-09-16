package my.sky.pro.homework2_13_Spring_Mockito.service;

import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeAlreadyAddedException;
import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeNotFoundException;
import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeStorageIsFullException;
import my.sky.pro.homework2_13_Spring_Mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Service
public class EmployeeService {

    public static final int LIMIT = 10;

    private final Map<String, Employee> employees = new HashMap<>();

    public void addEmployee(String firstName, String lastName, double salary, int departmentId) {
        if (employees.size() == LIMIT) {
            throw new EmployeeStorageIsFullException();
        }

        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }

        employees.put(key, new Employee(capitalize(firstName), capitalize(lastName), salary, departmentId));
    }

    public Employee findEmployee(String firstName, String lastName) {
        var employee = employees.get(makeKey(firstName, lastName));
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public boolean removeEmployee(String firstName, String lastName) {
        Employee removed = employees.remove(makeKey(firstName, lastName));
        if (removed == null) {
            throw new EmployeeNotFoundException();
        }
        return true;
    }

    public Collection<Employee> getAll() {
        return employees.values();
    }

    private String makeKey(String firstName, String lastName) {
        return (firstName + "_" + lastName).toLowerCase();
    }
}

