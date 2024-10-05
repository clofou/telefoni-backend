package org.bamappli.telefonidashboard.Repository;

import org.bamappli.telefonidashboard.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

}