package my.sky.pro.homework2_13_Spring_Mockito.service;

import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeNotFoundException;
import my.sky.pro.homework2_13_Spring_Mockito.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        var employees = List.of(
                new Employee("Иван", "Иванов", 10, 1),
                new Employee("Петр", "Петров", 20, 2),
                new Employee("Никита", "Никитов", 30, 1),
                new Employee("Илья", "Ильин", 40, 3),
                new Employee("Федор", "Федоров", 5, 3),
                new Employee("Сергей", "Сергеев", 50, 4),
                new Employee("Валентин", "Валентинов", 60, 1));
        when(employeeService.getAll()).thenReturn(employees);
    }
    /*
    Другой вариант инициализации моков.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentService = new DepartmentService(employeeService);
    }*/
    @Test
    void testSum() {
        Assertions.assertThat(departmentService.sum(1)).isEqualTo(100d);
    }

    @Test
    void testMaxSalary() {
        Assertions.assertThat(departmentService.maxSalary(1)).isEqualTo(60d);
    }

    @Test
    void testMinSalary() {
        Assertions.assertThat(departmentService.minSalary(3)).isEqualTo(5d);
    }

    @Test
    void testWhenEmployeesIsEmpty() {
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        Assertions.assertThatThrownBy(() -> departmentService.minSalary(1))
                .isInstanceOf(EmployeeNotFoundException.class);
        Assertions.assertThatThrownBy(() -> departmentService.maxSalary(1))
                .isInstanceOf(EmployeeNotFoundException.class);
        Assertions.assertThat(departmentService.sum(1)).isEqualTo(0d);
    }

    @Test
    void testAllByDept() {
        var employees = departmentService.findAllByDept(4);
        Assertions.assertThat(employees.size()).isEqualTo(1);
        Assertions.assertThat(employees.get(0)).isEqualTo(new Employee("Сергей", "Сергеев", 50, 4));
    }

    @Test
    void testGroupByDept() {
        Map<Integer, List<Employee>> actual = departmentService.groupByDept();
        Assertions.assertThat(actual.keySet()).containsExactly(1, 2, 3, 4);
        Assertions.assertThat(actual.get(1)).containsExactly(
                new Employee("Иван", "Иванов", 10, 1),
                new Employee("Никита", "Никитов", 30, 1),
                new Employee("Валентин", "Валентинов", 60, 1));
        Assertions.assertThat(actual.get(2)).containsExactly(new Employee("Петр", "Петров", 20, 2));
        Assertions.assertThat(actual.get(3)).containsExactly(
                new Employee("Илья", "Ильин", 40, 3),
                new Employee("Федор", "Федоров", 5, 3));
        Assertions.assertThat(actual.get(4)).containsExactly(new Employee("Сергей", "Сергеев", 50, 4));
    }
}
