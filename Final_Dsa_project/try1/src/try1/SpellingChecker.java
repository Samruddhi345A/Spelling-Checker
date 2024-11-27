package try1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

@SuppressWarnings("serial")
public class SpellingChecker extends JFrame {
    private JTextArea inputArea; // Multi-line input area
    private JButton checkButton;
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;
    private CustomHashTable dictionary;
    private final String DICTIONARY_FILE = "D:\\java_project\\trial2\\Final_Dsa_project\\try1\\src\\try1\\dictionary.txt";

    // LinkedList class for collision handling in the hash table
    public static class LinkedList {
        private Node head;

        private class Node {
            String word;
            Node next;

            Node(String word) {
                this.word = word;
                this.next = null;
            }
        }

        public void add(String word) {
            Node newNode = new Node(word);
            if (head == null) {
                head = newNode;
            } else {
            	newNode.next=head;
            	head=newNode;
            }
        }

        public boolean contains(String word) {
            Node current = head;
            while (current != null) {
                if (current.word.equals(word)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }

    // Hash table class for storing the dictionary
    public static class CustomHashTable {
        private final int SIZE = 100;
        private LinkedList[] table;

        public CustomHashTable() {
            table = new LinkedList[SIZE];
            for (int i = 0; i < SIZE; i++) {
                table[i] = new LinkedList();
            }
        }

        private int getHash(String word) {
            int hash = 0;
            for (char c : word.toCharArray()) {
                hash = (hash * 31 + c) % SIZE;
            }
            return hash;
        }

        public void add(String word) {
            int index = getHash(word);
            if (!table[index].contains(word)) {
                table[index].add(word);
            }
        }

        public boolean contains(String word) {
            int index = getHash(word);
            return table[index].contains(word);
        }
    }

    public SpellingChecker() {
        dictionary = new CustomHashTable();
        loadDictionary();
        initializeGUI();
    }

    // Method to load words into the dictionary from a file
    public void loadDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found. Creating a new one.");
            try {
                new File(DICTIONARY_FILE).createNewFile();
            } catch (IOException ioException) {
                System.out.println("Error creating dictionary file: " + ioException.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
        }
    }

    // Method to check if a word is correct
    public boolean isCorrect(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    // Method to get suggestions for misspelled words
    public LinkedList getSuggestions(String word) {
        LinkedList suggestions = new LinkedList();
        word = word.toLowerCase();

        for (int i = 0; i < dictionary.SIZE; i++) {
            LinkedList bucket = dictionary.table[i];
            LinkedList.Node current = bucket.head;
            while (current != null) {
                if (editDistance(word, current.word) <= 2) {
                    suggestions.add(current.word);
                }
                current = current.next;
            }
        }
        return suggestions;
    }

    // Edit distance function to find the similarity between words
    private int editDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j; // If first word is empty
                } else if (j == 0) {
                    dp[i][j] = i; // If second word is empty
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No operation needed
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])); // Insert, Remove, Replace
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

 // Initialize GUI components
    private void initializeGUI() {
        setTitle("Spelling Checker");
        setSize(1000,700);
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
        checkButton = new JButton("Check Spelling");
        buttonPanel.add(checkButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Suggestions list
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane suggestionScrollPane = new JScrollPane(suggestionList);
        suggestionScrollPane.setBorder(BorderFactory.createTitledBorder("Suggestions"));
        suggestionScrollPane.setPreferredSize(new Dimension(200, 400));
        mainPanel.add(suggestionScrollPane, BorderLayout.EAST);

        add(mainPanel);

        // Button actions
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSpelling();
            }
        });

      
    }
 // Method to check spelling and provide suggestions
    private void checkSpelling() {
        String[] words = inputArea.getText().split("\\s+");
        listModel.clear(); // Clear previous suggestions
        for (String word : words) {
            if (isCorrect(word)) {
                listModel.addElement("'" + word + "' is spelled correctly.");
            } else {
                LinkedList suggestions = getSuggestions(word);
                if (suggestions.head != null) {
                    listModel.addElement("Suggestions for '" + word + "':");
                    LinkedList.Node current = suggestions.head;
                    while (current != null) {
                        listModel.addElement(current.word);
                        current = current.next;
                    }
                } else {
                    listModel.addElement("No suggestions for '" + word + "'");
                }
            }
        }
    }

  

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpellingChecker checker = new SpellingChecker();
            checker.setVisible(true);
        });
    }
}