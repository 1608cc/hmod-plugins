/*    */ package classes.com.sk89q.worldguard;
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
/*    */ public class TestPlayer
/*    */   extends LocalPlayer
/*    */ {
/*    */   private String name;
/* 28 */   private Set<String> groups = new HashSet<String>();
/*    */   
/*    */   public TestPlayer(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void addGroup(String group) {
/* 35 */     this.groups.add(group.toLowerCase());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 40 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasGroup(String group) {
/* 45 */     return this.groups.contains(group.toLowerCase());
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\TestPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */