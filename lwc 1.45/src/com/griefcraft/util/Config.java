/*     */ package com.griefcraft.util;
/*     */ 
/*     */ import com.griefcraft.logging.Logger;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Properties;
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
/*     */ public class Config
/*     */   extends Properties
/*     */ {
/*     */   public static void destroy() {
/*  36 */     instance = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Config getInstance() {
/*  43 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*  50 */     if (instance == null) {
/*  51 */       instance = new Config();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private Logger logger = Logger.getLogger(getClass().getSimpleName());
/*     */ 
/*     */   
/*     */   private static Config instance;
/*     */ 
/*     */ 
/*     */   
/*     */   private Config() {
/*     */     byte b;
/*     */     int i;
/*     */     ConfigValues[] arrayOfConfigValues;
/*  69 */     for (i = (arrayOfConfigValues = ConfigValues.values()).length, b = 0; b < i; ) { ConfigValues value = arrayOfConfigValues[b];
/*  70 */       setProperty(value.getName(), value.getDefaultValue());
/*     */ 
/*     */ 
/*     */       
/*     */       b++; }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       File conf = new File("lwc.properties");
/*     */       
/*  81 */       if (!conf.exists()) {
/*  82 */         save();
/*     */         
/*     */         return;
/*     */       } 
/*  86 */       InputStream inputStream = new FileInputStream(conf);
/*  87 */       load(inputStream);
/*  88 */       inputStream.close();
/*     */       
/*  90 */       this.logger.info("Loaded " + size() + " config entries");
/*  91 */     } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void save() {
/*     */     try {
/*  98 */       File file = new File("lwc.properties");
/*     */       
/* 100 */       if (!file.exists()) {
/* 101 */         file.createNewFile();
/*     */       }
/*     */       
/* 104 */       OutputStream outputStream = new FileOutputStream(file);
/*     */       
/* 106 */       store(outputStream, "# LWC configuration file\n\n# + Github project page: https://github.com/Hidendra/LWC\n# + hMod thread link: http://forum.hey0.net/showthread.php?tid=837\n");
/* 107 */       outputStream.close();
/* 108 */     } catch (Exception e) {
/* 109 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraf\\util\Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */