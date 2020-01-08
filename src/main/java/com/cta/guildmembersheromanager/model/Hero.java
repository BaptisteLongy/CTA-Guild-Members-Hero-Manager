package com.cta.guildmembersheromanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Hero {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private HeroDefinition definition;

    @OneToOne
    private User owner;

    private int Stars;

    private int Awakenings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HeroDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(HeroDefinition definition) {
        this.definition = definition;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getStars() {
        return Stars;
    }

    public void setStars(int stars) {
        Stars = stars;
    }

    public int getAwakenings() {
        return Awakenings;
    }

    public void setAwakenings(int awakenings) {
        Awakenings = awakenings;
    }

    public Hero() {
    }

}
