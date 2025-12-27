/*    */ package classes;
/*    */ 
/*    */ import Player;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogRecord;
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
/*    */ public class LoggerToChatHandler
/*    */   extends Handler
/*    */ {
/*    */   private Player player;
/*    */   
/*    */   public LoggerToChatHandler(Player player) {
/* 40 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void publish(LogRecord record) {
/* 59 */     this.player.sendMessage("§7" + record.getLevel().getName() + ": " + 
/* 60 */         "§f" + record.getMessage());
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\LoggerToChatHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */