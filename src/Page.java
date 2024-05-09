import java.util.ArrayList;
import java.util.List;

public class Page {
    private static final int MAX_LINES = 25;
    private static final int MAX_CHARS_PER_LINE = 80;
    private final List<String> lines;
    private final int pageNumber;

    public Page(int pageNumber) {
        this.lines = new ArrayList<>();
        this.pageNumber = pageNumber;
    }

    // Checks if the page is full after adding a word
    public boolean isFull(String word) {
        return lines.size() >= MAX_LINES && lines.get(lines.size() - 1).length() + 1 + word.length() > MAX_CHARS_PER_LINE;
    }

    // Checks if the page is empty
    public boolean isEmpty() {
        return lines.isEmpty();
    }

    // Adds a word to the current line or creates a new line if necessary
    public void addWord(String word) {
        if (lines.isEmpty() || lines.get(lines.size() - 1).length() + 1 + word.length() > MAX_CHARS_PER_LINE) {
            lines.add(word);
        } else {
            lines.set(lines.size() - 1, lines.get(lines.size() - 1) + " " + word);
        }
    }

    // Returns the lines of the page
    public List<String> getLines() {
        return lines;
    }

    // Adds a separator line indicating the page number
    public void addSeparator() {
        String pageLabel = "Page " + pageNumber;
        String separator = "-".repeat((MAX_CHARS_PER_LINE-pageLabel.length())/2) +
                pageLabel +
                "-".repeat((MAX_CHARS_PER_LINE-pageLabel.length())/2);
        lines.add(separator);
    }
}
