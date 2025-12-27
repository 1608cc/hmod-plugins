/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.Manifest;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGuard
/*     */   extends Plugin
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldGuardListener listener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGuard() {
/*     */     try {
/*  49 */       this.listener = new WorldGuardListener(this);
/*  50 */     } catch (NoClassDefFoundError e) {
/*  51 */       logger.severe("*** WORLDGUARD FAILED TO LOAD. ALL PROTECTION IS DISABLED!");
/*  52 */       logger.severe("*** YOUR SERVER WILL BE SAVED AND STOPPED TO PREVENT DAMAGE TO YOUR WORLD. DISABLE WORLDGUARD OR CORRECT THE PROBLEM.");
/*  53 */       logger.severe("*** WorldEdit must be placed into the plugins/ directory");
/*  54 */       etc.getServer().useConsoleCommand("stop");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  63 */     if (this.listener == null) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     List<String> missingFeatures = new ArrayList<String>();
/*     */     
/*  69 */     registerHook("COMMAND", PluginListener.Priority.MEDIUM);
/*  70 */     registerHook("SERVERCOMMAND", PluginListener.Priority.MEDIUM);
/*  71 */     registerHook("EXPLODE", PluginListener.Priority.HIGH);
/*  72 */     registerHook("IGNITE", PluginListener.Priority.HIGH);
/*  73 */     registerHook("FLOW", PluginListener.Priority.HIGH);
/*  74 */     registerHook("LOGINCHECK", PluginListener.Priority.HIGH);
/*  75 */     registerHook("LOGIN", PluginListener.Priority.MEDIUM);
/*  76 */     registerHook("BLOCK_CREATED", PluginListener.Priority.HIGH);
/*  77 */     registerHook("BLOCK_DESTROYED", PluginListener.Priority.CRITICAL);
/*  78 */     registerHook("BLOCK_BROKEN", PluginListener.Priority.HIGH);
/*  79 */     registerHook("BLOCK_PLACE", PluginListener.Priority.HIGH);
/*  80 */     registerHook("DISCONNECT", PluginListener.Priority.HIGH);
/*  81 */     registerHook("ITEM_DROP", PluginListener.Priority.HIGH);
/*  82 */     registerHook("ITEM_USE", PluginListener.Priority.HIGH);
/*  83 */     registerHook("ITEM_PICK_UP", PluginListener.Priority.HIGH);
/*  84 */     registerHook("SIGN_CHANGE", PluginListener.Priority.HIGH);
/*  85 */     registerHook("OPEN_INVENTORY", PluginListener.Priority.HIGH);
/*  86 */     registerHook("BLOCK_PHYSICS", PluginListener.Priority.MEDIUM);
/*  87 */     registerHook("HEALTH_CHANGE", PluginListener.Priority.MEDIUM);
/*  88 */     registerHook("DAMAGE", PluginListener.Priority.MEDIUM);
/*  89 */     registerHook("LIQUID_DESTROY", PluginListener.Priority.MEDIUM);
/*  90 */     registerHook("BLOCK_RIGHTCLICKED", PluginListener.Priority.MEDIUM);
/*     */     
/*  92 */     if (missingFeatures.size() > 0) {
/*  93 */       logger.log(Level.WARNING, "WorldGuard: Your version of hMod does not support " + concatMissingFeatures(missingFeatures) + ".");
/*     */     } else {
/*     */       
/*  96 */       logger.log(Level.INFO, "WorldGuard: Your version of hMod appears to support all features.");
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
/*     */   public boolean registerHook(String name, PluginListener.Priority priority) {
/*     */     try {
/* 110 */       PluginLoader.Hook hook = PluginLoader.Hook.valueOf(name);
/* 111 */       etc.getLoader().addListener(hook, this.listener, this, priority);
/* 112 */       return true;
/* 113 */     } catch (IllegalArgumentException e) {
/* 114 */       logger.log(Level.WARNING, "WorldGuard: Missing hook " + name + "!");
/* 115 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() {
/* 124 */     if (this.listener == null) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     logger.log(Level.INFO, "WorldGuard version " + getVersion() + " loaded");
/* 129 */     this.listener.loadConfiguration();
/*     */     
/* 131 */     etc.getInstance().addCommand("/stopfire", "Globally stop fire spread");
/* 132 */     etc.getInstance().addCommand("/allowfire", "Globally re-enable fire spread");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/*     */     try {
/* 141 */       this.listener.disable();
/* 142 */       BlacklistEntry.forgetAllPlayers();
/* 143 */     } catch (Throwable t) {}
/*     */ 
/*     */     
/* 146 */     etc.getInstance().removeCommand("/stopfire");
/* 147 */     etc.getInstance().removeCommand("/allowfire");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getVersion() {
/*     */     try {
/* 157 */       String classContainer = WorldGuard.class.getProtectionDomain().getCodeSource().getLocation().toString();
/*     */       
/* 159 */       URL manifestUrl = new URL("jar:" + classContainer + "!/META-INF/MANIFEST.MF");
/* 160 */       Manifest manifest = new Manifest(manifestUrl.openStream());
/* 161 */       Attributes attrib = manifest.getMainAttributes();
/* 162 */       String ver = attrib.getValue("WorldGuard-Version");
/* 163 */       return (ver != null) ? ver : "(unavailable)";
/* 164 */     } catch (IOException e) {
/* 165 */       return "(unknown)";
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
/*     */   private static String concatMissingFeatures(List<String> str) {
/* 177 */     if (str.isEmpty()) {
/* 178 */       return "";
/*     */     }
/*     */     
/* 181 */     int size = str.size();
/* 182 */     StringBuilder buffer = new StringBuilder();
/* 183 */     buffer.append("(1) ");
/* 184 */     buffer.append(str.get(0));
/* 185 */     for (int i = 1; i < size; i++) {
/* 186 */       if (i == size - 1) {
/* 187 */         buffer.append(" or ");
/* 188 */         buffer.append("(");
/* 189 */         buffer.append(i + 1);
/* 190 */         buffer.append(") ");
/* 191 */         buffer.append(str.get(i));
/*     */       } else {
/* 193 */         buffer.append(", ");
/* 194 */         buffer.append("(");
/* 195 */         buffer.append(i + 1);
/* 196 */         buffer.append(") ");
/* 197 */         buffer.append(str.get(i));
/*     */       } 
/*     */     } 
/* 200 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\WorldGuard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */