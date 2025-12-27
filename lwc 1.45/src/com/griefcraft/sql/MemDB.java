package com.griefcraft.sql;

import com.griefcraft.model.Action;
import com.griefcraft.model.Entity;
import com.griefcraft.util.Performance;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemDB extends Database {
  public Action getAction(String paramString1, String paramString2) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM `actions` WHERE `player` = ? AND `action` = ?");
      preparedStatement.setString(1, paramString2);
      preparedStatement.setString(2, paramString1);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        int i = resultSet.getInt("id");
        String str1 = resultSet.getString("action");
        String str2 = resultSet.getString("player");
        int j = resultSet.getInt("chest");
        String str3 = resultSet.getString("data");
        Action action = new Action();
        action.setID(i);
        action.setAction(str1);
        action.setPlayer(str2);
        action.setChestID(j);
        action.setData(str3);
        return action;
      } 
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return null;
  }
  
  public int getActionID(String paramString1, String paramString2) {
    try {
      int i = -1;
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `chest` FROM `actions` WHERE `action` = ? AND `player` = ?");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next())
        i = resultSet.getInt("chest"); 
      preparedStatement.close();
      Performance.addMemDBQuery();
      return i;
    } catch (Exception exception) {
      exception.printStackTrace();
      return -1;
    } 
  }
  
  public List<String> getActions(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `action` FROM `actions` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String str = resultSet.getString("action");
        arrayList.add(str);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return arrayList;
  }
  
  public String getDatabasePath() {
    return ":memory:";
  }
  
  public String getLockPassword(String paramString) {
    try {
      String str = "";
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `password` FROM `locks` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next())
        str = resultSet.getString("password"); 
      preparedStatement.close();
      Performance.addMemDBQuery();
      return str;
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public String getModeData(String paramString1, String paramString2) {
    String str = null;
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `data` from `modes` WHERE `player` = ? AND `mode` = ?");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next())
        str = resultSet.getString("data"); 
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return str;
  }
  
  public List<String> getModes(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from `modes` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String str = resultSet.getString("mode");
        arrayList.add(str);
      } 
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return arrayList;
  }
  
  public List<String> getSessionUsers(int paramInt) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `player` FROM `sessions` WHERE `chest` = ?");
      preparedStatement.setInt(1, paramInt);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String str = resultSet.getString("player");
        arrayList.add(str);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return arrayList;
  }
  
  public int getUnlockID(String paramString) {
    return getActionID("unlock", paramString);
  }
  
  public boolean hasAccess(String paramString, Entity paramEntity) {
    return (paramEntity == null) ? true : hasAccess(paramString, paramEntity.getID());
  }
  
  public boolean hasAccess(String paramString, int paramInt) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `player` FROM `sessions` WHERE `chest` = ?");
      preparedStatement.setInt(1, paramInt);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String str = resultSet.getString("player");
        if (paramString.equals(str)) {
          preparedStatement.close();
          Performance.addMemDBQuery();
          return true;
        } 
      } 
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return false;
  }
  
  public boolean hasMode(String paramString1, String paramString2) {
    List<String> list = getModes(paramString1);
    return (list.size() > 0 && list.contains(paramString2));
  }
  
  public boolean hasPendingAction(String paramString1, String paramString2) {
    return (getAction(paramString1, paramString2) != null);
  }
  
  public boolean hasPendingChest(String paramString) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT `id` FROM `locks` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        preparedStatement.close();
        Performance.addMemDBQuery();
        return true;
      } 
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return false;
  }
  
  public boolean hasPendingUnlock(String paramString) {
    return (getUnlockID(paramString) != -1);
  }
  
  public void load() {
    try {
      Statement statement = this.connection.createStatement();
      log("Creating memory tables");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'sessions' (id INTEGER PRIMARY KEY,player TEXT,chest INTEGER);");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'locks' (id INTEGER PRIMARY KEY,player TEXT,password TEXT);");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'actions' (id INTEGER PRIMARY KEY,action TEXT,player TEXT,chest INTEGER,data TEXT);");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'modes' (id INTEGER PRIMARY KEY,player TEXT,mode TEXT,data TEXT);");
      statement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public int pendingCount() {
    byte b = 0;
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT `id` FROM `locks`");
      while (resultSet.next())
        b++; 
      statement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return b;
  }
  
  public void registerAction(String paramString1, String paramString2) {
    try {
      unregisterAction(paramString1, paramString2);
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `actions` (action, player, chest) VALUES (?, ?, ?)");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.setInt(3, -1);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerAction(String paramString1, String paramString2, int paramInt) {
    try {
      unregisterAction(paramString1, paramString2);
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `actions` (action, player, chest) VALUES (?, ?, ?)");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.setInt(3, paramInt);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerAction(String paramString1, String paramString2, String paramString3) {
    try {
      unregisterAction(paramString1, paramString2);
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `actions` (action, player, data) VALUES (?, ?, ?)");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.setString(3, paramString3);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerChest(String paramString1, String paramString2) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `locks` (player, password) VALUES (?, ?)");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerMode(String paramString1, String paramString2) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `modes` (player, mode) VALUES (?, ?)");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerMode(String paramString1, String paramString2, String paramString3) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `modes` (player, mode, data) VALUES (?, ?, ?)");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.setString(3, paramString3);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerPlayer(String paramString, int paramInt) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO `sessions` (player, chest) VALUES(?, ?)");
      preparedStatement.setString(1, paramString);
      preparedStatement.setInt(2, paramInt);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerUnlock(String paramString, int paramInt) {
    registerAction("unlock", paramString, paramInt);
  }
  
  public int sessionCount() {
    byte b = 0;
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT `id` FROM `sessions`");
      while (resultSet.next())
        b++; 
      statement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return b;
  }
  
  public void unregisterAction(String paramString1, String paramString2) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM `actions` WHERE `action` = ? AND `player` = ?");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterAllActions(String paramString) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM `actions` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterAllChests() {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate("DELETE FROM `locks`");
      statement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterAllModes(String paramString) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM `modes` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterChest(String paramString) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM `locks` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterMode(String paramString1, String paramString2) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM `modes` WHERE `player` = ? AND `mode` = ?");
      preparedStatement.setString(1, paramString1);
      preparedStatement.setString(2, paramString2);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterPlayer(String paramString) {
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM `sessions` WHERE `player` = ?");
      preparedStatement.setString(1, paramString);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      Performance.addMemDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterUnlock(String paramString) {
    unregisterAction("unlock", paramString);
  }
}


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraft\sql\MemDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */