/*    */ package com.griefcraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpdaterFile
/*    */ {
/*    */   private String remoteLocation;
/*    */   private String localLocation;
/*    */   
/*    */   public UpdaterFile(String location) {
/* 16 */     this.remoteLocation = location;
/* 17 */     this.localLocation = location;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRemoteLocation() {
/* 24 */     return this.remoteLocation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalLocation() {
/* 31 */     return this.localLocation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRemoteLocation(String remoteLocation) {
/* 40 */     this.remoteLocation = remoteLocation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLocalLocation(String localLocation) {
/* 49 */     this.localLocation = localLocation;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraf\\util\UpdaterFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */