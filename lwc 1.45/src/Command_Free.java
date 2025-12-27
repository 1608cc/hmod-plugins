/*    */ import com.griefcraft.util.StringUtils;
/*    */ 
/*    */ 
/*    */ public class Command_Free
/*    */   implements Command
/*    */ {
/*    */   public void execute(LWC lwc, Player player, String[] args) {
/*  8 */     if (args.length < 2) {
/*  9 */       lwc.sendSimpleUsage(player, "/lwc -r <chest|furnace|modes>");
/*    */       
/*    */       return;
/*    */     } 
/* 13 */     String type = args[1].toLowerCase();
/*    */     
/* 15 */     if (type.equals("chest") || type.equals("furnace")) {
/* 16 */       if (lwc.getMemoryDatabase().hasPendingChest(player.getName())) {
/* 17 */         player.sendMessage("§4You already have a pending action.");
/*    */         
/*    */         return;
/*    */       } 
/* 21 */       lwc.getMemoryDatabase().unregisterAllActions(player.getName());
/* 22 */       lwc.getMemoryDatabase().registerAction("free", player.getName());
/* 23 */       player.sendMessage("§aLeft click your Chest or Furnace to remove the lock");
/*    */     
/*    */     }
/* 26 */     else if (type.equals("modes")) {
/* 27 */       lwc.getMemoryDatabase().unregisterAllModes(player.getName());
/*    */       
/* 29 */       player.sendMessage("§2Successfully removed all set modes.");
/*    */     }
/*    */     else {
/*    */       
/* 33 */       lwc.sendSimpleUsage(player, "/lwc -r <chest|furnace|modes>");
/*    */       return;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(LWC lwc, Player player, String[] args) {
/* 40 */     return !(!StringUtils.hasFlag(args, "r") && !StringUtils.hasFlag(args, "free") && !StringUtils.hasFlag(args, "remove"));
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\Command_Free.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */