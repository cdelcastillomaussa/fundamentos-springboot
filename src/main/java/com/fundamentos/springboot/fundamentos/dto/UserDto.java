package com.fundamentos.springboot.fundamentos.dto;

import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String name;
    private LocalDate birthDay;

    public UserDto(Long id, String name, LocalDate birthDay) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getbirthDay() {
        return birthDay;
    }

    public void setbirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDay='" + birthDay + '\'' +
                '}';
    }
}
