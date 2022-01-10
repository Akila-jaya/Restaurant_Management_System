package controller;

import model.Tables;

import java.sql.SQLException;

public interface TableService{
    public boolean tableSave(Tables t) throws SQLException, ClassNotFoundException;
    public boolean tableUpdate(Tables t) throws SQLException, ClassNotFoundException;
    public boolean tableDelete(String id) throws SQLException, ClassNotFoundException;
    public Tables getTables(String id) throws SQLException, ClassNotFoundException;
}
