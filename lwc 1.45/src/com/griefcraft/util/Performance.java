package com.griefcraft.util;

import com.griefcraft.logging.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Performance {
  private static Logger logger = Logger.getLogger("Performance");
  
  private static long startTime = 0L;
  
  private static int physDBQueries = 0;
  
  private static int memDBQueries = 0;
  
  private static int playersOnline = 0;
  
  private static int chestCount = 0;
  
  private static List<String> generatedReport = new ArrayList<String>();
  
  public static void add(String paramString) {
    logger.info(paramString);
    generatedReport.add(paramString);
  }
  
  public static void addMemDBQuery() {
    memDBQueries++;
  }
  
  public static void addPhysDBQuery() {
    physDBQueries++;
  }
  
  public static void clear() {
    generatedReport.clear();
  }
  
  public static double getAverageQueriesSecond(int paramInt) {
    return paramInt / getTimeRunningSeconds();
  }
  
  public static List<String> getGeneratedReport() {
    if (generatedReport.size() == 0)
      report(); 
    return generatedReport;
  }
  
  public static int getTimeRunningSeconds() {
    return (int)((System.currentTimeMillis() - startTime) / 1000L);
  }
  
  public static void init() {
    startTime = System.currentTimeMillis();
  }
  
  public static void report() {
    add(" ************ Start Report ************ ");
    add(" ");
    add(" + Date:\t" + new Date());
    add(" + Time:\t" + getTimeRunningSeconds() + " seconds");
    add(" + Players:\t" + playersOnline);
    add(" + Protections:\t" + chestCount);
    add(" ");
    add(" - Physical database");
    add("  + Queries:\t" + physDBQueries);
    add("  + Average:\t" + getAverageQueriesSecond(physDBQueries) + " /second");
    add(" ");
    add(" - Memory database");
    add("  + Queries:\t" + memDBQueries);
    add("  + Average:\t" + getAverageQueriesSecond(memDBQueries) + " /second");
    add(" ");
    add(" ************  End Report  ************ ");
  }
  
  public static void setChestCount(int paramInt) {
    chestCount = paramInt;
  }
  
  public static void setPlayersOnline(int paramInt) {
    playersOnline = paramInt;
  }
}


/* Location:              C:\Users\1608c\Desktop\LWC.jar!\com\griefcraf\\util\Performance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */