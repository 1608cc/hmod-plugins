/*    */ package com.griefcraft.logging;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Logger
/*    */ {
/*    */   private String name;
/*    */   
/*    */   public static Logger getLogger(String name) {
/* 14 */     return new Logger(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Logger(String name) {
/* 23 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void info(String str) {
/* 32 */     log(str);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(String str) {
/* 41 */     System.out.println(format(str));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String format(String msg) {
/* 51 */     return String.format("%s\t[v%.2f]\t%s", new Object[] { this.name, Double.valueOf(1.45D), msg });
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraft\logging\Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */