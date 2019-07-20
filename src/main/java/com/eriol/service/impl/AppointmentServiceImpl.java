package com.eriol.service.impl;

import com.eriol.dao.AppointmentDao;
import com.eriol.entity.Appointment;
import com.eriol.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public Appointment getByKeyWithBook(long bookId, long studentId) {
        return appointmentDao.queryByKeyWithBook(bookId, studentId);
    }
}
