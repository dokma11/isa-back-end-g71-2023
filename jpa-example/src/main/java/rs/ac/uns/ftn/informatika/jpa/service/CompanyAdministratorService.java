package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.repository.CompanyAdministratorRepository;

import java.util.List;

@Service
public class CompanyAdministratorService {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    CompanyAdministratorRepository companyAdministratorRepository;

    public CompanyAdministrator findOne(Integer id) {
        return companyAdministratorRepository.findById(id).orElseGet(null);
    }

    public List<CompanyAdministrator> findAll() {
        return companyAdministratorRepository.findAll();
    }

    public Page<CompanyAdministrator> findAll(Pageable page) {
        return companyAdministratorRepository.findAll(page);
    }

    public CompanyAdministrator save(CompanyAdministrator companyAdministrator) {
        return companyAdministratorRepository.save(companyAdministrator);
    }

    public void remove(Integer id) {
        companyAdministratorRepository.deleteById(id);
    }

    public CompanyAdministrator findByEmail(String email) {
        return companyAdministratorRepository.findByUsername(email);
    }

    public void SetPassword(CompanyAdministrator administrator){
        administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
    }

}
