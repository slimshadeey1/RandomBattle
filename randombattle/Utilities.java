package com.randombattle;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

import com.pixelmonmod.pixelmon.battles.controller.BattleController2Participant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumPokeballs;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerNotLoadedException;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

public class Utilities {
	
	
	
	
	
	
	
	
	
	//Broadcasts a message to the entire server
	public static void broadcastServerMessage(String string)
	{
		String players[] = MinecraftServer.getServer().getAllUsernames();
		for(int i = 0; i < players.length;i++)
		{
			MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(players[i]).addChatMessage((new ChatComponentTranslation(string)));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Sets the player as the winner
	public static void doWin(EntityPlayer p)
	{
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Gets the winner of the match. It then calls the doWin function with the params of the winner.
	public static void getWinner(EntityPlayer p1, PlayerStorage ps1, EntityPlayer p2, PlayerStorage ps2){
		int fainted1 = 0;
		int fainted2 = 0;
		try {
			ps1 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p1);
		} catch (PlayerNotLoadedException e1) {
			
			e1.printStackTrace();
		}
		try {
			ps2 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p2);
		} catch (PlayerNotLoadedException e) {
			
			e.printStackTrace();
		}
		for(int i = 0; i <5; i++)
		{
			
			
			
			if(ps2.partyPokemon[i] != null)
			{
				if(ps2.partyPokemon[i].getBoolean("IsFainted") == true)
				{
					fainted2++;
				}
			}
			
		}
		
		for(int i = 0; i <5; i++)
		{
			
			
			
			if(ps1.partyPokemon[i]!= null)
			{
				if(ps1.partyPokemon[i].getBoolean("IsFainted") == true)
				{
					fainted1++;
				}	
			}
			
		}
		
		if(fainted1 == ps1.partyPokemon.length && fainted2 == ps2.partyPokemon.length)
		{
			doTie();
		}
		else
		if(fainted1 == ps1.partyPokemon.length)
		{
			broadcastServerMessage(p1.getDisplayName()+" has won the battle!");
			doWin(p1);
		}
		else
			if(fainted2 == ps2.partyPokemon.length)
			{
				broadcastServerMessage(p2.getDisplayName()+" has won the battle!");
				doWin(p2);
			}
			else
			{
				System.out.println("ERROR CALCULATING WINNER! THEY BOTH HAVE ACTIVE POKEMON AFTER BATTLE DOING TIE!");
				doTie();
			}
				
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//This will do stuff when both players loose. Example: Explosion, Self Destruct, Take Down, Ect
	public static void doTie()
	{
		
	}
	
	
	
	
	
	
	
	
	
	
	//This will make the game completely reset.
	public static void resetGame()
	{
		RandomBattleTickHandler.gameStage = "pregame";
		RandomBattleTickHandler.doneBattle = false;
		RandomBattleTickHandler.p1 = null;
		RandomBattleTickHandler.p2 = null;
		RandomBattleTickHandler.ps1 = null;
		RandomBattleTickHandler.ps2 = null;
		RandomBattleTickHandler.t1 = null;
		RandomBattleTickHandler.t2 = null;
		return;
	}
	
	
	
	
	
	
	
	
	
	
	//This code will give out random pokemon to the players.
	static String poke;
	public static boolean giveRandomPokemon(EntityPlayer p1, EntityPlayer p2)
	{
		
		if(MinecraftServer.getServer().getConfigurationManager().getAllUsernames().length == 2)
		{
			
			//Giving a random team to player 1
			
			for(int i = 0; i < 6; i++)
			{
				try {
					PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP) p1).changePokemonAndAssignID(i, null);
				} catch (PlayerNotLoadedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				for(int i = 0; i < 6; i++)
				{
					try{
						poke = EnumPokemon.randomPoke().toString();
						EntityPixelmon p = (EntityPixelmon) PixelmonEntityList.createEntityByName(poke, p1.worldObj);
						p.getLvl().setLevel(50);
						if(p.getMoveset().size() == 0)
						{
							p.loadMoveset();
							
						}
						p.caughtBall = EnumPokeballs.PokeBall;
						
						PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP) p1).addToParty(p);
						}
						catch(Exception e)
							{
							System.out.println("ERROR CALCULATING POKEMON FOR "+p1.getDisplayName()+"!");
							return false;
							}
				}
			
				
				
				for(int i = 0; i < 6; i++)
				{
					try {
						PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP) p2).changePokemonAndAssignID(i, null);
					} catch (PlayerNotLoadedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			
			for(int i = 0; i < 6; i++)
			{
				try{
				poke = EnumPokemon.randomPoke().toString();
				EntityPixelmon p = (EntityPixelmon) PixelmonEntityList.createEntityByName(poke, p2.worldObj);
				p.getLvl().setLevel(50);
				p.caughtBall = EnumPokeballs.PokeBall;
				if(p.getMoveset().size() == 0)
				{
					p.loadMoveset();
					
				}
				PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP) p2).addToParty(p);
				}
				catch(Exception e)
				{System.out.println("ERROR CALCULATING POKEMON FOR "+p2.getDisplayName()+"!");
				return false;
				}
			}
			
			Utilities.broadcastServerMessage("Boxes have been filled up! Entering team prep phase!");
			return true;
			}
			
		
		else
		{
			return false;
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	public static boolean getPlayers()
	{
		
		
			//TODO: Checkpokemon
			if(MinecraftServer.getServer().getConfigurationManager().getAllUsernames().length == 2)
			{
			RandomBattleTickHandler.p1 = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(MinecraftServer.getServer().getAllUsernames()[0]);

			RandomBattleTickHandler.p2 = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(MinecraftServer.getServer().getAllUsernames()[1]);
			try {
				RandomBattleTickHandler.ps1 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)RandomBattleTickHandler.p1);
				Utilities.broadcastServerMessage(RandomBattleTickHandler.p1.getDisplayName()+"'s party is loaded in ps1!");
				RandomBattleTickHandler.ps2 = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)RandomBattleTickHandler.p2);
				Utilities.broadcastServerMessage(RandomBattleTickHandler.p2.getDisplayName()+"'s party is loaded in ps2!");
				
			} catch (PlayerNotLoadedException e) {
				System.out.println("ERROR GETTING ONE OF THE PLAYERS PARTYS! THIS IS FATAL!");
				Utilities.broadcastServerMessage("ERROR: Couldn't load one of the players party!");
			}
			if(RandomBattleTickHandler.ps1 == null)
			{
				System.out.println("ERROR WITH "+RandomBattleTickHandler.p1.getDisplayName()+"'s PARTY! STARTING PREGAME PHASE OVER!");
				resetGame();
				return false;
				
			}
			if(RandomBattleTickHandler.ps2 == null)
			{
				System.out.println("ERROR WITH "+RandomBattleTickHandler.p2.getDisplayName()+"'s PARTY! STARTING PREGAME PHASE OVER!");
				resetGame();
				return false;
				
			}
			
			
			
		}
			return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void doBattle(EntityPlayer p1, EntityPlayer p2)
	{
		PlayerParticipant player1;
		PlayerParticipant player2;
		BattleController2Participant bc1;
		try {
			EntityPixelmon player1firstPokemon = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p1).getFirstAblePokemon(p1.worldObj);
			player1 = new PlayerParticipant((EntityPlayerMP)p1, player1firstPokemon);
			
			EntityPixelmon player2firstPokemon = PixelmonStorage.PokeballManager.getPlayerStorage((EntityPlayerMP)p2).getFirstAblePokemon(((EntityPlayerMP)p2).worldObj);
			player2 = new PlayerParticipant((EntityPlayerMP)p2, player2firstPokemon);
		
			//notifyAdmins(par1ICommandSender, 1, "Battle between " + par2ArrayOfStr[0] + " and " + par2ArrayOfStr[1] + " started!", new Object[] {par2ArrayOfStr[0], par2ArrayOfStr[1]});
			bc1 = new BattleController2Participant(player1, player2);
			return;
			}
		catch(Exception e)
		{
			broadcastServerMessage("There was an error starting battle! Starting game over!");
			resetGame();
		}
	}
	
}
