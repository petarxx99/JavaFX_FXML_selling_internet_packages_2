package org.example.util;

import javafx.scene.control.Alert;

public interface MessageDisplayer {
    public void showErrorMessage(String errorMessage);
    public void showMessage(String message);
    public void showConfirmMessage(String message);
    public void showMessageBasedOnACondition(boolean condition, String messageIfTrue, String messageIfFalse);
}
