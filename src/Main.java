import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pagination Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 300);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Pagination Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel fileContentPanel = new JPanel(new BorderLayout());
        fileContentPanel.add(scrollPane, BorderLayout.CENTER);
        fileContentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel fileContentLabel = new JLabel("File Paginated Content", SwingConstants.CENTER);
        fileContentPanel.add(fileContentLabel, BorderLayout.NORTH);

        JButton selectFileButton = new JButton("Select File");
        selectFileButton.addActionListener(e -> selectFileAction(frame, mainPanel, fileContentPanel, textArea));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(selectFileButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private static void selectFileAction(JFrame frame, JPanel mainPanel, JPanel fileContentPanel, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./input"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);


        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayFileContent(frame, selectedFile, mainPanel, fileContentPanel, textArea);
        } else {
            JOptionPane.showMessageDialog(frame, "No file selected.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void displayFileContent(JFrame frame, File file, JPanel mainPanel, JPanel fileContentPanel, JTextArea textArea) {
        try {
            Document document = new Document(file);
            List<Page> pages = document.getPages();
            StringBuilder sb = new StringBuilder();
            for (Page page : pages) {
                List<String> lines = page.getLines();
                for (String line : lines) {
                    sb.append(line).append("\n");
                }
            }
            textArea.setText(sb.toString());
            mainPanel.remove(mainPanel.getComponent(1)); // Remove buttonPanel
            mainPanel.add(fileContentPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            JOptionPane.showMessageDialog(frame, "Text document paginated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch(RuntimeException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
