package com.mybestsoft.tweakphoto.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Foo entity.
 */
public class FooDTO implements Serializable {

    private String id;

    private String name;


    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FooDTO fooDTO = (FooDTO) o;

        if ( ! Objects.equals(id, fooDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FooDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
