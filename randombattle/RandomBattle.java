package com.randombattle;



import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "randombattle", name = "RandomBattle", version = "1.0", dependencies = "required-after:pixelmon", acceptableRemoteVersions="*")


public class RandomBattle {
	
	
	//public static Configuration config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	}
	@EventHandler
    public void load(FMLInitializationEvent event) 
    {	
		//TickRegistry.registerTickHandler(new RandomBattleTickHandler(), Side.SERVER);
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		RandomBattleTickHandler tickHandler = new RandomBattleTickHandler();

         FMLCommonHandler.instance().bus().register(tickHandler);
         MinecraftForge.EVENT_BUS.register(tickHandler);
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		if (MinecraftServer.getServer().getCommandManager() instanceof ServerCommandManager) {
			
		}
	}
	
	
}
