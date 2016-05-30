package com.mybestsoft.tweakphoto.repository;

import com.mybestsoft.tweakphoto.domain.UserSettings;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the UserSettings entity.
 */
public interface UserSettingsRepository extends MongoRepository<UserSettings,String> {
    UserSettings findOneByLogin(String login);
}
