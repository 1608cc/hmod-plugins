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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlacklistEntry
/*     */ {
/*  33 */   private static Map<String, BlacklistTrackedEvent> lastAffected = new HashMap<String, BlacklistTrackedEvent>();
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
/*     */   
/*  87 */   private static ActionHandler silentHandler = new ActionHandler()
/*     */     {
/*     */       public void log(String itemName) {}
/*     */       
/*     */       public void kick(String itemName) {}
/*     */       
/*     */       public void ban(String itemName) {}
/*     */       
/*     */       public void notifyAdmins(String itemName) {}
/*     */       
/*     */       public void tell(String itemName) {}
/*     */     };
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
/* 115 */     Set<String> ignoreGroupsSet = new HashSet<String>();
/* 116 */     for (String group : ignoreGroups) {
/* 117 */       ignoreGroupsSet.add(group.toLowerCase());
/*     */     }
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
/* 269 */     if (this.ignoreGroups == null) {
/* 270 */       return false;
/*     */     }
/* 272 */     for (String group : player.getGroups()) {
/* 273 */       if (this.ignoreGroups.contains(group.toLowerCase())) {
/* 274 */         return true;
/*     */       }
/*     */     } 
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
/* 288 */       if (player.canUseCommand("/wprotectalerts") || player.canUseCommand("/worldguardnotify"))
/*     */       {
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
/* 303 */     etc.getLoader().callHook(PluginLoader.Hook.BAN, new Object[] { player.getUser(), player.getUser(), msg });
/*     */ 
/*     */     
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
/*     */   public boolean onDestroy(final Block block, final Player player) {
/* 318 */     if (this.destroyActions == null) {
/* 319 */       return true;
/*     */     }
/*     */     
/* 322 */     final BlacklistEntry entry = this;
/*     */     
/* 324 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 326 */           BlacklistEntry.this.blacklist.getLogger().logDestroyAttempt(player, block, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 329 */           player.kick("You are not allowed to destroy " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 332 */           entry.banPlayer(player, "Banned: You are not allowed to destroy " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 335 */           entry.notifyAdmins(player.getName() + " (destroy) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 339 */           player.sendMessage("§eYou are not allowed to destroy " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 343 */     return process(block.getType(), player, this.destroyActions, handler, false, false);
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
/*     */   public boolean onBreak(final Block block, final Player player) {
/* 355 */     if (this.breakActions == null) {
/* 356 */       return true;
/*     */     }
/*     */     
/* 359 */     final BlacklistEntry entry = this;
/*     */     
/* 361 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 363 */           BlacklistEntry.this.blacklist.getLogger().logBreakAttempt(player, block, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 366 */           player.kick("You are not allowed to break " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 369 */           entry.banPlayer(player, "Banned: You are not allowed to break " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 372 */           entry.notifyAdmins(player.getName() + " (break) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 376 */           player.sendMessage("§eYou are not allowed to break " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 380 */     return process(block.getType(), player, this.breakActions, handler, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDestroyWith(final int item, final Player player) {
/* 391 */     if (this.destroyWithActions == null) {
/* 392 */       return true;
/*     */     }
/*     */     
/* 395 */     final BlacklistEntry entry = this;
/*     */     
/* 397 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 399 */           BlacklistEntry.this.blacklist.getLogger().logDestroyWithAttempt(player, item, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 402 */           player.kick("You can't destroy with " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 405 */           entry.banPlayer(player, "Banned: You can't destroy with " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 408 */           entry.notifyAdmins(player.getName() + " (destroy w/) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 412 */           player.sendMessage("§eYou can't destroy with " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 416 */     return process(item, player, this.destroyWithActions, handler, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlace(final int item, final Player player) {
/* 427 */     if (this.placeActions == null) {
/* 428 */       return true;
/*     */     }
/*     */     
/* 431 */     final BlacklistEntry entry = this;
/*     */     
/* 433 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 435 */           BlacklistEntry.this.blacklist.getLogger().logPlaceAttempt(player, item, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 438 */           player.kick("You can't place " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 441 */           entry.banPlayer(player, "Banned: You can't place " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 444 */           entry.notifyAdmins(player.getName() + " (place) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 448 */           player.sendMessage("§eYou can't place " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 452 */     return process(item, player, this.placeActions, handler, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUse(final int item, final Player player) {
/* 463 */     if (this.useActions == null) {
/* 464 */       return true;
/*     */     }
/*     */     
/* 467 */     final BlacklistEntry entry = this;
/*     */     
/* 469 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 471 */           BlacklistEntry.this.blacklist.getLogger().logUseAttempt(player, item, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 474 */           player.kick("You can't use " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 477 */           entry.banPlayer(player, "Banned: You can't use " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 480 */           entry.notifyAdmins(player.getName() + " (use) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 484 */           player.sendMessage("§eYou're not allowed to use " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 488 */     return process(item, player, this.useActions, handler, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onRightClick(final Block block, final Player player) {
/* 499 */     if (this.useActions == null) {
/* 500 */       return true;
/*     */     }
/*     */     
/* 503 */     final BlacklistEntry entry = this;
/*     */     
/* 505 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 507 */           BlacklistEntry.this.blacklist.getLogger().logRightClickAttempt(player, block, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 510 */           player.kick("You can't use " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 513 */           entry.banPlayer(player, "Banned: You can't use " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 516 */           entry.notifyAdmins(player.getName() + " (use) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 520 */           player.sendMessage("§eYou're not allowed to use " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 524 */     return process(block.getType(), player, this.useActions, handler, false, false);
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
/*     */   public boolean onDrop(final int item, final Player player) {
/* 550 */     if (this.dropActions == null) {
/* 551 */       return true;
/*     */     }
/*     */     
/* 554 */     final BlacklistEntry entry = this;
/*     */     
/* 556 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 558 */           BlacklistEntry.this.blacklist.getLogger().logDropAttempt(player, item, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 561 */           player.kick("You can't drop " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 564 */           entry.banPlayer(player, "Banned: You can't drop " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 567 */           entry.notifyAdmins(player.getName() + " (drop) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 571 */           player.sendMessage("§eYou're not allowed to drop " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 575 */     return process(item, player, this.dropActions, handler, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onAcquire(final int item, final Player player) {
/* 586 */     if (this.acquireActions == null) {
/* 587 */       return true;
/*     */     }
/*     */     
/* 590 */     final BlacklistEntry entry = this;
/*     */     
/* 592 */     ActionHandler handler = new ActionHandler() {
/*     */         public void log(String itemName) {
/* 594 */           BlacklistEntry.this.blacklist.getLogger().logAcquireAttempt(player, item, BlacklistEntry.this.comment);
/*     */         }
/*     */         public void kick(String itemName) {
/* 597 */           player.kick("You can't acquire " + itemName);
/*     */         }
/*     */         public void ban(String itemName) {
/* 600 */           entry.banPlayer(player, "Banned: You can't acquire " + itemName);
/*     */         }
/*     */         public void notifyAdmins(String itemName) {
/* 603 */           entry.notifyAdmins(player.getName() + " (acquire) " + itemName + ((BlacklistEntry.this.comment != null) ? (" (" + BlacklistEntry.this.comment + ")") : "") + ".");
/*     */         }
/*     */         
/*     */         public void tell(String itemName) {
/* 607 */           player.sendMessage("§eYou're not allowed to acquire " + itemName + ".");
/*     */         }
/*     */       };
/*     */     
/* 611 */     return process(item, player, this.acquireActions, handler, true, false);
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
/* 664 */     boolean ret = true;
/*     */     
/* 666 */     for (String action : actions) {
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
/* 677 */           player.kick(String.format(this.message, new Object[] { etc.getDataSource().getItem(id) }));
/*     */         } else {
/*     */           
/* 680 */           handler.kick(etc.getDataSource().getItem(id));
/*     */         }
/*     */       
/*     */       }
/* 684 */       else if (action.equalsIgnoreCase("ban")) {
/* 685 */         handler.ban(etc.getDataSource().getItem(id));
/* 686 */         if (this.message != null) {
/* 687 */           banPlayer(player, "Banned: " + String.format(this.message, new Object[] { etc.getDataSource().getItem(id) }));
/*     */         } else {
/*     */           
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
/* 705 */             player.sendMessage("§e" + String.format(this.message, new Object[] { etc.getDataSource().getItem(id) }) + ".");
/*     */           }
/*     */           else {
/*     */             
/* 709 */             handler.tell(etc.getDataSource().getItem(id));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   
/*     */   private static interface ActionHandler {
/*     */     void log(String param1String);
/*     */     
/*     */     void kick(String param1String);
/*     */     
/*     */     void ban(String param1String);
/*     */     
/*     */     void notifyAdmins(String param1String);
/*     */     
/*     */     void tell(String param1String);
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\BlacklistEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */