package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company findOne(Integer id) {
        return companyRepository.findById(id).orElseGet(null);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Page<Company> findAll(Pageable page) {
        return companyRepository.findAll(page);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public void remove(Integer id) {
        companyRepository.deleteById(id);
    }

    public List<Company> search(String name, String city){
        return companyRepository.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(name,city);
    }
    public Company findOneWithAdministrators(Integer id) {
        return companyRepository.findOneWithAdministrators(id);
    }
    public Company findOneWithAppointments(Integer id) {
        return companyRepository.findOneWithAppointments(id);
    }
    public Company findOneWithEquipment(Integer id) {
        return companyRepository.findOneWithEquipment(id);
    }
}
