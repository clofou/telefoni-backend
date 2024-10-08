package org.bamappli.telefonibackend.Repository;


import org.bamappli.telefonibackend.Entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepo extends JpaRepository<Photos, Long> {

}
