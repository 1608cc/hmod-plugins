/*    */ package classes.com.sk89q.worldguard.domains;
/*    */ 
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.domains.Domain;
/*    */ import java.util.Arrays;
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
/*    */ public class GroupDomain
/*    */   implements Domain
/*    */ {
/*    */   private Set<String> groups;
/*    */   
/*    */   public GroupDomain() {
/* 31 */     this.groups = new LinkedHashSet<String>();
/*    */   }
/*    */   
/*    */   public GroupDomain(String[] groups) {
/* 35 */     this.groups = new LinkedHashSet<String>(Arrays.asList(groups));
/*    */   }
/*    */   
/*    */   public void addGroup(String name) {
/* 39 */     this.groups.add(name);
/*    */   }
/*    */   
/*    */   public boolean contains(LocalPlayer player) {
/* 43 */     for (String group : this.groups) {
/* 44 */       if (player.hasGroup(group)) {
/* 45 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public int size() {
/* 53 */     return this.groups.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\domains\GroupDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */