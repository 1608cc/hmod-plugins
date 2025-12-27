/*     */ package classes;
/*     */ 
/*     */ import BlacklistEntry;
/*     */ import BlacklistLogger;
/*     */ import Block;
/*     */ import Player;
/*     */ import etc;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class Blacklist
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private Map<Integer, List<BlacklistEntry>> blacklist = new HashMap<Integer, List<BlacklistEntry>>();
/*     */ 
/*     */ 
/*     */   
/*  46 */   private BlacklistLogger blacklistLogger = new BlacklistLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasOnAcquire;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  59 */     return this.blacklist.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BlacklistEntry> getEntries(int id) {
/*  66 */     return this.blacklist.get(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlacklistLogger getLogger() {
/*  75 */     return this.blacklistLogger;
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
/*  87 */     List<BlacklistEntry> entries = getEntries(block.getType());
/*  88 */     if (entries == null) {
/*  89 */       return true;
/*     */     }
/*  91 */     boolean ret = true;
/*  92 */     for (BlacklistEntry entry : entries) {
/*  93 */       if (!entry.onDestroy(block, player)) {
/*  94 */         ret = false;
/*     */       }
/*     */     } 
/*  97 */     return ret;
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
/* 109 */     List<BlacklistEntry> entries = getEntries(block.getType());
/* 110 */     if (entries == null) {
/* 111 */       return true;
/*     */     }
/* 113 */     boolean ret = true;
/* 114 */     for (BlacklistEntry entry : entries) {
/* 115 */       if (!entry.onBreak(block, player)) {
/* 116 */         ret = false;
/*     */       }
/*     */     } 
/* 119 */     return ret;
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
/* 130 */     List<BlacklistEntry> entries = getEntries(item);
/* 131 */     if (entries == null) {
/* 132 */       return true;
/*     */     }
/* 134 */     boolean ret = true;
/* 135 */     for (BlacklistEntry entry : entries) {
/* 136 */       if (!entry.onDestroyWith(item, player)) {
/* 137 */         ret = false;
/*     */       }
/*     */     } 
/* 140 */     return ret;
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
/* 151 */     List<BlacklistEntry> entries = getEntries(item);
/* 152 */     if (entries == null) {
/* 153 */       return true;
/*     */     }
/* 155 */     boolean ret = true;
/* 156 */     for (BlacklistEntry entry : entries) {
/* 157 */       if (!entry.onPlace(item, player)) {
/* 158 */         ret = false;
/*     */       }
/*     */     } 
/* 161 */     return ret;
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
/* 172 */     List<BlacklistEntry> entries = getEntries(block.getType());
/* 173 */     if (entries == null) {
/* 174 */       return true;
/*     */     }
/* 176 */     boolean ret = true;
/* 177 */     for (BlacklistEntry entry : entries) {
/* 178 */       if (!entry.onRightClick(block, player)) {
/* 179 */         ret = false;
/*     */       }
/*     */     } 
/* 182 */     return ret;
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
/* 193 */     List<BlacklistEntry> entries = getEntries(item);
/* 194 */     if (entries == null) {
/* 195 */       return true;
/*     */     }
/* 197 */     boolean ret = true;
/* 198 */     for (BlacklistEntry entry : entries) {
/* 199 */       if (!entry.onUse(item, player)) {
/* 200 */         ret = false;
/*     */       }
/*     */     } 
/* 203 */     return ret;
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
/* 214 */     List<BlacklistEntry> entries = getEntries(block.getType());
/* 215 */     if (entries == null) {
/* 216 */       return true;
/*     */     }
/* 218 */     boolean ret = true;
/* 219 */     for (BlacklistEntry entry : entries) {
/* 220 */       if (!entry.onSilentUse(block, player)) {
/* 221 */         ret = false;
/*     */       }
/*     */     } 
/* 224 */     return ret;
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
/* 235 */     List<BlacklistEntry> entries = getEntries(item);
/* 236 */     if (entries == null) {
/* 237 */       return true;
/*     */     }
/* 239 */     boolean ret = true;
/* 240 */     for (BlacklistEntry entry : entries) {
/* 241 */       if (!entry.onDrop(item, player)) {
/* 242 */         ret = false;
/*     */       }
/*     */     } 
/* 245 */     return ret;
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
/* 256 */     List<BlacklistEntry> entries = getEntries(item);
/* 257 */     if (entries == null) {
/* 258 */       return true;
/*     */     }
/* 260 */     boolean ret = true;
/* 261 */     for (BlacklistEntry entry : entries) {
/* 262 */       if (!entry.onAcquire(item, player)) {
/* 263 */         ret = false;
/*     */       }
/*     */     } 
/* 266 */     return ret;
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
/* 277 */     List<BlacklistEntry> entries = getEntries(item);
/* 278 */     if (entries == null) {
/* 279 */       return true;
/*     */     }
/* 281 */     boolean ret = true;
/* 282 */     for (BlacklistEntry entry : entries) {
/* 283 */       if (!entry.onSilentAcquire(item, player)) {
/* 284 */         ret = false;
/*     */       }
/*     */     } 
/* 287 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(File file) throws IOException {
/* 298 */     FileReader input = null;
/* 299 */     Map<Integer, List<BlacklistEntry>> blacklist = 
/* 300 */       new HashMap<Integer, List<BlacklistEntry>>();
/*     */     
/*     */     try {
/* 303 */       input = new FileReader(file);
/* 304 */       BufferedReader buff = new BufferedReader(input);
/*     */       
/* 306 */       this.hasOnAcquire = false;
/*     */ 
/*     */       
/* 309 */       List<BlacklistEntry> currentEntries = null; String line;
/* 310 */       while ((line = buff.readLine()) != null) {
/* 311 */         line = line.trim();
/*     */ 
/*     */         
/* 314 */         if (line.length() == 0)
/*     */           continue; 
/* 316 */         if (line.charAt(0) == ';' || line.charAt(0) == '#') {
/*     */           continue;
/*     */         }
/*     */         
/* 320 */         if (line.matches("^\\[.*\\]$")) {
/* 321 */           String[] items = line.substring(1, line.length() - 1).split(",");
/* 322 */           currentEntries = new ArrayList<BlacklistEntry>(); byte b; int i;
/*     */           String[] arrayOfString1;
/* 324 */           for (i = (arrayOfString1 = items).length, b = 0; b < i; ) { String item = arrayOfString1[b];
/* 325 */             int id = 0;
/*     */             
/*     */             try {
/* 328 */               id = Integer.parseInt(item.trim());
/* 329 */             } catch (NumberFormatException e) {
/* 330 */               id = etc.getDataSource().getItem(item.trim());
/* 331 */               if (id == 0) {
/* 332 */                 logger.log(Level.WARNING, "WorldGuard: Unknown block name: " + 
/* 333 */                     item);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 338 */             BlacklistEntry entry = new BlacklistEntry(this);
/* 339 */             if (blacklist.containsKey(Integer.valueOf(id))) {
/* 340 */               ((List<BlacklistEntry>)blacklist.get(Integer.valueOf(id))).add(entry);
/*     */             } else {
/* 342 */               List<BlacklistEntry> entries = new ArrayList<BlacklistEntry>();
/* 343 */               entries.add(entry);
/* 344 */               blacklist.put(Integer.valueOf(id), entries);
/*     */             } 
/* 346 */             currentEntries.add(entry); b++; }
/*     */            continue;
/* 348 */         }  if (currentEntries != null) {
/* 349 */           String[] parts = line.split("=");
/*     */           
/* 351 */           if (parts.length == 1) {
/* 352 */             logger.log(Level.WARNING, "Found option with no value " + 
/* 353 */                 file.getName() + " for '" + line + "'");
/*     */             
/*     */             continue;
/*     */           } 
/* 357 */           boolean unknownOption = false;
/*     */           
/* 359 */           for (BlacklistEntry entry : currentEntries) {
/* 360 */             if (parts[0].equalsIgnoreCase("ignore-groups")) {
/* 361 */               entry.setIgnoreGroups(parts[1].split(",")); continue;
/* 362 */             }  if (parts[0].equalsIgnoreCase("on-destroy")) {
/* 363 */               entry.setDestroyActions(parts[1].split(",")); continue;
/* 364 */             }  if (parts[0].equalsIgnoreCase("on-break")) {
/* 365 */               entry.setBreakActions(parts[1].split(",")); continue;
/* 366 */             }  if (parts[0].equalsIgnoreCase("on-left") || 
/* 367 */               parts[0].equalsIgnoreCase("on-destroy-with")) {
/* 368 */               entry.setDestroyWithActions(parts[1].split(",")); continue;
/* 369 */             }  if (parts[0].equalsIgnoreCase("on-place")) {
/* 370 */               entry.setPlaceActions(parts[1].split(",")); continue;
/* 371 */             }  if (parts[0].equalsIgnoreCase("on-use")) {
/* 372 */               entry.setUseActions(parts[1].split(",")); continue;
/* 373 */             }  if (parts[0].equalsIgnoreCase("on-right-click")) {
/* 374 */               entry.setRightClickActions(parts[1].split(",")); continue;
/* 375 */             }  if (parts[0].equalsIgnoreCase("on-drop")) {
/* 376 */               entry.setDropActions(parts[1].split(",")); continue;
/* 377 */             }  if (parts[0].equalsIgnoreCase("on-acquire")) {
/* 378 */               this.hasOnAcquire = true;
/* 379 */               entry.setAcquireActions(parts[1].split(",")); continue;
/* 380 */             }  if (parts[0].equalsIgnoreCase("message")) {
/* 381 */               entry.setMessage(parts[1].trim()); continue;
/* 382 */             }  if (parts[0].equalsIgnoreCase("comment")) {
/* 383 */               entry.setComment(parts[1].trim()); continue;
/*     */             } 
/* 385 */             unknownOption = true;
/*     */           } 
/*     */ 
/*     */           
/* 389 */           if (unknownOption)
/* 390 */             logger.log(Level.WARNING, "Unknown option '" + parts[0] + 
/* 391 */                 "' in " + file.getName() + " for '" + line + "'"); 
/*     */           continue;
/*     */         } 
/* 394 */         logger.log(Level.WARNING, "Found option with no heading " + 
/* 395 */             file.getName() + " for '" + line + "'");
/*     */       } 
/*     */ 
/*     */       
/* 399 */       this.blacklist = blacklist;
/*     */     } finally {
/*     */       try {
/* 402 */         if (input != null) {
/* 403 */           input.close();
/*     */         }
/* 405 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOnAcquire() {
/* 416 */     return this.hasOnAcquire;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\Blacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */