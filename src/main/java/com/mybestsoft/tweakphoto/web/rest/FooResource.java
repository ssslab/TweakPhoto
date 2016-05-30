package com.mybestsoft.tweakphoto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mybestsoft.tweakphoto.domain.Foo;
import com.mybestsoft.tweakphoto.service.FooService;
import com.mybestsoft.tweakphoto.web.rest.util.HeaderUtil;
import com.mybestsoft.tweakphoto.web.rest.dto.FooDTO;
import com.mybestsoft.tweakphoto.web.rest.mapper.FooMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Foo.
 */
@RestController
@RequestMapping("/api")
public class FooResource {

    private final Logger log = LoggerFactory.getLogger(FooResource.class);
        
    @Inject
    private FooService fooService;
    
    @Inject
    private FooMapper fooMapper;
    
    /**
     * POST  /foos : Create a new foo.
     *
     * @param fooDTO the fooDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fooDTO, or with status 400 (Bad Request) if the foo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/foos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FooDTO> createFoo(@RequestBody FooDTO fooDTO) throws URISyntaxException {
        log.debug("REST request to save Foo : {}", fooDTO);
        if (fooDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("foo", "idexists", "A new foo cannot already have an ID")).body(null);
        }
        FooDTO result = fooService.save(fooDTO);
        return ResponseEntity.created(new URI("/api/foos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("foo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /foos : Updates an existing foo.
     *
     * @param fooDTO the fooDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fooDTO,
     * or with status 400 (Bad Request) if the fooDTO is not valid,
     * or with status 500 (Internal Server Error) if the fooDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/foos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FooDTO> updateFoo(@RequestBody FooDTO fooDTO) throws URISyntaxException {
        log.debug("REST request to update Foo : {}", fooDTO);
        if (fooDTO.getId() == null) {
            return createFoo(fooDTO);
        }
        FooDTO result = fooService.save(fooDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("foo", fooDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /foos : get all the foos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foos in body
     */
    @RequestMapping(value = "/foos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<FooDTO> getAllFoos() {
        log.debug("REST request to get all Foos");
        return fooService.findAll();
    }

    /**
     * GET  /foos/:id : get the "id" foo.
     *
     * @param id the id of the fooDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fooDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/foos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FooDTO> getFoo(@PathVariable String id) {
        log.debug("REST request to get Foo : {}", id);
        FooDTO fooDTO = fooService.findOne(id);
        return Optional.ofNullable(fooDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /foos/:id : delete the "id" foo.
     *
     * @param id the id of the fooDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/foos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFoo(@PathVariable String id) {
        log.debug("REST request to delete Foo : {}", id);
        fooService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("foo", id.toString())).build();
    }

}
