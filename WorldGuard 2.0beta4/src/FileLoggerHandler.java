/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ public class FileLoggerHandler
/*     */   implements BlacklistLoggerHandler
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static Pattern pattern = Pattern.compile("%.");
/*     */ 
/*     */ 
/*     */   
/*  48 */   private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private int cacheSize = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   private String pathPattern;
/*     */ 
/*     */ 
/*     */   
/*  62 */   private TreeMap<String, FileLoggerWriter> writers = new TreeMap<String, FileLoggerWriter>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileLoggerHandler(String pathPattern) {
/*  71 */     this.pathPattern = pathPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileLoggerHandler(String pathPattern, int cacheSize) {
/*  81 */     if (cacheSize < 1) {
/*  82 */       throw new IllegalArgumentException("Cache size cannot be less than 1");
/*     */     }
/*  84 */     this.pathPattern = pathPattern;
/*  85 */     this.cacheSize = cacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildPath(String playerName) {
/*  94 */     GregorianCalendar calendar = new GregorianCalendar();
/*     */     
/*  96 */     Matcher m = pattern.matcher(this.pathPattern);
/*  97 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/*     */     
/* 100 */     while (m.find()) {
/* 101 */       String group = m.group();
/* 102 */       String rep = "?";
/*     */       
/* 104 */       if (group.matches("%%")) {
/* 105 */         rep = "%";
/* 106 */       } else if (group.matches("%u")) {
/* 107 */         rep = playerName.toLowerCase().replaceAll("[^A-Za-z0-9_]", "_");
/* 108 */         if (rep.length() > 32) {
/* 109 */           rep = rep.substring(0, 32);
/*     */         
/*     */         }
/*     */       }
/* 113 */       else if (group.matches("%Y")) {
/* 114 */         rep = String.valueOf(calendar.get(1));
/* 115 */       } else if (group.matches("%m")) {
/* 116 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(2)) });
/* 117 */       } else if (group.matches("%d")) {
/* 118 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(5)) });
/* 119 */       } else if (group.matches("%W")) {
/* 120 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(3)) });
/* 121 */       } else if (group.matches("%H")) {
/* 122 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(11)) });
/* 123 */       } else if (group.matches("%h")) {
/* 124 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(10)) });
/* 125 */       } else if (group.matches("%i")) {
/* 126 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(12)) });
/* 127 */       } else if (group.matches("%s")) {
/* 128 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(13)) });
/*     */       } 
/*     */       
/* 131 */       m.appendReplacement(buffer, rep);
/*     */     } 
/*     */     
/* 134 */     m.appendTail(buffer);
/*     */     
/* 136 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(Player player, String message, String comment) {
/* 146 */     String path = buildPath(player.getName());
/*     */     try {
/* 148 */       String date = dateFormat.format(new Date());
/* 149 */       String line = "[" + date + "] " + player.getName() + ": " + message + ((comment != null) ? (" (" + comment + ")") : "") + "\r\n";
/*     */ 
/*     */       
/* 152 */       FileLoggerWriter writer = this.writers.get(path);
/*     */ 
/*     */       
/* 155 */       if (writer != null) {
/*     */         try {
/* 157 */           BufferedWriter bufferedWriter = writer.getWriter();
/* 158 */           bufferedWriter.write(line);
/* 159 */           bufferedWriter.flush();
/* 160 */           writer.updateLastUse();
/*     */           return;
/* 162 */         } catch (IOException e) {}
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 168 */       File file = new File(path);
/* 169 */       File parent = file.getParentFile();
/* 170 */       if (parent != null && !parent.exists()) {
/* 171 */         parent.mkdirs();
/*     */       }
/*     */       
/* 174 */       FileWriter stream = new FileWriter(path, true);
/* 175 */       BufferedWriter out = new BufferedWriter(stream);
/* 176 */       out.write(line);
/* 177 */       out.flush();
/* 178 */       writer = new FileLoggerWriter(path, out);
/* 179 */       this.writers.put(path, writer);
/*     */ 
/*     */       
/* 182 */       if (this.writers.size() > this.cacheSize) {
/* 183 */         Iterator<Map.Entry<String, FileLoggerWriter>> it = this.writers.entrySet().iterator();
/*     */ 
/*     */ 
/*     */         
/* 187 */         while (it.hasNext()) {
/* 188 */           Map.Entry<String, FileLoggerWriter> entry = it.next();
/*     */           try {
/* 190 */             ((FileLoggerWriter)entry.getValue()).getWriter().close();
/* 191 */           } catch (IOException e) {}
/*     */           
/* 193 */           it.remove();
/*     */ 
/*     */           
/* 196 */           if (this.writers.size() <= this.cacheSize) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 202 */     } catch (IOException e) {
/* 203 */       logger.log(Level.WARNING, "Failed to log blacklist event to '" + path + "': " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCoordinates(Block block) {
/* 215 */     return "@" + block.getX() + "," + block.getY() + "," + block.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyAttempt(Player player, Block block, String comment) {
/* 225 */     log(player, "Tried to destroy " + getFriendlyItemName(block.getType()) + " " + getCoordinates(block), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logBreakAttempt(Player player, Block block, String comment) {
/* 237 */     log(player, "Tried to break " + getFriendlyItemName(block.getType()) + " " + getCoordinates(block), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logRightClickAttempt(Player player, Block block, String comment) {
/* 249 */     log(player, "Tried to right click " + getFriendlyItemName(block.getType()) + " " + getCoordinates(block), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyWithAttempt(Player player, int item, String comment) {
/* 261 */     log(player, "Tried to destroy with " + getFriendlyItemName(item), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logPlaceAttempt(Player player, int item, String comment) {
/* 271 */     log(player, "Tried to place " + getFriendlyItemName(item), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logUseAttempt(Player player, int item, String comment) {
/* 281 */     log(player, "Tried to use " + getFriendlyItemName(item), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDropAttempt(Player player, int item, String comment) {
/* 291 */     log(player, "Tried to drop " + getFriendlyItemName(item), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logAcquireAttempt(Player player, int item, String comment) {
/* 301 */     log(player, "Tried to acquire " + getFriendlyItemName(item), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getFriendlyItemName(int id) {
/* 310 */     return etc.getDataSource().getItem(id) + " (#" + id + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 317 */     for (Map.Entry<String, FileLoggerWriter> entry : this.writers.entrySet()) {
/*     */       try {
/* 319 */         ((FileLoggerWriter)entry.getValue()).getWriter().close();
/* 320 */       } catch (IOException e) {}
/*     */     } 
/*     */ 
/*     */     
/* 324 */     this.writers.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\FileLoggerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */