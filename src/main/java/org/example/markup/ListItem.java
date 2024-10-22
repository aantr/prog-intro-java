package markup;

import java.util.List;

public class ListItem implements Element {
    private final List<ListItemBase> elements;

    public ListItem(List<ListItemBase> elements) {
        this.elements = elements;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        // todo
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        stringBuilder.append("<listitem>");
        for (Element element : elements) {
            element.toDocBook(stringBuilder);
        }
        stringBuilder.append("</listitem>");
    }
}
