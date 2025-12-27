/*     */ package classes;
/*     */ 
/*     */ import BlacklistLoggerHandler;
/*     */ import Block;
/*     */ import Player;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DatabaseLoggerHandler
/*     */   implements BlacklistLoggerHandler
/*     */ {
/*  35 */   private static final Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String dsn;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String user;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String table;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection conn;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseLoggerHandler(String dsn, String user, String pass, String table) {
/*  66 */     this.dsn = dsn;
/*  67 */     this.user = user;
/*  68 */     this.pass = pass;
/*  69 */     this.table = table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getConnection() throws SQLException {
/*  79 */     if (this.conn == null || this.conn.isClosed()) {
/*  80 */       this.conn = DriverManager.getConnection(this.dsn, this.user, this.pass);
/*     */     }
/*  82 */     return this.conn;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logEvent(String event, String name, int x, int y, int z, int item, String comment) {
/*     */     try {
/*  99 */       Connection conn = getConnection();
/* 100 */       PreparedStatement stmt = conn.prepareStatement(
/* 101 */           "INSERT INTO " + this.table + 
/* 102 */           "(event, player, x, y, z, item, time, comment) VALUES " + 
/* 103 */           "(?, ?, ?, ?, ?, ?, ?, ?)");
/* 104 */       stmt.setString(1, event);
/* 105 */       stmt.setString(2, name);
/* 106 */       stmt.setInt(3, x);
/* 107 */       stmt.setInt(4, y);
/* 108 */       stmt.setInt(5, z);
/* 109 */       stmt.setInt(6, item);
/* 110 */       stmt.setInt(7, (int)(System.currentTimeMillis() / 1000L));
/* 111 */       stmt.setString(8, comment);
/* 112 */       stmt.executeUpdate();
/* 113 */     } catch (SQLException e) {
/* 114 */       logger.log(Level.SEVERE, "Failed to log blacklist event to database: " + 
/* 115 */           e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyAttempt(Player player, Block block, String comment) {
/* 126 */     logEvent("DESTROY", player.getName(), 
/* 127 */         block.getX(), block.getY(), block.getZ(), block.getType(), 
/* 128 */         comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logBreakAttempt(Player player, Block block, String comment) {
/* 138 */     logEvent("BREAK", player.getName(), 
/* 139 */         block.getX(), block.getY(), block.getZ(), block.getType(), 
/* 140 */         comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logRightClickAttempt(Player player, Block block, String comment) {
/* 150 */     logEvent("RIGHT_CLICK", player.getName(), 
/* 151 */         block.getX(), block.getY(), block.getZ(), block.getType(), 
/* 152 */         comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyWithAttempt(Player player, int item, String comment) {
/* 162 */     logEvent("DESTROY_WITH", player.getName(), 
/* 163 */         (int)Math.floor(player.getX()), (int)Math.floor(player.getY()), 
/* 164 */         (int)Math.floor(player.getZ()), item, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logPlaceAttempt(Player player, int item, String comment) {
/* 174 */     logEvent("PLACE", player.getName(), 
/* 175 */         (int)Math.floor(player.getX()), (int)Math.floor(player.getY()), 
/* 176 */         (int)Math.floor(player.getZ()), item, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logUseAttempt(Player player, int item, String comment) {
/* 186 */     logEvent("USE", player.getName(), 
/* 187 */         (int)Math.floor(player.getX()), (int)Math.floor(player.getY()), 
/* 188 */         (int)Math.floor(player.getZ()), item, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDropAttempt(Player player, int item, String comment) {
/* 198 */     logEvent("DROP", player.getName(), 
/* 199 */         (int)Math.floor(player.getX()), (int)Math.floor(player.getY()), 
/* 200 */         (int)Math.floor(player.getZ()), item, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logAcquireAttempt(Player player, int item, String comment) {
/* 210 */     logEvent("AQUIRE", player.getName(), 
/* 211 */         (int)Math.floor(player.getX()), (int)Math.floor(player.getY()), 
/* 212 */         (int)Math.floor(player.getZ()), item, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 220 */       if (this.conn != null && !this.conn.isClosed()) {
/* 221 */         this.conn.close();
/*     */       }
/* 223 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\DatabaseLoggerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */