package org.example.markup;

import java.util.List;

public class Strong extends MarkupBase {
    public Strong(List<Element> elements) {
        super(elements, "__", "emphasis", "bold");
    }
}
