/*    */ package com.sk89q.worldguard;
/*    */ 
/*    */ import java.io.BufferedWriter;
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
/*    */ 
/*    */ 
/*    */ public class FileLoggerWriter
/*    */   implements Comparable<FileLoggerWriter>
/*    */ {
/*    */   public String path;
/*    */   private BufferedWriter writer;
/*    */   private long lastUse;
/*    */   
/*    */   public FileLoggerWriter(String path, BufferedWriter writer) {
/* 48 */     this.path = path;
/* 49 */     this.writer = writer;
/* 50 */     this.lastUse = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 59 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BufferedWriter getWriter() {
/* 66 */     return this.writer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getLastUse() {
/* 73 */     return this.lastUse;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLastUse() {
/* 82 */     this.lastUse = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(FileLoggerWriter other) {
/* 92 */     if (this.lastUse > other.lastUse)
/* 93 */       return 1; 
/* 94 */     if (this.lastUse < other.lastUse) {
/* 95 */       return -1;
/*    */     }
/* 97 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\FileLoggerWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */