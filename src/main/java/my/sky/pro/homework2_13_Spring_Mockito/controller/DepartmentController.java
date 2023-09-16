package my.sky.pro.homework2_13_Spring_Mockito.controller;

import my.sky.pro.homework2_13_Spring_Mockito.model.Employee;
import my.sky.pro.homework2_13_Spring_Mockito.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("/{id}/salary/sum")
    public double sumByDept(@PathVariable int deptId) {
        return service.sum(deptId);
    }

    @GetMapping("/{id}/salary/max")
    public double max(@PathVariable int deptId) {
        return service.maxSalary(deptId);
    }

    @GetMapping("/{id}/salary/min")
    public double min(@PathVariable int deptId) {
        return service.minSalary(deptId);
    }

    @GetMapping("/{id}/employees")
    public Collection<Employee> byDept(@PathVariable int deptId) {
        return service.findAllByDept(deptId);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> all() {
        return service.groupByDept();
    }
}
