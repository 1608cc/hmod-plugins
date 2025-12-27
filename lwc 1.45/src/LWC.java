/*     */ import com.griefcraft.logging.Logger;
/*     */ import com.griefcraft.model.Entity;
/*     */ import com.griefcraft.sql.Database;
/*     */ import com.griefcraft.sql.MemDB;
/*     */ import com.griefcraft.sql.PhysDB;
/*     */ import com.griefcraft.util.Config;
/*     */ import com.griefcraft.util.ConfigValues;
/*     */ import com.griefcraft.util.Performance;
/*     */ import com.griefcraft.util.Updater;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
/*     */ import java.util.List;
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
/*     */ public class LWC
/*     */   extends Plugin
/*     */ {
/*  43 */   private Logger logger = Logger.getLogger(getClass().getSimpleName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LWCListener listener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PhysDB physicalDatabase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MemDB memoryDatabase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Updater updater;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Command> commands;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccessChest(Player player, Entity chest) {
/*     */     PhysDB instance;
/*  80 */     if (chest == null) {
/*  81 */       return true;
/*     */     }
/*     */     
/*  84 */     if (isAdmin(player)) {
/*  85 */       return true;
/*     */     }
/*     */     
/*  88 */     if (isMod(player)) {
/*  89 */       Player chestOwner = etc.getDataSource().getPlayer(chest.getOwner());
/*     */       
/*  91 */       if (chestOwner == null) {
/*  92 */         return true;
/*     */       }
/*     */       
/*  95 */       if (!isAdmin(chestOwner)) {
/*  96 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     switch (chest.getType()) {
/*     */       case 0:
/* 102 */         return true;
/*     */       
/*     */       case 1:
/* 105 */         return this.memoryDatabase.hasAccess(player.getName(), chest);
/*     */       
/*     */       case 2:
/* 108 */         instance = this.physicalDatabase;
/* 109 */         return !(!player.getName().equalsIgnoreCase(chest.getOwner()) && instance.getPrivateAccess(1, chest.getID(), new String[] { player.getName() }) == -1 && instance.getPrivateAccess(0, chest.getID(), player.getGroups()) == -1);
/*     */     } 
/*     */     
/* 112 */     return false;
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
/*     */   
/*     */   public boolean canAccessChest(Player player, int x, int y, int z) {
/* 130 */     return canAccessChest(player, this.physicalDatabase.loadProtectedEntity(x, y, z));
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
/*     */   public boolean canAdminChest(Player player, Entity chest) {
/*     */     PhysDB instance;
/* 143 */     if (chest == null) {
/* 144 */       return true;
/*     */     }
/*     */     
/* 147 */     if (isAdmin(player)) {
/* 148 */       return true;
/*     */     }
/*     */     
/* 151 */     switch (chest.getType()) {
/*     */       case 0:
/* 153 */         return player.getName().equalsIgnoreCase(chest.getOwner());
/*     */       
/*     */       case 1:
/* 156 */         return (player.getName().equalsIgnoreCase(chest.getOwner()) && this.memoryDatabase.hasAccess(player.getName(), chest));
/*     */       
/*     */       case 2:
/* 159 */         instance = this.physicalDatabase;
/* 160 */         return !(!player.getName().equalsIgnoreCase(chest.getOwner()) && instance.getPrivateAccess(1, chest.getID(), new String[] { player.getName() }) != 1 && instance.getPrivateAccess(0, chest.getID(), player.getGroups()) != 1);
/*     */     } 
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/* 169 */     log("Stopping LWC");
/* 170 */     Config.getInstance().save();
/* 171 */     Config.destroy();
/*     */     
/* 173 */     etc.getInstance().removeCommand("/lwc");
/*     */     
/*     */     try {
/* 176 */       this.physicalDatabase.connection.close();
/* 177 */       this.memoryDatabase.connection.close();
/*     */       
/* 179 */       this.physicalDatabase = null;
/* 180 */       this.memoryDatabase = null;
/* 181 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Updater getUpdater() {
/* 190 */     return this.updater;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enable() {
/*     */     try {
/* 196 */       log("Initializing LWC");
/*     */       
/* 198 */       Performance.init();
/*     */       
/* 200 */       this.commands = new ArrayList<Command>();
/* 201 */       this.physicalDatabase = new PhysDB();
/* 202 */       this.memoryDatabase = new MemDB();
/*     */       
/* 204 */       log("Binding commands");
/* 205 */       loadCommands();
/* 206 */       etc.getInstance().addCommand("/lwc", "- Chest/Furnace protection");
/*     */       
/* 208 */       Config.init();
/*     */       
/* 210 */       this.updater = new Updater();
/* 211 */       //this.updater.check();
/* 212 */       //this.updater.update();
/*     */       
/* 214 */       //if (ConfigValues.AUTO_UPDATE.getBool() && 
/* 215 */         //this.updater.checkDist()) {
/* 216 */         //log("Reloading LWC");
/* 217 */         //etc.getLoader().reloadPlugin("LWC");
/*     */         
/*     */         //return;
/*     */       //} 
/*     */       
/* 222 */       log("LWC config:      lwc.properties");
/* 223 */       log("SQLite jar:      lib/sqlite.jar");
/* 224 */       log("SQLite library:  lib/" + this.updater.getOSSpecificFileName());
/* 225 */       log("DB location:     " + this.physicalDatabase.getDatabasePath());
/*     */       
/* 227 */       log("Opening sqlite databases");
/*     */       
/* 229 */       this.physicalDatabase.connect();
/* 230 */       this.memoryDatabase.connect();
/*     */       
/* 232 */       this.physicalDatabase.load();
/* 233 */       this.memoryDatabase.load();
/*     */       
/* 235 */       log("Protections:\t" + this.physicalDatabase.entityCount());
/* 236 */       log("Limits:\t\t" + this.physicalDatabase.limitCount());
/*     */       
/* 238 */       if (ConfigValues.CUBOID_SAFE_AREAS.getBool()) {
/* 239 */         log("Only allowing chests to be protected in Cuboid-protected zones that DO NOT have PvP toggled!");
/*     */       }
/*     */       
/* 242 */       Config.getInstance().save();
/* 243 */     } catch (Exception e) {
/* 244 */       log("Error occured while initializing LWC : " + e.getMessage());
/* 245 */       e.printStackTrace();
/* 246 */       log("LWC will now be disabled");
/* 247 */       etc.getLoader().disablePlugin("LWC");
/*     */     } 
/*     */   }
/*     */   
/*     */   public String encrypt(String plaintext) {
/* 252 */     MessageDigest md = null;
/*     */     
/*     */     try {
/* 255 */       md = MessageDigest.getInstance("SHA");
/* 256 */       md.update(plaintext.getBytes("UTF-8"));
/*     */       
/* 258 */       byte[] raw = md.digest();
/* 259 */       return byteArray2Hex(raw);
/* 260 */     } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */       
/* 264 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enforceChestLimits(Player player) {
/* 275 */     if (isAdmin(player)) {
/* 276 */       return false;
/*     */     }
/*     */     
/* 279 */     int userLimit = this.physicalDatabase.getUserLimit(player.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     if (userLimit != -1) {
/* 285 */       int chests = this.physicalDatabase.getChestCount(player.getName());
/*     */       
/* 287 */       if (chests >= userLimit) {
/* 288 */         player.sendMessage("§4You have exceeded the amount of chests you can lock!");
/* 289 */         return true;
/*     */       } 
/*     */     } else {
/* 292 */       List<String> inheritedGroups = new ArrayList<String>();
/* 293 */       String groupName = ((player.getGroups()).length > 0) ? player.getGroups()[0] : (etc.getInstance().getDefaultGroup()).Name;
/*     */       
/* 295 */       inheritedGroups.add(groupName);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 301 */         Group group = etc.getDataSource().getGroup(groupName);
/*     */         
/* 303 */         if (group == null) {
/*     */           break;
/*     */         }
/*     */         
/* 307 */         String[] inherited = group.InheritedGroups;
/*     */         
/* 309 */         if (inherited == null || inherited.length == 0) {
/*     */           break;
/*     */         }
/*     */         
/* 313 */         groupName = inherited[0]; byte b; int i;
/*     */         String[] arrayOfString1;
/* 315 */         for (i = (arrayOfString1 = inherited).length, b = 0; b < i; ) { String _groupName = arrayOfString1[b];
/* 316 */           _groupName = _groupName.trim();
/*     */           
/* 318 */           if (!_groupName.isEmpty())
/*     */           {
/*     */ 
/*     */             
/* 322 */             inheritedGroups.add(_groupName); } 
/*     */           b++; }
/*     */       
/*     */       } 
/* 326 */       for (String group : inheritedGroups) {
/* 327 */         int groupLimit = this.physicalDatabase.getGroupLimit(group);
/*     */         
/* 329 */         if (groupLimit != -1) {
/* 330 */           int chests = this.physicalDatabase.getChestCount(player.getName());
/*     */           
/* 332 */           if (chests >= groupLimit) {
/* 333 */             player.sendMessage("§4You have exceeded the amount of chests you can lock!");
/* 334 */             return true;
/*     */           } 
/*     */           
/* 337 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 342 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Command> getCommands() {
/* 349 */     return this.commands;
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
/*     */   public List<ComplexBlock> getEntitySet(int x, int y, int z) {
/* 364 */     List<ComplexBlock> entities = new ArrayList<ComplexBlock>(2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     ComplexBlock baseBlock = etc.getServer().getComplexBlock(x, y, z);
/* 370 */     int dev = -1;
/* 371 */     boolean isXDir = true;
/*     */     
/* 373 */     entities = _validateChest(entities, baseBlock);
/*     */     
/*     */     while (true) {
/* 376 */       ComplexBlock block = etc.getServer().getComplexBlock(x + (isXDir ? dev : 0), y, z + (isXDir ? 0 : dev));
/* 377 */       entities = _validateChest(entities, block);
/*     */       
/* 379 */       if (dev == 1) {
/* 380 */         if (isXDir) {
/* 381 */           isXDir = false;
/* 382 */           dev = -1;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 389 */       dev = 1;
/*     */     } 
/*     */     
/* 392 */     return entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemDB getMemoryDatabase() {
/* 399 */     return this.memoryDatabase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PhysDB getPhysicalDatabase() {
/* 406 */     return this.physicalDatabase;
/*     */   }
/*     */   
/*     */   public int getPlayerDropTransferTarget(String player) {
/* 410 */     String rawTarget = this.memoryDatabase.getModeData(player, "dropTransfer");
/*     */     
/*     */     try {
/* 413 */       int ret = Integer.parseInt(rawTarget.substring(1));
/* 414 */       return ret;
/* 415 */     } catch (Throwable throwable) {
/*     */ 
/*     */       
/* 418 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initialize() {
/* 423 */     if (!Database.isConnected()) {
/*     */       return;
/*     */     }
/*     */     
/* 427 */     log("Registering hooks");
/*     */     
/* 429 */     this.listener = new LWCListener(this);
/*     */     
/* 431 */     registerHook(PluginLoader.Hook.DISCONNECT);
/* 432 */     registerHook(PluginLoader.Hook.COMMAND);
/*     */     
/* 434 */     registerHook(PluginLoader.Hook.BLOCK_BROKEN);
/* 435 */     registerHook(PluginLoader.Hook.BLOCK_DESTROYED);
/* 436 */     registerHook(PluginLoader.Hook.OPEN_INVENTORY);
/* 437 */     registerHook(PluginLoader.Hook.EXPLODE);
/* 438 */     registerHook(PluginLoader.Hook.ITEM_DROP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdmin(Player player) {
/* 449 */     return player.canUseCommand("/lwcadmin");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMod(Player player) {
/* 460 */     return player.canUseCommand("/lwcmod");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModeBlacklisted(String mode) {
/* 470 */     String blacklistedModes = ConfigValues.BLACKLISTED_MODES.getString();
/*     */     
/* 472 */     if (blacklistedModes.isEmpty()) {
/* 473 */       return false;
/*     */     }
/*     */     
/* 476 */     String[] modes = blacklistedModes.split(","); byte b; int i;
/*     */     String[] arrayOfString1;
/* 478 */     for (i = (arrayOfString1 = modes).length, b = 0; b < i; ) { String _mode = arrayOfString1[b];
/* 479 */       if (mode.equalsIgnoreCase(_mode)) {
/* 480 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 484 */     return false;
/*     */   }
/*     */   
/*     */   public void log(String str) {
/* 488 */     this.logger.log(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean notInPersistentMode(String player) {
/* 499 */     return !this.memoryDatabase.hasMode(player, "persist");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerDropTransferring(String player) {
/* 509 */     return (this.memoryDatabase.hasMode(player, "dropTransfer") && this.memoryDatabase.getModeData(player, "dropTransfer").startsWith("t"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendFullHelp(Player player) {
/* 519 */     player.sendMessage(" ");
/* 520 */     player.sendMessage("§2Welcome to LWC, a Protection mod");
/* 521 */     player.sendMessage(" ");
/* 522 */     player.sendMessage("§a/lwc -c - View creation help");
/* 523 */     player.sendMessage("§a/lwc -c <public|private|password>");
/* 524 */     player.sendMessage("§a/lwc -m - Modify an existing private protection");
/* 525 */     player.sendMessage("§a/lwc -u - Unlock a password protected entity");
/* 526 */     player.sendMessage("§a/lwc -i  - View information on a protected Chest or Furnace");
/* 527 */     player.sendMessage("§a/lwc -r <chest|furnace|modes>");
/*     */     
/* 529 */     player.sendMessage("§a/lwc -p <persist|droptransfer>");
/*     */     
/* 531 */     if (isAdmin(player)) {
/* 532 */       player.sendMessage("");
/* 533 */       player.sendMessage("§4/lwc admin - Admin functions");
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
/*     */ 
/*     */   
/*     */   public boolean isInCuboidSafeZone(Player player) {
/* 547 */     if (!ConfigValues.CUBOID_SAFE_AREAS.getBool()) {
/* 548 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 554 */       Plugin cuboidPlugin = etc.getLoader().getPlugin("CuboidPlugin");
/*     */       
/* 556 */       if (cuboidPlugin == null) {
/* 557 */         player.sendMessage("CuboidPlugin is not activated");
/* 558 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 562 */       Class<?> cuboidClass = cuboidPlugin.getClass().getClassLoader().loadClass("CuboidAreas");
/*     */ 
/*     */       
/* 565 */       Method findCuboidArea = cuboidClass.getMethod("findCuboidArea", new Class[] { int.class, int.class, int.class });
/*     */ 
/*     */       
/* 568 */       Object cuboidC = findCuboidArea.invoke(null, new Object[] { Integer.valueOf((int)player.getX()), Integer.valueOf((int)player.getY()), Integer.valueOf((int)player.getZ()) });
/*     */ 
/*     */       
/* 571 */       if (cuboidC != null) {
/*     */         
/* 573 */         Field pvp = cuboidC.getClass().getDeclaredField("PvP");
/* 574 */         pvp.setAccessible(true);
/*     */         
/* 576 */         boolean bool = pvp.getBoolean(cuboidC);
/*     */         
/* 578 */         return bool;
/*     */       } 
/*     */       
/* 581 */       Class<?> cuboidPluginClass = cuboidPlugin.getClass().getClassLoader().loadClass("CuboidPlugin");
/*     */ 
/*     */       
/* 584 */       Field globalDisablePvP = cuboidPluginClass.getDeclaredField("globalDisablePvP");
/* 585 */       globalDisablePvP.setAccessible(true);
/*     */       
/* 587 */       boolean isPvP = !globalDisablePvP.getBoolean((Object)null);
/*     */       
/* 589 */       return isPvP;
/*     */     }
/* 591 */     catch (Exception e) {
/* 592 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPendingRequest(Player player) {
/* 602 */     player.sendMessage("§4You already have a pending chest request.");
/* 603 */     player.sendMessage("§4To remove it, type /lwc free pending");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSimpleUsage(Player player, String command) {
/* 613 */     player.sendMessage("§4Usage:§6 " + command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ComplexBlock> _validateChest(List<ComplexBlock> entities, ComplexBlock block) {
/* 624 */     if (block == null) {
/* 625 */       return entities;
/*     */     }
/*     */     
/* 628 */     if (entities.size() > 2) {
/* 629 */       return entities;
/*     */     }
/*     */     
/* 632 */     if (block instanceof Furnace) {
/* 633 */       if (entities.size() == 0)
/*     */       {
/* 635 */         if (!entities.contains(block)) {
/* 636 */           entities.add(block);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 641 */       return entities;
/*     */     } 
/* 643 */     if (entities.size() == 1) {
/* 644 */       ComplexBlock other = entities.get(0);
/*     */       
/* 646 */       if (!(other instanceof Chest) && !(other instanceof DoubleChest)) {
/* 647 */         return entities;
/*     */       }
/*     */     } 
/*     */     
/* 651 */     if (!entities.contains(block)) {
/* 652 */       entities.add(block);
/*     */     }
/*     */ 
/*     */     
/* 656 */     return entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String byteArray2Hex(byte[] hash) {
/* 667 */     Formatter formatter = new Formatter(); byte b; int i; byte[] arrayOfByte;
/* 668 */     for (i = (arrayOfByte = hash).length, b = 0; b < i; ) { byte b1 = arrayOfByte[b];
/* 669 */       formatter.format("%02x", new Object[] { Byte.valueOf(b1) }); b++; }
/*     */     
/* 671 */     return formatter.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadCommands() {
/* 679 */     registerCommand(Command_Admin.class);
/* 680 */     registerCommand(Command_Create.class);
/* 681 */     registerCommand(Command_Free.class);
/* 682 */     registerCommand(Command_Info.class);
/* 683 */     registerCommand(Command_Modes.class);
/* 684 */     registerCommand(Command_Modify.class);
/* 685 */     registerCommand(Command_Unlock.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerCommand(Class<?> clazz) {
/*     */     try {
/* 695 */       Command command = (Command)clazz.newInstance();
/* 696 */       this.commands.add(command);
/* 697 */       log("Loaded command : " + clazz.getSimpleName());
/* 698 */     } catch (InstantiationException e) {
/* 699 */       e.printStackTrace();
/* 700 */     } catch (IllegalAccessException e) {
/* 701 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerHook(PluginLoader.Hook hook) {
/* 712 */     registerHook(hook, PluginListener.Priority.MEDIUM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerHook(PluginLoader.Hook hook, PluginListener.Priority priority) {
/* 723 */     log("LWCListener -> " + hook.toString());
/*     */     
/* 725 */     etc.getLoader().addListener(hook, this.listener, this, priority);
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\LWC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */