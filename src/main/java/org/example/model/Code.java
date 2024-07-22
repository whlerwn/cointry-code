package org.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "code")
public class Code extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "number")
    private String number;

    public Code(Country country, String number) {
        this.country = country;
        this.number = number;
    }

    public Code() {

    }
}
