# Login Form Task (Java AWT)

A simple login form implemented with Java AWT.

## Features
- Checks that username and password are not empty.
- Uses hard-coded users in `loadUsers()`.
- Validates username, password, and selected role.
- Shows a message for invalid credentials.
- Disables the Login button and form fields after 3 failed login attempts.
- Opens a welcome window showing the username and role after successful login.
- Cancel closes the login window.

## Test accounts

| Username | Password | Role |
|---|---|---|
| `student1` | `stud123` | Student |
| `ta1` | `ta123` | TA |
| `prof1` | `prof123` | Professor |

## Run
Make sure a JDK is installed, then open a terminal in this folder and run:

```bash
javac LoginFormTask.java
java LoginFormTask
```

## Suggested validation screenshots for the repository
Capture real screenshots of the running application and place them in `screenshots/`:
1. `empty-fields.png` — click Login with username/password empty.
2. `wrong-credentials.png` — enter an incorrect password and show the error message.
3. `three-failed-attempts.png` — after three failed attempts, show that Login is disabled.
4. `successful-login.png` — log in with a valid account and show the welcome window.

Do not use screenshots of mockups; capture them from the running Java application.
