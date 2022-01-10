package controller;

import Utill.ValidationUtill;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Worker;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeController {


    public JFXTextField txtWorkerId;
    public JFXTextField txtWorkerName;
    public JFXTextField txtWorkerEmail;
    public JFXTextField txtWorkerContact;
    public JFXTextField txtWorkerType;
    public JFXTextField txtWorkerStatus;
    public JFXButton SaveOnAction;


    // Validation ====================



    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern idPattern = Pattern.compile("^(W)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");       //^(B)[0-9][0-9][0-9]$
    Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern contactPattern = Pattern.compile("^[0-9]{3}[-][0-9]{7}$");
    //Pattern emailPattern.compile("^[0-9]{3}[-][0-9]{7}$");


    public void initialize() {
        storeValidations();
    }
    private void storeValidations() {
        map.put(txtWorkerId, idPattern);
        map.put(txtWorkerName, namePattern);
        map.put(txtWorkerEmail, namePattern);
        map.put(txtWorkerContact, contactPattern);
        map.put(txtWorkerType, namePattern);
        map.put(txtWorkerStatus, namePattern);
        
    }



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






    // Validation ====================





    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try{

            if (txtWorkerId==null){
                new Alert(Alert.AlertType.WARNING,"Nothing..").show();
            }else{
                Worker worker=new Worker(
                        txtWorkerId.getText(),
                        txtWorkerName.getText(),
                        txtWorkerType.getText(),
                        txtWorkerStatus.getText(),
                        txtWorkerContact.getText(),
                        txtWorkerEmail.getText()
                );

                if (new WorkerInterfaceController().SaveWorker(worker)){
                    new Alert(Alert.AlertType.CONFIRMATION,"SAVED..").show();
                }else {
                    new Alert(Alert.AlertType.WARNING,"TRY AGAIN..").show();
                }
            }
        }catch (Exception e){

        }


    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {



        try{

            String code = txtWorkerId.getText();
            if (new WorkerInterfaceController().DeleteWorker(code)) {
                new Alert(Alert.AlertType.CONFIRMATION, "DELETED..").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "No..").show();
            }
        }catch (Exception e){

        }

    }

    public void RefreshOnAction(ActionEvent actionEvent) {


        try{

            txtWorkerId.clear();
            txtWorkerName.clear();
            txtWorkerEmail.clear();
            txtWorkerContact.clear();
            txtWorkerType.clear();
            txtWorkerStatus.clear();
        }catch (Exception e){

        }

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            Worker worker=new Worker(
                    txtWorkerId.getText(),
                    txtWorkerName.getText(),
                    txtWorkerType.getText(),
                    txtWorkerStatus.getText(),
                    txtWorkerContact.getText(),
                    txtWorkerEmail.getText()
            );
            if (new WorkerInterfaceController().UpdateWorker(worker)){
                new Alert(Alert.AlertType.CONFIRMATION,"UPDATED..").show();

            }else{
                new Alert(Alert.AlertType.WARNING,"NO UPDATED..").show();
            }

        }catch (Exception e){

        }
    }

    public void WorkerIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String code=txtWorkerId.getText();
    Worker worker= new WorkerInterfaceController().getWorker(code);
    if (worker==null){
        new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
    }else{
        setData(worker);
    }

    }

    private void setData(Worker worker) {
        txtWorkerId.setText(worker.getId());
        txtWorkerName.setText(worker.getName());
        txtWorkerEmail.setText(worker.getEmail());
        txtWorkerContact.setText(worker.getContact());
        txtWorkerType.setText(worker.getType());
        txtWorkerStatus.setText(worker.getStatus());
    }
}
