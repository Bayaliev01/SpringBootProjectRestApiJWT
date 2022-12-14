package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.company.CompanyRequest;
import peaksoft.dto.company.CompanyResponse;
import peaksoft.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyApi {
    private final CompanyService companyService;


    @PostMapping
    public CompanyResponse createCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.addCompany(companyRequest);
    }


    @PutMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable Long companyId, @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompany(companyId, companyRequest);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @DeleteMapping("/{companyId}")
    public CompanyResponse deleteCompany(@PathVariable Long companyId) {
        return companyService.deleteCompany(companyId);
    }

    @GetMapping
    public List<CompanyResponse> findAllCompany() {
        return companyService.getAllCompanies();
    }
}
