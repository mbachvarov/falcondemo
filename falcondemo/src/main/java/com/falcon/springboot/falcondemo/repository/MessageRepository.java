package com.falcon.springboot.falcondemo.repository;

import com.falcon.springboot.falcondemo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository used to interact with messages table
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}