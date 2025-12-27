/*    */ package com.sk89q.worldguard.protection;
/*    */ 
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class LinearRegionManager
/*    */   implements RegionManager
/*    */ {
/* 43 */   private Map<String, ProtectedRegion> regions = new LinkedHashMap<String, ProtectedRegion>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, ProtectedRegion> getRegions() {
/* 52 */     return this.regions;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRegion(String id, ProtectedRegion region) {
/* 62 */     this.regions.put(id, region);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeRegion(String id) {
/* 71 */     this.regions.remove(id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRegions(Map<String, ProtectedRegion> regions) {
/* 80 */     this.regions = regions;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ApplicableRegionSet getApplicableRegions(Vector pt) {
/* 90 */     return new ApplicableRegionSet(pt, this.regions);
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\LinearRegionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */