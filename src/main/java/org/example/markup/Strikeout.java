package org.example.markup;

import java.util.List;

public class Strikeout extends MarkupBase {
    public Strikeout(List<Element> elements) {
        super(elements, "~", "emphasis", "strikeout");
    }
}
