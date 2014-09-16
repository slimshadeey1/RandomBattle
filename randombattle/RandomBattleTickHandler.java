package com.randombattle;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class RandomBattleTickHandler{
	public int ticks = 0;
	public int tickspersecond = 60;
	public static String gameStage = "pregame";
	public int seconds = 0;
	public static EntityPlayer p1 = null;
	public static EntityPlayer p2 = null;
	public static EntityPlayer p3 = null;
	public static PlayerStorage ps1 = null;
	public static PlayerStorage ps2 = null;
	public static PlayerStorage t1 = null;
	public static PlayerStorage t2 = null;
	public static boolean doneBattle = false;
	public static String poke;
	@SubscribeEvent
    public void playerTickStart(TickEvent.PlayerTickEvent event) {
		
		
		ticks++;
			if(ticks % tickspersecond == 0)
			{
				if(MinecraftServer.getServer().getConfigurationManager().getAllUsernames().length == 2)
				{
					seconds++;
					if(doneBattle == false)
					{
					if(Utilities.getPlayers())
					{
						if(Utilities.giveRandomPokemon(p1, p2))
						{
							
							Utilities.doBattle(p1, p2);
							doneBattle = true;
							
						}
						else
						{
							Utilities.resetGame();
						}
					}
					else
					{
						Utilities.resetGame();
					}
					
					
				}
					else
					{
						if(BattleRegistry.getBattle(p1) == null || BattleRegistry.getBattle(p2) == null)
						{
							((EntityPlayerMP)p1).playerNetServerHandler.kickPlayerFromServer("The Game is over. Thanks for playing!");
							((EntityPlayerMP)p2).playerNetServerHandler.kickPlayerFromServer("The Game is over. Thanks for playing!");
							Utilities.resetGame();
						}
					}
					}
				else
				{
					seconds = 0;
				}
				
				
			}
		
	}
	
	
}
