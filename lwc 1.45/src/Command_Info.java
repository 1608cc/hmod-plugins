/*    */ import com.griefcraft.util.StringUtils;
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
/*    */ public class Command_Info
/*    */   implements Command
/*    */ {
/*    */   public void execute(LWC lwc, Player player, String[] args) {
/* 24 */     lwc.getMemoryDatabase().unregisterAllActions(player.getName());
/* 25 */     lwc.getMemoryDatabase().registerAction("info", player.getName());
/* 26 */     player.sendMessage("§aLeft click a Chest or Furnace to see information about it");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(LWC lwc, Player player, String[] args) {
/* 31 */     return !(!StringUtils.hasFlag(args, "i") && !StringUtils.hasFlag(args, "info"));
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\Command_Info.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */