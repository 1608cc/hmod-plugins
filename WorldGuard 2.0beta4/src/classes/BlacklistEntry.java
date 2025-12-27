/*     */ package classes;
/*     */ 
/*     */ import Blacklist;
/*     */ import BlacklistTrackedEvent;
/*     */ import Block;
/*     */ import Player;
/*     */ import PluginLoader;
/*     */ import etc;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class BlacklistEntry
/*     */ {
/*  34 */   private static Map<String, BlacklistTrackedEvent> lastAffected = new HashMap<String, BlacklistTrackedEvent>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Blacklist blacklist;
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> ignoreGroups;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] destroyActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] breakActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] destroyWithActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] placeActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] useActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] rightClickActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] dropActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] acquireActions;
/*     */ 
/*     */ 
/*     */   
/*     */   private String message;
/*     */ 
/*     */ 
/*     */   
/*     */   private String comment;
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static ActionHandler silentHandler = (ActionHandler)new Object();
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
/*     */   public BlacklistEntry(Blacklist blacklist) {
/* 101 */     this.blacklist = blacklist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getIgnoreGroups() {
/* 108 */     return this.ignoreGroups.<String>toArray(new String[this.ignoreGroups.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIgnoreGroups(String[] ignoreGroups) {
/* 115 */     Set<String> ignoreGroupsSet = new HashSet<String>(); byte b; int i; String[] arrayOfString;
/* 116 */     for (i = (arrayOfString = ignoreGroups).length, b = 0; b < i; ) { String group = arrayOfString[b];
/* 117 */       ignoreGroupsSet.add(group.toLowerCase()); b++; }
/*     */     
/* 119 */     this.ignoreGroups = ignoreGroupsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getDestroyActions() {
/* 126 */     return this.destroyActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDestroyActions(String[] actions) {
/* 133 */     this.destroyActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getBreakActions() {
/* 140 */     return this.breakActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBreakActions(String[] actions) {
/* 147 */     this.breakActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getDestroyWithActions() {
/* 154 */     return this.destroyWithActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDestroyWithActions(String[] actions) {
/* 161 */     this.destroyWithActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPlaceActions() {
/* 168 */     return this.placeActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlaceActions(String[] actions) {
/* 175 */     this.placeActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getUseActions() {
/* 182 */     return this.useActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseActions(String[] actions) {
/* 189 */     this.useActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRightClickActions() {
/* 196 */     return this.rightClickActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRightClickActions(String[] actions) {
/* 203 */     this.rightClickActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getDropActions() {
/* 210 */     return this.dropActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDropActions(String[] actions) {
/* 217 */     this.dropActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAcquireActions() {
/* 224 */     return this.acquireActions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAcquireActions(String[] actions) {
/* 231 */     this.acquireActions = actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 238 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/* 245 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getComment() {
/* 252 */     return this.comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComment(String comment) {
/* 259 */     this.comment = comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldIgnore(Player player) {
/* 269 */     if (this.ignoreGroups == null)
/* 270 */       return false;  byte b; int i;
/*     */     String[] arrayOfString;
/* 272 */     for (i = (arrayOfString = player.getGroups()).length, b = 0; b < i; ) { String group = arrayOfString[b];
/* 273 */       if (this.ignoreGroups.contains(group.toLowerCase())) {
/* 274 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyAdmins(String str) {
/* 287 */     for (Player player : etc.getServer().getPlayerList()) {
/* 288 */       if (player.canUseCommand("/wprotectalerts") || 
/* 289 */         player.canUseCommand("/worldguardnotify")) {
/* 290 */         player.sendMessage("§7WG: " + str);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void banPlayer(Player player, String msg) {
/* 302 */     etc.getServer().ban(player.getName());
/* 303 */     etc.getLoader().callHook(PluginLoader.Hook.BAN, new Object[] {
/* 304 */           player.getUser(), player.getUser(), msg
/*     */         });
/* 306 */     player.kick(msg);
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
/*     */   public boolean onDestroy(Block block, Player player) {
/* 318 */     if (this.destroyActions == null) {
/* 319 */       return true;
/*     */     }
/*     */     
/* 322 */     BlacklistEntry entry = this;
/*     */     
/* 324 */     Object object = new Object(this, player, block, entry);
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
/* 343 */     return process(block.getType(), player, this.destroyActions, (ActionHandler)object, false, false);
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
/*     */   public boolean onBreak(Block block, Player player) {
/* 355 */     if (this.breakActions == null) {
/* 356 */       return true;
/*     */     }
/*     */     
/* 359 */     BlacklistEntry entry = this;
/*     */     
/* 361 */     Object object = new Object(this, player, block, entry);
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
/* 380 */     return process(block.getType(), player, this.breakActions, (ActionHandler)object, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDestroyWith(int item, Player player) {
/* 391 */     if (this.destroyWithActions == null) {
/* 392 */       return true;
/*     */     }
/*     */     
/* 395 */     BlacklistEntry entry = this;
/*     */     
/* 397 */     Object object = new Object(this, player, item, entry);
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
/* 416 */     return process(item, player, this.destroyWithActions, (ActionHandler)object, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlace(int item, Player player) {
/* 427 */     if (this.placeActions == null) {
/* 428 */       return true;
/*     */     }
/*     */     
/* 431 */     BlacklistEntry entry = this;
/*     */     
/* 433 */     Object object = new Object(this, player, item, entry);
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
/* 452 */     return process(item, player, this.placeActions, (ActionHandler)object, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUse(int item, Player player) {
/* 463 */     if (this.useActions == null) {
/* 464 */       return true;
/*     */     }
/*     */     
/* 467 */     BlacklistEntry entry = this;
/*     */     
/* 469 */     Object object = new Object(this, player, item, entry);
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
/* 488 */     return process(item, player, this.useActions, (ActionHandler)object, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onRightClick(Block block, Player player) {
/* 499 */     if (this.useActions == null) {
/* 500 */       return true;
/*     */     }
/*     */     
/* 503 */     BlacklistEntry entry = this;
/*     */     
/* 505 */     Object object = new Object(this, player, block, entry);
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
/* 524 */     return process(block.getType(), player, this.useActions, (ActionHandler)object, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSilentUse(Block block, Player player) {
/* 535 */     if (this.useActions == null) {
/* 536 */       return true;
/*     */     }
/*     */     
/* 539 */     return process(block.getType(), player, this.useActions, silentHandler, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDrop(int item, Player player) {
/* 550 */     if (this.dropActions == null) {
/* 551 */       return true;
/*     */     }
/*     */     
/* 554 */     BlacklistEntry entry = this;
/*     */     
/* 556 */     Object object = new Object(this, player, item, entry);
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
/* 575 */     return process(item, player, this.dropActions, (ActionHandler)object, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onAcquire(int item, Player player) {
/* 586 */     if (this.acquireActions == null) {
/* 587 */       return true;
/*     */     }
/*     */     
/* 590 */     BlacklistEntry entry = this;
/*     */     
/* 592 */     Object object = new Object(this, player, item, entry);
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
/* 611 */     return process(item, player, this.acquireActions, (ActionHandler)object, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSilentAcquire(int item, Player player) {
/* 622 */     if (this.acquireActions == null) {
/* 623 */       return true;
/*     */     }
/*     */     
/* 626 */     return process(item, player, this.acquireActions, silentHandler, false, true);
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
/*     */   
/*     */   private boolean process(int id, Player player, String[] actions, ActionHandler handler, boolean allowRepeat, boolean silent) {
/* 643 */     if (shouldIgnore(player)) {
/* 644 */       return true;
/*     */     }
/*     */     
/* 647 */     String name = player.getName();
/* 648 */     long now = System.currentTimeMillis();
/* 649 */     boolean repeating = false;
/*     */ 
/*     */     
/* 652 */     BlacklistTrackedEvent tracked = lastAffected.get(name);
/* 653 */     if (tracked != null) {
/* 654 */       if (tracked.getId() == id && tracked.getTime() > now - 3000L) {
/* 655 */         repeating = true;
/*     */       } else {
/* 657 */         tracked.setTime(now);
/* 658 */         tracked.setId(id);
/*     */       } 
/*     */     } else {
/* 661 */       lastAffected.put(name, new BlacklistTrackedEvent(id, now));
/*     */     } 
/*     */     
/* 664 */     boolean ret = true; byte b; int i;
/*     */     String[] arrayOfString;
/* 666 */     for (i = (arrayOfString = actions).length, b = 0; b < i; ) { String action = arrayOfString[b];
/*     */       
/* 668 */       if (action.equalsIgnoreCase("deny")) {
/* 669 */         if (silent) {
/* 670 */           return false;
/*     */         }
/* 672 */         ret = false;
/*     */       
/*     */       }
/* 675 */       else if (action.equalsIgnoreCase("kick")) {
/* 676 */         if (this.message != null) {
/* 677 */           player.kick(String.format(this.message, new Object[] {
/* 678 */                   etc.getDataSource().getItem(id) }));
/*     */         } else {
/* 680 */           handler.kick(etc.getDataSource().getItem(id));
/*     */         }
/*     */       
/*     */       }
/* 684 */       else if (action.equalsIgnoreCase("ban")) {
/* 685 */         handler.ban(etc.getDataSource().getItem(id));
/* 686 */         if (this.message != null) {
/* 687 */           banPlayer(player, "Banned: " + String.format(this.message, new Object[] {
/* 688 */                   etc.getDataSource().getItem(id) }));
/*     */         } else {
/* 690 */           handler.ban(etc.getDataSource().getItem(id));
/*     */         }
/*     */       
/* 693 */       } else if (!repeating || allowRepeat) {
/*     */         
/* 695 */         if (action.equalsIgnoreCase("notify")) {
/* 696 */           handler.notifyAdmins(etc.getDataSource().getItem(id));
/*     */         
/*     */         }
/* 699 */         else if (action.equalsIgnoreCase("log")) {
/* 700 */           handler.log(etc.getDataSource().getItem(id));
/*     */         
/*     */         }
/* 703 */         else if (action.equalsIgnoreCase("tell")) {
/* 704 */           if (this.message != null) {
/* 705 */             player.sendMessage("§e" + 
/* 706 */                 String.format(this.message, new Object[] { etc.getDataSource().getItem(id)
/* 707 */                   }) + ".");
/*     */           } else {
/* 709 */             handler.tell(etc.getDataSource().getItem(id));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 715 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forgetPlayer(Player player) {
/* 724 */     lastAffected.remove(player.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forgetAllPlayers() {
/* 733 */     lastAffected.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\BlacklistEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */