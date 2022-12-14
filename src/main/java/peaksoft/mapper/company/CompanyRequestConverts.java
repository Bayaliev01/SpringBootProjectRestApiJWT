package peaksoft.mapper.company;

import org.springframework.stereotype.Component;
import peaksoft.dto.company.CompanyRequest;
import peaksoft.entity.Company;

@Component
public class CompanyRequestConverts {
    public Company createCompany(CompanyRequest companyRequest) {
        if (companyRequest == null) {
            return null;
        }
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setLocatedCountry(companyRequest.getLocatedCountry());
        return company;
    }
    public void updateCompany(Company company , CompanyRequest companyRequest){
        company.setCompanyName(companyRequest.getCompanyName());
        company.setCompanyName(companyRequest.getCompanyName());
    }
}
