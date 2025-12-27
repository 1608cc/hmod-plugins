/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlacklistLogger
/*     */   implements BlacklistLoggerHandler
/*     */ {
/*  31 */   private Set<BlacklistLoggerHandler> handlers = new HashSet<BlacklistLoggerHandler>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHandler(BlacklistLoggerHandler handler) {
/*  40 */     this.handlers.add(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHandler(BlacklistLoggerHandler handler) {
/*  49 */     this.handlers.remove(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearHandlers() {
/*  58 */     this.handlers.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyAttempt(Player player, Block block, String comment) {
/*  68 */     for (BlacklistLoggerHandler handler : this.handlers) {
/*  69 */       handler.logDestroyAttempt(player, block, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logBreakAttempt(Player player, Block block, String comment) {
/*  80 */     for (BlacklistLoggerHandler handler : this.handlers) {
/*  81 */       handler.logBreakAttempt(player, block, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logUseAttempt(Player player, int item, String comment) {
/*  92 */     for (BlacklistLoggerHandler handler : this.handlers) {
/*  93 */       handler.logUseAttempt(player, item, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logRightClickAttempt(Player player, Block block, String comment) {
/* 104 */     for (BlacklistLoggerHandler handler : this.handlers) {
/* 105 */       handler.logRightClickAttempt(player, block, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyWithAttempt(Player player, int item, String comment) {
/* 116 */     for (BlacklistLoggerHandler handler : this.handlers) {
/* 117 */       handler.logDestroyWithAttempt(player, item, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logPlaceAttempt(Player player, int item, String comment) {
/* 128 */     for (BlacklistLoggerHandler handler : this.handlers) {
/* 129 */       handler.logPlaceAttempt(player, item, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDropAttempt(Player player, int item, String comment) {
/* 140 */     for (BlacklistLoggerHandler handler : this.handlers) {
/* 141 */       handler.logDropAttempt(player, item, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logAcquireAttempt(Player player, int item, String comment) {
/* 152 */     for (BlacklistLoggerHandler handler : this.handlers) {
/* 153 */       handler.logAcquireAttempt(player, item, comment);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 161 */     for (BlacklistLoggerHandler handler : this.handlers)
/* 162 */       handler.close(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\BlacklistLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */