package controller;

import model.Worker;

import java.sql.SQLException;

public interface WorkerService {
    public boolean SaveWorker(Worker w) throws SQLException, ClassNotFoundException;
    public boolean DeleteWorker(String code) throws SQLException, ClassNotFoundException;
    public boolean UpdateWorker(Worker w) throws SQLException, ClassNotFoundException;
    public Worker getWorker (String code) throws SQLException, ClassNotFoundException;
}
