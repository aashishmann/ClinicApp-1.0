package com.springapp.mvc.dto;

public class LoginForm {

    private int    id;
    private String username;
    private String password;
    private String roleType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "LoginForm [id=" + id + ", username=" + username + ", password=" + password + ", roleType=" + roleType + "]";
    }

}
