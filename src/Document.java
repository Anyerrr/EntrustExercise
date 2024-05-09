import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Document {
    private final File file;
    private final List<Page> pages;
    private final String paginatedFileName;

    public Document(File file) {
        this.file = file;
        this.pages = new ArrayList<>();
        this.paginatedFileName = createPaginatedFileName(file);
        paginate();
        writePaginatedFile();
    }

    // Creates a paginated file name based on the given file, also creates the directory ./out if it doesn't exist
    private String createPaginatedFileName(File file) {
        File directory = new File("./out");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return "./out/paginated_" + file.getName();
    }

    // Reads the content of the original file, splits it into words, and organizes them into pages.
    private void paginate() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int pageNumber = 1;
            Page currentPage = new Page(pageNumber);
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (currentPage.isFull(word)) {
                        currentPage.addSeparator();
                        pages.add(currentPage);
                        pageNumber++;
                        currentPage = new Page(pageNumber);
                    }
                    currentPage.addWord(word);
                }
            }
            if (!currentPage.isEmpty()) {
                currentPage.addSeparator();
                pages.add(currentPage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Writes the paginated content to a new file
    private void writePaginatedFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paginatedFileName))) {
            for (Page page : pages) {
                for (String line : page.getLines()) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing paginated file", e);
        }
    }

    // Returns the list of pages generated during pagination
    public List<Page> getPages() {
        return pages;
    }
}