/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author 陈炯昊
 */
public class User {

    private int id;
    private String name;
    private String password;
    private boolean isStudent;

    public User(String name, String password, boolean isStudent) {
        this.name = name;
        this.password = password;
        this.isStudent = isStudent;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isStudent() {
        return isStudent;
    }
}
