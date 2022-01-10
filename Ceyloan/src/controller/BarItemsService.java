package controller;

import model.BarItems;

import java.sql.SQLException;

public interface BarItemsService {
        public boolean saveBarItems(BarItems c) throws SQLException, ClassNotFoundException;
        public boolean deleteBarItems(String id) throws SQLException, ClassNotFoundException;
        public boolean updateBarItems(BarItems c) throws SQLException, ClassNotFoundException;
        public BarItems getBarItems(String id) throws SQLException, ClassNotFoundException;

}
