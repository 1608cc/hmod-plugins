/*    */ package com.sk89q.worldguard.protection;
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
/*    */   public enum State
/*    */   {
/* 28 */     NONE,
/* 29 */     ALLOW,
/* 30 */     DENY;
/*    */   }
/*    */   
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
/* 44 */     if (!(obj instanceof AreaFlags)) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     AreaFlags other = (AreaFlags)obj;
/*    */     
/* 50 */     return (other.allowBuild == this.allowBuild && other.allowPvP == this.allowPvP && other.allowMobDamage == this.allowMobDamage && other.allowCreeperExplosions == this.allowCreeperExplosions && other.allowTNT == this.allowTNT && other.allowLighter == this.allowLighter && other.allowFireSpread == this.allowFireSpread && other.allowLavaFire == this.allowLavaFire);
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\AreaFlags.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */