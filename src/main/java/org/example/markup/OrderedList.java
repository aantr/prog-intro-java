package org.example.markup;

import java.util.List;

public class OrderedList extends ListBase {
    public OrderedList(List<ListItem> elements) {
        super("orderedlist", elements);
    }
}
