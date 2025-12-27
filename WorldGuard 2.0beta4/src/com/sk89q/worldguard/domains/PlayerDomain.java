/*    */ package com.sk89q.worldguard.domains;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import java.util.HashSet;
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
/*    */ public class PlayerDomain
/*    */   implements Domain
/*    */ {
/*    */   private Set<String> players;
/*    */   
/*    */   public PlayerDomain() {
/* 30 */     this.players = new HashSet<String>();
/*    */   }
/*    */   
/*    */   public PlayerDomain(String[] players) {
/* 34 */     this.players = new HashSet<String>();
/*    */     
/* 36 */     for (String name : players) {
/* 37 */       this.players.add(name.toLowerCase());
/*    */     }
/*    */   }
/*    */   
/*    */   public void addPlayer(String name) {
/* 42 */     this.players.add(name.toLowerCase());
/*    */   }
/*    */   
/*    */   public boolean contains(LocalPlayer player) {
/* 46 */     return this.players.contains(player.getName().toLowerCase());
/*    */   }
/*    */   
/*    */   public int size() {
/* 50 */     return this.players.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\domains\PlayerDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */