package com.mybestsoft.tweakphoto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserSettings.
 */

@Document(collection = "user_settings")
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("login")
    private String login;

    @Field("misc")
    private String misc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserSettings userSettings = (UserSettings) o;
        if(userSettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userSettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserSettings{" +
            "id=" + id +
            ", login='" + login + "'" +
            ", misc='" + misc + "'" +
            '}';
    }
}
