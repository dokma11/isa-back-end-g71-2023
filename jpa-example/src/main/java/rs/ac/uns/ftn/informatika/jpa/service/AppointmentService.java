package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.repository.AppointmentRepository;

import java.util.List;

public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment findOne(Integer id) {
        return appointmentRepository.findById(id).orElseGet(null);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Page<Appointment> findAll(Pageable page) {
        return appointmentRepository.findAll(page);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void remove(Integer id) {
        appointmentRepository.deleteById(id);
    }

}
