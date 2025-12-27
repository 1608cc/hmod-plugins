/*     */ package classes.com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.AreaFlags;
/*     */ import com.sk89q.worldguard.protection.CSVDatabase;
/*     */ import com.sk89q.worldguard.protection.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.ProtectedRegion;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.junit.After;
/*     */ import org.junit.Assert;
/*     */ import org.junit.Before;
/*     */ import org.junit.Test;
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
/*     */ public class CSVDatabaseTest
/*     */ {
/*     */   @Before
/*     */   public void setUp() throws Exception {}
/*     */   
/*     */   @After
/*     */   public void tearDown() throws Exception {}
/*     */   
/*     */   @Test
/*     */   public void testLoadSave() throws Exception {
/*  43 */     File temp = File.createTempFile("worldguard_csv_test", ".tmp");
/*  44 */     temp.deleteOnExit();
/*     */     
/*  46 */     Map<String, ProtectedRegion> regions = 
/*  47 */       new HashMap<String, ProtectedRegion>();
/*  48 */     regions.put("test1", getTestRegion1());
/*  49 */     regions.put("test2", getTestRegion2());
/*     */     
/*  51 */     CSVDatabase writeDB = new CSVDatabase(temp);
/*  52 */     writeDB.setRegions(regions);
/*  53 */     writeDB.save();
/*     */     
/*  55 */     CSVDatabase readDB = new CSVDatabase(temp);
/*  56 */     readDB.load();
/*     */     
/*  58 */     Map<String, ProtectedRegion> loaded = readDB.getRegions();
/*     */     
/*  60 */     ProtectedRegion region1 = loaded.get("test1");
/*  61 */     checkTestRegion1(region1);
/*     */   }
/*     */   
/*     */   private void checkTestRegion1(ProtectedRegion region) {
/*  65 */     AreaFlags flags = new AreaFlags();
/*  66 */     flags.allowFireSpread = AreaFlags.State.ALLOW;
/*  67 */     flags.allowPvP = AreaFlags.State.DENY;
/*  68 */     flags.allowLighter = AreaFlags.State.DENY;
/*  69 */     region.setFlags(flags);
/*     */     
/*  71 */     Assert.assertEquals(region.getFlags(), flags);
/*     */   }
/*     */   
/*     */   private ProtectedRegion getTestRegion1() {
/*  75 */     BlockVector min = new BlockVector(1, 2, 3);
/*  76 */     BlockVector max = new BlockVector(4, 5, 6);
/*     */     
/*  78 */     ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(min, max);
/*     */     
/*  80 */     AreaFlags flags = new AreaFlags();
/*  81 */     flags.allowFireSpread = AreaFlags.State.ALLOW;
/*  82 */     flags.allowPvP = AreaFlags.State.DENY;
/*  83 */     flags.allowLighter = AreaFlags.State.DENY;
/*  84 */     protectedCuboidRegion.setFlags(flags);
/*     */     
/*  86 */     DefaultDomain domain = new DefaultDomain();
/*  87 */     domain.addGroup("members");
/*  88 */     domain.addGroup("sturmehs");
/*  89 */     domain.addPlayer("hollie");
/*  90 */     domain.addPlayer("chad");
/*  91 */     domain.addPlayer("tetsu");
/*  92 */     protectedCuboidRegion.setOwners(domain);
/*     */     
/*  94 */     protectedCuboidRegion.setEnterMessage("hello there!");
/*  95 */     protectedCuboidRegion.setPriority(444);
/*     */     
/*  97 */     return (ProtectedRegion)protectedCuboidRegion;
/*     */   }
/*     */   
/*     */   private ProtectedRegion getTestRegion2() {
/* 101 */     BlockVector min = new BlockVector(7, 8, 9);
/* 102 */     BlockVector max = new BlockVector(10, 11, 12);
/*     */     
/* 104 */     ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(min, max);
/*     */     
/* 106 */     AreaFlags flags = new AreaFlags();
/* 107 */     flags.allowFireSpread = AreaFlags.State.DENY;
/* 108 */     flags.allowPvP = AreaFlags.State.ALLOW;
/* 109 */     flags.allowMobDamage = AreaFlags.State.DENY;
/* 110 */     protectedCuboidRegion.setFlags(flags);
/*     */     
/* 112 */     DefaultDomain domain = new DefaultDomain();
/* 113 */     domain.addGroup("admins");
/* 114 */     domain.addPlayer("jon");
/* 115 */     domain.addPlayer("ester");
/* 116 */     domain.addPlayer("amy");
/* 117 */     protectedCuboidRegion.setOwners(domain);
/*     */     
/* 119 */     protectedCuboidRegion.setEnterMessage("Testing");
/* 120 */     protectedCuboidRegion.setPriority(555);
/*     */     
/* 122 */     return (ProtectedRegion)protectedCuboidRegion;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\protection\CSVDatabaseTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */