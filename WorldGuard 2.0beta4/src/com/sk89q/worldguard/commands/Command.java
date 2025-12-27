package com.sk89q.worldguard.commands;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuardController;

public interface Command {
  void act(WorldGuardController paramWorldGuardController, LocalPlayer paramLocalPlayer, String[] paramArrayOfString) throws CommandException;
}


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\com\sk89q\worldguard\commands\Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */