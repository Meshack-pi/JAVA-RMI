package cat_2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author harshvirsinghahuja
 */
import java.io.Serializable;

public class Student implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String course;
    private int score;
    private String email;
    
    
    public Student(int id, String name, String course, int score, String email){
        this.id = id;
        this.name = name;
        this.course = course;
        this.score = score;
        this.email = email;
    }
    
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getCourse(){
        return course;
    }
    
    public int getScore(){
        return score;
    }
    
    public String getEmail(){
        return email;
    }
         
    
    
}