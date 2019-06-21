package com.gold.model.tester;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

//@Entity
//@Getter
//@Setter
public class Company {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "companies")
    private Set<Contragent> contragents;

}


