package GUI;

import javax.swing.*;

/**
 * The Editable interface provides a contract for any class that wants to implement
 * input panel initialization. This can be used to standardize the setup of input components
 * in various GUIs, allowing for easy customization and consistent behavior across different frames.
 */
public interface Editable {
    
    /**
     * Initializes components for an input panel, setting up fields and other input elements 
     * that will allow users to input data. Implementing classes should define the specific 
     * components to be added to the panel, such as text fields, labels, and buttons.
     *
     * @param inputPanel The JPanel that will contain input fields and components
     */
    void initializeInputPanelComponents(JPanel inputPanel); 
}
