package com.dental.repository;

import com.dental.entity.Appointment;
import com.dental.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Page<Appointment> findAllByOrderByDateDesc(Pageable pageable);

    Page<Appointment> findAllByDate(Date date, Pageable pageable);

    Page<Appointment> findAllByStatus(String status, Pageable pageable);

    Page<Appointment> findAllByStatusAndDate(String status, Date date, Pageable pageable);

    Page<Appointment> findAllByPatientPatientIdOrderByDateDesc(int patientId, Pageable pageable);

    Page<Appointment> findAllByPatientPatientIdAndDate(int patientId, Date date, Pageable pageable);

    Page<Appointment> findAllByPatientPatientIdAndStatus(int patientId, String status, Pageable pageable);

    Page<Appointment> findAllByPatientPatientIdAndStatusAndDate(int patientId, String status, Date date, Pageable pageable);

    Page<Appointment> findAllByDoctorDoctorIdOrderByDateDesc(int patientId, Pageable pageable);

    Page<Appointment> findAllByDoctorDoctorIdAndDate(int patientId, Date date, Pageable pageable);

    Page<Appointment> findAllByDoctorDoctorIdAndStatus(int patientId, String status, Pageable pageable);

    Page<Appointment> findAllByDoctorDoctorIdAndStatusAndDate(int patientId, String status, Date date, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE appointment p SET p.status = ?1, p.doctor_id = null WHERE p.appointment_id = ?2", nativeQuery = true)
    void updateAppointmentStatus(String status, int userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE `clinic_dental`.`appointment` SET `doctor_id` = ? WHERE (`appointment_id` = ?)", nativeQuery = true)
    void updateAppointmentDoctor(int doctorId, int appointmentId);

    int countAppointmentsByDate(Date date);

    int countAppointmentsByDateAndTime(Date date, String time);
}
