/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 * QA class represents a question and answer entry in the Q/A system.
 * It contains fields for the question, answer, the ID of the creator, and the creation time.
 * Provides methods to access and modify these fields.
 * 
 * @author 陈炯昊
 */
public class QA {
    private int id; // Unique identifier for the QA entry
    private final String question; // The question text
    private final String answer; // The answer text
    private int createdBy; // ID of the user who created this entry
    private String createdAt; // Timestamp of when this entry was created
    
    /**
     * Constructor to initialize a QA object with a question, answer, and creator's ID.
     *
     * @param question The question text
     * @param answer The answer text
     * @param createdBy The ID of the user who created this entry
     */
    public QA(String question, String answer, int createdBy) {
        this.question = question;
        this.answer = answer;
        this.createdBy = createdBy;
    }

    /**
     * Gets the unique identifier for this QA entry.
     *
     * @return The ID of the QA entry
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier for this QA entry.
     *
     * @param id The ID to set for this QA entry
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the question text.
     *
     * @return The question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the answer text.
     *
     * @return The answer text
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Gets the ID of the user who created this QA entry.
     *
     * @return The creator's ID
     */
    public int getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the timestamp of when this QA entry was created.
     *
     * @return The creation timestamp
     */
    public String getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Sets the timestamp of when this QA entry was created.
     *
     * @param createdAt The creation timestamp to set
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}
