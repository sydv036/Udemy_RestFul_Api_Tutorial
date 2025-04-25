package com.example.restful_api.service;

import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.request.CompanyRequest;
import com.example.restful_api.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    Company handleSave(CompanyRequest companyRequest);

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    List<Company> getAllCompanies();

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    Optional<Company> getCompanyById(Long id);

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    Company handleUpdateCompany(Company company);

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    void deleteCompanyById(Long id);

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    PaginationResponse getCompanyPagination(Specification<Company> companySpecification, Pageable pageable);

}
