package com.cta.guildmembersheromanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HeroDefinition {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String element;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public HeroDefinition() {
    }

    public HeroDefinition(String name, String element) {
        this.name = name;
        this.element = element;
    }
}
