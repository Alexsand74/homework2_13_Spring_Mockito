package my.sky.pro.homework2_13_Spring_Mockito.controller;

import my.sky.pro.homework2_13_Spring_Mockito.exception.NotValidArgumentException;
import my.sky.pro.homework2_13_Spring_Mockito.model.Employee;
import my.sky.pro.homework2_13_Spring_Mockito.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public void add(@RequestParam String firstName,
                    @RequestParam String lastName,
                    @RequestParam double salary,
                    @RequestParam int departmentId) {
        check(firstName, lastName);
        service.addEmployee(firstName, lastName, salary, departmentId);
    }

    @GetMapping("/get")
    public Employee get(@RequestParam String firstName,
                        @RequestParam String lastName) {
        check(firstName, lastName);
        return service.findEmployee(firstName, lastName);
    }

    @GetMapping("/remove")
    public boolean remove(@RequestParam String firstName,
                          @RequestParam String lastName) {
        check(firstName, lastName);
        return service.removeEmployee(firstName, lastName);
    }

    @GetMapping("/all")
    public Collection<Employee> getAll() {
        return service.getAll();
    }

    private void check(String... args) {
        for (String arg : args) {
            if (!StringUtils.isAlpha(arg)) {
                throw new NotValidArgumentException();
            }
        }
    }
}
