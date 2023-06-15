package com.dental.service;

import com.dental.entity.Appointment;
import com.dental.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Appointment get(int id) {
        return appointmentRepository.findById(id).get();
    }

//    public Page<Appointment> getAllByBlogId(int blogId, Pageable pageable) {
//        return appointmentRepository.findAllByBlogBlogId(blogId, pageable);
//    }

    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void delete(int id) {
        appointmentRepository.deleteById(id);
    }

    public void updateAppointmentStatus(String status, int id) {
        appointmentRepository.updateAppointmentStatus(status, id);
    }

    public Page<Appointment> findAllByOrderByDateDesc(Pageable pageable) {
        return appointmentRepository.findAllByOrderByDateDesc(pageable);
    }

    public Page<Appointment> findAllByDate(Date date, Pageable pageable) {
        return appointmentRepository.findAllByDate(date, pageable);
    }

    public Page<Appointment> findAllByStatus(String status, Pageable pageable) {
        return appointmentRepository.findAllByStatus(status, pageable);
    }

    public Page<Appointment> findAllByStatusAndDate(String status, Date date, Pageable pageable) {
        return appointmentRepository.findAllByStatusAndDate(status, date, pageable);
    }
}
