package com.springapp.mvc.entity;

import javax.persistence.*;

/**
 * Created by aashish on 3/6/15.
 */
@Entity
@Table(name = "login_detail")
public class Login {

    private int    id;
    private String username;
    private String password;
    // private RoleType roleType;
    private String roleType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role_type")
    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "Login [id=" + id + ", username=" + username + ", password=" + password + ", roleType=" + roleType + "]";
    }

}
