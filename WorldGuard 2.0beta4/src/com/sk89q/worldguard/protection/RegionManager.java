package com.sk89q.worldguard.protection;

import com.sk89q.worldedit.Vector;
import java.util.List;
import java.util.Map;

public interface RegionManager {
  Map<String, ProtectedRegion> getRegions();
  
  void setRegions(Map<String, ProtectedRegion> paramMap);
  
  void addRegion(String paramString, ProtectedRegion paramProtectedRegion);
  
  boolean hasRegion(String paramString);
  
  ProtectedRegion getRegion(String paramString);
  
  void removeRegion(String paramString);
  
  ApplicableRegionSet getApplicableRegions(Vector paramVector);
  
  List<String> getApplicableRegionsIDs(Vector paramVector);
  
  int size();
}


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\RegionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */