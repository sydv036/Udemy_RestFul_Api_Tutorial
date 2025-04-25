package com.example.restful_api.dtos.request;

import com.example.restful_api.utils.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsersRequest {

    @NotBlank(message = "Please enter name")
    private String name;

    @NotBlank(message = "Please enter email")
    @Email(message = "Email isn't valid")
    private String email;

    @NotBlank(message = "Please enter email")
    private String password;

    private Integer age;

    private GenderEnum gender;

    private String address;


    public UsersRequest() {
    }

    public UsersRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UsersRequest(String name, String email, String password, Integer age, GenderEnum gender, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    public @NotBlank(message = "Please enter name") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Please enter name") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Please enter email") @Email(message = "Email isn't valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Please enter email") @Email(message = "Email isn't valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Please enter email") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Please enter email") String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
