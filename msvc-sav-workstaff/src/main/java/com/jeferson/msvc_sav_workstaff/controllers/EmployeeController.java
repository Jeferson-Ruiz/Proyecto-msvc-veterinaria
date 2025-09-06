package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import com.jeferson.msvc_sav_workstaff.services.EmployeeService;

@RestController
@RequestMapping("api/sav/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("disabled")
    public ResponseEntity<List<EmployeeResponseDto>> getAllDisabled(){
        return ResponseEntity.ok(employeeService.findAllDisabled());
    }

    @GetMapping("id/{idEmployee}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long idEmployee) {
        EmployeeResponseDto employeeDto = employeeService.findById(idEmployee);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("document/{documentNumber}")
    public ResponseEntity<?> getEmployeeByDocumentNumber(@PathVariable String documentNumber) {
        EmployeeResponseDto employeeDto = employeeService.findByDocumentNumber(documentNumber);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("all/{workArea}")
    public ResponseEntity<?> getAllEmployeesByArea(@PathVariable WorkArea workArea) {
        List<EmployeeResponseDto> employeesDto = employeeService.getEmployeesByType(workArea);
        return ResponseEntity.ok(employeesDto);
    }

    @DeleteMapping("id/{idEmployee}")
    public ResponseEntity<?> deleteInfoEmployee(@PathVariable Long idEmployee) {
        employeeService.delete(idEmployee);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-email/{idEmployee}")
    public ResponseEntity<?> updateInfoEmailEmployee(@PathVariable Long idEmployee, @RequestBody String email) {
        employeeService.updateEmail(idEmployee, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("update-phonenumber/{idEmployee}")
    public ResponseEntity<?> updateInfoNumberPhoneEmployee(@PathVariable Long idEmployee,
            @RequestBody String numberPhone) {
        employeeService.updateNumberPhone(idEmployee, numberPhone);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("update-contractype/{idEmployee}")
    public ResponseEntity<?> updateInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        employeeService.updateContractType(idEmployee, contractType);
        return ResponseEntity.noContent().build();
    }
}
