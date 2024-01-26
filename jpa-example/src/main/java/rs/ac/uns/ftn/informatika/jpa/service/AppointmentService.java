package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.repository.AppointmentRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.CompanyRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.RegisteredUserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

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
        //if appointment has user, it is already reserved
        return appointmentRepository.findByCompany_IdAndTypeAndUserIsNull(companyId, Appointment.AppointmentType.PREDEFINED);
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

        HashMap<Integer, Integer> bookedAdmins = new HashMap<>();

        for (Appointment appointment : appointments) {
            LocalDateTime appointmentStartTime = appointment.getPickupTime();
            LocalDateTime appointmentEndTime = appointmentStartTime.plusMinutes(appointment.getDuration());

            // Ako se termin preklapa sa razmatranim vremenskim intervalom
            if ((appointmentStartTime.isBefore(startTime) && appointmentEndTime.isAfter(startTime) && appointmentEndTime.isBefore(endTime)) ||
                (appointmentStartTime.isAfter(startTime) && appointmentStartTime.isBefore(endTime) && appointmentEndTime.isAfter(endTime)) ||
                (appointmentStartTime.equals(startTime) && appointmentEndTime.equals(endTime))) {

                // Ako postoji bar jedan admin koji je zauzet, povećavamo brojač
                if (appointment.getAdministrator() != null) {
                    if(!bookedAdmins.containsKey(appointment.getAdministrator().getId())){
                        bookedAdmins.put(appointment.getAdministrator().getId(), 1);
                    }
                    else{
                        Integer currentCount = bookedAdmins.getOrDefault(appointment.getAdministrator().getId(), 0);
                        currentCount += 1;
                        bookedAdmins.put(appointment.getAdministrator().getId(), currentCount);
                    }
                }
            }
        }

        // Poređenje broja zauzetih admina sa ukupnim brojem admina u kompaniji
        return bookedAdmins.size() == numberOfAdmins;
    }

    public List<Integer> findAdminIdsForAppointmentsAtPickupTime(LocalDateTime dateTime,Integer companyId){
        return appointmentRepository.findAdminIdsForAppointmentsAtPickupTime(dateTime,companyId);
    }

    public Appointment schedulePredefinedAppointment(Integer userId, Integer appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseGet(null);
        RegisteredUser user = registeredUserRepository.findById(userId).orElseGet(null);
        // nema rezervisanja ako - ne postoje user ili appointment, isto tako nema rez ako je vec neko rezervisao
        if(appointment == null || user == null || appointment.getUser() != null)
            return null;

        appointment.setUser(user);
        //save changes
        appointment = appointmentRepository.save(appointment);
        return appointment;

    }

    public List<Appointment> getUsersAppointments(Integer userId){
        return appointmentRepository.findAllByUser_Id(userId);
    }

    public List<Appointment> getUsersFutureAppointments(Integer userId){
        List<Appointment.AppointmentStatus> allowedStatuses = Arrays.asList(Appointment.AppointmentStatus.ON_HOLD, Appointment.AppointmentStatus.IN_PROGRESS);
        return appointmentRepository.findUsersFutureAppointments(userId,allowedStatuses);
    }
    public void SendPickUpInformationEmail(){

    }

    public Appointment cancelApppointment (Appointment appointment){
        if(appointment.getType() == Appointment.AppointmentType.EXCEPTIONAL){
            //ako je exceptional onda cemo ga postaviti na cancelled
            appointment.setStatus(Appointment.AppointmentStatus.CANCELED);
        }else{
            //postavi na cancel ali napravi novi sa istim parametrima!
            //pravljenje novog
            Appointment freedAppointment = new Appointment(appointment.getAdministrator(), appointment.getPickupTime(), appointment.getDuration(), null, appointment.getCompany(), appointment.getStatus(), appointment.getType());
            save(freedAppointment);
            //ovaj je otkazan
            appointment.setStatus(Appointment.AppointmentStatus.CANCELED);
        }
        //cuvanje
        return save(appointment);

    }



}
