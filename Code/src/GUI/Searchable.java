package GUI;

import javax.swing.*;
import backend.QA;

/**
 * The Searchable interface defines a contract for classes that implement search functionality 
 * within a QA (Question and Answer) system. It provides a method to search for QA pairs 
 * based on a user ID and a specified keyword.
 */
public interface Searchable {

    /**
     * Searches for QA pairs that match the specified keyword for a given user.
     *
     * @param userid The ID of the user performing the search.
     * @param keyword The keyword to search for in the QA pairs.
     * @return An array of QA objects that match the search criteria. 
     *         If no matches are found, an empty array should be returned.
     */
    QA[] searchable(int userid, String keyword);
}

