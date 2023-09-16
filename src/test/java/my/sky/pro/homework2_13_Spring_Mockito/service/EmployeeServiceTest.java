package my.sky.pro.homework2_13_Spring_Mockito.service;

import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeAlreadyAddedException;
import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeNotFoundException;
import my.sky.pro.homework2_13_Spring_Mockito.exception.EmployeeStorageIsFullException;
import my.sky.pro.homework2_13_Spring_Mockito.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static my.sky.pro.homework2_13_Spring_Mockito.service.EmployeeService.LIMIT;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    @Test
    void testAddEmployee() {
        employeeService.addEmployee("test", "test2", 15_000, 15);

        Collection<Employee> allEmployees = employeeService.getAll();
        assertEquals(1, allEmployees.size());
        var employee = allEmployees.iterator().next();
        assertEquals("Test", employee.getFirstName());
        assertEquals("Test2", employee.getLastName());
        assertEquals(15_000, employee.getSalary());
        assertEquals(15, employee.getDepartment());
    }


    @Test
    void testAddEmployeesWhenStorageIsFull() {
        for (int i = 0; i < LIMIT; i++) {
            employeeService.addEmployee("test_" + i, "test_test_" + i, 0d, 0);
        }
        assertThrows(EmployeeStorageIsFullException.class, () -> employeeService.addEmployee(
                                  "test_"+ LIMIT, "test_test_" + LIMIT, 0, 0));
    }

    @Test
    void testAddEmployeeWhenAlreadyExists() {
        employeeService.addEmployee("test", "test", 0, 0);
        assertThrows(EmployeeAlreadyAddedException.class, () -> employeeService.addEmployee(
                                                     "Test", "Test", 0, 0));

    }

    @Test
    void testFindEmployee() {
        employeeService.addEmployee("test", "testtest", 15_000, 1);
        var actual = employeeService.findEmployee("test", "testtest");
        assertEquals("Test", actual.getFirstName());
        assertEquals("Testtest", actual.getLastName());
        assertEquals(15_000, actual.getSalary());
        assertEquals(1, actual.getDepartment());
    }

    @Test
    void testFindEmployeeWhenNotExist() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("test", "testtest"));
    }

    @Test
    void testRemoveEmployee() {
        employeeService.addEmployee("test", "testtest", 10, 1);
        assertTrue(employeeService.removeEmployee("test", "testtest"));
        assertEquals(0, employeeService.getAll().size());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.removeEmployee("test0", "testtest0"));
    }

    @Test
    void testGetAllEmployees() {
        employeeService.addEmployee("test_1", "test_test_1", 100, 1);
        employeeService.addEmployee("test_2", "test_test_2", -100, 1);
        employeeService.addEmployee("test_3", "test_test_3", 100, -1);

        var all = employeeService.getAll();
        assertEquals(3,all.size());

        var allTest = List.of(new Employee("Test_1", "Test_test_1", 100, 1),
                              new Employee("Test_2", "Test_test_2", -100, 1),
                              new Employee("Test_3", "Test_test_3", 100, -1));

        for (Employee e:all) {
            assertTrue(allTest.contains(e));
        }
        for (Employee e:allTest) {
            assertTrue(all.contains(e));
        }

        Assertions.assertThat(all.size()).isEqualTo(3);
        Assertions.assertThat(all).containsExactlyInAnyOrderElementsOf(allTest);


    }
}