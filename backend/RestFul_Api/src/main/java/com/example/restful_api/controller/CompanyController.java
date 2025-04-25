package com.example.restful_api.controller;

import com.example.restful_api.dtos.global.MetaPagination;
import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.request.CompanyRequest;
import com.example.restful_api.entity.Company;
import com.example.restful_api.service.ICompanyService;
import com.example.restful_api.utils.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @ApiMessage("Create new an Company")
    public ResponseEntity<?> handleSaveCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        var result = this.companyService.handleSave(companyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("")
    @ApiMessage("Updated an Company")
    public ResponseEntity<Company> handleUpdateCompanies(@Valid @RequestBody Company company) {
        var result = this.companyService.handleUpdateCompany(company);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Deleted Company By Id")
    public ResponseEntity<Object> handleUpdateCompanies(@PathVariable("id") Long id) {
        this.companyService.deleteCompanyById(id);
        return ResponseEntity.ok().body("Delete successfully!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Company>> handleGetAllCompanies() {
        var result = this.companyService.getAllCompanies();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping()
    @ApiMessage("Fetch all Company")
    public ResponseEntity<PaginationResponse> handleGetCompaniesPagination(@Filter Specification<Company> companySpecification, Pageable pageable) {
        PaginationResponse response = this.companyService.getCompanyPagination(companySpecification, pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
