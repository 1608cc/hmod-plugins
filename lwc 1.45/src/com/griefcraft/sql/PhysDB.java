package com.griefcraft.sql;

import com.griefcraft.model.Entity;
import com.griefcraft.util.Performance;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhysDB extends Database {
  private boolean loaded = false;
  
  private PreparedStatement _select_protectedEntity_ID;
  
  private PreparedStatement _select_chestExist_ID;
  
  private PreparedStatement _select_chestCount_user;
  
  private PreparedStatement _select_limit_type_entity;
  
  private PreparedStatement _select_privateAccess_type_ID_entities;
  
  private PreparedStatement _select_protectedEntity_x_y_z_radius;
  
  private PreparedStatement _select_protectedEntity_x_y_z;
  
  private PreparedStatement _insert_protectedEntity_type_player_password_x_y_z;
  
  private PreparedStatement _insert_protectedLimit_type_amount_entity;
  
  private PreparedStatement _insert_rights_ID_entity_rights_type;
  
  private PreparedStatement _delete_protectedEntity_ID;
  
  private PreparedStatement _delete_protectedEntity_x_y_z;
  
  private PreparedStatement _delete_limit_type_entity;
  
  private PreparedStatement _delete_rights_ID;
  
  private PreparedStatement _delete_rights_ID_entity;
  
  public boolean doesChestExist(int paramInt) {
    boolean bool = false;
    try {
      this._select_chestExist_ID.setInt(1, paramInt);
      ResultSet resultSet = this._select_chestExist_ID.executeQuery();
      bool = (resultSet.getInt("count") > 0) ? true : false;
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return bool;
  }
  
  public void doUpdate100() {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeQuery("SELECT `type` FROM `protections`");
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      log("Outdated database!");
      log("UPGRADING FROM 1.00 TO 1.10");
      log("ALTERING TABLE `protections` AND FILLING WITH DEFAULT DATA");
      try {
        Statement statement = this.connection.createStatement();
        statement.addBatch("ALTER TABLE `protections` ADD `type` INTEGER");
        statement.addBatch("UPDATE `protections` SET `type`='1'");
        statement.executeBatch();
        statement.close();
        Performance.addPhysDBQuery();
      } catch (Exception exception1) {
        log("Oops! Something went wrong: ");
        exception.printStackTrace();
        System.exit(0);
      } 
      log("Update completed!");
    } 
  }
  
  public void doUpdate130() {
    boolean bool = true;
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("PRAGMA INDEX_LIST('protections')");
      while (resultSet.next())
        bool = false; 
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {}
    if (!bool)
      return; 
    log("Outdated database!");
    log("UPGRADING FROM 1.10 TO 1.30");
    log("CREATING INDEXES!");
    try {
      Statement statement = this.connection.createStatement();
      statement.addBatch("BEGIN TRANSACTION");
      statement.addBatch("CREATE INDEX in1 ON `protections` (owner, x, y, z)");
      statement.addBatch("CREATE INDEX in2 ON `limits` (type, entity)");
      statement.addBatch("CREATE INDEX in3 ON `rights` (chest, entity)");
      statement.addBatch("END TRANSACTION");
      statement.executeBatch();
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      log("Oops! Something went wrong: ");
      exception.printStackTrace();
    } 
    log("Update complete!");
  }
  
  public int entityCount() {
    byte b = 0;
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT `id` FROM `protections`");
      while (resultSet.next())
        b++; 
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return b;
  }
  
  public int getChestCount(String paramString) {
    byte b = 0;
    try {
      this._select_chestCount_user.setString(1, paramString);
      ResultSet resultSet = this._select_chestCount_user.executeQuery();
      while (resultSet.next())
        b++; 
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return b;
  }
  
  public int getGroupLimit(String paramString) {
    return getLimit(0, paramString);
  }
  
  public int getLimit(int paramInt, String paramString) {
    int i = -1;
    try {
      this._select_limit_type_entity.setInt(1, paramInt);
      this._select_limit_type_entity.setString(2, paramString.toLowerCase());
      ResultSet resultSet = this._select_limit_type_entity.executeQuery();
      while (resultSet.next())
        i = resultSet.getInt("amount"); 
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return i;
  }
  
  public int getPrivateAccess(int paramInt1, int paramInt2, String... paramVarArgs) {
    int i = -1;
    try {
      this._select_privateAccess_type_ID_entities.setInt(1, paramInt1);
      this._select_privateAccess_type_ID_entities.setInt(2, paramInt2);
      ResultSet resultSet = this._select_privateAccess_type_ID_entities.executeQuery();
      label20: while (resultSet.next()) {
        String str = resultSet.getString("entity");
        for (String str1 : paramVarArgs) {
          if (str1.equalsIgnoreCase(str)) {
            i = resultSet.getInt("rights");
            break label20;
          } 
        } 
      } 
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return i;
  }
  
  public int getUserLimit(String paramString) {
    return getLimit(1, paramString);
  }
  
  public int limitCount() {
    byte b = 0;
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT `id` FROM `limits`");
      while (resultSet.next())
        b++; 
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return b;
  }
  
  public void load() {
    if (this.loaded)
      return; 
    doUpdate140();
    try {
      Statement statement = this.connection.createStatement();
      this.connection.setAutoCommit(false);
      log("Creating physical tables if needed");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'protections' (id INTEGER PRIMARY KEY,type INTEGER,owner TEXT,password TEXT,x INTEGER,y INTEGER,z INTEGER,date TEXT);");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'limits' (id INTEGER PRIMARY KEY,type INTEGER,amount INTEGER,entity TEXT);");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'rights' (id INTEGER PRIMARY KEY,chest INTEGER,entity TEXT,rights INTEGER,type INTEGER);");
      this.connection.commit();
      this.connection.setAutoCommit(true);
      statement.close();
      Performance.addPhysDBQuery();
      loadPreparedStatements();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    doUpdate100();
    doUpdate130();
    this.loaded = true;
  }
  
  public List<Entity> loadProtectedEntities(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    ArrayList<Entity> arrayList = new ArrayList();
    try {
      this._select_protectedEntity_x_y_z_radius.setInt(1, paramInt1 - paramInt4);
      this._select_protectedEntity_x_y_z_radius.setInt(2, paramInt1 + paramInt4);
      this._select_protectedEntity_x_y_z_radius.setInt(3, paramInt2 - paramInt4);
      this._select_protectedEntity_x_y_z_radius.setInt(4, paramInt2 + paramInt4);
      this._select_protectedEntity_x_y_z_radius.setInt(5, paramInt3 - paramInt4);
      this._select_protectedEntity_x_y_z_radius.setInt(6, paramInt3 + paramInt4);
      ResultSet resultSet = this._select_protectedEntity_x_y_z_radius.executeQuery();
      while (resultSet.next()) {
        int i = resultSet.getInt("id");
        int j = resultSet.getInt("type");
        String str1 = resultSet.getString("owner");
        String str2 = resultSet.getString("password");
        int k = resultSet.getInt("x");
        int m = resultSet.getInt("y");
        int n = resultSet.getInt("z");
        String str3 = resultSet.getString("date");
        Entity entity = new Entity();
        entity.setID(i);
        entity.setType(j);
        entity.setOwner(str1);
        entity.setPassword(str2);
        entity.setX(k);
        entity.setY(m);
        entity.setZ(n);
        entity.setDate(str3);
        arrayList.add(entity);
      } 
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return arrayList;
  }
  
  public Entity loadProtectedEntity(int paramInt) {
    try {
      this._select_protectedEntity_ID.setInt(1, paramInt);
      ResultSet resultSet = this._select_protectedEntity_ID.executeQuery();
      if (resultSet.next()) {
        int i = resultSet.getInt("id");
        int j = resultSet.getInt("type");
        String str1 = resultSet.getString("owner");
        String str2 = resultSet.getString("password");
        int k = resultSet.getInt("x");
        int m = resultSet.getInt("y");
        int n = resultSet.getInt("z");
        String str3 = resultSet.getString("date");
        Entity entity = new Entity();
        entity.setID(i);
        entity.setType(j);
        entity.setOwner(str1);
        entity.setPassword(str2);
        entity.setX(k);
        entity.setY(m);
        entity.setZ(n);
        entity.setDate(str3);
        resultSet.close();
        Performance.addPhysDBQuery();
        return entity;
      } 
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return null;
  }
  
  public Entity loadProtectedEntity(int paramInt1, int paramInt2, int paramInt3) {
    try {
      this._select_protectedEntity_x_y_z.setInt(1, paramInt1);
      this._select_protectedEntity_x_y_z.setInt(2, paramInt2);
      this._select_protectedEntity_x_y_z.setInt(3, paramInt3);
      ResultSet resultSet = this._select_protectedEntity_x_y_z.executeQuery();
      if (resultSet.next()) {
        int i = resultSet.getInt("id");
        int j = resultSet.getInt("type");
        String str1 = resultSet.getString("owner");
        String str2 = resultSet.getString("password");
        String str3 = resultSet.getString("date");
        Entity entity = new Entity();
        entity.setID(i);
        entity.setType(j);
        entity.setOwner(str1);
        entity.setPassword(str2);
        entity.setX(paramInt1);
        entity.setY(paramInt2);
        entity.setZ(paramInt3);
        entity.setDate(str3);
        resultSet.close();
        Performance.addPhysDBQuery();
        return entity;
      } 
      resultSet.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return null;
  }
  
  public void registerProtectedEntity(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, int paramInt4) {
    try {
      this._insert_protectedEntity_type_player_password_x_y_z.setInt(1, paramInt1);
      this._insert_protectedEntity_type_player_password_x_y_z.setString(2, paramString1);
      this._insert_protectedEntity_type_player_password_x_y_z.setString(3, paramString2);
      this._insert_protectedEntity_type_player_password_x_y_z.setInt(4, paramInt2);
      this._insert_protectedEntity_type_player_password_x_y_z.setInt(5, paramInt3);
      this._insert_protectedEntity_type_player_password_x_y_z.setInt(6, paramInt4);
      this._insert_protectedEntity_type_player_password_x_y_z.setString(7, (new Timestamp((new Date()).getTime())).toString());
      this._insert_protectedEntity_type_player_password_x_y_z.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerProtectionLimit(int paramInt1, int paramInt2, String paramString) {
    try {
      unregisterProtectionLimit(paramInt1, paramString.toLowerCase());
      this._insert_protectedLimit_type_amount_entity.setInt(1, paramInt1);
      this._insert_protectedLimit_type_amount_entity.setInt(2, paramInt2);
      this._insert_protectedLimit_type_amount_entity.setString(3, paramString.toLowerCase());
      this._insert_protectedLimit_type_amount_entity.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void registerProtectionRights(int paramInt1, String paramString, int paramInt2, byte b1) {
    try {
      this._insert_rights_ID_entity_rights_type.setInt(1, paramInt1);
      this._insert_rights_ID_entity_rights_type.setString(2, paramString.toLowerCase());
      this._insert_rights_ID_entity_rights_type.setInt(3, paramInt2);
      this._insert_rights_ID_entity_rights_type.setByte(4, b1);
      this._insert_rights_ID_entity_rights_type.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectedEntity(int paramInt) {
    try {
      this._delete_protectedEntity_ID.setInt(1, paramInt);
      this._delete_protectedEntity_ID.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    unregisterProtectionRights(paramInt);
  }
  
  public void unregisterProtectedEntity(int paramInt1, int paramInt2, int paramInt3) {
    try {
      this._delete_protectedEntity_x_y_z.setInt(1, paramInt1);
      this._delete_protectedEntity_x_y_z.setInt(2, paramInt2);
      this._delete_protectedEntity_x_y_z.setInt(3, paramInt3);
      this._delete_protectedEntity_x_y_z.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectionEntities() {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate("DELETE FROM `protections`");
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectionLimit(int paramInt, String paramString) {
    try {
      this._delete_limit_type_entity.setInt(1, paramInt);
      this._delete_limit_type_entity.setString(2, paramString.toLowerCase());
      this._delete_limit_type_entity.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectionLimits() {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate("DELETE FROM `limits`");
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectionRights() {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate("DELETE FROM `rights`");
      statement.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectionRights(int paramInt) {
    try {
      this._delete_rights_ID.setInt(1, paramInt);
      this._delete_rights_ID.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void unregisterProtectionRights(int paramInt, String paramString) {
    try {
      this._delete_rights_ID_entity.setInt(1, paramInt);
      this._delete_rights_ID_entity.setString(2, paramString.toLowerCase());
      this._delete_rights_ID_entity.executeUpdate();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  private void doUpdate140() {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeQuery("SELECT `id` FROM `protections`");
      statement.close();
      Performance.addPhysDBQuery();
    } catch (Exception exception) {
      log("Outdated database!");
      log("UPGRADING FROM 1.30 TO 1.40");
      log("Renaming table `chests` to `protections`");
      try {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("ALTER TABLE `chests` RENAME TO `protections`");
        statement.close();
        Performance.addPhysDBQuery();
      } catch (Exception exception1) {
        exception1.printStackTrace();
      } 
    } 
  }
  
  private void loadPreparedStatements() throws SQLException {
    this._select_protectedEntity_ID = this.connection.prepareStatement("SELECT * FROM `protections` WHERE `id` = ?");
    this._select_chestExist_ID = this.connection.prepareStatement("SELECT COUNT(*) AS count FROM `protections` WHERE `id` = ?");
    this._select_chestCount_user = this.connection.prepareStatement("SELECT `id` FROM `protections` WHERE `owner` = ?");
    this._select_limit_type_entity = this.connection.prepareStatement("SELECT `amount` FROM `limits` WHERE `type` = ? AND `entity` = ?");
    this._select_privateAccess_type_ID_entities = this.connection.prepareStatement("SELECT `entity`, `rights` FROM `rights` WHERE `type` = ? AND `chest` = ?");
    this._select_protectedEntity_x_y_z_radius = this.connection.prepareStatement("SELECT * FROM `protections` WHERE x >= ? AND x <= ? AND y >= ? AND y <= ? AND z >= ? AND z <= ?");
    this._select_protectedEntity_x_y_z = this.connection.prepareStatement("SELECT `id`, `type`, `owner`, `password`, `date` FROM `protections` WHERE `x` = ? AND `y` = ? AND `z` = ?");
    this._insert_protectedEntity_type_player_password_x_y_z = this.connection.prepareStatement("INSERT INTO `protections` (type, owner, password, x, y, z, date) VALUES(?, ?, ?, ?, ?, ?, ?)");
    this._insert_protectedLimit_type_amount_entity = this.connection.prepareStatement("INSERT INTO `limits` (type, amount, entity) VALUES(?, ?, ?)");
    this._insert_rights_ID_entity_rights_type = this.connection.prepareStatement("INSERT INTO `rights` (chest, entity, rights, type) VALUES (?, ?, ?, ?)");
    this._delete_protectedEntity_ID = this.connection.prepareStatement("DELETE FROM `protections` WHERE `id` = ?");
    this._delete_protectedEntity_x_y_z = this.connection.prepareStatement("DELETE FROM `protections` WHERE `x` = ? AND `y` = ? AND `z` = ?");
    this._delete_limit_type_entity = this.connection.prepareStatement("DELETE FROM `limits` WHERE `type` = ? AND `entity` = ?");
    this._delete_rights_ID = this.connection.prepareStatement("DELETE FROM `rights` WHERE `chest` = ?");
    this._delete_rights_ID_entity = this.connection.prepareStatement("DELETE FROM `rights` WHERE `chest` = ? AND `entity` = ?");
  }
}


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraft\sql\PhysDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */