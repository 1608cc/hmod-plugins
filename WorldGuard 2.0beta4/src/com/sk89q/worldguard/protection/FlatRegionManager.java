/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatRegionManager
/*     */   implements RegionManager
/*     */ {
/*  45 */   private Map<String, ProtectedRegion> regions = new LinkedHashMap<String, ProtectedRegion>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, ProtectedRegion> getRegions() {
/*  54 */     return this.regions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRegion(String id, ProtectedRegion region) {
/*  64 */     this.regions.put(id, region);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRegion(String id) {
/*  73 */     this.regions.remove(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRegion(String id) {
/*  83 */     return this.regions.containsKey(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtectedRegion getRegion(String id) {
/*  92 */     return this.regions.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegions(Map<String, ProtectedRegion> regions) {
/* 101 */     this.regions = regions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicableRegionSet getApplicableRegions(Vector pt) {
/* 111 */     return new ApplicableRegionSet(pt, this.regions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getApplicableRegionsIDs(Vector pt) {
/* 121 */     List<String> applicable = new ArrayList<String>();
/*     */     
/* 123 */     for (Map.Entry<String, ProtectedRegion> entry : this.regions.entrySet()) {
/* 124 */       if (((ProtectedRegion)entry.getValue()).contains(pt)) {
/* 125 */         applicable.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return applicable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 138 */     return this.regions.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\FlatRegionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */