package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.company.CompanyRequest;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.entity.Company;
import peaksoft.mapper.company.CompanyRequestConverts;
import peaksoft.mapper.company.CompanyResponseConverts;
import peaksoft.repository.CompanyRepository;
import peaksoft.service.CompanyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final CompanyRequestConverts companyRequestConverts;
    private final CompanyResponseConverts companyResponseConverts;

    @Override
    public List<CompanyResponse>getAllCompanies() {
        return companyResponseConverts.view(repository.findAll());
    }

    @Override
    public CompanyResponse addCompany(CompanyRequest companyRequest) {
        Company company = companyRequestConverts.createCompany(companyRequest);
        repository.save(company);
        return companyResponseConverts.viewCompany(company);
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        Company company = repository.findById(id).get();
        return companyResponseConverts.viewCompany(company);
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = repository.findById(id).get();
        companyRequestConverts.updateCompany(company, companyRequest);
        repository.save(company);
        return companyResponseConverts.viewCompany(company);
    }

    @Override
    public CompanyResponse deleteCompany(Long id) {
        Company company = repository.findById(id).get();
        repository.delete(company);
        return companyResponseConverts.viewCompany(company);
    }
}
