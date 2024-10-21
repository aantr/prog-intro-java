package org.example.markup;

import java.util.List;

public class Paragraph extends MarkupBase {
    public Paragraph(List<Element> elements) {
        super(elements, "", "para");
    }
}
