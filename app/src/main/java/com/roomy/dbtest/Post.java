package com.roomy.dbtest;

public class Post{
    private String Pofile_name;
    private String location;
    private String description;
    private String deposit;
    private String date;
    private String romates;
    private String rooms;
    private String floor;
    private String phone_number;
    private String name_name;

    private int imagProfile;
    private int imagApaetmet;

    public Post(String date, String deposit, String location,String description, String romates, String rooms,String floor,String phone_number, String name) {
        this.date = date;
        this.deposit = deposit;
        this.location = location;
        this.description=description;
        this.floor=floor;
        this.romates=romates;
        this.rooms=rooms;
        this.phone_number=phone_number;
        this.name_name = name;

    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "date='" + date + '\'' +
                ", deposit='" + deposit + '\'' +
                ", location='" + location + '\'' +
                ",romates='" + romates + '\'' +
                ", rooms='" + rooms + '\'' +
                ", floor='" + floor + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPofile_name() {
        return Pofile_name;
    }

    public void setPofile_name(String pofile_name) {
        Pofile_name = pofile_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRomates() {
        return romates;
    }

    public void setRomates(String romates) {
        this.romates = romates;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getImagProfile() {
        return imagProfile;
    }

    public void setImagProfile(int imagProfile) {
        this.imagProfile = imagProfile;
    }

    public int getImagApaetmet() {
        return imagApaetmet;
    }

    public void setImagApaetmet(int imagApaetmet) {
        this.imagApaetmet = imagApaetmet;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getName_name() {
        return name_name;
    }

    public void setName_name(String name_name) {
        this.name_name = name_name;
    }
}
