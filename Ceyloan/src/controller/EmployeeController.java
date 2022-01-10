package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Worker;

import java.sql.SQLException;

public class EmployeeController {


    public JFXTextField txtWorkerId;
    public JFXTextField txtWorkerName;
    public JFXTextField txtWorkerEmail;
    public JFXTextField txtWorkerContact;
    public JFXTextField txtWorkerType;
    public JFXTextField txtWorkerStatus;

    public void SaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code = txtWorkerId.getText();
        if (new WorkerInterfaceController().DeleteWorker(code)) {
            new Alert(Alert.AlertType.CONFIRMATION, "DELETED..").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "No..").show();
        }
    }

    public void RefreshOnAction(ActionEvent actionEvent) {
        txtWorkerId.clear();
        txtWorkerName.clear();
        txtWorkerEmail.clear();
        txtWorkerContact.clear();
        txtWorkerType.clear();
        txtWorkerStatus.clear();

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Worker worker=new Worker(
                txtWorkerId.getText(),
                txtWorkerName.getText(),
                txtWorkerEmail.getText(),
                txtWorkerContact.getText(),
                txtWorkerType.getText(),
                txtWorkerStatus.getText()
        );
        if (new WorkerInterfaceController().UpdateWorker(worker)){
            new Alert(Alert.AlertType.CONFIRMATION,"UPDATED..").show();

        }else{
            new Alert(Alert.AlertType.WARNING,"NO UPDATED..").show();
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
