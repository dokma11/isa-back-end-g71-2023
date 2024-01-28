package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.repository.*;

import javax.annotation.PostConstruct;
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

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentQuantityRepository equipmentQuantityRepository;

    @Autowired RegisteredUserService registeredUserService;

    @PostConstruct
    public void onStartup() {
       markAsExpiredOrDone();
    }
    public Appointment findOne(Integer id) {
        return appointmentRepository.findById(id).orElseGet(null);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Page<Appointment> findAll(Pageable page) {
        return appointmentRepository.findAll(page);
    }

    @Transactional(readOnly = false)
    public Appointment save(Appointment appointment) {

        return appointmentRepository.save(appointment);
    }

    public void remove(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findAllPredefinedAppointmentsForCompany(Integer companyId) {
        //if appointment has user, it is already reserved
        return appointmentRepository.findByCompany_IdAndTypeAndUserIsNullAndStatus(companyId, Appointment.AppointmentType.PREDEFINED, Appointment.AppointmentStatus.ON_HOLD);
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


    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED) //provjeriti ovo
    public List<Integer> findAdminIdsForAppointmentsAtPickupTime(LocalDateTime dateTime,Integer companyId){
        return appointmentRepository.findAdminIdsForAppointmentsAtPickupTime(dateTime,companyId);
    }

    @Transactional(readOnly = false)
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



    public List<Appointment> findDoneAppointments(Integer userId) {
        return appointmentRepository.findByStatusAndUser(Appointment.AppointmentStatus.DONE,userId);
    }



    public Appointment cancelApppointment (Appointment appointment){
        if(appointment.getStatus() == Appointment.AppointmentStatus.ON_HOLD){
            if(appointment.getType() == Appointment.AppointmentType.EXCEPTIONAL ){
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

        }
        updateEquipment(appointment);
        //cuvanje
        return save(appointment);

    }

    @Transactional(readOnly = false)
    public void updateEquipment(Appointment appointment){
        if(appointment.getStatus() == Appointment.AppointmentStatus.CANCELED || appointment.getStatus() == Appointment.AppointmentStatus.EXPIRED ){
            for (EquipmentQuantity eqq: equipmentQuantityRepository.findAllByAppointment_Id(appointment.getId())) {
                Equipment equipmentForUpdate = equipmentRepository.findById(eqq.getEquipmentId()).orElseGet(null);
                if(equipmentForUpdate != null){
                    equipmentForUpdate.setAvailableQuantity(equipmentForUpdate.getAvailableQuantity() + eqq.getQuantity());
                    equipmentRepository.save(equipmentForUpdate);
                }
            }
        }

        if(appointment.getStatus() == Appointment.AppointmentStatus.DONE){
            for (EquipmentQuantity eqq: equipmentQuantityRepository.findAllByAppointment_Id(appointment.getId())) {
                Equipment equipmentForUpdate = equipmentRepository.findById(eqq.getEquipmentId()).orElseGet(null);
                if(equipmentForUpdate != null){
                    equipmentForUpdate.setQuantity(equipmentForUpdate.getQuantity() - eqq.getQuantity());
                    equipmentRepository.save(equipmentForUpdate);
                }
            }
        }
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void markAsExpiredOrDone(){
        System.out.println("pokrenuo zakazanog");
        LocalDateTime timeNow = LocalDateTime.now();
        List<Appointment> allAppointments = appointmentRepository.findAll();
        int i = 0;
        for(; i < allAppointments.size(); i++){
            Appointment appointment = allAppointments.get(i);

            if(appointment.getStatus() == Appointment.AppointmentStatus.ON_HOLD){
                //i ako je proslo vrijeme
                if(appointment.getPickupTime().isBefore(timeNow)){
                    appointment.setStatus(Appointment.AppointmentStatus.EXPIRED);

                    if(appointment.getUser() != null){
                        appointment.getUser().setPoints(appointment.getUser().getPoints() + 2);
                        registeredUserService.create(appointment.getUser(),false);
                    }
                    updateEquipment(appointment);
                }
            }
            else if(appointment.getStatus() == Appointment.AppointmentStatus.IN_PROGRESS){
                LocalDateTime endOfPickup = appointment.getPickupTime().plusMinutes(30);
                if(endOfPickup.isBefore(timeNow)){
                    appointment.setStatus(Appointment.AppointmentStatus.DONE);

                    updateEquipment(appointment);
                }
            }

            save(appointment);
        }

        System.out.println("pokrenuo zakazanog");



    }

}
