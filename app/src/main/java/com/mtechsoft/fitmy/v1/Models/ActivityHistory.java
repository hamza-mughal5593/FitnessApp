package com.mtechsoft.fitmy.v1.Models;

public class ActivityHistory {


    String name;
    String distance;
    String steps;
    String calories;
    String time;
    String activity_type;
   // int activity_id;

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

//    public int getActivity_id() {
//        return activity_id;
//    }

//    public void setActivity_id(int activity_id) {
//        this.activity_id = activity_id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
