package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.repository.AppointmentRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.CompanyRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

    public List<Appointment> findAllPredefinedAppointmentsForCompany(Integer companyId) {
        return appointmentRepository.findByCompany_IdAndType(companyId, Appointment.AppointmentType.PREDEFINED);
    }

    public List<Appointment> findBookedTimeSlotsForDay(Integer companyId, LocalDateTime date) {
        return appointmentRepository.findBookedTimeSlotsForDay(companyId, date);
    }
    public List<LocalDateTime> findFreeTimeSlots(Integer companyId, LocalDateTime date,LocalDateTime startTime, LocalDateTime endTime) {
        List<Appointment> appointments = findBookedTimeSlotsForDay(companyId,date);
        List<LocalDateTime> freeTimeSlots = new ArrayList<>();
        LocalDateTime currentTime = startTime;
        long numberOfAdmins = companyRepository.countAdminsByCompanyId(companyId);
        while (currentTime.isBefore(endTime)) {
            LocalDateTime endTimeSlot = currentTime.plusMinutes(30); // Trajanje slota je 30 minuta

            // Provera da li su svi admini zauzeti u ovom time slotu
            if (!areAllAdminsBooked(appointments, numberOfAdmins,currentTime, endTimeSlot)) {
                freeTimeSlots.add(currentTime);
            }

            currentTime = endTimeSlot; // Sledeći time slot
        }

        return freeTimeSlots;
    }

    private boolean areAllAdminsBooked(List<Appointment> appointments, long numberOfAdmins, LocalDateTime startTime, LocalDateTime endTime) {
        // Prolaz kroz sve termine i provera zauzetosti admina
        if (appointments.isEmpty()) {
            return false;
        }

        int bookedAdmins = 0;

        for (Appointment appointment : appointments) {
            LocalDateTime appointmentStartTime = appointment.getPickupTime();
            LocalDateTime appointmentEndTime = appointmentStartTime.plusMinutes(appointment.getDuration());

            // Ako se termin preklapa sa razmatranim vremenskim intervalom
            if (appointmentStartTime.isBefore(endTime) && appointmentEndTime.isAfter(startTime)) {
                // Ako postoji bar jedan admin koji nije zauzet, povećavamo brojač
                if (appointment.getAdministrator() != null) {
                    bookedAdmins++;
                }
            }
        }

        // Poređenje broja zauzetih admina sa ukupnim brojem admina u kompaniji
        return bookedAdmins == numberOfAdmins;
    }

    public List<Integer> findAdminIdsForAppointmentsAtPickupTime(LocalDateTime dateTime,Integer companyId){
        return appointmentRepository.findAdminIdsForAppointmentsAtPickupTime(dateTime,companyId);
    }



}
