package com.mybestsoft.tweakphoto.repository;

import com.mybestsoft.tweakphoto.domain.PersistentToken;
import com.mybestsoft.tweakphoto.domain.User;
import java.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends MongoRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
