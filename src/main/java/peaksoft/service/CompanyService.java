package peaksoft.service;

import peaksoft.dto.company.CompanyRequest;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.entity.Company;


import java.util.List;

public interface CompanyService {

    List<CompanyResponse> getAllCompanies();

    CompanyResponse addCompany(CompanyRequest companyRequest);

    CompanyResponse getCompanyById(Long id);

    CompanyResponse updateCompany(Long id ,CompanyRequest companyRequest);

    CompanyResponse deleteCompany(Long id);
}
