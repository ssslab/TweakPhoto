package com.mybestsoft.tweakphoto.service;

import com.mybestsoft.tweakphoto.domain.Foo;
import com.mybestsoft.tweakphoto.web.rest.dto.FooDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Foo.
 */
public interface FooService {

    /**
     * Save a foo.
     * 
     * @param fooDTO the entity to save
     * @return the persisted entity
     */
    FooDTO save(FooDTO fooDTO);

    /**
     *  Get all the foos.
     *  
     *  @return the list of entities
     */
    List<FooDTO> findAll();

    /**
     *  Get the "id" foo.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    FooDTO findOne(String id);

    /**
     *  Delete the "id" foo.
     *  
     *  @param id the id of the entity
     */
    void delete(String id);
}
