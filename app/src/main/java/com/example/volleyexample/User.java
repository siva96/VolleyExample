package com.example.volleyexample;

import java.io.Serializable;
import java.util.Comparator;

public class User implements Serializable {

    // instance variables
    int userId;
    String name;
    String email;
    String password;
    String phoneNum;

    // getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
          this.name = name;
    }

    public static Comparator<User> UserIdComparator = new Comparator<User>() {
        @Override
        public int compare(User user, User t1) {
            return (t1.userId - user.userId);
        }
    };
}
