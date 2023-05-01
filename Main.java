package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Define the authorized users and passwords
        HashMap<String, String> authorizedUsers = new HashMap<>();
        authorizedUsers.put("Alice", "password123");
        authorizedUsers.put("Bob", "password456");
        authorizedUsers.put("Charlie", "password789");
        // Create the login screen
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authorizedUsers.containsKey(username) && authorizedUsers.get(username).equals(password)) {
                    // User has been authenticated, show the main program window
                    loginFrame.dispose();
                    JFrame mainFrame = new JFrame("Encryption/Decryption Program");
                    mainFrame.setSize(400, 300);
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    InputPanel inputPanel = new InputPanel();
                    OutputPanel outputPanel = new OutputPanel();

                    mainFrame.getContentPane().setLayout(new GridLayout(2, 1));
                    mainFrame.getContentPane().add(inputPanel);
                    mainFrame.getContentPane().add(outputPanel);

                    mainFrame.setVisible(true);
                } else {
                    // Authentication failed, show an error message
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the registration screen
                loginFrame.dispose();
                JFrame registerFrame = new JFrame("Registration");
                registerFrame.setSize(400, 300);
                registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel registerPanel = new JPanel();
                registerPanel.setLayout(new GridLayout(4, 1));

                JLabel newUsernameLabel = new JLabel("New Username:");
                JTextField newUsernameField = new JTextField();

                JLabel newPasswordLabel = new JLabel("New Password:");
                JPasswordField newPasswordField = new JPasswordField();

                JButton registerUserButton = new JButton("Register User");
                registerUserButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Add the new user to the authorized users and show the login screen
                        authorizedUsers.put(newUsernameField.getText(), new String(newPasswordField.getPassword()));
                        JOptionPane.showMessageDialog(registerFrame, "User successfully registered.", "Success", JOptionPane.PLAIN_MESSAGE);
                        registerFrame.dispose();
                        loginFrame.setVisible(true);
                    }
                });
                  registerPanel.add(newUsernameLabel);
                registerPanel.add(newUsernameField);
                registerPanel.add(newPasswordLabel);
                registerPanel.add(newPasswordField);
                registerPanel.add(registerUserButton);

                registerFrame.getContentPane().add(registerPanel);
                registerFrame.setVisible(true);
            }
        });
        
        
 
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setVisible(true);
    }
}
        
        
      

class Encryption {
    public static String caesarEncrypt(String input, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    result.append((char) ((ch + key - 65) % 26 + 65));
                } else {
                    result.append((char) ((ch + key - 97) % 26 + 97));
                }
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static String railFenceEncrypt(String input, int key) {
        char[][] matrix = new char[key][input.length()];
        int row = 0, col = 0;
        boolean down = true;

        for (int i = 0; i < input.length(); i++) {
            matrix[row][col] = input.charAt(i);

            if (row == 0) {
                down = true;
            } else if (row == key - 1) {
                down = false;
            }

            if (down) {
                row++;
            } else {
                row--;
                col++;
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < input.length(); j++) {
                if (matrix[i][j] != '\0') {
                    result.append(matrix[i][j]);
                }
            }
        }

        return result.toString();
    }
    public static String vigenereEncrypt(String input, String key) {
    StringBuilder result = new StringBuilder();
    int keyIndex = 0;

    for (int i = 0; i < input.length(); i++) {
        char ch = input.charAt(i);

        if (Character.isLetter(ch)) {
            char shift = key.charAt(keyIndex);
            if (Character.isUpperCase(shift)) {
                shift = (char) (shift - 'A');
            } else {
                shift = (char) (shift - 'a');
            }

            if (Character.isUpperCase(ch)) {
                result.append((char) ((ch + shift - 'A') % 26 + 'A'));
            } else {
                result.append((char) ((ch + shift - 'a') % 26 + 'a'));
            }

            keyIndex = (keyIndex + 1) % key.length();
        } else {
            result.append(ch);
        }
    }

    return result.toString();
}

}

class Decryption {
    public static String caesarDecrypt(String input, int key) {
        return Encryption.caesarEncrypt(input, 26 - key);
    }

    public static String railFenceDecrypt(String input, int key) {
        int[] rails = new int[input.length()];
        int row = 0, col = 0;
        boolean down = true;

        for (int i = 0; i < input.length(); i++) {
            rails[i] = row;

            if (row == 0) {
                down = true;
            } else if (row == key - 1) {
                down = false;
            }

            if (down) {
                row++;
            } else {
                row--;
                col++;
            }
        }

        char[][] matrix = new char[key][input.length()];
        int index = 0;

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < input.length(); j++) {
                if (rails[j] == i) {
                    matrix[i][j] = input.charAt(index++);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        row = 0;
        col = 0;
        down = true;

        for (int i = 0; i < input.length(); i++) {
            result.append(matrix[row][col]);

            if (row == 0) {
                down = true;
            } else if (row == key - 1) {
                down = false;
            }

            if (down) {
                row++;
            } else {
                row--;
                col++;
            }
        }

        return result.toString();
    }
    public static String vigenereDecrypt(String input, String key) {
    StringBuilder result = new StringBuilder();

    for (int i = 0, j = 0; i < input.length(); i++) {
        char ch = input.charAt(i);

        if (Character.isLetter(ch)) {
            if (Character.isUpperCase(ch)) {
                result.append((char) ((ch - key.charAt(j) + 26) % 26 + 65));
            } else {
                result.append((char) ((ch - key.charAt(j) + 26) % 26 + 97));
            }
            j = (j + 1) % key.length();
        } else {
            result.append(ch);
        }
    }

    return result.toString();
}

}

class InputPanel extends JPanel implements ActionListener {
    private JTextField inputField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JComboBox<String> encryptionTypeComboBox;

    public InputPanel() {
        inputField = new JTextField(20);
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
            encryptionTypeComboBox = new JComboBox<>(new String[]{"Caesar", "Rail Fence", "Vigenere"});
    encryptionTypeComboBox.setSelectedIndex(0);

    encryptButton.addActionListener(this);
    decryptButton.addActionListener(this);

    add(inputField);
    add(encryptionTypeComboBox);
    add(encryptButton);
    add(decryptButton);
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == encryptButton) {
        String input = inputField.getText();
        String output = "";
        String encryptionType = (String) encryptionTypeComboBox.getSelectedItem();

        if (encryptionType.equals("Caesar")) {
            int key = Integer.parseInt(JOptionPane.showInputDialog("Enter key (an integer between 1 and 25)"));
            output = Encryption.caesarEncrypt(input, key);
        } else if (encryptionType.equals("Rail Fence")) {
            int key = Integer.parseInt(JOptionPane.showInputDialog("Enter key (an integer between 2 and input length)"));
            output = Encryption.railFenceEncrypt(input, key);
        } else if (encryptionType.equals("Vigenere")) {
            String key = JOptionPane.showInputDialog("Enter key (a word or phrase)");
            output = Encryption.vigenereEncrypt(input, key);
        }

        OutputPanel.setOutput(output);
    } else if (e.getSource() == decryptButton) {
        String input = inputField.getText();
        String output = "";
        String encryptionType = (String) encryptionTypeComboBox.getSelectedItem();

        if (encryptionType.equals("Caesar")) {
            int key = Integer.parseInt(JOptionPane.showInputDialog("Enter key (an integer between 1 and 25)"));
            output = Decryption.caesarDecrypt(input, key);
        } else if (encryptionType.equals("Rail Fence")) {
            int key = Integer.parseInt(JOptionPane.showInputDialog("Enter key (an integer between 2 and input length)"));
            output = Decryption.railFenceDecrypt(input, key);
        } else if (encryptionType.equals("Vigenere")) {
            String key = JOptionPane.showInputDialog("Enter key (a word or phrase)");
            output = Decryption.vigenereDecrypt(input, key);
        }

        OutputPanel.setOutput(output);
    }
}
}

class OutputPanel extends JPanel {
private static JTextArea outputArea;
public OutputPanel() {
    outputArea = new JTextArea(10, 30);
    outputArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(outputArea);
    add(scrollPane);
}

public static void setOutput(String output) {
    outputArea.setText(output);
}
}
        
