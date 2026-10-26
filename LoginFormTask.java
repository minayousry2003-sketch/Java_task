import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class User {
    private final String username;
    private final String password;
    private final String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class WelcomePage extends Frame {
    WelcomePage(User user) {
        super("Welcome");
        setLayout(new BorderLayout(10, 10));

        Label greeting = new Label("Hello, " + user.getUsername() + "!", Label.CENTER);
        greeting.setFont(new Font("SansSerif", Font.BOLD, 20));

        Label userRole = new Label("Role: " + user.getRole(), Label.CENTER);
        userRole.setFont(new Font("SansSerif", Font.PLAIN, 16));

        Panel content = new Panel(new GridLayout(2, 1, 5, 5));
        content.add(greeting);
        content.add(userRole);
        add(content, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(320, 180);
        setLocationRelativeTo(null);
    }
}

public class LoginFormTask extends Frame {
    private final List<User> users = new ArrayList<>();
    private int failedAttempts = 0;
    private static final int MAX_FAILED_ATTEMPTS = 3;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        Button cancel = new Button("Cancel");
        Button login = new Button("Login");
        buttons.add(cancel);
        buttons.add(login);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {
            String enteredUsername = username.getText().trim();
            String enteredPassword = password.getText();
            String selectedRole = role.getSelectedItem();

            if (enteredUsername.isEmpty() || enteredPassword.trim().isEmpty()) {
                showMessage("Please fill in both username and password.");
                return;
            }

            User user = findUser(enteredUsername);
            if (user == null
                    || !user.getPassword().equals(enteredPassword)
                    || !user.getRole().equals(selectedRole)) {
                failedAttempts++;

                if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                    login.setEnabled(false);
                    username.setEnabled(false);
                    password.setEnabled(false);
                    role.setEnabled(false);
                    showMessage("Too many failed attempts. Login is now disabled.");
                } else {
                    showMessage("Incorrect username, password, or role. Attempts left: "
                            + (MAX_FAILED_ATTEMPTS - failedAttempts));
                }
                return;
            }

            failedAttempts = 0;
            showMessage("Login successful!");
            new WelcomePage(user).setVisible(true);
            dispose();
        });

        cancel.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(420, 340);
        setLocationRelativeTo(null);
    }

    private void loadUsers() {
        users.add(new User("student1", "stud123", "Student"));
        users.add(new User("ta1", "ta123", "TA"));
        users.add(new User("prof1", "prof123", "Professor"));
    }

    private User findUser(String name) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    private void showMessage(String message) {
        MessageDialog dialog = new MessageDialog(this, message);
        dialog.setVisible(true);
    }

    private static class MessageDialog extends Dialog {
        MessageDialog(Frame owner, String message) {
            super(owner, "Login message", true);
            setLayout(new BorderLayout(10, 10));
            add(new Label(message, Label.CENTER), BorderLayout.CENTER);

            Button ok = new Button("OK");
            ok.addActionListener(e -> dispose());
            Panel buttons = new Panel(new FlowLayout());
            buttons.add(ok);
            add(buttons, BorderLayout.SOUTH);

            setSize(330, 130);
            setLocationRelativeTo(owner);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginFormTask().setVisible(true));
    }
}
