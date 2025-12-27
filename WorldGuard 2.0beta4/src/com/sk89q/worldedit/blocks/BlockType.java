/*     */ package com.sk89q.worldedit.blocks;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public enum BlockType
/*     */ {
/*  32 */   AIR(0, "Air", "air"),
/*  33 */   STONE(1, "Stone", new String[] { "stone", "rock" }),
/*  34 */   GRASS(2, "Grass", "grass"),
/*  35 */   DIRT(3, "Dirt", "dirt"),
/*  36 */   COBBLESTONE(4, "Cobblestone", "cobblestone"),
/*  37 */   WOOD(5, "Wood", "wood"),
/*  38 */   SAPLING(6, "Sapling", "sapling"),
/*  39 */   BEDROCK(7, "Bedrock", "bedrock"),
/*  40 */   WATER(8, "Water", "watermoving"),
/*  41 */   STATIONARY_WATER(9, "Water (stationary)", "water"),
/*  42 */   LAVA(10, "Lava", "lavamoving"),
/*  43 */   STATIONARY_LAVA(11, "Lava (stationary)", "lava"),
/*  44 */   SAND(12, "Sand", "sand"),
/*  45 */   GRAVEL(13, "Gravel", "gravel"),
/*  46 */   GOLD_ORE(14, "Gold ore", "goldore"),
/*  47 */   IRON_ORE(15, "Iron ore", "ironore"),
/*  48 */   COAL_ORE(16, "Coal ore", "coalore"),
/*  49 */   LOG(17, "Log", "log"),
/*  50 */   LEAVES(18, "Leaves", "leaves"),
/*  51 */   SPONGE(19, "Sponge", "sponge"),
/*  52 */   GLASS(20, "Glass", "glass"),
/*  53 */   CLOTH(35, "Cloth", "cloth"),
/*  54 */   YELLOW_FLOWER(37, "Yellow flower", "yellowflower"),
/*  55 */   RED_FLOWER(38, "Red rose", new String[] { "redflower", "redrose" }),
/*  56 */   BROWN_MUSHROOM(39, "Brown mushroom", "brownmushroom"),
/*  57 */   RED_MUSHROOM(40, "Red mushroom", "redmushroom"),
/*  58 */   GOLD_BLOCK(41, "Gold block", new String[] { "gold", "goldblock" }),
/*  59 */   IRON_BLOCK(42, "Iron block", new String[] { "iron", "ironblock" }),
/*  60 */   DOUBLE_STEP(43, "Double step", "doublestep"),
/*  61 */   STEP(44, "Step", "step"),
/*  62 */   BRICK(45, "Brick", "brick"),
/*  63 */   TNT(46, "TNT", "tnt"),
/*  64 */   BOOKCASE(47, "Bookcase", new String[] { "bookshelf", "bookshelf" }),
/*  65 */   MOSSY_COBBLESTONE(48, "Cobblestone (mossy)", "mossycobblestone"),
/*  66 */   OBSIDIAN(49, "Obsidian", "obsidian"),
/*  67 */   TORCH(50, "Torch", "torch"),
/*  68 */   FIRE(51, "Fire", "fire"),
/*  69 */   MOB_SPAWNER(52, "Mob spawner", "mobspawner"),
/*  70 */   WOODEN_STAIRS(53, "Wooden stairs", "woodstairs"),
/*  71 */   CHEST(54, "Chest", "chest"),
/*  72 */   REDSTONE_WIRE(55, "Redstone wire", "redstone"),
/*  73 */   DIAMOND_ORE(56, "Diamond ore", "diamondore"),
/*  74 */   DIAMOND_BLOCK(57, "Diamond block", new String[] { "diamond", "diamondblock" }),
/*  75 */   WORKBENCH(58, "Workbench", "workbench"),
/*  76 */   CROPS(59, "Crops", "crops"),
/*  77 */   SOIL(60, "Soil", "soil"),
/*  78 */   FURNACE(61, "Furnace", "furnace"),
/*  79 */   BURNING_FURNACE(62, "Furnace (burning)", "burningfurnace"),
/*  80 */   SIGN_POST(63, "Sign post", new String[] { "sign", "signpost" }),
/*  81 */   WOODEN_DOOR(64, "Wooden door", "wooddoor"),
/*  82 */   LADDER(65, "Ladder", "ladder"),
/*  83 */   MINECART_TRACKS(66, "Minecart tracks", new String[] { "track", "tracks" }),
/*  84 */   COBBLESTONE_STAIRS(67, "Cobblestone stairs", "cobblestonestairs"),
/*  85 */   WALL_SIGN(68, "Wall sign", "wallsign"),
/*  86 */   LEVER(69, "Lever", "level"),
/*  87 */   STONE_PRESSURE_PLATE(70, "Stone pressure plate", "stonepressureplate"),
/*  88 */   IRON_DOOR(71, "Iron Door", "irondoor"),
/*  89 */   WOODEN_PRESSURE_PLATE(72, "Wooden pressure plate", "woodpressureplate"),
/*  90 */   REDSTONE_ORE(73, "Redstone ore", "redstoneore"),
/*  91 */   GLOWING_REDSTONE_ORE(74, "Glowing redstone ore", "glowingredstoneore"),
/*  92 */   REDSTONE_TORCH_OFF(75, "Redstone torch (off)", new String[] { "redstonetorch", " redstonetorchon"
/*     */     }),
/*  94 */   REDSTONE_TORCH_ON(76, "Redstone torch (on)", "redstonetorchon"),
/*  95 */   STONE_BUTTON(77, "Stone Button", "stonebutton"),
/*  96 */   SNOW(78, "Snow", "snow"),
/*  97 */   ICE(79, "Ice", "ice"),
/*  98 */   SNOW_BLOCK(80, "Snow block", "snowblock"),
/*  99 */   CACTUS(81, "Cactus", "cactus"),
/* 100 */   CLAY(82, "Clay", "clay"),
/* 101 */   REED(83, "Reed", "reed"),
/* 102 */   JUKEBOX(84, "Jukebox", "jukebox"),
/* 103 */   FENCE(85, "Fence", "fence"),
/* 104 */   PUMPKIN(86, "Pumpkin", "pumpkin"),
/* 105 */   RED_BLOCK(87, "Cobblestone (red mossy)", new String[] { "redmossycobblestone", "redcobblestone" }),
/* 106 */   HELL_DIRT(88, "Mud", "mud"),
/* 107 */   HELL_GOLD(89, "Brittle gold", "brittlegold"),
/* 108 */   PORTAL(90, "Portal", "portal"),
/* 109 */   LIGHTED_PUMPKIN(91, "Pumpkin (on)", new String[] { "pumpkinlighted", "pumpkinon", "litpumpkin" });
/*     */   private static final Map<Integer, BlockType> ids;
/*     */   private static final Map<String, BlockType> lookup;
/*     */   
/*     */   static {
/* 114 */     ids = new HashMap<Integer, BlockType>();
/*     */ 
/*     */ 
/*     */     
/* 118 */     lookup = new HashMap<String, BlockType>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     for (BlockType type : EnumSet.<BlockType>allOf(BlockType.class)) {
/* 126 */       ids.put(Integer.valueOf(type.id), type);
/* 127 */       for (String key : type.lookupKeys) {
/* 128 */         lookup.put(key, type);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final int id;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final String[] lookupKeys;
/*     */   
/*     */   BlockType(int id, String name, String lookupKey) {
/* 141 */     this.id = id;
/* 142 */     this.name = name;
/* 143 */     this.lookupKeys = new String[] { lookupKey };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BlockType(int id, String name, String[] lookupKeys) {
/* 153 */     this.id = id;
/* 154 */     this.name = name;
/* 155 */     this.lookupKeys = lookupKeys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockType fromID(int id) {
/* 165 */     return ids.get(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockType lookup(String name) {
/* 175 */     return lookup.get(name.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID() {
/* 184 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 193 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldPlaceLast() {
/* 203 */     return shouldPlaceLast(this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldPlaceLast(int id) {
/* 213 */     return (id == 6 || id == 37 || id == 38 || id == 39 || id == 40 || id == 50 || id == 51 || id == 55 || id == 59 || id == 63 || id == 64 || id == 65 || id == 66 || id == 68 || id == 69 || id == 70 || id == 71 || id == 72 || id == 75 || id == 76 || id == 77 || id == 78 || id == 81 || id == 83 || id == 90);
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
/*     */   public static boolean canPassThrough(int id) {
/* 247 */     return (id == 0 || id == 6 || id == 37 || id == 38 || id == 39 || id == 40 || id == 50 || id == 51 || id == 55 || id == 59 || id == 63 || id == 65 || id == 66 || id == 68 || id == 69 || id == 70 || id == 72 || id == 75 || id == 76 || id == 77 || id == 78 || id == 83 || id == 90);
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldedit\blocks\BlockType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */