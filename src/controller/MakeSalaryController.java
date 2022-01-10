package controller;

import Utill.ValidationUtill;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class MakeSalaryController {

    public JFXTextField WorlingDays;
    public JFXTextField WorkerId;
    public JFXTextField PerDaySalary;

    // Validation ======


 /*   LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern idPattern = Pattern.compile("^(C00-)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");       //^(B)[0-9][0-9][0-9]$
    Pattern salaryPattern = Pattern.compile("^[1-9][0-9]$");


    public void initialize() {
        storeValidations();
    }
   *//* private void storeValidations() {
        map.put(txtTableId, idPattern);
        map.put(txtTableName, namePattern);
        map.put(txtNumberOfChairs, salaryPattern);
        map.put(txtOrderId, idPattern);
        map.put(txtStatus, namePattern);
    }
*//*


    public void textFields_Key_Released(KeyEvent keyEvent) {

        Object response = ValidationUtill.validate(map,SaveOnAction);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }


    }

*/


    // Validation ======


}
