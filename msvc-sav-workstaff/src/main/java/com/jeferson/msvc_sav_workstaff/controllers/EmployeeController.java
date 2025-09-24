package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.EmployeeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import com.jeferson.msvc_sav_workstaff.services.EmployeeService;

@RestController
@RequestMapping("api/sav/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployee(@PathVariable EmployeeStatus status) {
        return ResponseEntity.ok(employeeService.findAllByStatus(status));
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

    @GetMapping("all/{workArea}/status/{status}")
    public ResponseEntity<?> getAllEmployeesByArea(@PathVariable WorkArea workArea, @PathVariable EmployeeStatus status) {
        List<EmployeeResponseDto> employeesDto = employeeService.getEmployeesByType(workArea, status);
        return ResponseEntity.ok(employeesDto);
    }

    @DeleteMapping("id/{idEmployee}")
    public ResponseEntity<?> deleteInfoEmployee(@PathVariable Long idEmployee, @RequestBody String deleteBy, String reason) {
        employeeService.delete(idEmployee, deleteBy, reason);
        return ResponseEntity.noContent().build();
    }
}
