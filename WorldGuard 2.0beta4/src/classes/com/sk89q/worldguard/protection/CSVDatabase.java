/*     */ package classes.com.sk89q.worldguard.protection;
/*     */ 
/*     */ import au.com.bytecode.opencsv.CSVReader;
/*     */ import au.com.bytecode.opencsv.CSVWriter;
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.AreaFlags;
/*     */ import com.sk89q.worldguard.protection.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.ProtectionDatabase;
/*     */ import com.sk89q.worldguard.protection.RegionManager;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class CSVDatabase
/*     */   implements ProtectionDatabase
/*     */ {
/*  41 */   private static Logger logger = Logger.getLogger("Minecraft.WorldGuard");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File file;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, ProtectedRegion> regions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CSVDatabase(File file) {
/*  59 */     this.file = file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() throws IOException {
/*  67 */     CSVWriter writer = new CSVWriter(new FileWriter(this.file));
/*     */     
/*     */     try {
/*  70 */       for (Map.Entry<String, ProtectedRegion> entry : this.regions.entrySet()) {
/*  71 */         String id = entry.getKey();
/*  72 */         ProtectedRegion region = entry.getValue();
/*     */         
/*  74 */         if (!(region instanceof ProtectedCuboidRegion)) {
/*  75 */           logger.warning("The CSV database only supports cuboid regions.");
/*     */           
/*     */           continue;
/*     */         } 
/*  79 */         ProtectedCuboidRegion cuboid = (ProtectedCuboidRegion)region;
/*  80 */         BlockVector min = cuboid.getMinimumPoint();
/*  81 */         BlockVector max = cuboid.getMaximumPoint();
/*     */         
/*  83 */         writer.writeNext(new String[] { 
/*  84 */               id, 
/*  85 */               "cuboid", 
/*  86 */               String.valueOf(min.getBlockX()), 
/*  87 */               String.valueOf(min.getBlockY()), 
/*  88 */               String.valueOf(min.getBlockZ()), 
/*  89 */               String.valueOf(max.getBlockX()), 
/*  90 */               String.valueOf(max.getBlockY()), 
/*  91 */               String.valueOf(max.getBlockZ()), 
/*  92 */               String.valueOf(cuboid.getPriority()), 
/*  93 */               writeDomains(cuboid.getOwners()), 
/*  94 */               writeFlags(cuboid.getFlags()), 
/*  95 */               cuboid.getEnterMessage() });
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 100 */         writer.close();
/* 101 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/* 111 */     Map<String, ProtectedRegion> regions = 
/* 112 */       new HashMap<String, ProtectedRegion>();
/*     */     
/* 114 */     CSVReader reader = new CSVReader(new FileReader(this.file));
/*     */     
/*     */     try {
/*     */       String[] line;
/*     */       
/* 119 */       while ((line = reader.readNext()) != null) {
/* 120 */         if (line.length >= 12) {
/* 121 */           String type = line[1];
/* 122 */           if (!type.equalsIgnoreCase("cuboid")) {
/* 123 */             logger.warning("Only cuboid region types are supported: " + 
/* 124 */                 line);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 129 */           String id = line[0];
/* 130 */           BlockVector min = new BlockVector(
/* 131 */               Integer.parseInt(line[2]), 
/* 132 */               Integer.parseInt(line[3]), 
/* 133 */               Integer.parseInt(line[4]));
/* 134 */           BlockVector max = new BlockVector(
/* 135 */               Integer.parseInt(line[5]), 
/* 136 */               Integer.parseInt(line[6]), 
/* 137 */               Integer.parseInt(line[7]));
/* 138 */           int priority = Integer.parseInt(line[8]);
/* 139 */           String ownersData = line[9];
/* 140 */           String flagsData = line[10];
/* 141 */           String enterMessage = line[11];
/*     */           
/* 143 */           ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(min, max);
/* 144 */           protectedCuboidRegion.setPriority(priority);
/* 145 */           protectedCuboidRegion.setOwners(parseDomains(ownersData));
/* 146 */           protectedCuboidRegion.setEnterMessage(enterMessage);
/* 147 */           protectedCuboidRegion.setFlags(parseFlags(flagsData));
/* 148 */           regions.put(id, protectedCuboidRegion); continue;
/*     */         } 
/* 150 */         logger.warning("Line has invalid: " + line);
/*     */       } 
/*     */     } finally {
/*     */       
/*     */       try {
/* 155 */         reader.close();
/* 156 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/* 160 */     this.regions = regions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(RegionManager manager) throws IOException {
/* 169 */     load();
/* 170 */     manager.setRegions(this.regions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(RegionManager manager) throws IOException {
/* 179 */     this.regions = manager.getRegions();
/* 180 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DefaultDomain parseDomains(String data) {
/* 190 */     DefaultDomain domain = new DefaultDomain();
/* 191 */     Pattern pattern = Pattern.compile("^([A-Za-z]):(.*)$");
/*     */     
/* 193 */     String[] parts = data.split(","); byte b; int i;
/*     */     String[] arrayOfString1;
/* 195 */     for (i = (arrayOfString1 = parts).length, b = 0; b < i; ) { String part = arrayOfString1[b];
/* 196 */       Matcher matcher = pattern.matcher(part);
/*     */       
/* 198 */       if (!matcher.matches()) {
/* 199 */         logger.warning("Invalid owner specification: " + part);
/*     */       }
/*     */       else {
/*     */         
/* 203 */         String type = matcher.group(1);
/* 204 */         String id = matcher.group(2);
/*     */         
/* 206 */         if (type.equals("u")) {
/* 207 */           domain.addPlayer(id);
/* 208 */         } else if (type.equals("g")) {
/* 209 */           domain.addGroup(id);
/*     */         } else {
/* 211 */           logger.warning("Unknown owner specification: " + type);
/*     */         } 
/*     */       }  b++; }
/*     */     
/* 215 */     return domain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AreaFlags parseFlags(String data) {
/* 225 */     AreaFlags flags = new AreaFlags();
/* 226 */     AreaFlags.State curState = AreaFlags.State.ALLOW;
/*     */     
/* 228 */     for (int i = 0; i < data.length(); i++) {
/* 229 */       char flag = data.charAt(i);
/* 230 */       if (flag == '+') {
/* 231 */         curState = AreaFlags.State.ALLOW;
/* 232 */       } else if (flag == '-') {
/* 233 */         curState = AreaFlags.State.DENY;
/* 234 */       } else if (flag == 'b') {
/* 235 */         flags.allowBuild = curState;
/* 236 */       } else if (flag == 'p') {
/* 237 */         flags.allowPvP = curState;
/* 238 */       } else if (flag == 'm') {
/* 239 */         flags.allowMobDamage = curState;
/* 240 */       } else if (flag == 'c') {
/* 241 */         flags.allowCreeperExplosions = curState;
/* 242 */       } else if (flag == 't') {
/* 243 */         flags.allowTNT = curState;
/* 244 */       } else if (flag == 'l') {
/* 245 */         flags.allowLighter = curState;
/* 246 */       } else if (flag == 'f') {
/* 247 */         flags.allowFireSpread = curState;
/* 248 */       } else if (flag == 'F') {
/* 249 */         flags.allowLavaFire = curState;
/*     */       } else {
/* 251 */         logger.warning("Unknown area flag/flag modifier: " + flag);
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String writeDomains(DefaultDomain domain) {
/* 265 */     StringBuilder str = new StringBuilder();
/*     */     
/* 267 */     for (String player : domain.getPlayers()) {
/* 268 */       str.append("u:" + player + ",");
/*     */     }
/*     */     
/* 271 */     for (String group : domain.getGroups()) {
/* 272 */       str.append("g:" + group + ",");
/*     */     }
/*     */     
/* 275 */     return (str.length() > 0) ? 
/* 276 */       str.toString().substring(0, str.length() - 1) : "";
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
/*     */   private String writeFlag(AreaFlags.State state, String flag) {
/* 288 */     if (state == AreaFlags.State.ALLOW)
/* 289 */       return "+" + flag; 
/* 290 */     if (state == AreaFlags.State.DENY) {
/* 291 */       return "-" + flag;
/*     */     }
/*     */     
/* 294 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String writeFlags(AreaFlags flags) {
/* 304 */     StringBuilder str = new StringBuilder();
/* 305 */     str.append(writeFlag(flags.allowBuild, "b"));
/* 306 */     str.append(writeFlag(flags.allowPvP, "p"));
/* 307 */     str.append(writeFlag(flags.allowMobDamage, "m"));
/* 308 */     str.append(writeFlag(flags.allowCreeperExplosions, "c"));
/* 309 */     str.append(writeFlag(flags.allowTNT, "t"));
/* 310 */     str.append(writeFlag(flags.allowLighter, "l"));
/* 311 */     str.append(writeFlag(flags.allowFireSpread, "f"));
/* 312 */     str.append(writeFlag(flags.allowLavaFire, "F"));
/* 313 */     return str.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, ProtectedRegion> getRegions() {
/* 322 */     return this.regions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegions(Map<String, ProtectedRegion> regions) {
/* 331 */     this.regions = regions;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\protection\CSVDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */