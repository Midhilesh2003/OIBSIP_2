import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineExamSystem extends JFrame {
    private JButton loginButton;
    private JButton updateProfileButton;
    private JButton startExamButton;
    private JButton logoutButton;
    private JLabel statusLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User loggedInUser;
    private ExamWindow examWindow;

    public OnlineExamSystem() {
        setTitle("Online Exam System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkbox = (JCheckBox) e.getSource();
                if (checkbox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(showPasswordCheckbox, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(loginButton, gbc);

        updateProfileButton = new JButton("Update Profile");
        updateProfileButton.setEnabled(false);
        updateProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(updateProfileButton, gbc);

        startExamButton = new JButton("Start Exam");
        startExamButton.setEnabled(false);
        startExamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startExam();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(startExamButton, gbc);

        logoutButton = new JButton("Logout");
        logoutButton.setEnabled(false);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(logoutButton, gbc);

        statusLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(statusLabel, gbc);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (isValidUser(username, password)) {
            loggedInUser = new User(username);
            updateUI(true);
            statusLabel.setText("Logged in as: " + username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    private boolean isValidUser(String username, String password) {
        if (username.equals("Midhilesh") && password.equalsIgnoreCase("admin")) {
            return true;
        } else if (username.equals("dummy1") && password.equals("password1")) {
            return true;
        } else if (username.equals("dummy2") && password.equals("password2")) {
            return true;
        } else if (username.equals("dummy3") && password.equals("password3")) {
            return true;
        } else if (username.equals("") && password.equals("")) {
            return true;
        }
        return false;
    }

    private void updateProfile() {
        String newPassword = JOptionPane.showInputDialog(this, "Enter new password:");
        if (newPassword != null) {
            loggedInUser.setPassword(newPassword);
            JOptionPane.showMessageDialog(this, "Password updated successfully!");
        }
    }

    private void startExam() {
        examWindow = new ExamWindow();
        examWindow.setVisible(true);
        setVisible(false);
    }

    private void logout() {
        loggedInUser = null;
        updateUI(false);
        statusLabel.setText("");
        setVisible(true);
    }

    private void updateUI(boolean loggedIn) {
        usernameField.setEnabled(!loggedIn);
        passwordField.setEnabled(!loggedIn);
        loginButton.setEnabled(!loggedIn);
        updateProfileButton.setEnabled(loggedIn);
        startExamButton.setEnabled(loggedIn);
        logoutButton.setEnabled(loggedIn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OnlineExamSystem().setVisible(true);
            }
        });
    }

    private class ExamWindow extends JFrame {
        private JTextArea questionArea;
        private JButton nextButton;
        private JButton previousButton;
        private JButton submitButton;
        private JButton[] optionButtons;
        private int currentQuestionIndex;
        private Question[] questions;

        public ExamWindow() {
            setTitle("Exam");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            questionArea = new JTextArea(10, 30);
            questionArea.setEditable(false);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            add(questionArea, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            nextButton = new JButton("Next");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showNextQuestion();
                }
            });
            buttonPanel.add(nextButton);

            previousButton = new JButton("Previous");
            previousButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showPreviousQuestion();
                }
            });
            buttonPanel.add(previousButton);

            submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    submitExam();
                }
            });
            buttonPanel.add(submitButton);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            add(buttonPanel, gbc);

            optionButtons = new JButton[4];
            for (int i = 0; i < 4; i++) {
                optionButtons[i] = new JButton();
                optionButtons[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        selectOption(e);
                        showNextQuestion(); // Move to next question automatically after selecting an option
                    }
                });
                gbc.gridx = i % 2;
                gbc.gridy = i / 2 + 2;
                gbc.gridwidth = 1;
                add(optionButtons[i], gbc);
            }

            questions = new Question[3];
            questions[0] = new Question("Question 1", new String[] { "Option 1", "Option 2", "Option 3", "Option 4" },
                    0);
            questions[1] = new Question("Question 2", new String[] { "Option 1", "Option 2", "Option 3", "Option 4" },
                    1);
            questions[2] = new Question("Question 3", new String[] { "Option 1", "Option 2", "Option 3", "Option 4" },
                    2);

            currentQuestionIndex = 0;
            showQuestion();
        }

        private void showQuestion() {
            if (currentQuestionIndex < questions.length) {
                Question question = questions[currentQuestionIndex];
                questionArea.setText(question.getQuestion());
                String[] options = question.getOptions();
                for (int i = 0; i < options.length; i++) {
                    optionButtons[i].setText(options[i]);
                }
            } else {
                questionArea.setText("No more questions");
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
                for (JButton optionButton : optionButtons) {
                    optionButton.setEnabled(false);
                }
            }
        }

        private void showNextQuestion() {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                showQuestion();
                previousButton.setEnabled(true);
                nextButton.setEnabled(true); // Re-enable "Next" button when moving to the next question
            } else {
                nextButton.setEnabled(false); // Disable "Next" button when at the last question
            }
        }

        private void showPreviousQuestion() {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                showQuestion();
            }
            if (currentQuestionIndex == 0) {
                previousButton.setEnabled(false);
            }
        }

        private void selectOption(ActionEvent e) {
            if (currentQuestionIndex >= questions.length) {
                // If we have already reached the last question, do nothing
                return;
            }

            JButton selectedButton = (JButton) e.getSource();
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i] == selectedButton) {
                    questions[currentQuestionIndex].setSelectedOption(i);
                    break;
                }
            }
        }

        private void submitExam() {
            int score = 0;
            for (Question question : questions) {
                if (question.isAnswerCorrect()) {
                    score++;
                }
            }
            JOptionPane.showMessageDialog(this, "Exam submitted. Score: " + score + "/" + questions.length);
            dispose();
            loggedInUser = null;
            updateUI(false);
            statusLabel.setText("");
            setVisible(true);
        }
    }

    private class Question {
        private String question;
        private String[] options;
        private int correctOptionIndex;
        private int selectedOptionIndex;

        public Question(String question, String[] options, int correctOptionIndex) {
            this.question = question;
            this.options = options;
            this.correctOptionIndex = correctOptionIndex;
            this.selectedOptionIndex = -1;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public void setSelectedOption(int selectedOptionIndex) {
            this.selectedOptionIndex = selectedOptionIndex;
        }

        public boolean isAnswerCorrect() {
            return selectedOptionIndex == correctOptionIndex;
        }
    }

    private class User {
        private String username;
        private String password;

        public User(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
