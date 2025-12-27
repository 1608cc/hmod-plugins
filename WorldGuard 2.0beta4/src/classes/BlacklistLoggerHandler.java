package classes;

import Block;
import Player;

public interface BlacklistLoggerHandler {
  void logDestroyAttempt(Player paramPlayer, Block paramBlock, String paramString);
  
  void logBreakAttempt(Player paramPlayer, Block paramBlock, String paramString);
  
  void logUseAttempt(Player paramPlayer, int paramInt, String paramString);
  
  void logDestroyWithAttempt(Player paramPlayer, int paramInt, String paramString);
  
  void logPlaceAttempt(Player paramPlayer, int paramInt, String paramString);
  
  void logRightClickAttempt(Player paramPlayer, Block paramBlock, String paramString);
  
  void logDropAttempt(Player paramPlayer, int paramInt, String paramString);
  
  void logAcquireAttempt(Player paramPlayer, int paramInt, String paramString);
  
  void close();
}


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\BlacklistLoggerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */