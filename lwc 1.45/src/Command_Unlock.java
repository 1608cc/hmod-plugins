/*    */ import com.griefcraft.model.Entity;
/*    */ import com.griefcraft.util.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Command_Unlock
/*    */   implements Command
/*    */ {
/*    */   public void execute(LWC lwc, Player player, String[] args) {
/* 11 */     if (args.length < 1) {
/* 12 */       player.sendMessage("§4Usage: §6/lwc -u <Password>");
/*    */       
/*    */       return;
/*    */     } 
/* 16 */     String password = StringUtils.join(args, 1);
/* 17 */     password = lwc.encrypt(password);
/*    */     
/* 19 */     if (!lwc.getMemoryDatabase().hasPendingUnlock(player.getName())) {
/* 20 */       player.sendMessage("§4Nothing selected. Open a locked Chest/Furnace first.");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 25 */     int chestID = lwc.getMemoryDatabase().getUnlockID(player.getName());
/*    */     
/* 27 */     if (chestID == -1) {
/* 28 */       player.sendMessage("§4[lwc] Internal error. [ulock]");
/*    */       
/*    */       return;
/*    */     } 
/* 32 */     Entity entity = lwc.getPhysicalDatabase().loadProtectedEntity(chestID);
/*    */     
/* 34 */     if (entity.getType() != 1) {
/* 35 */       player.sendMessage("§4That is not password protected!");
/*    */       
/*    */       return;
/*    */     } 
/* 39 */     if (entity.getPassword().equals(password)) {
/* 40 */       player.sendMessage("§2Password accepted.");
/* 41 */       lwc.getMemoryDatabase().unregisterUnlock(player.getName());
/* 42 */       lwc.getMemoryDatabase().registerPlayer(player.getName(), chestID);
/*    */       
/* 44 */       for (ComplexBlock entity_ : lwc.getEntitySet(entity.getX(), entity.getY(), entity.getZ())) {
/* 45 */         if (entity_ != null) {
/* 46 */           entity_.update();
/*    */         }
/*    */       } 
/*    */     } else {
/* 50 */       player.sendMessage("§4Invalid password.");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate(LWC lwc, Player player, String[] args) {
/* 57 */     return !(!StringUtils.hasFlag(args, "u") && !StringUtils.hasFlag(args, "unlock"));
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\Command_Unlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */