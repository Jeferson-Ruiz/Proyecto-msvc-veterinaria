package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeDto;
import com.jeferson.msvc_sav_workstaff.mapper.EmployeeMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Employee;
import com.jeferson.msvc_sav_workstaff.models.JobPosition;
import com.jeferson.msvc_sav_workstaff.services.EmployeeService;

@RestController
@RequestMapping("api/sav/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> employeesDto = employeeService.findAll().stream()
                .map(employeeMapper::toDto)
                .toList();
        return ResponseEntity.ok(employeesDto);
    }

    @GetMapping("id/{idEmployee}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long idEmployee) {
        Optional<Employee> optEmployee = employeeService.findEmployeeById(idEmployee);

        if (optEmployee.isPresent()) {
            Employee employee = optEmployee.get();
            EmployeeDto employeeDto = employeeMapper.toDto(employee);
            return ResponseEntity.ok(employeeDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("jobposition/{jobPosition}")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeeByJonPosition(@PathVariable JobPosition jobPosition) {
        List<EmployeeDto> employeesDto = employeeService.findAllByJobPosition(jobPosition).stream()
                .map(employeeMapper::toDto)
                .toList();
        return ResponseEntity.ok(employeesDto);
    }

    @DeleteMapping("id/{idEmployee}")
    public ResponseEntity<?> deleteInfoEmployee(@PathVariable Long idEmployee) {
        Optional<Employee> optEmployee = employeeService.findEmployeeById(idEmployee);

        if (optEmployee.isPresent()) {
            employeeService.delete(idEmployee);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("update/email/{idEmployee}")
    public ResponseEntity<?> updateInfoEmailEmployee(@PathVariable Long idEmployee, @RequestBody String email) {
        if (employeeService.findEmployeeById(idEmployee).isPresent()) {
            employeeService.updateEmail(idEmployee, email);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Actualización no valida. El empleado con el Id: " + idEmployee + " no existe en el sistema");
    }

    @PatchMapping("update/phonenumber/{idEmployee}")
    public ResponseEntity<?> updateInfoNumberPhoneEmployee(@PathVariable Long idEmployee,
            @RequestBody Long numberPhone) {
        if (employeeService.findEmployeeById(idEmployee).isPresent()) {
            employeeService.updateNumberPhone(idEmployee, numberPhone);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Actualización no valida. El empleado con el Id: " + idEmployee + " no existe en el sistema");
    }

    @PatchMapping("update/contractype/{idEmployee}")
    public ResponseEntity<?> updateInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        if (employeeService.findEmployeeById(idEmployee).isPresent()) {
            employeeService.updateContractType(idEmployee, contractType);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Actualización no valida. El empleado con el Id: " + idEmployee + " no existe en el sistema");
    }
}
