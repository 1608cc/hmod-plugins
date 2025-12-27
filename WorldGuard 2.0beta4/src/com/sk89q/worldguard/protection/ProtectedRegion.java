/*     */ package com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProtectedRegion
/*     */ {
/*     */   private String enterMessage;
/*  38 */   private DefaultDomain owners = new DefaultDomain();
/*     */ 
/*     */ 
/*     */   
/*  42 */   private int priority = 0;
/*     */ 
/*     */ 
/*     */   
/*  46 */   private AreaFlags flags = new AreaFlags();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtectedRegion() {
/*  57 */     this.priority = 0;
/*  58 */     this.owners = new DefaultDomain();
/*  59 */     this.enterMessage = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority() {
/*  66 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPriority(int priority) {
/*  73 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlags(AreaFlags flags) {
/*  81 */     this.flags = flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEnterMessage() {
/*  88 */     return this.enterMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnterMessage(String enterMessage) {
/*  95 */     this.enterMessage = enterMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDomain getOwners() {
/* 102 */     return this.owners;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwners(DefaultDomain owners) {
/* 109 */     this.owners = owners;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AreaFlags getFlags() {
/* 118 */     return this.flags;
/*     */   }
/*     */   
/*     */   public abstract boolean contains(Vector paramVector);
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\ProtectedRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */