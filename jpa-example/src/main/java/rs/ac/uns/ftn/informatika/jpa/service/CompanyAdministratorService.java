package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.repository.CompanyAdministratorRepository;

@Service
public class CompanyAdministratorService {

    @Autowired
    CompanyAdministratorRepository companyAdministratorRepository;
}
