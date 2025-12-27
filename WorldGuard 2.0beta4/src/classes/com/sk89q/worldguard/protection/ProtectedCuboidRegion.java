/*    */ package classes.com.sk89q.worldguard.protection;
/*    */ 
/*    */ import com.sk89q.worldedit.BlockVector;
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldguard.protection.ProtectedRegion;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtectedCuboidRegion
/*    */   extends ProtectedRegion
/*    */ {
/*    */   private BlockVector min;
/*    */   private BlockVector max;
/*    */   
/*    */   public ProtectedCuboidRegion(BlockVector min, BlockVector max) {
/* 48 */     this.min = min;
/* 49 */     this.max = max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockVector getMinimumPoint() {
/* 58 */     return this.min;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMinimumPoint(BlockVector pt) {
/* 67 */     this.min = pt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockVector getMaximumPoint() {
/* 76 */     return this.max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMaximumPoint(BlockVector pt) {
/* 85 */     this.max = pt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(Vector pt) {
/* 90 */     int x = pt.getBlockX();
/* 91 */     int y = pt.getBlockY();
/* 92 */     int z = pt.getBlockZ();
/* 93 */     if (x >= this.min.getBlockX() && x <= this.max.getBlockX() && 
/* 94 */       y >= this.min.getBlockY() && y <= this.max.getBlockY() && 
/* 95 */       z >= this.min.getBlockZ() && z <= this.max.getBlockZ()) return true; 
/*    */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\protection\ProtectedCuboidRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */