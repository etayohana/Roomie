package com.roomy.dbtest;

import android.graphics.Bitmap;

public class Profile {

    private String name;
    private String age;
    private String city;
    private String education;
    private String gender;
    private String aboutme;
    private String pic;




    public Profile(String name, String age, String city, String education, String gender, String aboutme,String pic) {
        this.name = name;
        this.age= age;
        this.city = city;
        this.education = education;
        this.gender = gender;
        this.aboutme= aboutme;
        this.pic = pic;
     }

    public Profile() {
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", age='"+ age+'\''+
                ", city='" + city + '\'' +
                ", education='" + education + '\'' +
                ", gender='" + gender + '\'' +
                ", aboutme='" + aboutme + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
