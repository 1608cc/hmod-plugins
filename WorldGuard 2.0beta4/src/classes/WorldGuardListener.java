/*      */ package classes;
/*      */ import BaseEntity;
/*      */ import Blacklist;
/*      */ import BlacklistLogger;
/*      */ import BlacklistLoggerHandler;
/*      */ import Block;
/*      */ import ComplexBlock;
/*      */ import FileLoggerHandler;
/*      */ import HMPlayer;
/*      */ import Inventory;
/*      */ import Item;
/*      */ import Location;
/*      */ import LoggerToChatHandler;
/*      */ import Player;
/*      */ import PluginLoader;
/*      */ import PropertiesFile;
/*      */ import Server;
/*      */ import Sign;
/*      */ import Warp;
/*      */ import WorldEditBridge;
/*      */ import com.sk89q.worldedit.BlockVector;
/*      */ import com.sk89q.worldedit.IncompleteRegionException;
/*      */ import com.sk89q.worldedit.Vector;
/*      */ import com.sk89q.worldedit.blocks.BlockType;
/*      */ import com.sk89q.worldguard.LocalPlayer;
/*      */ import com.sk89q.worldguard.domains.DefaultDomain;
/*      */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*      */ import com.sk89q.worldguard.protection.AreaFlags;
/*      */ import com.sk89q.worldguard.protection.ProtectedCuboidRegion;
/*      */ import com.sk89q.worldguard.protection.ProtectedRegion;
/*      */ import com.sk89q.worldguard.protection.ProtectionDatabase;
/*      */ import com.sk89q.worldguard.protection.RegionManager;
/*      */ import etc;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Handler;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ public class WorldGuardListener extends PluginListener {
/*   50 */   private static final Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   private PropertiesFile properties = new PropertiesFile("worldguard.properties");
/*      */   
/*   57 */   private static int CMD_LIST_SIZE = 9;
/*   58 */   private static Pattern groupPattern = Pattern.compile("^[gG]:(.+)$");
/*      */   
/*   60 */   private RegionManager regionManager = (RegionManager)new FlatRegionManager();
/*      */   
/*   62 */   private ProtectionDatabase regionLoader = (ProtectionDatabase)new CSVDatabase(new File("worldguard-regions.txt"));
/*      */   
/*   64 */   private Set<String> invinciblePlayers = new HashSet<String>();
/*   65 */   private Set<String> amphibiousPlayers = new HashSet<String>();
/*   66 */   private Map<String, Long> recentLogins = new HashMap<String, Long>();
/*   67 */   private Map<String, Long> lastSpawn = new HashMap<String, Long>();
/*      */   
/*      */   private boolean useRegions = false;
/*      */   private boolean stopFireSpread = false;
/*      */   private boolean enforceOneSession;
/*      */   private boolean blockCreepers;
/*      */   private boolean blockTNT;
/*      */   private boolean blockLighter;
/*      */   private boolean preventLavaFire;
/*      */   private boolean disableAllFire;
/*      */   private boolean simulateSponge;
/*      */   private int spongeRadius;
/*      */   private Set<Integer> fireNoSpreadBlocks;
/*      */   private Set<Integer> allowedLavaSpreadOver;
/*      */   private Set<Integer> itemDropBlacklist;
/*      */   private Set<Integer> preventWaterDamage;
/*      */   private boolean classicWater;
/*      */   private boolean noPhysicsGravel;
/*      */   private boolean noPhysicsSand;
/*      */   private boolean allowPortalAnywhere;
/*      */   private boolean disableFallDamage;
/*      */   private boolean disableLavaDamage;
/*      */   private boolean disableFireDamage;
/*      */   private boolean disableWaterDamage;
/*      */   private boolean disableSuffocationDamage;
/*      */   private boolean teleportOnSuffocation;
/*      */   private int loginProtection;
/*      */   private int spawnProtection;
/*      */   private boolean teleportToHome;
/*      */   private boolean exactRespawn;
/*      */   private boolean kickOnDeath;
/*   98 */   private int regionWand = 287;
/*      */ 
/*      */ 
/*      */   
/*      */   private Blacklist blacklist;
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGuardListener(WorldGuard plugin) {
/*  107 */     postReload();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<Integer> toBlockIDSet(String str) {
/*  117 */     if (str.trim().length() == 0) {
/*  118 */       return null;
/*      */     }
/*      */     
/*  121 */     String[] items = str.split(",");
/*  122 */     Set<Integer> result = new HashSet<Integer>(); byte b; int i;
/*      */     String[] arrayOfString1;
/*  124 */     for (i = (arrayOfString1 = items).length, b = 0; b < i; ) { String item = arrayOfString1[b];
/*      */       try {
/*  126 */         result.add(Integer.valueOf(Integer.parseInt(item.trim())));
/*  127 */       } catch (NumberFormatException e) {
/*  128 */         int id = etc.getDataSource().getItem(item.trim());
/*  129 */         if (id != 0) {
/*  130 */           result.add(Integer.valueOf(id));
/*      */         } else {
/*  132 */           logger.log(Level.WARNING, "WorldGuard: Unknown block name: " + 
/*  133 */               item);
/*      */         } 
/*      */       } 
/*      */       b++; }
/*      */     
/*  138 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postReload() {
/*  145 */     this.invinciblePlayers.clear();
/*  146 */     this.amphibiousPlayers.clear();
/*      */     
/*      */     try {
/*  149 */       for (Player player : etc.getServer().getPlayerList()) {
/*  150 */         if (player.isInGroup("wg-invincible")) {
/*  151 */           this.invinciblePlayers.add(player.getName());
/*      */         }
/*      */         
/*  154 */         if (player.isInGroup("wg-amphibious")) {
/*  155 */           this.amphibiousPlayers.add(player.getName());
/*      */         }
/*      */       } 
/*  158 */     } catch (NullPointerException nullPointerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadConfiguration() {
/*      */     try {
/*  167 */       this.properties.load();
/*  168 */     } catch (IOException e) {
/*  169 */       logger.log(Level.WARNING, "WorldGuard: Failed to load configuration: " + 
/*  170 */           e.getMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  174 */       this.regionLoader.load();
/*  175 */       this.regionManager.setRegions(this.regionLoader.getRegions());
/*  176 */     } catch (IOException e) {
/*  177 */       logger.warning("WorldGuard: Failed to load regions: " + 
/*  178 */           e.getMessage());
/*      */     } 
/*      */     
/*  181 */     this.recentLogins.clear();
/*      */ 
/*      */     
/*  184 */     this.useRegions = this.properties.getBoolean("use-regions", true);
/*  185 */     this.enforceOneSession = this.properties.getBoolean("enforce-single-session", true);
/*  186 */     this.blockCreepers = this.properties.getBoolean("block-creepers", false);
/*  187 */     this.blockTNT = this.properties.getBoolean("block-tnt", false);
/*  188 */     this.blockLighter = this.properties.getBoolean("block-lighter", false);
/*  189 */     this.preventLavaFire = this.properties.getBoolean("disable-lava-fire", true);
/*  190 */     this.disableAllFire = this.properties.getBoolean("disable-all-fire-spread", false);
/*  191 */     this.preventWaterDamage = toBlockIDSet(this.properties.getString("disable-water-damage-blocks", ""));
/*  192 */     this.itemDropBlacklist = toBlockIDSet(this.properties.getString("item-drop-blacklist", ""));
/*  193 */     this.fireNoSpreadBlocks = toBlockIDSet(this.properties.getString("disallowed-fire-spread-blocks", ""));
/*  194 */     this.allowedLavaSpreadOver = toBlockIDSet(this.properties.getString("allowed-lava-spread-blocks", ""));
/*  195 */     this.classicWater = this.properties.getBoolean("classic-water", false);
/*  196 */     this.simulateSponge = this.properties.getBoolean("simulate-sponge", true);
/*  197 */     this.spongeRadius = Math.max(1, this.properties.getInt("sponge-radius", 3)) - 1;
/*  198 */     this.noPhysicsGravel = this.properties.getBoolean("no-physics-gravel", false);
/*  199 */     this.noPhysicsSand = this.properties.getBoolean("no-physics-sand", false);
/*  200 */     this.allowPortalAnywhere = this.properties.getBoolean("allow-portal-anywhere", false);
/*  201 */     this.disableFallDamage = this.properties.getBoolean("disable-fall-damage", false);
/*  202 */     this.disableLavaDamage = this.properties.getBoolean("disable-lava-damage", false);
/*  203 */     this.disableFireDamage = this.properties.getBoolean("disable-fire-damage", false);
/*  204 */     this.disableWaterDamage = this.properties.getBoolean("disable-water-damage", false);
/*  205 */     this.disableSuffocationDamage = this.properties.getBoolean("disable-suffocation-damage", false);
/*  206 */     this.teleportOnSuffocation = this.properties.getBoolean("teleport-on-suffocation", false);
/*  207 */     this.loginProtection = this.properties.getInt("login-protection", 3);
/*  208 */     this.spawnProtection = this.properties.getInt("spawn-protection", 0);
/*  209 */     this.kickOnDeath = this.properties.getBoolean("kick-on-death", false);
/*  210 */     this.teleportToHome = this.properties.getBoolean("teleport-to-home-on-death", false);
/*  211 */     this.exactRespawn = this.properties.getBoolean("exact-respawn", false);
/*  212 */     this.regionWand = this.properties.getInt("regions-wand", 287);
/*      */ 
/*      */     
/*  215 */     boolean logConsole = this.properties.getBoolean("log-console", true);
/*      */ 
/*      */     
/*  218 */     boolean logDatabase = this.properties.getBoolean("log-database", false);
/*  219 */     String dsn = this.properties.getString("log-database-dsn", "jdbc:mysql://localhost:3306/minecraft");
/*  220 */     String user = this.properties.getString("log-database-user", "root");
/*  221 */     String pass = this.properties.getString("log-database-pass", "");
/*  222 */     String table = this.properties.getString("log-database-table", "blacklist_events");
/*      */ 
/*      */     
/*  225 */     boolean logFile = this.properties.getBoolean("log-file", false);
/*  226 */     String logFilePattern = this.properties.getString("log-file-path", "worldguard/logs/%Y-%m-%d.log");
/*  227 */     int logFileCacheSize = Math.max(1, this.properties.getInt("log-file-open-files", 10));
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  232 */       if (this.blacklist != null) {
/*  233 */         this.blacklist.getLogger().close();
/*      */       }
/*      */ 
/*      */       
/*  237 */       Blacklist blist = new Blacklist();
/*  238 */       blist.load(new File("worldguard-blacklist.txt"));
/*      */ 
/*      */ 
/*      */       
/*  242 */       if (blist.isEmpty()) {
/*  243 */         this.blacklist = null;
/*      */       } else {
/*  245 */         this.blacklist = blist;
/*  246 */         logger.log(Level.INFO, "WorldGuard: Blacklist loaded.");
/*      */         
/*  248 */         BlacklistLogger blacklistLogger = blist.getLogger();
/*      */         
/*  250 */         if (logDatabase) {
/*  251 */           blacklistLogger.addHandler((BlacklistLoggerHandler)new DatabaseLoggerHandler(dsn, user, pass, table));
/*      */         }
/*      */         
/*  254 */         if (logConsole) {
/*  255 */           blacklistLogger.addHandler((BlacklistLoggerHandler)new ConsoleLoggerHandler());
/*      */         }
/*      */         
/*  258 */         if (logFile) {
/*  259 */           FileLoggerHandler handler = 
/*  260 */             new FileLoggerHandler(logFilePattern, logFileCacheSize);
/*  261 */           blacklistLogger.addHandler((BlacklistLoggerHandler)handler);
/*      */         } 
/*      */       } 
/*  264 */     } catch (FileNotFoundException e) {
/*  265 */       logger.log(Level.WARNING, "WorldGuard blacklist does not exist.");
/*  266 */     } catch (IOException e) {
/*  267 */       logger.log(Level.WARNING, "Could not load WorldGuard blacklist: " + 
/*  268 */           e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/*  272 */     if (this.properties.getBoolean("summary-on-start", true)) {
/*  273 */       logger.log(Level.INFO, this.enforceOneSession ? "WorldGuard: Single session is enforced." : 
/*  274 */           "WorldGuard: Single session is NOT ENFORCED.");
/*  275 */       logger.log(Level.INFO, this.blockTNT ? "WorldGuard: TNT ignition is blocked." : 
/*  276 */           "WorldGuard: TNT ignition is PERMITTED.");
/*  277 */       logger.log(Level.INFO, this.blockLighter ? "WorldGuard: Lighters are blocked." : 
/*  278 */           "WorldGuard: Lighters are PERMITTED.");
/*  279 */       logger.log(Level.INFO, this.preventLavaFire ? "WorldGuard: Lava fire is blocked." : 
/*  280 */           "WorldGuard: Lava fire is PERMITTED.");
/*  281 */       if (this.disableAllFire) {
/*  282 */         logger.log(Level.INFO, "WorldGuard: All fire spread is disabled.");
/*      */       }
/*  284 */       else if (this.fireNoSpreadBlocks != null) {
/*  285 */         logger.log(Level.INFO, "WorldGuard: Fire spread is limited to " + 
/*  286 */             this.fireNoSpreadBlocks.size() + " block types.");
/*      */       } else {
/*  288 */         logger.log(Level.INFO, "WorldGuard: Fire spread is UNRESTRICTED.");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String onLoginChecks(String user) {
/*  303 */     if (this.enforceOneSession) {
/*  304 */       for (Player player : etc.getServer().getPlayerList()) {
/*  305 */         if (player.getName().equalsIgnoreCase(user)) {
/*  306 */           player.kick("Logged in from another location.");
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  311 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLogin(Player player) {
/*  321 */     if (this.stopFireSpread) {
/*  322 */       player.sendMessage("§eFire spread is currently globally disabled.");
/*      */     }
/*      */     
/*  325 */     if (this.loginProtection > 0 || this.spawnProtection > 0 || 
/*  326 */       this.kickOnDeath || this.teleportToHome || this.exactRespawn) {
/*  327 */       this.recentLogins.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/*      */     }
/*      */     
/*  330 */     if (player.isInGroup("wg-invincible")) {
/*  331 */       this.invinciblePlayers.add(player.getName());
/*      */     }
/*      */     
/*  334 */     if (player.isInGroup("wg-amphibious")) {
/*  335 */       this.amphibiousPlayers.add(player.getName());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onCommand(Player player, String[] split) {
/*  349 */     if (split[0].equalsIgnoreCase("/stopfire") && 
/*  350 */       player.canUseCommand("/stopfire")) {
/*  351 */       if (!this.stopFireSpread) {
/*  352 */         etc.getServer().messageAll("§eFire spread has been globally disabled by " + 
/*  353 */             player.getName() + ".");
/*      */       } else {
/*  355 */         player.sendMessage("§eFire spread was already globally disabled.");
/*      */       } 
/*  357 */       this.stopFireSpread = true;
/*  358 */       return true;
/*  359 */     }  if (split[0].equalsIgnoreCase("/allowfire") && 
/*  360 */       player.canUseCommand("/stopfire")) {
/*  361 */       if (this.stopFireSpread) {
/*  362 */         etc.getServer().messageAll("§eFire spread has been globally re-enabled by " + 
/*  363 */             player.getName() + ".");
/*      */       } else {
/*  365 */         player.sendMessage("§eFire spread was already globally enabled.");
/*      */       } 
/*  367 */       this.stopFireSpread = false;
/*  368 */       return true;
/*  369 */     }  if (split[0].equalsIgnoreCase("/god") && 
/*  370 */       player.canUseCommand("/god")) {
/*      */       
/*  372 */       if (split.length > 1) {
/*  373 */         if (!player.canUseCommand("/godother")) {
/*  374 */           player.sendMessage("§cYou don't have permission to make others invincible.");
/*  375 */           return true;
/*      */         } 
/*      */         
/*  378 */         Player other = etc.getServer().matchPlayer(split[1]);
/*  379 */         if (other == null) {
/*  380 */           player.sendMessage("§cPlayer not found.");
/*      */         }
/*  382 */         else if (!this.invinciblePlayers.contains(other.getName())) {
/*  383 */           this.invinciblePlayers.add(other.getName());
/*  384 */           player.sendMessage("§e" + other.getName() + " is now invincible!");
/*  385 */           other.sendMessage("§e" + player.getName() + " has made you invincible!");
/*      */         } else {
/*  387 */           this.invinciblePlayers.remove(other.getName());
/*  388 */           player.sendMessage("§e" + other.getName() + " is no longer invincible.");
/*  389 */           other.sendMessage("§e" + player.getName() + " has taken away your invincibility.");
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  394 */       else if (!this.invinciblePlayers.contains(player.getName())) {
/*  395 */         this.invinciblePlayers.add(player.getName());
/*  396 */         player.sendMessage("§eYou are now invincible!");
/*      */       } else {
/*  398 */         this.invinciblePlayers.remove(player.getName());
/*  399 */         player.sendMessage("§eYou are no longer invincible.");
/*      */       } 
/*      */       
/*  402 */       return true;
/*  403 */     }  if ((split[0].equalsIgnoreCase("/stack") || 
/*  404 */       split[0].equalsIgnoreCase("/;")) && 
/*  405 */       player.canUseCommand("/stack")) {
/*  406 */       Item[] items = player.getInventory().getContents();
/*  407 */       int len = items.length;
/*      */       
/*  409 */       int affected = 0;
/*      */       
/*  411 */       for (int i = 0; i < len; i++) {
/*  412 */         Item item = items[i];
/*      */ 
/*      */         
/*  415 */         if (item != null && item.getAmount() > 0 && 
/*  416 */           !shouldNotStack(item.getItemId()))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  421 */           if (item.getItemId() < 325 || item.getItemId() > 327)
/*      */           {
/*      */ 
/*      */             
/*  425 */             if (item.getAmount() < 64) {
/*  426 */               int needed = 64 - item.getAmount();
/*      */ 
/*      */               
/*  429 */               for (int j = i + 1; j < len; j++) {
/*  430 */                 Item item2 = items[j];
/*      */ 
/*      */                 
/*  433 */                 if (item2 != null && item2.getAmount() > 0 && 
/*  434 */                   !shouldNotStack(item.getItemId()))
/*      */                 {
/*      */ 
/*      */ 
/*      */                   
/*  439 */                   if (item2.getItemId() == item.getItemId()) {
/*      */                     
/*  441 */                     if (item2.getAmount() > needed) {
/*  442 */                       item.setAmount(64);
/*  443 */                       item2.setAmount(item2.getAmount() - needed);
/*      */                       
/*      */                       break;
/*      */                     } 
/*  447 */                     items[j] = null;
/*  448 */                     item.setAmount(item.getAmount() + item2.getAmount());
/*  449 */                     needed = 64 - item.getAmount();
/*      */ 
/*      */                     
/*  452 */                     affected++;
/*      */                   }  } 
/*      */               } 
/*      */             }  } 
/*      */         }
/*      */       } 
/*  458 */       if (affected > 0) {
/*  459 */         ItemArrayUtil.setContents((ItemArray)player.getInventory(), items);
/*      */       }
/*      */       
/*  462 */       player.sendMessage("§eItems compacted into stacks!");
/*      */       
/*  464 */       return true;
/*  465 */     }  if (split[0].equalsIgnoreCase("/rg") || 
/*  466 */       split[0].equalsIgnoreCase("/region")) {
/*  467 */       if (split.length < 2) {
/*  468 */         player.sendMessage("§c/rg <define|flag|delete|info|add|remove|list|save|load> ...");
/*  469 */         return true;
/*      */       } 
/*      */       
/*  472 */       String action = split[1];
/*  473 */       String[] args = new String[split.length - 1];
/*  474 */       System.arraycopy(split, 1, args, 0, split.length - 1);
/*      */       
/*  476 */       handleRegionCommand(player, action, args);
/*      */       
/*  478 */       return true;
/*  479 */     }  if (split[0].equalsIgnoreCase("/reload") && 
/*  480 */       player.canUseCommand("/reload") && 
/*  481 */       split.length > 1 && 
/*  482 */       split[1].equalsIgnoreCase("WorldGuard")) {
/*  483 */       LoggerToChatHandler handler = new LoggerToChatHandler(player);
/*  484 */       handler.setLevel(Level.ALL);
/*  485 */       Logger minecraftLogger = Logger.getLogger("Minecraft");
/*  486 */       minecraftLogger.addHandler((Handler)handler);
/*      */       
/*      */       try {
/*  489 */         loadConfiguration();
/*  490 */         postReload();
/*  491 */         player.sendMessage("WorldGuard configuration reloaded.");
/*  492 */       } catch (Throwable t) {
/*  493 */         player.sendMessage("Error while reloading: " + 
/*  494 */             t.getMessage());
/*      */       } finally {
/*  496 */         minecraftLogger.removeHandler((Handler)handler);
/*      */       } 
/*      */       
/*  499 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  503 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleRegionCommand(Player player, String action, String[] args) {
/*  514 */     if (action.equalsIgnoreCase("define") && 
/*  515 */       canUseRegionCommand(player, "/regiondefine")) {
/*  516 */       if (args.length < 2) {
/*  517 */         player.sendMessage("§c/rg define <id> [owner1 [owner2 [owners...]]]");
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  522 */         String id = args[1].toLowerCase();
/*  523 */         BlockVector min = WorldEditBridge.getRegionMinimumPoint(player).toBlockVector();
/*  524 */         BlockVector max = WorldEditBridge.getRegionMaximumPoint(player).toBlockVector();
/*  525 */         ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(min, max);
/*  526 */         if (args.length >= 3) {
/*  527 */           protectedCuboidRegion.setOwners(parseDomainString(args, 2));
/*      */         }
/*  529 */         this.regionManager.addRegion(id, (ProtectedRegion)protectedCuboidRegion);
/*  530 */         this.regionLoader.save(this.regionManager);
/*  531 */         player.sendMessage("§eRegion saved as " + id + ".");
/*  532 */       } catch (WorldEditNotInstalled e) {
/*  533 */         player.sendMessage("§cWorldEdit must be installed and enabled as a plugin.");
/*  534 */       } catch (IncompleteRegionException e) {
/*  535 */         player.sendMessage("§cYou must first define an area in WorldEdit.");
/*  536 */       } catch (IOException e) {
/*  537 */         player.sendMessage("§cRegion database failed to save: " + 
/*  538 */             e.getMessage());
/*      */       } 
/*  540 */     } else if (action.equalsIgnoreCase("flag") && 
/*  541 */       canUseRegionCommand(player, "/regiondefine")) {
/*  542 */       if (args.length < 4) {
/*  543 */         player.sendMessage("§c/rg flag <id> <build|pvp|tnt|lighter> <none|allow|deny>");
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  548 */         String id = args[1].toLowerCase();
/*  549 */         String flagStr = args[2];
/*  550 */         String stateStr = args[3];
/*  551 */         ProtectedRegion region = this.regionManager.getRegion(id);
/*      */         
/*  553 */         if (region == null) {
/*  554 */           player.sendMessage("§cCould not find a region by that ID.");
/*      */           
/*      */           return;
/*      */         } 
/*  558 */         AreaFlags.State state = null;
/*      */         
/*  560 */         if (stateStr.equalsIgnoreCase("allow")) {
/*  561 */           state = AreaFlags.State.ALLOW;
/*  562 */         } else if (stateStr.equalsIgnoreCase("deny")) {
/*  563 */           state = AreaFlags.State.DENY;
/*  564 */         } else if (stateStr.equalsIgnoreCase("none")) {
/*  565 */           state = AreaFlags.State.NONE;
/*      */         } else {
/*  567 */           player.sendMessage("§cAcceptable states: allow, deny, none");
/*      */           
/*      */           return;
/*      */         } 
/*  571 */         AreaFlags flags = region.getFlags();
/*      */         
/*  573 */         if (flagStr.equalsIgnoreCase("build")) {
/*  574 */           flags.allowBuild = state;
/*  575 */         } else if (flagStr.equalsIgnoreCase("pvp")) {
/*  576 */           flags.allowPvP = state;
/*  577 */         } else if (flagStr.equalsIgnoreCase("tnt")) {
/*  578 */           flags.allowTNT = state;
/*  579 */         } else if (flagStr.equalsIgnoreCase("lighter")) {
/*  580 */           flags.allowLighter = state;
/*      */         } else {
/*  582 */           player.sendMessage("§cAcceptable flags: build, pvp, tnt, lighter");
/*      */           
/*      */           return;
/*      */         } 
/*  586 */         this.regionLoader.save(this.regionManager);
/*  587 */         player.sendMessage("§eRegion '" + id + "' updated.");
/*  588 */       } catch (IOException e) {
/*  589 */         player.sendMessage("§cRegion database failed to save: " + 
/*  590 */             e.getMessage());
/*      */       } 
/*  592 */     } else if (action.equalsIgnoreCase("info") && 
/*  593 */       canUseRegionCommand(player, "/regioninfo")) {
/*  594 */       if (args.length < 2) {
/*  595 */         player.sendMessage("§c/rg info <id>");
/*      */         
/*      */         return;
/*      */       } 
/*  599 */       String id = args[1].toLowerCase();
/*  600 */       if (!this.regionManager.hasRegion(id)) {
/*  601 */         player.sendMessage("§cA region with ID '" + 
/*  602 */             id + "' doesn't exist.");
/*      */         
/*      */         return;
/*      */       } 
/*  606 */       ProtectedRegion region = this.regionManager.getRegion(id);
/*  607 */       AreaFlags flags = region.getFlags();
/*  608 */       DefaultDomain domain = region.getOwners();
/*      */       
/*  610 */       player.sendMessage("§eRegion ID: " + id);
/*  611 */       player.sendMessage("§7Type: " + region.getClass().getCanonicalName());
/*  612 */       player.sendMessage("§bBuild: " + flags.allowBuild.name());
/*  613 */       player.sendMessage("§bPvP: " + flags.allowPvP.name());
/*  614 */       player.sendMessage("§bTNT: " + flags.allowTNT.name());
/*  615 */       player.sendMessage("§bLighter: " + flags.allowLighter.name());
/*  616 */       player.sendMessage("§dPlayers: " + domain.toPlayersString());
/*  617 */       player.sendMessage("§dGroups: " + domain.toGroupsString());
/*  618 */     } else if (action.equalsIgnoreCase("add") && 
/*  619 */       canUseRegionCommand(player, "/regiondefine")) {
/*  620 */       if (args.length < 2) {
/*  621 */         player.sendMessage("§c/rg add <id>");
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  626 */         String id = args[1].toLowerCase();
/*  627 */         if (!this.regionManager.hasRegion(id)) {
/*  628 */           player.sendMessage("§cA region with ID '" + 
/*  629 */               id + "' doesn't exist.");
/*      */           
/*      */           return;
/*      */         } 
/*  633 */         addToDomain(this.regionManager.getRegion(id).getOwners(), args, 1);
/*      */         
/*  635 */         this.regionLoader.save(this.regionManager);
/*  636 */         player.sendMessage("§eRegion updated!");
/*  637 */       } catch (IOException e) {
/*  638 */         player.sendMessage("§cRegion database failed to save: " + 
/*  639 */             e.getMessage());
/*      */       } 
/*  641 */     } else if (action.equalsIgnoreCase("remove") && 
/*  642 */       canUseRegionCommand(player, "/regiondefine")) {
/*  643 */       if (args.length < 2) {
/*  644 */         player.sendMessage("§c/rg remove <id>");
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  649 */         String id = args[1].toLowerCase();
/*  650 */         if (!this.regionManager.hasRegion(id)) {
/*  651 */           player.sendMessage("§cA region with ID '" + 
/*  652 */               id + "' doesn't exist.");
/*      */           
/*      */           return;
/*      */         } 
/*  656 */         removeFromDomain(this.regionManager.getRegion(id).getOwners(), args, 1);
/*      */         
/*  658 */         this.regionLoader.save(this.regionManager);
/*  659 */         player.sendMessage("§eRegion updated!");
/*  660 */       } catch (IOException e) {
/*  661 */         player.sendMessage("§cRegion database failed to save: " + 
/*  662 */             e.getMessage());
/*      */       } 
/*  664 */     } else if (action.equalsIgnoreCase("list") && 
/*  665 */       canUseRegionCommand(player, "/regionlist")) {
/*  666 */       int page = 0;
/*      */       
/*  668 */       if (args.length >= 2) {
/*      */         try {
/*  670 */           page = Math.max(0, Integer.parseInt(args[1]) - 1);
/*  671 */         } catch (NumberFormatException e) {
/*  672 */           page = 0;
/*      */         } 
/*      */       }
/*      */       
/*  676 */       Map<String, ProtectedRegion> regions = this.regionManager.getRegions();
/*  677 */       int size = regions.size();
/*  678 */       int pages = (int)Math.ceil((size / CMD_LIST_SIZE));
/*      */       
/*  680 */       String[] regionIDList = new String[size];
/*  681 */       int index = 0;
/*  682 */       for (String id : regions.keySet()) {
/*  683 */         regionIDList[index] = id;
/*  684 */         index++;
/*      */       } 
/*  686 */       Arrays.sort((Object[])regionIDList);
/*      */ 
/*      */       
/*  689 */       player.sendMessage("§cRegions (page " + (
/*  690 */           page + 1) + " of " + pages + "):");
/*      */       
/*  692 */       if (page < pages) {
/*  693 */         for (int i = page * CMD_LIST_SIZE; i < page * CMD_LIST_SIZE + CMD_LIST_SIZE && 
/*  694 */           i < size; i++) {
/*  695 */           player.sendMessage("§e" + (i + 1) + ". " + regionIDList[i]);
/*      */         }
/*      */       }
/*  698 */     } else if (action.equalsIgnoreCase("delete") && 
/*  699 */       canUseRegionCommand(player, "/regiondelete")) {
/*  700 */       if (args.length < 2) {
/*  701 */         player.sendMessage("§c/rg delete <id>");
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  706 */         String id = args[1].toLowerCase();
/*  707 */         if (!this.regionManager.hasRegion(id)) {
/*  708 */           player.sendMessage("§cA region with ID '" + 
/*  709 */               id + "' doesn't exist.");
/*      */           return;
/*      */         } 
/*  712 */         this.regionManager.removeRegion(id);
/*  713 */         this.regionLoader.save(this.regionManager);
/*  714 */         player.sendMessage("§eRegion removed!");
/*  715 */       } catch (IOException e) {
/*  716 */         player.sendMessage("§cRegion database failed to save: " + 
/*  717 */             e.getMessage());
/*      */       } 
/*  719 */     } else if (action.equalsIgnoreCase("save") && 
/*  720 */       canUseRegionCommand(player, "/regionsave")) {
/*      */       try {
/*  722 */         this.regionLoader.save(this.regionManager);
/*  723 */         player.sendMessage("§eRegion database saved to file!");
/*  724 */       } catch (IOException e) {
/*  725 */         player.sendMessage("§cRegion database failed to save: " + 
/*  726 */             e.getMessage());
/*      */       } 
/*  728 */     } else if (action.equalsIgnoreCase("load") && 
/*  729 */       canUseRegionCommand(player, "/regionload")) {
/*      */       try {
/*  731 */         this.regionLoader.load(this.regionManager);
/*  732 */         player.sendMessage("§eRegion database loaded from file!");
/*  733 */       } catch (IOException e) {
/*  734 */         player.sendMessage("§cRegion database failed to load: " + 
/*  735 */             e.getMessage());
/*      */       } 
/*      */     } else {
/*  738 */       player.sendMessage("§c/rg <define|flag|delete|info|add|remove|list|save|load> ...");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean canUseRegionCommand(Player player, String cmd) {
/*  750 */     if (!player.canUseCommand("/region") && 
/*  751 */       !player.canUseCommand(cmd)) return false;
/*      */     
/*      */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToDomain(DefaultDomain domain, String[] split, int startIndex) {
/*  763 */     for (int i = startIndex; i < split.length; i++) {
/*  764 */       String s = split[i];
/*  765 */       Matcher m = groupPattern.matcher(s);
/*  766 */       if (m.matches()) {
/*  767 */         domain.addGroup(m.group(1));
/*      */       } else {
/*  769 */         domain.addPlayer(s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void removeFromDomain(DefaultDomain domain, String[] split, int startIndex) {
/*  783 */     for (int i = startIndex; i < split.length; i++) {
/*  784 */       String s = split[i];
/*  785 */       Matcher m = groupPattern.matcher(s);
/*  786 */       if (m.matches()) {
/*  787 */         domain.removeGroup(m.group(1));
/*      */       } else {
/*  789 */         domain.removePlayer(s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DefaultDomain parseDomainString(String[] split, int startIndex) {
/*  802 */     DefaultDomain domain = new DefaultDomain();
/*      */     
/*  804 */     for (int i = startIndex; i < split.length; i++) {
/*  805 */       String s = split[i];
/*  806 */       Matcher m = groupPattern.matcher(s);
/*  807 */       if (m.matches()) {
/*  808 */         domain.addGroup(m.group(1));
/*      */       } else {
/*  810 */         domain.addPlayer(s);
/*      */       } 
/*      */     } 
/*      */     
/*  814 */     return domain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean shouldNotStack(int id) {
/*  824 */     if ((id < 256 || id > 259) && 
/*  825 */       id != 261 && (
/*  826 */       id < 267 || id > 279) && (
/*  827 */       id < 281 || id > 286) && (
/*  828 */       id < 290 || id > 294) && (
/*  829 */       id < 298 || id > 317) && (
/*  830 */       id < 325 || id > 327) && 
/*  831 */       id != 335 && 
/*  832 */       id != 346) return false;
/*      */     
/*      */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onConsoleCommand(String[] split) {
/*  844 */     if (split[0].equalsIgnoreCase("fire-stop")) {
/*  845 */       if (!this.stopFireSpread) {
/*  846 */         etc.getServer().messageAll("§eFire spread has been globally disabled by server console.");
/*      */         
/*  848 */         logger.log(Level.INFO, "Fire spread is now globally disabled.");
/*      */       } else {
/*  850 */         logger.log(Level.INFO, "Fire spread was already globally disabled.");
/*      */       } 
/*  852 */       this.stopFireSpread = true;
/*  853 */       return true;
/*  854 */     }  if (split[0].equalsIgnoreCase("fire-allow")) {
/*  855 */       if (this.stopFireSpread) {
/*  856 */         etc.getServer().messageAll("§eFire spread has been globally re-enabled by server console.");
/*      */         
/*  858 */         logger.log(Level.INFO, "Fire spread is now globally enabled.");
/*      */       } else {
/*  860 */         logger.log(Level.INFO, "Fire spread was already globally enabled.");
/*      */       } 
/*  862 */       this.stopFireSpread = false;
/*  863 */       return true;
/*      */     } 
/*      */     
/*  866 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemDrop(Player player, Item item) {
/*  881 */     if (this.itemDropBlacklist != null) {
/*  882 */       int n = item.getItemId();
/*  883 */       if (this.itemDropBlacklist.contains(Integer.valueOf(n))) {
/*  884 */         player.sendMessage("§cItem was destroyed!");
/*  885 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  889 */     if (this.blacklist != null && 
/*  890 */       !this.blacklist.onDrop(item.getItemId(), player)) {
/*  891 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemPickUp(Player player, Item item) {
/*  913 */     if (this.blacklist != null && this.blacklist.hasOnAcquire() && 
/*  914 */       !this.blacklist.onSilentAcquire(item.getItemId(), player)) {
/*  915 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  919 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemUse(Player player, Block blockPlaced, Block blockClicked, Item item) {
/*  963 */     if (this.blacklist != null && item != null) {
/*  964 */       int itemId = item.getItemId();
/*      */       
/*  966 */       if (!this.blacklist.onUse(itemId, player)) {
/*  967 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  971 */     if (this.useRegions && blockPlaced != null) {
/*  972 */       Vector pt = new Vector(blockPlaced.getX(), 
/*  973 */           blockPlaced.getY(), blockPlaced.getZ());
/*  974 */       HMPlayer hMPlayer = new HMPlayer(player);
/*      */       
/*  976 */       if (!player.canUseCommand("/regionbypass") && 
/*  977 */         !this.regionManager.getApplicableRegions(pt).canBuild((LocalPlayer)hMPlayer)) {
/*  978 */         player.sendMessage("§4You don't have permission for this area.");
/*  979 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  983 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockPlace(Player player, Block blockPlaced, Block blockClicked, Item itemInHand) {
/*  999 */     int itemId = itemInHand.getItemId();
/*      */     
/* 1001 */     if (this.useRegions) {
/* 1002 */       Vector pt = new Vector(blockPlaced.getX(), 
/* 1003 */           blockPlaced.getY(), blockPlaced.getZ());
/* 1004 */       HMPlayer hMPlayer = new HMPlayer(player);
/*      */       
/* 1006 */       if (!player.canUseCommand("/regionbypass") && 
/* 1007 */         !this.regionManager.getApplicableRegions(pt).canBuild((LocalPlayer)hMPlayer)) {
/* 1008 */         player.sendMessage("§4You don't have permission for this area.");
/* 1009 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1013 */     if (this.blacklist != null) {
/* 1014 */       if (!this.blacklist.onPlace(itemId, player)) {
/* 1015 */         return true;
/*      */       }
/*      */       
/* 1018 */       if (!this.blacklist.onRightClick(blockClicked, player)) {
/* 1019 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1023 */     if (this.simulateSponge && blockPlaced.getType() == 19) {
/* 1024 */       int ox = blockPlaced.getX();
/* 1025 */       int oy = blockPlaced.getY();
/* 1026 */       int oz = blockPlaced.getZ();
/*      */       
/* 1028 */       Server server = etc.getServer();
/*      */       
/* 1030 */       for (int cx = -this.spongeRadius; cx <= this.spongeRadius; cx++) {
/* 1031 */         for (int cy = -this.spongeRadius; cy <= this.spongeRadius; cy++) {
/* 1032 */           for (int cz = -this.spongeRadius; cz <= this.spongeRadius; cz++) {
/* 1033 */             int id = server.getBlockIdAt(ox + cx, oy + cy, oz + cz);
/* 1034 */             if (id == 8 || id == 9) {
/* 1035 */               server.setBlockAt(0, ox + cx, oy + cy, oz + cz);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1042 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockDestroy(Player player, Block block) {
/* 1054 */     if (this.blacklist != null) {
/* 1055 */       if (!this.blacklist.onDestroyWith(player.getItemInHand(), player)) {
/* 1056 */         return true;
/*      */       }
/*      */       
/* 1059 */       if (!this.blacklist.onDestroy(block, player)) {
/* 1060 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1064 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockBreak(Player player, Block block) {
/* 1076 */     if (this.useRegions) {
/* 1077 */       Vector pt = new Vector(block.getX(), 
/* 1078 */           block.getY(), block.getZ());
/* 1079 */       HMPlayer hMPlayer = new HMPlayer(player);
/*      */       
/* 1081 */       if (!player.canUseCommand("/regionbypass") && 
/* 1082 */         !this.regionManager.getApplicableRegions(pt).canBuild((LocalPlayer)hMPlayer)) {
/* 1083 */         player.sendMessage("§4You don't have permission for this area.");
/* 1084 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1088 */     if (this.blacklist != null && 
/* 1089 */       !this.blacklist.onBreak(block, player)) {
/* 1090 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1094 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onOpenInventory(Player player, Inventory inventory) {
/* 1113 */     if (this.useRegions && (inventory instanceof Chest || 
/* 1114 */       inventory instanceof DoubleChest || 
/* 1115 */       inventory instanceof Furnace)) {
/* 1116 */       ComplexBlock chest = (ComplexBlock)inventory;
/* 1117 */       Vector pt = new Vector(chest.getX(), chest.getY(), chest.getZ());
/* 1118 */       HMPlayer hMPlayer = new HMPlayer(player);
/*      */       
/* 1120 */       if (!player.canUseCommand("/regionbypass") && 
/* 1121 */         !this.regionManager.getApplicableRegions(pt).canBuild((LocalPlayer)hMPlayer)) {
/* 1122 */         player.sendMessage("§4You don't have permission for this area.");
/* 1123 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1127 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSignChange(Player player, Sign sign) {
/* 1138 */     if (this.blacklist != null) {
/* 1139 */       int id = etc.getServer().getBlockIdAt(sign.getX(), 
/* 1140 */           sign.getY(), sign.getZ());
/* 1141 */       Block block = new Block(id, sign.getX(), 
/* 1142 */           sign.getY(), sign.getZ());
/*      */       
/* 1144 */       if (!this.blacklist.onSilentUse(block, player)) {
/* 1145 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1149 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onIgnite(Block block, Player player) {
/* 1165 */     if (this.preventLavaFire && block.getStatus() == 1) {
/* 1166 */       return true;
/*      */     }
/*      */     
/* 1169 */     if (this.blockLighter && block.getStatus() == 2) {
/* 1170 */       if (!player.canUseCommand("/uselighter") && 
/* 1171 */         !player.canUseCommand("/lighter")) return true; 
/*      */       return false;
/*      */     } 
/* 1174 */     if (this.stopFireSpread && block.getStatus() == 3) {
/* 1175 */       return true;
/*      */     }
/*      */     
/* 1178 */     if (this.disableAllFire && block.getStatus() == 3) {
/* 1179 */       return true;
/*      */     }
/*      */     
/* 1182 */     if (this.fireNoSpreadBlocks != null && block.getStatus() == 3) {
/* 1183 */       int x = block.getX();
/* 1184 */       int y = block.getY();
/* 1185 */       int z = block.getZ();
/* 1186 */       if (this.fireNoSpreadBlocks.contains(Integer.valueOf(etc.getServer().getBlockIdAt(x, y - 1, z))) || 
/* 1187 */         this.fireNoSpreadBlocks.contains(Integer.valueOf(etc.getServer().getBlockIdAt(x + 1, y, z))) || 
/* 1188 */         this.fireNoSpreadBlocks.contains(Integer.valueOf(etc.getServer().getBlockIdAt(x - 1, y, z))) || 
/* 1189 */         this.fireNoSpreadBlocks.contains(Integer.valueOf(etc.getServer().getBlockIdAt(x, y, z - 1))) || 
/* 1190 */         this.fireNoSpreadBlocks.contains(Integer.valueOf(etc.getServer().getBlockIdAt(x, y, z + 1)))) {
/* 1191 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1195 */     if (this.useRegions && !player.canUseCommand("/regionbypass")) {
/* 1196 */       Vector pt = new Vector(block.getX(), 
/* 1197 */           block.getY(), block.getZ());
/* 1198 */       HMPlayer hMPlayer = new HMPlayer(player);
/*      */       
/* 1200 */       if (block.getStatus() == 2 && 
/* 1201 */         !this.regionManager.getApplicableRegions(pt).canBuild((LocalPlayer)hMPlayer)) {
/* 1202 */         return true;
/*      */       }
/*      */       
/* 1205 */       if (block.getStatus() == 2 && 
/* 1206 */         !this.regionManager.getApplicableRegions(pt).allowsLighter()) {
/* 1207 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1211 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onExplode(Block block) {
/* 1226 */     if (this.blockCreepers && block.getStatus() == 2) {
/* 1227 */       return true;
/*      */     }
/*      */     
/* 1230 */     if (this.blockTNT && block.getStatus() == 1) {
/* 1231 */       return true;
/*      */     }
/*      */     
/* 1234 */     Vector pt = new Vector(block.getX(), block.getY(), block.getZ());
/*      */     
/* 1236 */     if (this.useRegions && 
/* 1237 */       block.getStatus() == 1 && 
/* 1238 */       !this.regionManager.getApplicableRegions(pt).allowsTNT()) {
/* 1239 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onFlow(Block blockFrom, Block blockTo) {
/* 1264 */     boolean isWater = !(blockFrom.getType() != 8 && blockFrom.getType() != 9);
/* 1265 */     boolean isLava = !(blockFrom.getType() != 10 && blockFrom.getType() != 11);
/*      */     
/* 1267 */     if (this.simulateSponge && isWater) {
/* 1268 */       int ox = blockTo.getX();
/* 1269 */       int oy = blockTo.getY();
/* 1270 */       int oz = blockTo.getZ();
/*      */       
/* 1272 */       Server server = etc.getServer();
/*      */       
/* 1274 */       for (int cx = -this.spongeRadius; cx <= this.spongeRadius; cx++) {
/* 1275 */         for (int cy = -this.spongeRadius; cy <= this.spongeRadius; cy++) {
/* 1276 */           for (int cz = -this.spongeRadius; cz <= this.spongeRadius; cz++) {
/* 1277 */             if (server.getBlockIdAt(ox + cx, oy + cy, oz + cz) == 19) {
/* 1278 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1285 */     if (this.classicWater && isWater) {
/* 1286 */       int blockBelow = etc.getServer().getBlockIdAt(blockFrom.getX(), blockFrom.getY() - 1, blockFrom.getZ());
/* 1287 */       if (blockBelow != 0 && blockBelow != 8 && blockBelow != 9) {
/* 1288 */         etc.getServer().setBlockAt(9, blockFrom.getX(), blockFrom.getY(), blockFrom.getZ());
/* 1289 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1293 */     if (this.allowedLavaSpreadOver != null && isLava) {
/* 1294 */       int targetId = etc.getServer().getBlockIdAt(
/* 1295 */           blockTo.getX(), blockTo.getY() - 1, blockTo.getZ());
/* 1296 */       if (!this.allowedLavaSpreadOver.contains(Integer.valueOf(targetId))) {
/* 1297 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1301 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockPhysics(Block block, boolean placed) {
/* 1315 */     int id = block.getType();
/*      */     
/* 1317 */     if (id == 13 && this.noPhysicsGravel) {
/* 1318 */       return true;
/*      */     }
/*      */     
/* 1321 */     if (id == 12 && this.noPhysicsSand) {
/* 1322 */       return true;
/*      */     }
/*      */     
/* 1325 */     if (id == 90 && this.allowPortalAnywhere) {
/* 1326 */       return true;
/*      */     }
/*      */     
/* 1329 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onHealthChange(Player player, int oldValue, int newValue) {
/* 1345 */     String playerName = player.getName();
/*      */     
/* 1347 */     if (this.disableSuffocationDamage && oldValue - newValue == 1) {
/* 1348 */       Location loc = player.getLocation();
/* 1349 */       int x = (int)Math.floor(loc.x);
/* 1350 */       int y = (int)Math.floor(loc.y);
/* 1351 */       int z = (int)Math.floor(loc.z);
/* 1352 */       int type = etc.getServer().getBlockIdAt(x, y + 1, z);
/*      */ 
/*      */       
/* 1355 */       if ((type < 8 || type > 11) && !BlockType.canPassThrough(type)) {
/* 1356 */         if (this.teleportOnSuffocation) {
/* 1357 */           findFreePosition(player, x, y, z);
/*      */         }
/* 1359 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1363 */     if (this.invinciblePlayers.contains(playerName)) {
/* 1364 */       return (oldValue > newValue);
/*      */     }
/*      */     
/* 1367 */     if (this.loginProtection > 0 || this.spawnProtection > 0 || 
/* 1368 */       this.kickOnDeath || this.teleportToHome || this.exactRespawn) {
/* 1369 */       long now = System.currentTimeMillis();
/* 1370 */       boolean recentLogin = false;
/*      */       
/* 1372 */       if (this.recentLogins.containsKey(playerName)) {
/* 1373 */         long time = ((Long)this.recentLogins.get(playerName)).longValue();
/* 1374 */         long elapsed = now - time;
/*      */         
/* 1376 */         if (this.loginProtection > 0 && elapsed <= (this.loginProtection * 1000) && 
/* 1377 */           newValue < oldValue) {
/* 1378 */           return true;
/*      */         }
/*      */         
/* 1381 */         recentLogin = (elapsed <= 2000L);
/*      */         
/* 1383 */         if (elapsed > 2000L && elapsed > (this.loginProtection * 1000)) {
/* 1384 */           this.recentLogins.remove(playerName);
/*      */         }
/*      */       } 
/*      */       
/* 1388 */       boolean alreadyMoved = false;
/*      */       
/* 1390 */       if (this.teleportToHome && oldValue <= 0 && newValue == 20 && !recentLogin) {
/* 1391 */         Warp warp = etc.getDataSource().getHome(player.getName());
/* 1392 */         if (warp != null) {
/* 1393 */           player.teleportTo(warp.Location);
/* 1394 */           alreadyMoved = true;
/* 1395 */         } else if (player.canUseCommand("/sethome")) {
/* 1396 */           player.sendMessage("§eUse /sethome to set your spawn location.");
/*      */         } 
/*      */       } 
/*      */       
/* 1400 */       if (this.exactRespawn && !alreadyMoved && oldValue <= 0 && newValue == 20 && !recentLogin) {
/* 1401 */         player.teleportTo(etc.getServer().getSpawnLocation());
/*      */       }
/*      */       
/* 1404 */       if (this.kickOnDeath && oldValue <= 0 && newValue == 20 && !recentLogin) {
/* 1405 */         player.kick("You died! Rejoin please.");
/* 1406 */         return false;
/*      */       } 
/*      */       
/* 1409 */       if (this.spawnProtection > 0) {
/* 1410 */         if (oldValue <= 0 && newValue == 20 && !recentLogin) {
/* 1411 */           this.lastSpawn.put(player.getName(), Long.valueOf(now));
/* 1412 */         } else if (this.lastSpawn.containsKey(playerName)) {
/* 1413 */           long time = ((Long)this.lastSpawn.get(playerName)).longValue();
/* 1414 */           long elapsed = now - time;
/*      */           
/* 1416 */           if (elapsed < (this.spawnProtection * 1000)) {
/* 1417 */             return (newValue < oldValue);
/*      */           }
/* 1419 */           this.lastSpawn.remove(playerName);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1425 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onDamage(PluginLoader.DamageType type, BaseEntity attacker, BaseEntity defender, int amount) {
/* 1448 */     if (defender.isPlayer()) {
/* 1449 */       Player player = defender.getPlayer();
/*      */       
/* 1451 */       if (this.invinciblePlayers.contains(player.getName())) {
/* 1452 */         return true;
/*      */       }
/*      */       
/* 1455 */       if (this.disableFallDamage && type == PluginLoader.DamageType.FALL) {
/* 1456 */         return true;
/*      */       }
/*      */       
/* 1459 */       if (this.disableLavaDamage && type == PluginLoader.DamageType.LAVA) {
/* 1460 */         return true;
/*      */       }
/*      */       
/* 1463 */       if (this.disableFireDamage && (type == PluginLoader.DamageType.FIRE || 
/* 1464 */         type == PluginLoader.DamageType.FIRE_TICK)) {
/* 1465 */         return true;
/*      */       }
/*      */       
/* 1468 */       if (this.disableWaterDamage && type == PluginLoader.DamageType.WATER) {
/* 1469 */         return true;
/*      */       }
/*      */       
/* 1472 */       if (type == PluginLoader.DamageType.WATER && 
/* 1473 */         this.amphibiousPlayers.contains(player.getName())) {
/* 1474 */         return true;
/*      */       }
/*      */       
/* 1477 */       if (attacker != null && attacker.isPlayer() && 
/* 1478 */         this.useRegions) {
/* 1479 */         Vector pt = new Vector(defender.getX(), 
/* 1480 */             defender.getY(), defender.getZ());
/*      */         
/* 1482 */         if (!this.regionManager.getApplicableRegions(pt).allowsPvP()) {
/* 1483 */           player.sendMessage("§4You are in a no-PvP area.");
/* 1484 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1490 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockRightClicked(Player player, Block blockClicked, Item item) {
/* 1503 */     if (this.useRegions && item.getType().getId() == this.regionWand) {
/* 1504 */       Vector pt = new Vector(blockClicked.getX(), 
/* 1505 */           blockClicked.getY(), blockClicked.getZ());
/* 1506 */       ApplicableRegionSet app = this.regionManager.getApplicableRegions(pt);
/* 1507 */       List<String> regions = this.regionManager.getApplicableRegionsIDs(pt);
/*      */       
/* 1509 */       if (regions.size() > 0) {
/* 1510 */         player.sendMessage("§eCan you build? " + (
/* 1511 */             app.canBuild((LocalPlayer)new HMPlayer(player)) ? "Yes" : "No"));
/*      */         
/* 1513 */         StringBuilder str = new StringBuilder();
/* 1514 */         for (Iterator<String> it = regions.iterator(); it.hasNext(); ) {
/* 1515 */           str.append(it.next());
/* 1516 */           if (it.hasNext()) {
/* 1517 */             str.append(", ");
/*      */           }
/*      */         } 
/*      */         
/* 1521 */         player.sendMessage("§eApplicable regions: " + str.toString());
/*      */       } else {
/* 1523 */         player.sendMessage("§eWorldGuard: No defined regions here!");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PluginLoader.HookResult onLiquidDestroy(PluginLoader.HookResult currentState, int liquidBlockId, Block targetBlock) {
/* 1541 */     if (this.preventWaterDamage != null && liquidBlockId <= 9 && 
/* 1542 */       this.preventWaterDamage.contains(Integer.valueOf(targetBlock.getType()))) {
/* 1543 */       return PluginLoader.HookResult.PREVENT_ACTION;
/*      */     }
/*      */ 
/*      */     
/* 1547 */     return PluginLoader.HookResult.DEFAULT_ACTION;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(Player player) {
/* 1557 */     BlacklistEntry.forgetPlayer(player);
/* 1558 */     this.invinciblePlayers.remove(player.getName());
/* 1559 */     this.amphibiousPlayers.remove(player.getName());
/* 1560 */     this.recentLogins.remove(player.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disable() {
/* 1567 */     if (this.blacklist != null) {
/* 1568 */       this.blacklist.getLogger().close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void findFreePosition(Player player, int sx, int sy, int sz) {
/* 1584 */     int x = sx;
/* 1585 */     int y = Math.max(0, sy);
/* 1586 */     int origY = y;
/* 1587 */     int z = sz;
/*      */     
/* 1589 */     byte free = 0;
/*      */     
/* 1591 */     while (y <= 129) {
/* 1592 */       if (BlockType.canPassThrough(etc.getServer().getBlockIdAt(x, y, z))) {
/* 1593 */         free = (byte)(free + 1);
/*      */       } else {
/* 1595 */         free = 0;
/*      */       } 
/*      */       
/* 1598 */       if (free == 2) {
/* 1599 */         if (y - 1 != origY) {
/* 1600 */           player.teleportTo(x + 0.5D, y, z + 0.5D, 
/* 1601 */               player.getRotation(), player.getPitch());
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1607 */       y++;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\WorldGuardListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */