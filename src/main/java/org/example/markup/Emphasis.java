package org.example.markup;

import java.util.List;

public class Emphasis extends MarkupBase {
    public Emphasis(List<Element> elements) {
        super(elements, "*", "emphasis");
    }
}
