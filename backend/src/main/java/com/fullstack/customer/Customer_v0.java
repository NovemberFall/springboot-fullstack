package com.fullstack.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;

//@Entity
//@Table(
//        name = "customer",
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "customer_email_unique",
//                        columnNames = "email"
//                )
//        }
//)
//public class Customer_v0 {
//
//    @Id
//    @SequenceGenerator( // a sequence is just an auto increment number
//            name="customer_id_seq",
//            sequenceName = "customer_id_seq",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "customer_id_seq"
//    )
//    private Integer id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private Integer age;
//
//    @Column(
//            nullable = false
//    )
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//
//    public Customer_v0() {
//    }
//
//    public Customer_v0(String name, String email, Integer age, Gender gender) {
//        this.name = name;
//        this.email = email;
//        this.age = age;
//        this.gender = gender;
//    }
//
//    public Customer_v0(Integer id, String name, String email, Integer age, Gender gender) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.age = age;
//        this.gender = gender;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Customer_v0 customer = (Customer_v0) o;
//        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age) && gender == customer.gender;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, email, age, gender);
//    }
//
//    @Override
//    public String toString() {
//        return "Customer{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", age=" + age +
//                ", gender=" + gender +
//                '}';
//    }
//}
