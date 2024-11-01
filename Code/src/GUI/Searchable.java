package GUI;

import javax.swing.*;
import backend.QA;

public interface Searchable {
        QA[] searchable(int userid, String keyword);
            
}
