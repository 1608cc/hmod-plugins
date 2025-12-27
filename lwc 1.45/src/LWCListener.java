/*     */ import com.griefcraft.model.Action;
/*     */ import com.griefcraft.model.Entity;
/*     */ import com.griefcraft.sql.MemDB;
/*     */ import com.griefcraft.sql.PhysDB;
/*     */ import com.griefcraft.util.ConfigValues;
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
/*     */ public class LWCListener
/*     */   extends PluginListener
/*     */ {
/*     */   private final LWC lwc;
/*     */   private static final int BLAST_RADIUS = 4;
/*     */   public boolean debugMode = false;
/*     */   private PhysDB physicalDatabase;
/*     */   private MemDB memoryDatabase;
/*     */   
/*     */   public LWCListener(LWC paramLWC) {
/*  58 */     this.lwc = paramLWC;
/*  59 */     this.physicalDatabase = paramLWC.getPhysicalDatabase();
/*  60 */     this.memoryDatabase = paramLWC.getMemoryDatabase();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockBreak(Player paramPlayer, Block paramBlock) {
/*  65 */     if (!isProtectable(paramBlock)) {
/*  66 */       return false;
/*     */     }
/*     */     
/*  69 */     List<ComplexBlock> list = this.lwc.getEntitySet(paramBlock.getX(), paramBlock.getY(), paramBlock.getZ());
/*  70 */     boolean bool1 = true;
/*  71 */     boolean bool2 = true;
/*  72 */     Entity entity = null;
/*     */     
/*  74 */     for (ComplexBlock complexBlock : list) {
/*  75 */       if (complexBlock == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  79 */       entity = this.physicalDatabase.loadProtectedEntity(complexBlock.getX(), complexBlock.getY(), complexBlock.getZ());
/*     */       
/*  81 */       if (entity == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  85 */       bool1 = this.lwc.canAccessChest(paramPlayer, entity);
/*  86 */       bool2 = this.lwc.canAdminChest(paramPlayer, entity);
/*     */     } 
/*     */     
/*  89 */     if (bool1 && entity != null && 
/*  90 */       bool2) {
/*  91 */       this.physicalDatabase.unregisterProtectedEntity(entity.getX(), entity.getY(), entity.getZ());
/*  92 */       this.physicalDatabase.unregisterProtectionRights(entity.getID());
/*  93 */       paramPlayer.sendMessage("§4Chest unregistered.");
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return !bool2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockDestroy(Player paramPlayer, Block paramBlock) {
/* 105 */     if (!isProtectable(paramBlock)) {
/* 106 */       return false;
/*     */     }
/*     */     
/* 109 */     List<ComplexBlock> list = this.lwc.getEntitySet(paramBlock.getX(), paramBlock.getY(), paramBlock.getZ());
/* 110 */     boolean bool1 = true;
/* 111 */     Entity entity = null;
/* 112 */     boolean bool = true;
/*     */     
/* 114 */     for (ComplexBlock complexBlock : list) {
/* 115 */       if (complexBlock == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 119 */       entity = this.physicalDatabase.loadProtectedEntity(complexBlock.getX(), complexBlock.getY(), complexBlock.getZ());
/*     */       
/* 121 */       if (entity == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 125 */       bool1 = this.lwc.canAccessChest(paramPlayer, entity);
/* 126 */       bool = false;
/*     */     } 
/*     */ 
/*     */     
/* 130 */     if (paramBlock.getStatus() != 0) {
/* 131 */       return !bool1;
/*     */     }
/*     */     
/* 134 */     List list1 = this.memoryDatabase.getActions(paramPlayer.getName());
/*     */     
/* 136 */     boolean bool2 = list1.contains("free");
/* 137 */     boolean bool3 = list1.contains("info");
/* 138 */     boolean bool4 = list1.contains("create");
/* 139 */     boolean bool5 = list1.contains("modify");
/* 140 */     boolean bool6 = list1.contains("dropTransferSelect");
/*     */     
/* 142 */     if (entity != null) {
/* 143 */       bool = false;
/*     */       
/* 145 */       if (bool3) {
/* 146 */         String str1 = "";
/*     */ 
/*     */ 
/*     */         
/* 150 */         if (entity.getType() == 1) {
/* 151 */           List list2 = this.memoryDatabase.getSessionUsers(entity.getID());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 156 */           for (String str : list2) {
/* 157 */             Player player = etc.getServer().getPlayer(str);
/*     */             
/* 159 */             if (player == null) {
/*     */               continue;
/*     */             }
/*     */             
/* 163 */             str1 = str1 + player.getColor() + str + "§f" + ", ";
/*     */           } 
/*     */           
/* 166 */           if (list2.size() > 0) {
/* 167 */             str1 = str1.substring(0, str1.length() - 4);
/*     */           }
/*     */         } 
/*     */         
/* 171 */         String str2 = " ";
/*     */         
/* 173 */         switch (entity.getType()) {
/*     */           case 0:
/* 175 */             str2 = "Public";
/*     */             break;
/*     */           case 1:
/* 178 */             str2 = "Password";
/*     */             break;
/*     */           case 2:
/* 181 */             str2 = "Private";
/*     */             break;
/*     */         } 
/*     */         
/* 185 */         boolean bool7 = this.lwc.canAdminChest(paramPlayer, entity);
/*     */ 
/*     */ 
/*     */         
/* 189 */         if (bool7) {
/* 190 */           paramPlayer.sendMessage("§2ID: §6" + entity.getID());
/*     */         }
/*     */         
/* 193 */         paramPlayer.sendMessage("§2Type: §6" + str2);
/* 194 */         paramPlayer.sendMessage("§2Owner: §6" + entity.getOwner());
/*     */         
/* 196 */         if (entity.getType() == 1 && bool7) {
/* 197 */           paramPlayer.sendMessage("§2Authed players: " + str1);
/*     */         }
/*     */         
/* 200 */         if (bool7) {
/* 201 */           paramPlayer.sendMessage("§2Location: §6{" + entity.getX() + ", " + entity.getY() + ", " + entity.getZ() + "}");
/* 202 */           paramPlayer.sendMessage("§2Date created: §6" + entity.getDate());
/*     */         } 
/*     */         
/* 205 */         if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 206 */           this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */         }
/* 208 */         return false;
/* 209 */       }  if (bool6) {
/* 210 */         boolean bool7 = this.lwc.canAccessChest(paramPlayer, entity);
/* 211 */         if (!bool7) {
/* 212 */           paramPlayer.sendMessage("§4You cannot use a chest that you cannot access as a drop transfer target.");
/* 213 */           paramPlayer.sendMessage("§4If this is a passworded chest, please unlock it before retrying.");
/* 214 */           paramPlayer.sendMessage("§4Use \"/lwc droptransfer select\" to try again.");
/*     */         } else {
/* 216 */           for (ComplexBlock complexBlock : list) {
/* 217 */             if (!(complexBlock instanceof Chest) && !(complexBlock instanceof DoubleChest)) {
/* 218 */               paramPlayer.sendMessage("§4You need to select a chest as the Drop Transfer target!");
/* 219 */               this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/* 220 */               return false;
/*     */             } 
/*     */           } 
/*     */           
/* 224 */           this.memoryDatabase.registerMode(paramPlayer.getName(), "dropTransfer", "f" + entity.getID());
/* 225 */           paramPlayer.sendMessage("§2Successfully registered chest as drop transfer target.");
/*     */         } 
/* 227 */         this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */         
/* 229 */         return false;
/* 230 */       }  if (bool2) {
/* 231 */         if (this.lwc.isAdmin(paramPlayer) || entity.getOwner().equals(paramPlayer.getName())) {
/* 232 */           paramPlayer.sendMessage("§aRemoved lock on the chest succesfully!");
/* 233 */           this.physicalDatabase.unregisterProtectedEntity(entity.getX(), entity.getY(), entity.getZ());
/* 234 */           this.physicalDatabase.unregisterProtectionRights(entity.getID());
/* 235 */           if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 236 */             this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */           }
/* 238 */           return false;
/*     */         } 
/* 240 */         paramPlayer.sendMessage("§4You do not own that chest!");
/* 241 */         if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 242 */           this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */         }
/* 244 */         return true;
/*     */       } 
/* 246 */       if (bool5) {
/* 247 */         if (this.lwc.canAdminChest(paramPlayer, entity)) {
/* 248 */           Action action = this.memoryDatabase.getAction("modify", paramPlayer.getName());
/*     */           
/* 250 */           String str = action.getData();
/* 251 */           String[] arrayOfString = new String[0];
/*     */           
/* 253 */           if (str.length() > 0) {
/* 254 */             arrayOfString = str.split(" ");
/*     */           }
/*     */           
/* 257 */           if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 258 */             this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */           }
/*     */           
/* 261 */           for (String str1 : arrayOfString) {
/* 262 */             boolean bool7 = false;
/* 263 */             boolean bool8 = false;
/* 264 */             boolean bool9 = true;
/*     */             
/* 266 */             if (str1.startsWith("-")) {
/* 267 */               bool7 = true;
/* 268 */               str1 = str1.substring(1);
/*     */             } 
/*     */             
/* 271 */             if (str1.startsWith("@")) {
/* 272 */               bool8 = true;
/* 273 */               str1 = str1.substring(1);
/*     */             } 
/*     */             
/* 276 */             if (str1.toLowerCase().startsWith("g:")) {
/* 277 */               bool9 = false;
/* 278 */               str1 = str1.substring(2);
/*     */             } 
/*     */             
/* 281 */             int i = this.physicalDatabase.loadProtectedEntity(paramBlock.getX(), paramBlock.getY(), paramBlock.getZ()).getID();
/*     */             
/* 283 */             if (!bool7) {
/* 284 */               this.physicalDatabase.unregisterProtectionRights(i, str1);
/* 285 */               this.physicalDatabase.registerProtectionRights(i, str1, bool8 ? 1 : 0, bool9);
/* 286 */               paramPlayer.sendMessage("§aRegistered rights for §6" + str1 + "§2" + " " + (bool8 ? "[§4ADMIN§6]" : "") + " [" + ((bool9 == true) ? "Player" : "Group") + "]");
/*     */             } else {
/* 288 */               this.physicalDatabase.unregisterProtectionRights(i, str1);
/* 289 */               paramPlayer.sendMessage("§aRemoved rights for §6" + str1 + "§2" + " [" + ((bool9 == true) ? "Player" : "Group") + "]");
/*     */             } 
/*     */           } 
/*     */           
/* 293 */           return false;
/*     */         } 
/* 295 */         paramPlayer.sendMessage("§4You do not own that chest!");
/* 296 */         if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 297 */           this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */         }
/* 299 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 304 */     if (bool6) {
/* 305 */       paramPlayer.sendMessage("§4Cannot select unregistered chest as drop transfer target.");
/* 306 */       paramPlayer.sendMessage("§4Use \"/lwc droptransfer select\" to try again.");
/* 307 */       this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */ 
/*     */       
/* 310 */       return false;
/*     */     } 
/*     */     
/* 313 */     if (bool3 || bool2) {
/* 314 */       paramPlayer.sendMessage("§4Chest is unregistered");
/* 315 */       if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 316 */         this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */       }
/* 318 */       return false;
/*     */     } 
/*     */     
/* 321 */     if ((bool4 || bool5) && !bool) {
/* 322 */       if (!this.lwc.canAdminChest(paramPlayer, entity)) {
/* 323 */         paramPlayer.sendMessage("§4You do not own that chest!");
/*     */       } else {
/* 325 */         paramPlayer.sendMessage("§4You have already registered that chest!");
/*     */       } 
/* 327 */       if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 328 */         this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */       }
/* 330 */       return true;
/*     */     } 
/*     */     
/* 333 */     if (bool && bool4) {
/* 334 */       Action action = this.memoryDatabase.getAction("create", paramPlayer.getName());
/*     */       
/* 336 */       String str1 = action.getData();
/* 337 */       String[] arrayOfString = str1.split(" ");
/* 338 */       String str2 = arrayOfString[0].toLowerCase();
/* 339 */       String str3 = "";
/*     */       
/* 341 */       if (arrayOfString.length > 1) {
/* 342 */         for (byte b = 1; b < arrayOfString.length; b++) {
/* 343 */           str3 = str3 + arrayOfString[b] + " ";
/*     */         }
/*     */       }
/*     */       
/* 347 */       if (this.lwc.enforceChestLimits(paramPlayer)) {
/* 348 */         if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 349 */           this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */         }
/* 351 */         return false;
/*     */       } 
/*     */       
/* 354 */       if (!this.lwc.isAdmin(paramPlayer) && this.lwc.isInCuboidSafeZone(paramPlayer)) {
/* 355 */         paramPlayer.sendMessage("§4You need to be in a Cuboid-protected safe zone to do that!");
/* 356 */         this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/* 357 */         return false;
/*     */       } 
/*     */       
/* 360 */       if (str2.equals("public")) {
/* 361 */         this.physicalDatabase.registerProtectedEntity(0, paramPlayer.getName(), "", paramBlock.getX(), paramBlock.getY(), paramBlock.getZ());
/* 362 */         paramPlayer.sendMessage("§2Created public protection successfully");
/* 363 */       } else if (str2.equals("password")) {
/* 364 */         String str = action.getData().substring("password ".length());
/* 365 */         str = this.lwc.encrypt(str);
/*     */         
/* 367 */         this.physicalDatabase.registerProtectedEntity(1, paramPlayer.getName(), str, paramBlock.getX(), paramBlock.getY(), paramBlock.getZ());
/* 368 */         this.memoryDatabase.registerPlayer(paramPlayer.getName(), this.physicalDatabase.loadProtectedEntity(paramBlock.getX(), paramBlock.getY(), paramBlock.getZ()).getID());
/* 369 */         paramPlayer.sendMessage("§2Created password protection successfully");
/* 370 */         paramPlayer.sendMessage("§aFor convenience, you don't have to enter your password until");
/* 371 */         paramPlayer.sendMessage("§ayou next log in");
/*     */         
/* 373 */         for (ComplexBlock complexBlock : list) {
/* 374 */           if (complexBlock != null) {
/* 375 */             complexBlock.update();
/*     */           }
/*     */         }
/*     */       
/* 379 */       } else if (str2.equals("private")) {
/* 380 */         String str = action.getData();
/* 381 */         String[] arrayOfString1 = new String[0];
/*     */         
/* 383 */         if (str.length() > "private ".length()) {
/* 384 */           str = str.substring("private ".length());
/* 385 */           arrayOfString1 = str.split(" ");
/*     */         } 
/*     */         
/* 388 */         this.physicalDatabase.registerProtectedEntity(2, paramPlayer.getName(), "", paramBlock.getX(), paramBlock.getY(), paramBlock.getZ());
/*     */         
/* 390 */         paramPlayer.sendMessage("§2Created private protection successfully");
/*     */         
/* 392 */         for (String str4 : arrayOfString1) {
/* 393 */           boolean bool7 = false;
/* 394 */           boolean bool8 = true;
/*     */           
/* 396 */           if (str4.startsWith("@")) {
/* 397 */             bool7 = true;
/* 398 */             str4 = str4.substring(1);
/*     */           } 
/*     */           
/* 401 */           if (str4.toLowerCase().startsWith("g:")) {
/* 402 */             bool8 = false;
/* 403 */             str4 = str4.substring(2);
/*     */           } 
/*     */           
/* 406 */           this.physicalDatabase.registerProtectionRights(this.physicalDatabase.loadProtectedEntity(paramBlock.getX(), paramBlock.getY(), paramBlock.getZ()).getID(), str4, bool7 ? 1 : 0, bool8);
/* 407 */           paramPlayer.sendMessage("§aRegistered rights for §6" + str4 + ": " + (bool7 ? "[§4ADMIN§6]" : "") + " [" + ((bool8 == true) ? "Player" : "Group") + "]");
/*     */         } 
/*     */       } 
/*     */       
/* 411 */       if (this.lwc.notInPersistentMode(paramPlayer.getName())) {
/* 412 */         this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */       }
/*     */     } 
/*     */     
/* 416 */     return !bool1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(Player paramPlayer, String[] paramArrayOfString) {
/* 421 */     String str1 = paramArrayOfString[0].substring(1);
/* 422 */     String str2 = "";
/* 423 */     String[] arrayOfString = (paramArrayOfString.length > 1) ? new String[paramArrayOfString.length - 1] : new String[0];
/*     */ 
/*     */     
/* 426 */     if (paramArrayOfString.length > 1) {
/* 427 */       for (byte b = 1; b < paramArrayOfString.length; b++) {
/* 428 */         paramArrayOfString[b] = paramArrayOfString[b].trim();
/*     */         
/* 430 */         if (!paramArrayOfString[b].isEmpty()) {
/*     */ 
/*     */ 
/*     */           
/* 434 */           arrayOfString[b - 1] = paramArrayOfString[b];
/* 435 */           str2 = str2 + paramArrayOfString[b] + " ";
/*     */         } 
/*     */       } 
/*     */     }
/* 439 */     if (str1.equals("cpublic")) {
/* 440 */       return onCommand(paramPlayer, "/lwc -c public".split(" "));
/*     */     }
/* 442 */     if (str1.equals("cpassword")) {
/* 443 */       return onCommand(paramPlayer, ("/lwc -c password " + str2).split(" "));
/*     */     }
/* 445 */     if (str1.equals("cprivate")) {
/* 446 */       return onCommand(paramPlayer, "/lwc -c private".split(" "));
/*     */     }
/* 448 */     if (str1.equals("cinfo")) {
/* 449 */       return onCommand(paramPlayer, "/lwc -i".split(" "));
/*     */     }
/* 451 */     if (str1.equals("cunlock")) {
/* 452 */       return onCommand(paramPlayer, "/lwc -u".split(" "));
/*     */     }
/*     */     
/* 455 */     if (!paramPlayer.canUseCommand(paramArrayOfString[0])) {
/* 456 */       return false;
/*     */     }
/*     */     
/* 459 */     if (!"lwc".equalsIgnoreCase(str1)) {
/* 460 */       return false;
/*     */     }
/*     */     
/* 463 */     if (arrayOfString.length == 0) {
/* 464 */       this.lwc.sendFullHelp(paramPlayer);
/* 465 */       return true;
/*     */     } 
/*     */     
/* 468 */     for (Command command : this.lwc.getCommands()) {
/* 469 */       if (!command.validate(this.lwc, paramPlayer, arrayOfString)) {
/*     */         continue;
/*     */       }
/*     */       
/* 473 */       command.execute(this.lwc, paramPlayer, arrayOfString);
/* 474 */       return true;
/*     */     } 
/*     */     
/* 477 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(Player paramPlayer) {
/* 482 */     this.memoryDatabase.unregisterPlayer(paramPlayer.getName());
/* 483 */     this.memoryDatabase.unregisterUnlock(paramPlayer.getName());
/* 484 */     this.memoryDatabase.unregisterChest(paramPlayer.getName());
/* 485 */     this.memoryDatabase.unregisterAllActions(paramPlayer.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onExplode(Block paramBlock) {
/* 492 */     boolean bool = (this.physicalDatabase.loadProtectedEntities(paramBlock.getX(), paramBlock.getY(), paramBlock.getZ(), 4).size() > 0) ? true : false;
/*     */     
/* 494 */     if (bool)
/*     */     {
/*     */ 
/*     */       
/* 498 */       return true;
/*     */     }
/*     */     
/* 501 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemDrop(Player paramPlayer, Item paramItem) {
/* 509 */     String str = paramPlayer.getName();
/* 510 */     int i = this.lwc.getPlayerDropTransferTarget(str);
/*     */     
/* 512 */     if (i == -1 || !this.lwc.isPlayerDropTransferring(str)) {
/* 513 */       return false;
/*     */     }
/*     */     
/* 516 */     if (!this.physicalDatabase.doesChestExist(i)) {
/* 517 */       paramPlayer.sendMessage("§4Your drop transfer target was unregistered and/or destroyed.");
/* 518 */       paramPlayer.sendMessage("§4Please re-register a target chest. Drop transfer will be deactivated.");
/*     */       
/* 520 */       this.memoryDatabase.unregisterMode(str, "dropTransfer");
/* 521 */       return false;
/*     */     } 
/*     */     
/* 524 */     Entity entity = this.physicalDatabase.loadProtectedEntity(i);
/*     */     
/* 526 */     if (entity == null) {
/* 527 */       paramPlayer.sendMessage("§4An unknown error occured. Drop transfer will be deactivated.");
/*     */       
/* 529 */       this.memoryDatabase.unregisterMode(str, "dropTransfer");
/* 530 */       return false;
/*     */     } 
/*     */     
/* 533 */     if (!this.lwc.canAccessChest(paramPlayer, entity)) {
/* 534 */       paramPlayer.sendMessage("§4You have lost access to your target chest.");
/* 535 */       paramPlayer.sendMessage("§4Please re-register a target chest. Drop transfer will be deactivated.");
/*     */       
/* 537 */       this.memoryDatabase.unregisterMode(str, "dropTransfer");
/* 538 */       return false;
/*     */     } 
/*     */     
/* 541 */     List<ComplexBlock> list = this.lwc.getEntitySet(entity.getX(), entity.getY(), entity.getZ());
/* 542 */     int j = paramItem.getAmount();
/*     */     
/* 544 */     for (ComplexBlock complexBlock : list) {
/* 545 */       Inventory inventory = (Inventory)complexBlock;
/*     */ 
/*     */       
/* 548 */       for (Item item1 : inventory.getContents()) {
/* 549 */         System.out.println("" + item1.getItemId() + ":" + item1.getAmount() + " " + item1.getSlot());
/*     */       }
/*     */       Item item;
/* 552 */       while (((item = inventory.getItemFromId(paramItem.getItemId(), 63)) != null || inventory.getEmptySlot() != -1) && j > 0) {
/* 553 */         if (item != null) {
/* 554 */           int k = Math.min(64 - item.getAmount(), paramItem.getAmount());
/* 555 */           inventory.setSlot(paramItem.getItemId(), item.getAmount() + k, item.getSlot());
/* 556 */           j -= k; continue;
/*     */         } 
/* 558 */         inventory.addItem(new Item(paramItem.getItemId(), j));
/* 559 */         j = 0;
/*     */       } 
/*     */ 
/*     */       
/* 563 */       complexBlock.update();
/*     */       
/* 565 */       if (j == 0) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 570 */     if (j > 0) {
/* 571 */       paramPlayer.sendMessage("§4Your chest is full. Drop transfer will be deactivated.");
/* 572 */       paramPlayer.sendMessage("§4Any remaining quantity that could not be stored will be returned.");
/* 573 */       this.memoryDatabase.unregisterMode(str, "dropTransfer");
/* 574 */       this.memoryDatabase.registerMode(str, "dropTransfer", "f" + i);
/* 575 */       paramPlayer.getInventory().addItem(paramItem);
/* 576 */       paramPlayer.getInventory().update();
/*     */     } 
/*     */     
/* 579 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onOpenInventory(Player paramPlayer, Inventory paramInventory) {
/* 584 */     if (this.lwc.isAdmin(paramPlayer) && !this.debugMode) {
/* 585 */       return false;
/*     */     }
/*     */     
/* 588 */     if (paramInventory instanceof Workbench) {
/* 589 */       return false;
/*     */     }
/*     */     
/* 592 */     if (paramInventory instanceof Dispenser) {
/* 593 */       return false;
/*     */     }
/*     */     
/* 596 */     ComplexBlock complexBlock = (ComplexBlock)paramInventory;
/*     */     
/* 598 */     if (!isProtectable(complexBlock.getBlock())) {
/* 599 */       return false;
/*     */     }
/*     */     
/* 602 */     List<ComplexBlock> list = this.lwc.getEntitySet(complexBlock.getX(), complexBlock.getY(), complexBlock.getZ());
/* 603 */     boolean bool = true;
/*     */     
/* 605 */     for (ComplexBlock complexBlock1 : list) {
/* 606 */       if (complexBlock1 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 610 */       Entity entity = this.physicalDatabase.loadProtectedEntity(complexBlock1.getX(), complexBlock1.getY(), complexBlock1.getZ());
/*     */       
/* 612 */       if (entity == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 616 */       bool = this.lwc.canAccessChest(paramPlayer, entity);
/*     */       
/* 618 */       switch (entity.getType()) {
/*     */         case 1:
/* 620 */           if (!bool) {
/* 621 */             this.memoryDatabase.unregisterUnlock(paramPlayer.getName());
/* 622 */             this.memoryDatabase.registerUnlock(paramPlayer.getName(), entity.getID());
/*     */             
/* 624 */             paramPlayer.sendMessage("§4This chest is locked.");
/* 625 */             paramPlayer.sendMessage("§4Type §6/lwc -u <password>§4 to unlock it");
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 631 */           if (!bool) {
/* 632 */             paramPlayer.sendMessage("§4This chest is locked with a magical spell.");
/*     */           }
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 639 */     return !bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isProtectable(Block paramBlock) {
/* 649 */     switch (paramBlock.getType()) {
/*     */       
/*     */       case 54:
/* 652 */         return true;
/*     */       
/*     */       case 61:
/*     */       case 62:
/* 656 */         if (ConfigValues.ALLOW_FURNACE_PROTECTION.getBool()) {
/* 657 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 664 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\LWCListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */