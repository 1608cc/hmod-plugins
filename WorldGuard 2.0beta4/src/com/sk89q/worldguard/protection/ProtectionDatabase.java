package com.sk89q.worldguard.protection;

import java.io.IOException;
import java.util.Map;

public interface ProtectionDatabase {
  void load() throws IOException;
  
  void save() throws IOException;
  
  void load(RegionManager paramRegionManager) throws IOException;
  
  void save(RegionManager paramRegionManager) throws IOException;
  
  Map<String, ProtectedRegion> getRegions();
  
  void setRegions(Map<String, ProtectedRegion> paramMap);
}


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\protection\ProtectionDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */