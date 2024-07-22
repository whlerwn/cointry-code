package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country extends BaseEntity{

    @Column(name = "name")
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public Country() {

    }
}
