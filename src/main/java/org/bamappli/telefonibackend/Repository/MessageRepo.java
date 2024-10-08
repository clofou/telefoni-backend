package org.bamappli.telefonibackend.Repository;

import org.bamappli.telefonibackend.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

}