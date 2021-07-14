package com.java8newfeature.optional;

/**
 * @decription: 用户
 * @author: 180449
 * @date 2021/7/14 11:10
 */
//@Data
public class User {

    private String address;

    private String email;

    private String mobile;

    private String age;

    public User(String email, String mobile) {
        this.email = email;
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
