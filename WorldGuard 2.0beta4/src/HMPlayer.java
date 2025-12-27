/*    */ import com.sk89q.worldguard.LocalPlayer;
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
/*    */ public class HMPlayer
/*    */   extends LocalPlayer
/*    */ {
/*    */   private Player player;
/*    */   
/*    */   public HMPlayer(Player player) {
/* 26 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 31 */     return this.player.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasGroup(String group) {
/* 36 */     return this.player.isInGroup(group);
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\HMPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */