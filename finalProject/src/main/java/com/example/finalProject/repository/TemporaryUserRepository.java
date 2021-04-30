package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.TemporaryUser;
import org.springframework.data.repository.CrudRepository;

public interface TemporaryUserRepository extends CrudRepository<TemporaryUser, Integer> {
    TemporaryUser findByUserName(String username);
    TemporaryUser findByParentEmail(String parentEmail);
    TemporaryUser findByParentEmailAndSerialNumber(String parentEmail,String serialNumber);

}
