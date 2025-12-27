/*    */ package classes.com.sk89q.worldguard.protection;
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
/*    */ public class AreaFlags
/*    */ {
/* 33 */   public State allowBuild = State.NONE;
/* 34 */   public State allowPvP = State.NONE;
/* 35 */   public State allowMobDamage = State.NONE;
/* 36 */   public State allowCreeperExplosions = State.NONE;
/* 37 */   public State allowTNT = State.NONE;
/* 38 */   public State allowLighter = State.NONE;
/* 39 */   public State allowFireSpread = State.NONE;
/* 40 */   public State allowLavaFire = State.NONE;
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 44 */     if (!(obj instanceof com.sk89q.worldguard.protection.AreaFlags)) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     com.sk89q.worldguard.protection.AreaFlags other = (com.sk89q.worldguard.protection.AreaFlags)obj;
/*    */     
/* 50 */     if (other.allowBuild == this.allowBuild && 
/* 51 */       other.allowPvP == this.allowPvP && 
/* 52 */       other.allowMobDamage == this.allowMobDamage && 
/* 53 */       other.allowCreeperExplosions == this.allowCreeperExplosions && 
/* 54 */       other.allowTNT == this.allowTNT && 
/* 55 */       other.allowLighter == this.allowLighter && 
/* 56 */       other.allowFireSpread == this.allowFireSpread && 
/* 57 */       other.allowLavaFire == this.allowLavaFire) return true; 
/*    */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\protection\AreaFlags.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */