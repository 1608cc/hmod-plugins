/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class ApplicableRegionSet
/*     */ {
/*     */   private Vector pt;
/*     */   private Map<String, ProtectedRegion> regions;
/*     */   
/*     */   public ApplicableRegionSet(Vector pt, Map<String, ProtectedRegion> regions) {
/*  14 */     this.pt = pt;
/*  15 */     this.regions = regions;
/*     */   }
/*     */   
/*     */   public boolean canBuild(LocalPlayer player) {
/*  19 */     boolean allowed = false;
/*  20 */     boolean found = false;
/*     */     
/*  22 */     for (ProtectedRegion region : this.regions.values()) {
/*  23 */       if (!region.contains(this.pt))
/*  24 */         continue;  if ((region.getFlags()).allowBuild == AreaFlags.State.DENY) return false;
/*     */       
/*  26 */       found = true;
/*     */       
/*  28 */       if (!allowed && (region.getFlags()).allowBuild == AreaFlags.State.ALLOW) {
/*  29 */         allowed = true;
/*     */       }
/*     */       
/*  32 */       if (!allowed && region.getOwners().contains(player)) {
/*  33 */         allowed = true;
/*     */       }
/*     */     } 
/*     */     
/*  37 */     return found ? allowed : true;
/*     */   }
/*     */   
/*     */   public boolean allowsPvP() {
/*  41 */     for (ProtectedRegion region : this.regions.values()) {
/*  42 */       if (region.contains(this.pt) && 
/*  43 */         (region.getFlags()).allowPvP == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  46 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsMobDamage() {
/*  50 */     for (ProtectedRegion region : this.regions.values()) {
/*  51 */       if (region.contains(this.pt) && 
/*  52 */         (region.getFlags()).allowMobDamage == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  55 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsCreeperExplosions() {
/*  59 */     for (ProtectedRegion region : this.regions.values()) {
/*  60 */       if (region.contains(this.pt) && 
/*  61 */         (region.getFlags()).allowCreeperExplosions == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  64 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsTNT() {
/*  68 */     for (ProtectedRegion region : this.regions.values()) {
/*  69 */       if (region.contains(this.pt) && 
/*  70 */         (region.getFlags()).allowTNT == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsLighter() {
/*  77 */     for (ProtectedRegion region : this.regions.values()) {
/*  78 */       if (region.contains(this.pt) && 
/*  79 */         (region.getFlags()).allowLighter == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsFireSpread() {
/*  86 */     for (ProtectedRegion region : this.regions.values()) {
/*  87 */       if (region.contains(this.pt) && 
/*  88 */         (region.getFlags()).allowFireSpread == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsLavaFire() {
/*  95 */     for (ProtectedRegion region : this.regions.values()) {
/*  96 */       if (region.contains(this.pt) && 
/*  97 */         (region.getFlags()).allowLavaFire == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\ApplicableRegionSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */