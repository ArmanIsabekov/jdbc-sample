/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myexercises.jdbc.sample.data;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class Film {
    private Integer id;
    private Integer directorId;
    private String name;
    private Date releaseDate;

    public Film() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.directorId != null ? this.directorId.hashCode() : 0);
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.releaseDate != null ? this.releaseDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Film other = (Film) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.directorId != other.directorId && (this.directorId == null || !this.directorId.equals(other.directorId))) {
            return false;
        }
        if (this.releaseDate != other.releaseDate && (this.releaseDate == null || !this.releaseDate.equals(other.releaseDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Film{" + "id=" + id + ", directorId=" + directorId + ", name=" + name + ", releaseDate=" + releaseDate + '}';
    }
}
