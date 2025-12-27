/*     */ import com.griefcraft.util.Performance;
/*     */ import com.griefcraft.util.StringUtils;
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
/*     */ public class Command_Admin
/*     */   implements Command
/*     */ {
/*     */   public void execute(LWC lwc, Player player, String[] args) {
/*  30 */     if (args.length < 2) {
/*  31 */       sendHelp(player);
/*     */       
/*     */       return;
/*     */     } 
/*  35 */     String action = args[1].toLowerCase();
/*     */     
/*  37 */     if (action.equals("report")) {
/*  38 */       Performance.setChestCount(lwc.getPhysicalDatabase().entityCount());
/*  39 */       Performance.setPlayersOnline(etc.getServer().getPlayerList().size());
/*     */       
/*  41 */       for (String line : Performance.getGeneratedReport()) {
/*  42 */         player.sendMessage("§2" + line);
/*     */       }
/*     */       
/*  45 */       Performance.clear();
/*     */     
/*     */     }
/*  48 */     else if (action.equals("update")) {
/*  49 */       boolean updated = lwc.getUpdater().checkDist();
/*     */       
/*  51 */       if (updated) {
/*  52 */         etc.getLoader().reloadPlugin("LWC");
/*  53 */         player.sendMessage("§2Updated LWC successfully to version: " + lwc.getUpdater().getLatestVersion());
/*     */       } else {
/*  55 */         player.sendMessage("§4No update found.");
/*     */       }
/*     */     
/*     */     }
/*  59 */     else if (action.equalsIgnoreCase("limits")) {
/*  60 */       if (args.length < 3) {
/*  61 */         lwc.sendSimpleUsage(player, "/lwc -admin limits <count> <Group/User>");
/*     */         
/*     */         return;
/*     */       } 
/*  65 */       int limit = Integer.parseInt(args[2]);
/*     */       
/*  67 */       for (int i = 3; i < args.length; i++) {
/*  68 */         String entity = args[i];
/*  69 */         boolean isGroup = entity.startsWith("g:");
/*     */         
/*  71 */         if (isGroup) {
/*  72 */           entity = entity.substring(2);
/*     */         }
/*     */         
/*  75 */         if (limit != -2) {
/*  76 */           lwc.getPhysicalDatabase().registerProtectionLimit(isGroup ? 0 : 1, limit, entity);
/*  77 */           player.sendMessage("§2Registered limit of §6" + limit + "§2" + " chests to the " + (isGroup ? "group" : "user") + " " + "§6" + entity);
/*     */         } else {
/*  79 */           lwc.getPhysicalDatabase().unregisterProtectionLimit(isGroup ? 0 : 1, entity);
/*  80 */           player.sendMessage("§2Unregistered limit for §6" + entity);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*  85 */     } else if (action.equals("convert")) {
/*  86 */       if (args.length < 2) {
/*  87 */         lwc.sendSimpleUsage(player, "/lwc -admin convert chestprotect");
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       String pluginToConvert = args[1].toLowerCase();
/*     */       
/*  93 */       if (pluginToConvert.equals("chestprotect"));
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  98 */     else if (action.equals("clear")) {
/*  99 */       if (args.length < 2) {
/* 100 */         lwc.sendSimpleUsage(player, "/lwc -admin clear chests|limits|rights");
/*     */         
/*     */         return;
/*     */       } 
/* 104 */       String toClear = args[2].toLowerCase();
/*     */       
/* 106 */       if (toClear.equals("protections")) {
/* 107 */         lwc.getPhysicalDatabase().unregisterProtectionEntities();
/*     */         
/* 109 */         player.sendMessage("§2Removed all protected chests and furnaces");
/* 110 */       } else if (toClear.equals("rights")) {
/* 111 */         lwc.getPhysicalDatabase().unregisterProtectionRights();
/*     */         
/* 113 */         player.sendMessage("§2Removed all protection rights");
/* 114 */       } else if (toClear.equals("limits")) {
/* 115 */         lwc.getPhysicalDatabase().unregisterProtectionLimits();
/*     */         
/* 117 */         player.sendMessage("§2Removed all protection limits");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendHelp(Player player) {
/* 123 */     player.sendMessage(" ");
/* 124 */     player.sendMessage("§2LWC Administration");
/* 125 */     player.sendMessage(" ");
/* 126 */     player.sendMessage("/lwc admin report - §6Generate a Performance report");
/* 127 */     player.sendMessage("/lwc admin update - §6Check for an update (if one, update)");
/* 128 */     player.sendMessage("/lwc admin convert §6<chestprotect> - Convert X to LWC");
/* 129 */     player.sendMessage(" ");
/* 130 */     player.sendMessage("/lwc admin clear - §4PERMANENTLY removes data");
/* 131 */     player.sendMessage("/lwc admin clear §6<protections|rights|limits>");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate(LWC lwc, Player player, String[] args) {
/* 136 */     return (lwc.isAdmin(player) && (StringUtils.hasFlag(args, "a") || StringUtils.hasFlag(args, "admin")));
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\Command_Admin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */