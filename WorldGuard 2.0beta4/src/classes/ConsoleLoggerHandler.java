/*     */ package classes;
/*     */ 
/*     */ import BlacklistLoggerHandler;
/*     */ import Block;
/*     */ import Player;
/*     */ import etc;
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
/*     */ public class ConsoleLoggerHandler
/*     */   implements BlacklistLoggerHandler
/*     */ {
/*  31 */   private static final Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyAttempt(Player player, Block block, String comment) {
/*  40 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/*  41 */         " tried to destroy " + getFriendlyItemName(block.getType()) + (
/*  42 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logBreakAttempt(Player player, Block block, String comment) {
/*  52 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/*  53 */         " tried to break " + getFriendlyItemName(block.getType()) + (
/*  54 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logRightClickAttempt(Player player, Block block, String comment) {
/*  64 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/*  65 */         " tried to right click " + getFriendlyItemName(block.getType()) + (
/*  66 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDestroyWithAttempt(Player player, int item, String comment) {
/*  76 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/*  77 */         " tried to destroy with " + getFriendlyItemName(item) + (
/*  78 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logPlaceAttempt(Player player, int item, String comment) {
/*  88 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/*  89 */         " tried to place " + getFriendlyItemName(item) + (
/*  90 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logUseAttempt(Player player, int item, String comment) {
/* 100 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/* 101 */         " tried to use " + getFriendlyItemName(item) + (
/* 102 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logDropAttempt(Player player, int item, String comment) {
/* 112 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/* 113 */         " tried to drop " + getFriendlyItemName(item) + (
/* 114 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logAcquireAttempt(Player player, int item, String comment) {
/* 124 */     logger.log(Level.INFO, "WorldGuard: " + player.getName() + 
/* 125 */         " tried to acquire " + getFriendlyItemName(item) + (
/* 126 */         (comment != null) ? (" (" + comment + ")") : ""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getFriendlyItemName(int id) {
/* 135 */     return String.valueOf(etc.getDataSource().getItem(id)) + " (#" + id + ")";
/*     */   }
/*     */   
/*     */   public void close() {}
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\ConsoleLoggerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */