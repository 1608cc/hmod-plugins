/*    */ package com.griefcraft.sql;
/*    */ 
/*    */ import com.griefcraft.logging.Logger;
/*    */ import com.griefcraft.util.ConfigValues;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Database
/*    */ {
/*    */   public static boolean isConnected() {
/* 32 */     return connected;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   private Logger logger = Logger.getLogger(getClass().getSimpleName());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public Connection connection = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean connected = false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean connect() throws Exception {
/* 56 */     if (this.connection != null) {
/* 57 */       return true;
/*    */     }
/*    */     
/* 60 */     Class.forName("org.sqlite.JDBC");
/* 61 */     this.connection = DriverManager.getConnection("jdbc:sqlite:" + getDatabasePath());
/* 62 */     connected = true;
/*    */     
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDatabasePath() {
/* 79 */     return ConfigValues.DB_PATH.getString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void load();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(String str) {
/* 91 */     this.logger.info(str);
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraft\sql\Database.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */