/*    */ package classes.com.sk89q.worldguard.domains;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.domains.Domain;
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
/* 34 */     this.players = new HashSet<String>(); byte b; int i;
/*    */     String[] arrayOfString;
/* 36 */     for (i = (arrayOfString = players).length, b = 0; b < i; ) { String name = arrayOfString[b];
/* 37 */       this.players.add(name.toLowerCase());
/*    */       b++; }
/*    */   
/*    */   }
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


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\domains\PlayerDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */