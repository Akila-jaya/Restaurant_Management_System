package controller;

import model.KitchenItems;

import java.sql.SQLException;

public interface KitchenItemService {
    public boolean saveKitchenItems(KitchenItems c) throws SQLException, ClassNotFoundException;
    public boolean updateKitchenItems(KitchenItems c) throws SQLException, ClassNotFoundException;
    public boolean deleteKitchenItems(String code) throws SQLException, ClassNotFoundException;
    public KitchenItems getKitchenItems(String c) throws SQLException, ClassNotFoundException;
}
