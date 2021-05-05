package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Parent;
import com.example.finalProject.entity.TemporaryUser;
import org.springframework.data.repository.CrudRepository;

public interface ParentRepository  extends CrudRepository<Parent, Integer> {
    Parent findByParentEmailAndParentPassword(String parentEmail,String parentPassword);
    Parent findByUserNameAndParentPassword(String username,String parentPassword);
    Parent findByParentEmail(String parentEmail);
    Parent findByUserName(String username);

}
