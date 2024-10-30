package GUI;

import backend.QA;

public interface Searchable {
        QA[] queryable(int id, String keyword);
}
