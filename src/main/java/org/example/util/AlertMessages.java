package org.example.util;

import javafx.scene.control.Alert;

public class AlertMessages implements MessageDisplayer {
    public void showErrorMessage(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }

    public void showMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public void showConfirmMessage(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }
    public void showMessageBasedOnACondition(boolean condition, String messageIfTrue, String messageIfFalse){
        if(condition){
            showConfirmMessage(messageIfTrue);
        } else {
            showErrorMessage(messageIfFalse);
        }
    }
}
