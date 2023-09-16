package my.sky.pro.homework2_13_Spring_Mockito.service;

import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeNotFoundException;
import my.sky.pro.homework2_13_Spring_Mockito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double sum(int deptId){
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public double maxSalary(int deptId) {
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)
                .max(Comparator.comparingDouble(o -> o))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public double minSalary(int deptId) {
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)
                .min(Comparator.comparingDouble(o -> o))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findAllByDept(int deptId) {
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> groupByDept() {
        return employeeService.getAll()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}

