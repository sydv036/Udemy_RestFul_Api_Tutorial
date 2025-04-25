package com.example.restful_api.service.impl;

import com.example.restful_api.dtos.global.MetaPagination;
import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.request.CompanyRequest;
import com.example.restful_api.entity.Company;
import com.example.restful_api.repository.ICompanyRepository;
import com.example.restful_api.service.ICompanyService;
import com.example.restful_api.utils.ValueConstant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValueConstant valueConstant;

    @Autowired
    private ICompanyRepository companyRepository;

    /**
     * @param companyRequest
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    @Override
    public Company handleSave(CompanyRequest companyRequest) {
        if (Optional.ofNullable(companyRequest).isEmpty()) {
            throw new NullPointerException("Company not found");
        }
        Company company = modelMapper.map(companyRequest, Company.class);
        return companyRepository.save(company);
    }

    /**
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    @Override
    public List<Company> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }

    /**
     * @param id
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    @Override
    public Optional<Company> getCompanyById(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new EntityNotFoundException("Company not found");
        }
        return companyRepository.findById(id);
    }

    /**
     * @param company
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    @Override
    public Company handleUpdateCompany(Company company) {
        return getCompanyById(company.getId()).map(item -> {
            item.setLogo(company.getLogo());
            item.setAddress(company.getAddress());
            item.setDescription(company.getDescription());
            item.setName(company.getName());
            return companyRepository.saveAndFlush(item);
        }).orElseThrow(() -> new EntityNotFoundException("Company not found!"));

    }

    /**
     * @param id
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    @Override
    public void deleteCompanyById(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new IllegalArgumentException("Company not found");
        }
        companyRepository.deleteById(id);
    }

    /**
     * @param companySpecification
     * @param pageable
     * @author SyDV3
     * @birthday 2003_01_04
     * @date:
     */
    @Override
    public PaginationResponse getCompanyPagination(Specification<Company> companySpecification, Pageable pageable) {
        Page<Company> page = companyRepository.findAll(companySpecification, pageable);
        PaginationResponse response = new PaginationResponse();
        response.setMeta(new MetaPagination(page.getTotalElements(), pageable.getPageNumber() + 1, pageable.getPageSize(), page.getTotalPages()));
        response.setResult(page.getContent());
        return response;
    }


}
