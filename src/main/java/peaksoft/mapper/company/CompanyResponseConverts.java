package peaksoft.mapper.company;

import org.springframework.stereotype.Component;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.entity.Company;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyResponseConverts {

    public CompanyResponse viewCompany(Company company) {
        if (company == null) {
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();
        if (company.getId() != null) {
            companyResponse.setId(Long.valueOf(String.valueOf(company.getId())));
        }
        companyResponse.setId(company.getId());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setLocatedCountry(company.getLocatedCountry());
        companyResponse.setCount(company.getCount());
        return companyResponse;
    }

    public List<CompanyResponse> view(List<Company> companies) {
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        for (Company company : companies) {
            companyResponseList.add(viewCompany(company));
        }
        return companyResponseList;
    }
}
