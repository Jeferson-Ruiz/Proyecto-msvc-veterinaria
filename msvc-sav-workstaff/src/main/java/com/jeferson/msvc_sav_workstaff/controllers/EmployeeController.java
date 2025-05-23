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

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("id/{idEmployee}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long idEmployee) {
        Optional<EmployeeDto> optEmployee = employeeService.findById(idEmployee);

        return optEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("document/{documentNumber}")
    public ResponseEntity<EmployeeDto> getEmployeeByDocumentNumber(@PathVariable Long documentNumber) {
        return employeeService.findByDocumentNumber(documentNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("jobposition/{jobPosition}")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeeByJonPosition(@PathVariable JobPosition jobPosition) {
        List<EmployeeDto> employeesDto = employeeService.findAllByJobPosition(jobPosition);
        return ResponseEntity.ok(employeesDto);
    }

    @DeleteMapping("id/{idEmployee}")
    public ResponseEntity<?> deleteInfoEmployee(@PathVariable Long idEmployee) {
        if (employeeService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        employeeService.delete(idEmployee);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> updateInfoEmailEmployee(@PathVariable Long idEmployee, @RequestBody String email) {
        if (employeeService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        employeeService.updateEmail(idEmployee, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("update/phonenumber/{idEmployee}")
    public ResponseEntity<?> updateInfoNumberPhoneEmployee(@PathVariable Long idEmployee, @RequestBody Long numberPhone) {
        if (employeeService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        employeeService.updateNumberPhone(idEmployee, numberPhone);
        return  ResponseEntity.noContent().build();
    }

    @PatchMapping("update/contractype/{idEmployee}")
    public ResponseEntity<?> updateInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        if (employeeService.findById(idEmployee).isPresent()) {
            employeeService.updateContractType(idEmployee, contractType);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Actualización no valida. El empleado con el Id: " + idEmployee + " no existe en el sistema");
    }
}
