package com.jeferson.msvc.workstaff.controllers;

import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc.workstaff.dto.EmailRequestDto;
import com.jeferson.msvc.workstaff.dto.EmployeeRequestDto;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.dto.PhoneNumberRequestDto;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;
import com.jeferson.msvc.workstaff.services.EmployeeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody @Valid EmployeeRequestDto employeeDto) {
        EmployeeResponseDto employee = employeeService.save(employeeDto);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployee() {
        List<EmployeeResponseDto> employess = employeeService.findAllEmployee();
        return ResponseEntity.ok(employess);
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<?> getEmployeeByDocumentNumber(@PathVariable String document) {
        EmployeeResponseDto employeeDto = employeeService.findByDocumentNumber(document);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getEmployeeByCode(@PathVariable String code) {
        EmployeeResponseDto employeeDto = employeeService.findByEmployeeCode(code);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeResponseDto>> filterEmployees(
            @RequestParam(required = false) WorkArea workArea,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) EmployeeStatus status) {
        List<EmployeeResponseDto> employees = employeeService.findAllByAreaRolAndStatus(workArea, role, status);
        return ResponseEntity.ok(employees);
    }

    @PatchMapping("/update-email/{code}")
    public ResponseEntity<?> updateEmail(@PathVariable String code, @RequestBody EmailRequestDto request) {
        employeeService.updateEmail(code, request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-phone/{code}")
    public ResponseEntity<?> updatePhone(@PathVariable String code, @RequestBody PhoneNumberRequestDto request) {
        employeeService.updatePhone(code, request.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-role/{code}")
    public ResponseEntity<?> updateRole(@PathVariable String code, @RequestBody Set<String> role) {
        employeeService.updateRole(code, role);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-contract/{code}")
    public ResponseEntity<?> updateContract(@PathVariable String code, @RequestBody ContractType contract) {
        employeeService.updateContractType(code, contract);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-status/{code}")
    public ResponseEntity<?> updateStatus(@PathVariable String code, @RequestBody EmployeeStatus status) {
        employeeService.updateEmployeeStatus(code, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteInfoEmployee(@PathVariable String code, @RequestBody String deleteBy,
            String reason) {
        employeeService.delete(code, deleteBy, reason);
        return ResponseEntity.noContent().build();
    }
}
