package com.eriol.service;

import com.eriol.entity.Appointment;

public interface AppointmentService {

    Appointment getByKeyWithBook(long bookId, long studentId);

}
