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
/* 46 */     this.path = path;
/* 47 */     this.writer = writer;
/* 48 */     this.lastUse = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 57 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BufferedWriter getWriter() {
/* 64 */     return this.writer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getLastUse() {
/* 71 */     return this.lastUse;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLastUse() {
/* 80 */     this.lastUse = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(FileLoggerWriter other) {
/* 90 */     if (this.lastUse > other.lastUse)
/* 91 */       return 1; 
/* 92 */     if (this.lastUse < other.lastUse) {
/* 93 */       return -1;
/*    */     }
/* 95 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\FileLoggerWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */