package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.dto.company.CompanyRequest;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.entity.Company;
import peaksoft.mapper.company.CompanyRequestConverts;
import peaksoft.mapper.company.CompanyResponseConverts;
import peaksoft.repository.CompanyRepository;
import peaksoft.service.CompanyService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final CompanyRequestConverts companyRequestConverts;
    private final CompanyResponseConverts companyResponseConverts;

    @Override
    public List<CompanyResponse> getAllCompanies() {
        return companyResponseConverts.view(repository.findAll());
    }

    @Override
    public CompanyResponse addCompany(CompanyRequest companyRequest) throws IOException {
        validator(companyRequest.getCompanyName(),companyRequest.getLocatedCountry());
        Company company = companyRequestConverts.createCompany(companyRequest);
        repository.save(company);
        return companyResponseConverts.viewCompany(company);
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        Company company = repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Компания с такой id "+id + " не существует"));
        return companyResponseConverts.viewCompany(company);
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Компания с такой id "+id + " не существует"));
        companyRequestConverts.updateCompany(company, companyRequest);
        repository.save(company);
        return companyResponseConverts.viewCompany(company);
    }

    @Override
    public CompanyResponse deleteCompany(Long id) {
        Company company = repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Компания с такой id "+id + " не существует"));
        repository.delete(company);
        return companyResponseConverts.viewCompany(company);
    }

    private void validator(String companyName, String locatedCountry) throws IOException {
        if (companyName.length() > 2 && locatedCountry.length() > 2) {
            for (Character i : companyName.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В названи компани нельзя вставлять цифры");
                }
            }
            for (Character i : locatedCountry.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("В названии страны нельзя вставлять цифры");
                }
            }
        } else {
            throw new IOException("В названии компании или или страны должно должно быть как минимум 2 буквы");
        }
    }
}
