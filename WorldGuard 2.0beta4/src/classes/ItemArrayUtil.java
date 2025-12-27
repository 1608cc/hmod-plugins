/*    */ package classes;
/*    */ 
/*    */ import Item;
/*    */ import ItemArray;
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
/*    */ public class ItemArrayUtil
/*    */ {
/*    */   public static void setContents(ItemArray<?> itemArray, Item[] contents) {
/* 33 */     int size = itemArray.getContentsSize();
/*    */     
/* 35 */     for (int i = 0; i < size; i++) {
/* 36 */       if (contents[i] == null) {
/* 37 */         itemArray.removeItem(i);
/*    */       } else {
/* 39 */         itemArray.setSlot(contents[i], i);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\ItemArrayUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */