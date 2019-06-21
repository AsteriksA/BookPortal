package com.gold.model.tester;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

//@Entity
//@Getter
//@Setter
public class Contragent {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "company_contragents",
            joinColumns = @JoinColumn(name = "contrag_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Company company;

    @Enumerated(EnumType.STRING)
    private RoleCompany role;
}
