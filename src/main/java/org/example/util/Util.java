package org.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static boolean stringIsntNullNorBlank(String string){
        if(string == null) return false;
        return !string.isBlank();
    }

    public static boolean trimmedStringsAreEqual(String string1, String string2){
        if(string1 == string2) return true;
        if(string1 == null || string2 == null) return false;

        return string1.trim().equals(string2.trim());
    }

    public static boolean trimmedStringsAreNOTEqual(String string1, String string2){
        return !trimmedStringsAreEqual(string1, string2);
    }
    public static boolean listIsNullOrHasNoElements(List list){
        if(list == null) return true;
        return list.size() == 0;
    }

    public static <T> ObservableList<T> copyList(ObservableList<T> observableList){
        return observableList.stream().
                collect(Collectors.toCollection(FXCollections::<T>observableArrayList));
    }

}
