package peaksoft.service;

import peaksoft.dto.company.CompanyRequest;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.entity.Company;


import java.io.IOException;
import java.util.List;

public interface CompanyService {

    List<CompanyResponse> getAllCompanies();

    CompanyResponse addCompany(CompanyRequest companyRequest) throws IOException;

    CompanyResponse getCompanyById(Long id);

    CompanyResponse updateCompany(Long id ,CompanyRequest companyRequest);

    CompanyResponse deleteCompany(Long id);
}
