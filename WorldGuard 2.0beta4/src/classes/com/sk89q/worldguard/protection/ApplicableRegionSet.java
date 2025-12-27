/*     */ package classes.com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.protection.AreaFlags;
/*     */ import com.sk89q.worldguard.protection.ProtectedRegion;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class ApplicableRegionSet
/*     */ {
/*     */   private Vector pt;
/*     */   private Map<String, ProtectedRegion> regions;
/*     */   
/*     */   public ApplicableRegionSet(Vector pt, Map<String, ProtectedRegion> regions) {
/*  16 */     this.pt = pt;
/*  17 */     this.regions = regions;
/*     */   }
/*     */   
/*     */   public boolean canBuild(LocalPlayer player) {
/*  21 */     boolean allowed = false;
/*  22 */     boolean found = false;
/*     */     
/*  24 */     for (ProtectedRegion region : this.regions.values()) {
/*  25 */       if (!region.contains(this.pt))
/*  26 */         continue;  if ((region.getFlags()).allowBuild == AreaFlags.State.DENY) return false;
/*     */       
/*  28 */       found = true;
/*     */       
/*  30 */       if (!allowed && (region.getFlags()).allowBuild == AreaFlags.State.ALLOW) {
/*  31 */         allowed = true;
/*     */       }
/*     */       
/*  34 */       if (!allowed && region.getOwners().contains(player)) {
/*  35 */         allowed = true;
/*     */       }
/*     */     } 
/*     */     
/*  39 */     return found ? allowed : true;
/*     */   }
/*     */   
/*     */   public boolean allowsPvP() {
/*  43 */     for (ProtectedRegion region : this.regions.values()) {
/*  44 */       if (region.contains(this.pt) && 
/*  45 */         (region.getFlags()).allowPvP == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsMobDamage() {
/*  52 */     for (ProtectedRegion region : this.regions.values()) {
/*  53 */       if (region.contains(this.pt) && 
/*  54 */         (region.getFlags()).allowMobDamage == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsCreeperExplosions() {
/*  61 */     for (ProtectedRegion region : this.regions.values()) {
/*  62 */       if (region.contains(this.pt) && 
/*  63 */         (region.getFlags()).allowCreeperExplosions == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsTNT() {
/*  70 */     for (ProtectedRegion region : this.regions.values()) {
/*  71 */       if (region.contains(this.pt) && 
/*  72 */         (region.getFlags()).allowTNT == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsLighter() {
/*  79 */     for (ProtectedRegion region : this.regions.values()) {
/*  80 */       if (region.contains(this.pt) && 
/*  81 */         (region.getFlags()).allowLighter == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsFireSpread() {
/*  88 */     for (ProtectedRegion region : this.regions.values()) {
/*  89 */       if (region.contains(this.pt) && 
/*  90 */         (region.getFlags()).allowFireSpread == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   public boolean allowsLavaFire() {
/*  97 */     for (ProtectedRegion region : this.regions.values()) {
/*  98 */       if (region.contains(this.pt) && 
/*  99 */         (region.getFlags()).allowLavaFire == AreaFlags.State.DENY) return false;
/*     */     
/*     */     } 
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\protection\ApplicableRegionSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */