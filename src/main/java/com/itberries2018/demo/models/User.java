package com.itberries2018.demo.models;


import java.util.Objects;

public class User {

    private long id;
    private Long score;
    private String username;
    private String email;
    private String password;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = "noavatar.png";
        this.score = 0L;
    }

    public User(long id, String username, String email, String password, String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.score = 0L;
    }

    public User(long id, String username, String email, String password, long score) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = "noavatar.png";
        this.score = score;
    }

    public User(long id, String username, String email, String password, String avatar, long score) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.score = score;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getName() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final User other = (User) obj;
        //noinspection RedundantIfStatement
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ",  username=" + username
                + ", email=" + email + ",  avatar=" + avatar + ']';
    }
}
