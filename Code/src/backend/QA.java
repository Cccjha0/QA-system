/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author 陈炯昊
 */
public class QA {
    private int id;
    private String question;
    private String answer;
    private int createdBy;
    private String createdAt;
    
    public QA(int id, String question, String answer, int createdBy, String createdAt) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    
    
}
