/*     */ package classes.com.sk89q.worldguard.protection;
/*     */ 
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.TestPlayer;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.AreaFlags;
/*     */ import com.sk89q.worldguard.protection.FlatRegionManager;
/*     */ import com.sk89q.worldguard.protection.ProtectedCuboidRegion;
/*     */ import com.sk89q.worldguard.protection.ProtectedRegion;
/*     */ import com.sk89q.worldguard.protection.RegionManager;
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
/*     */ public class ApplicableRegionSetTest
/*     */ {
/*  31 */   static String COURTYARD_ID = "courtyard";
/*  32 */   static String FOUNTAIN_ID = "fountain";
/*  33 */   static String MEMBER_GROUP = "member";
/*  34 */   static String COURTYARD_GROUP = "courtyard";
/*     */   
/*  36 */   Vector inFountain = new Vector(2, 2, 2);
/*  37 */   Vector inCourtyard = new Vector(7, 7, 7);
/*  38 */   Vector outside = new Vector(15, 15, 15);
/*     */   RegionManager manager;
/*     */   TestPlayer player1;
/*     */   TestPlayer player2;
/*     */   
/*     */   @Before
/*     */   public void setUp() throws Exception {
/*  45 */     this.manager = (RegionManager)new FlatRegionManager();
/*     */     
/*  47 */     setUpPlayers();
/*  48 */     setUpCourtyardRegion();
/*  49 */     setUpFountainRegion();
/*     */   }
/*     */   
/*     */   void setUpPlayers() {
/*  53 */     this.player1 = new TestPlayer("tetsu");
/*  54 */     this.player1.addGroup(MEMBER_GROUP);
/*  55 */     this.player1.addGroup(COURTYARD_GROUP);
/*     */     
/*  57 */     this.player2 = new TestPlayer("alex");
/*  58 */     this.player2.addGroup(MEMBER_GROUP);
/*     */   }
/*     */   
/*     */   void setUpCourtyardRegion() {
/*  62 */     DefaultDomain domain = new DefaultDomain();
/*  63 */     domain.addGroup(COURTYARD_GROUP);
/*     */     
/*  65 */     ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(
/*  66 */         new BlockVector(0, 0, 0), new BlockVector(10, 10, 10));
/*  67 */     AreaFlags flags = new AreaFlags();
/*  68 */     flags.allowBuild = AreaFlags.State.NONE;
/*  69 */     flags.allowFireSpread = AreaFlags.State.ALLOW;
/*  70 */     protectedCuboidRegion.setFlags(flags);
/*  71 */     protectedCuboidRegion.setOwners(domain);
/*  72 */     this.manager.addRegion(COURTYARD_ID, (ProtectedRegion)protectedCuboidRegion);
/*     */   }
/*     */   
/*     */   void setUpFountainRegion() {
/*  76 */     ProtectedCuboidRegion protectedCuboidRegion = new ProtectedCuboidRegion(
/*  77 */         new BlockVector(0, 0, 0), new BlockVector(5, 5, 5));
/*  78 */     AreaFlags flags = new AreaFlags();
/*  79 */     flags.allowBuild = AreaFlags.State.ALLOW;
/*  80 */     flags.allowFireSpread = AreaFlags.State.DENY;
/*  81 */     protectedCuboidRegion.setFlags(flags);
/*  82 */     this.manager.addRegion(FOUNTAIN_ID, (ProtectedRegion)protectedCuboidRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testNonBuildFlag() {
/*  90 */     ApplicableRegionSet appl = this.manager.getApplicableRegions(this.outside);
/*  91 */     Assert.assertTrue(appl.allowsFireSpread());
/*     */     
/*  93 */     appl = this.manager.getApplicableRegions(this.inCourtyard);
/*  94 */     Assert.assertTrue(appl.allowsFireSpread());
/*     */     
/*  96 */     appl = this.manager.getApplicableRegions(this.inFountain);
/*  97 */     Assert.assertFalse(appl.allowsFireSpread());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testPlayer1BuildAccess() {
/* 105 */     ApplicableRegionSet appl = this.manager.getApplicableRegions(this.outside);
/* 106 */     Assert.assertTrue(appl.canBuild((LocalPlayer)this.player1));
/*     */     
/* 108 */     appl = this.manager.getApplicableRegions(this.inCourtyard);
/* 109 */     Assert.assertTrue(appl.canBuild((LocalPlayer)this.player1));
/*     */     
/* 111 */     appl = this.manager.getApplicableRegions(this.inFountain);
/* 112 */     Assert.assertTrue(appl.canBuild((LocalPlayer)this.player1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Test
/*     */   public void testPlayer2BuildAccess() {
/* 120 */     ApplicableRegionSet appl = this.manager.getApplicableRegions(this.outside);
/* 121 */     Assert.assertTrue(appl.canBuild((LocalPlayer)this.player2));
/*     */     
/* 123 */     appl = this.manager.getApplicableRegions(this.inCourtyard);
/* 124 */     Assert.assertFalse(appl.canBuild((LocalPlayer)this.player2));
/*     */     
/* 126 */     appl = this.manager.getApplicableRegions(this.inFountain);
/* 127 */     Assert.assertTrue(appl.canBuild((LocalPlayer)this.player2));
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\com\sk89q\worldguard\protection\ApplicableRegionSetTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */