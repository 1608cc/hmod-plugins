/*    */ package classes;
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
/*    */ public class BlacklistTrackedEvent
/*    */ {
/*    */   private int id;
/*    */   private long time;
/*    */   
/*    */   public BlacklistTrackedEvent(int id, long time) {
/* 35 */     this.id = id;
/* 36 */     this.time = time;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 43 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setId(int id) {
/* 50 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getTime() {
/* 57 */     return this.time;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTime(long time) {
/* 64 */     this.time = time;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\BlacklistTrackedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */