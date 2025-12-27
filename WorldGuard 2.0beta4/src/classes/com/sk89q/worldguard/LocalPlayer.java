/*    */ package classes.com.sk89q.worldguard;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LocalPlayer
/*    */ {
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract boolean hasGroup(String paramString);
/*    */   
/*    */   public boolean equals(Object obj) {
/* 40 */     if (!(obj instanceof com.sk89q.worldguard.LocalPlayer)) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     return ((com.sk89q.worldguard.LocalPlayer)obj).getName().equals(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return getName().hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\LocalPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */