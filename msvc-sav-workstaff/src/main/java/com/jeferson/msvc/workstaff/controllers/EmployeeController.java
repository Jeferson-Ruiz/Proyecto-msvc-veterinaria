package com.jeferson.msvc.workstaff.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc.workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.WorkArea;
import com.jeferson.msvc.workstaff.services.EmployeeService;
import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployee(@PathVariable EmployeeStatus status) {
        return ResponseEntity.ok(employeeService.findAllByStatus(status));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getEmployeeByCode(@PathVariable String code){
        EmployeeResponseDto employeeDto = employeeService.findByEmployeeCode(code);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("document/{documentNumber}")
    public ResponseEntity<?> getEmployeeByDocumentNumber(@PathVariable String documentNumber) {
        EmployeeResponseDto employeeDto = employeeService.findByDocumentNumber(documentNumber);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("all/{workArea}/status/{status}")
    public ResponseEntity<?> getAllEmployeesByArea(@PathVariable WorkArea workArea, @PathVariable EmployeeStatus status) {
        List<EmployeeResponseDto> employeesDto = employeeService.getEmployeesByType(workArea, status);
        return ResponseEntity.ok(employeesDto);
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<?> deleteInfoEmployee(@PathVariable String employeeCode, @RequestBody String deleteBy, String reason) {
        employeeService.delete(employeeCode, deleteBy, reason);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/update-status/{employeeCode}")
    public ResponseEntity<?> updateStatus(@RequestParam String employeeCode, @Valid @RequestBody EmployeeStatus status ) {
        employeeService.updateEmployeeStatus(employeeCode, status);
        return ResponseEntity.noContent().build();
    }
}
