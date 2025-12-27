/*    */ package com.sk89q.worldguard.domains;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
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
/*    */ public class DomainCollection
/*    */   implements Domain
/*    */ {
/* 30 */   private Set<Domain> domains = new LinkedHashSet<Domain>();
/*    */ 
/*    */   
/*    */   public void add(Domain domain) {
/* 34 */     this.domains.add(domain);
/*    */   }
/*    */   
/*    */   public void remove(Domain domain) {
/* 38 */     this.domains.remove(domain);
/*    */   }
/*    */   
/*    */   public int size() {
/* 42 */     return this.domains.size();
/*    */   }
/*    */   
/*    */   public boolean contains(LocalPlayer player) {
/* 46 */     for (Domain domain : this.domains) {
/* 47 */       if (domain.contains(player)) {
/* 48 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\domains\DomainCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */