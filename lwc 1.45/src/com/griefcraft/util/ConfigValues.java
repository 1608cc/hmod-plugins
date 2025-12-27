/*    */ package com.griefcraft.util;
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
/*    */ public enum ConfigValues
/*    */ {
/* 24 */   BLACKLISTED_MODES("blacklisted-modes", ""), ALLOW_FURNACE_PROTECTION("furnace-locks", "true"),
/* 25 */   DB_PATH("db-path", "lwc.db"), CUBOID_SAFE_AREAS("only-protect-in-cuboid-safe-zones", "false"),
/* 26 */   AUTO_UPDATE("auto-update", "false");
/*    */ 
/*    */ 
/*    */   
/*    */   private String name;
/*    */ 
/*    */ 
/*    */   
/*    */   private String defaultValue;
/*    */ 
/*    */ 
/*    */   
/*    */   ConfigValues(String name, String defaultValue) {
/* 39 */     this.name = name;
/* 40 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBool() {
/* 47 */     return getString().equalsIgnoreCase("true");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDefaultValue() {
/* 54 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInt() {
/* 61 */     return Integer.parseInt(getString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 68 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getString() {
/* 75 */     return Config.getInstance().getProperty(this.name, this.defaultValue);
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraf\\util\ConfigValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */