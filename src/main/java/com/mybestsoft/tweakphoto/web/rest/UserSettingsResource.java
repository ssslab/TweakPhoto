package com.mybestsoft.tweakphoto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mybestsoft.tweakphoto.domain.UserSettings;
import com.mybestsoft.tweakphoto.repository.UserSettingsRepository;
import com.mybestsoft.tweakphoto.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserSettings.
 */
@RestController
@RequestMapping("/api")
public class UserSettingsResource {

    private final Logger log = LoggerFactory.getLogger(UserSettingsResource.class);

    @Inject
    private UserSettingsRepository userSettingsRepository;

    /**
     * POST  /user-settings : Create a new userSettings.
     *
     * @param userSettings the userSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userSettings, or with status 400 (Bad Request) if the userSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-settings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserSettings> createUserSettings(@RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to save UserSettings : {}", userSettings);
        if (userSettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userSettings", "idexists", "A new userSettings cannot already have an ID")).body(null);
        }
        if (userSettingsRepository.findOneByLogin(userSettings.getLogin()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userSettings", "idexists", "There is the user settings with the same login already")).body(null);
        }
        UserSettings result = userSettingsRepository.save(userSettings);
        return ResponseEntity.created(new URI("/api/user-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userSettings", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-settings : Updates an existing userSettings.
     *
     * @param userSettings the userSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userSettings,
     * or with status 400 (Bad Request) if the userSettings is not valid,
     * or with status 500 (Internal Server Error) if the userSettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-settings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserSettings> updateUserSettings(@RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to update UserSettings : {}", userSettings);
        if (userSettings.getId() == null) {
            return createUserSettings(userSettings);
        }
        UserSettings result = userSettingsRepository.save(userSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userSettings", userSettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-settings : get all the userSettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userSettings in body
     */
    @RequestMapping(value = "/user-settings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserSettings> getAllUserSettings() {
        log.debug("REST request to get all UserSettings");
        List<UserSettings> userSettings = userSettingsRepository.findAll();
        return userSettings;
    }

    /**
     * GET  /user-settings/:id : get the "id" userSettings.
     *
     * @param id the id of the userSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userSettings, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/user-settings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserSettings> getUserSettings(
        @PathVariable String id,
        @RequestParam(name = "login", required = false, defaultValue = "") String login) {
        log.debug("REST request to get UserSettings : {}", id);

        UserSettings userSettings;

        if(!login.isEmpty()){
            userSettings = userSettingsRepository.findOneByLogin(login);
        } else {
            userSettings = userSettingsRepository.findOne(id);
        }
        return Optional.ofNullable(userSettings)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-settings/:id : delete the "id" userSettings.
     *
     * @param id the id of the userSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/user-settings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserSettings(@PathVariable String id) {
        log.debug("REST request to delete UserSettings : {}", id);
        userSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userSettings", id.toString())).build();
    }

}
