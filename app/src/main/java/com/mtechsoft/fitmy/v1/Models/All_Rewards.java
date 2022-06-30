package com.mtechsoft.fitmy.v1.Models;

public class All_Rewards {



    int id;
    String image;
    String name;
    String description;
    String term_and_condition;
    String required_fitness_points;

    public String getRequired_fitness_points() {
        return required_fitness_points;
    }

    public void setRequired_fitness_points(String required_fitness_points) {
        this.required_fitness_points = required_fitness_points;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTerm_and_condition() {
        return term_and_condition;
    }

    public void setTerm_and_condition(String term_and_condition) {
        this.term_and_condition = term_and_condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
