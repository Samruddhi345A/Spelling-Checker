package try1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import try1.SpellingChecker.CustomHashTable;

public class adminadd extends JFrame {
    private JTextArea inputArea; // Multi-line input area
    private JButton addButton;
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;
    private CustomHashTable dictionary; // Declare the dictionary
    private final String DICTIONARY_FILE = "D:\\java_project\\trial2\\Final_Dsa_project\\try1\\src\\try1\\dictionary.txt";

    public adminadd() {
        dictionary = new CustomHashTable(); // Initialize the dictionary
        loadDictionary(); // Load existing words from the dictionary file
        initializeGUI(); // Initialize the GUI components
    }

    // Method to load words into the dictionary from a file
    public void loadDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
        }
    }

    // Method to add a new word to the dictionary and the file
    public void addWord(String word) {
        word = word.toLowerCase();
        dictionary.add(word); // Now this should work without throwing NullPointerException
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DICTIONARY_FILE, true))) {
            bw.write(word);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error adding word to dictionary: " + e.getMessage());
        }
    }
    // Method to add a new word to the dictionary
    private void addNewWord() {
        String newWord = inputArea.getText();

        if (newWord != null && !newWord.trim().isEmpty()) {
            addWord(newWord.trim());
            JOptionPane.showMessageDialog(this, "Word added to dictionary!");
        }
    }


    // Initialize GUI components
    private void initializeGUI() {
        setTitle("Spelling Checker");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(220, 220, 255)); // Light blue background
        mainPanel.setLayout(new BorderLayout());

        // Input area with a border
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(inputArea);
        
        // Adjust the height of the input area
        scrollPane.setBorder(BorderFactory.createTitledBorder("Input Text"));
        scrollPane.setPreferredSize(new Dimension(400, 150)); // Reduced height from 200 to 150
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Word");
        buttonPanel.add(addButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    
        add(mainPanel);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewWord();
            }
        });
    }


    public static void main(String[] args) {
        adminadd add = new adminadd();
        add.setVisible(true); // Make the frame visible
    }
}