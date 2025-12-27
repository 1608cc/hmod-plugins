/*     */ package classes.com.sk89q.worldguard.domains;
/*     */ 
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.domains.Domain;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
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
/*     */ public class DefaultDomain
/*     */   implements Domain
/*     */ {
/*  33 */   private Set<String> groups = new LinkedHashSet<String>();
/*  34 */   private Set<String> players = new HashSet<String>();
/*     */ 
/*     */   
/*     */   public void addPlayer(String name) {
/*  38 */     this.players.add(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void addPlayer(LocalPlayer player) {
/*  42 */     this.players.add(player.getName().toLowerCase());
/*     */   }
/*     */   
/*     */   public void removePlayer(String name) {
/*  46 */     this.players.remove(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void removePlayer(LocalPlayer player) {
/*  50 */     this.players.remove(player.getName().toLowerCase());
/*     */   }
/*     */   
/*     */   public void addGroup(String name) {
/*  54 */     this.groups.add(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void removeGroup(String name) {
/*  58 */     this.groups.remove(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public Set<String> getGroups() {
/*  62 */     return this.groups;
/*     */   }
/*     */   
/*     */   public Set<String> getPlayers() {
/*  66 */     return this.players;
/*     */   }
/*     */   
/*     */   public boolean contains(LocalPlayer player) {
/*  70 */     if (this.players.contains(player.getName().toLowerCase())) {
/*  71 */       return true;
/*     */     }
/*     */     
/*  74 */     for (String group : this.groups) {
/*  75 */       if (player.hasGroup(group)) {
/*  76 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public int size() {
/*  84 */     return this.groups.size();
/*     */   }
/*     */   
/*     */   public String toPlayersString() {
/*  88 */     StringBuilder str = new StringBuilder();
/*  89 */     for (Iterator<String> it = this.players.iterator(); it.hasNext(); ) {
/*  90 */       str.append(it.next());
/*  91 */       if (it.hasNext()) {
/*  92 */         str.append(", ");
/*     */       }
/*     */     } 
/*  95 */     return str.toString();
/*     */   }
/*     */   
/*     */   public String toGroupsString() {
/*  99 */     StringBuilder str = new StringBuilder();
/* 100 */     for (Iterator<String> it = this.groups.iterator(); it.hasNext(); ) {
/* 101 */       str.append(it.next());
/* 102 */       if (it.hasNext()) {
/* 103 */         str.append(", ");
/*     */       }
/*     */     } 
/* 106 */     return str.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\domains\DefaultDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */