package it.zygotecode.mobplus;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import it.zygotecode.mobplus.cmd.DisguiseCmd;
import it.zygotecode.mobplus.cmd.UndisguiseCmd;
import it.zygotecode.mobplus.disguise.DisguiseListener;
import it.zygotecode.mobplus.disguise.DisguiseManager;

public class Main extends JavaPlugin
{
	private static String prefix = "§9Mob+> §7";
	private static FileConfiguration config;
	private static JavaPlugin plugin;
	private static DisguiseManager disguiseManager;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable()
	{
		if (!Bukkit.getVersion().replace("_", "").replace(".", "").contains("18"))
		{
			print("This plugin supports only Minecraft version 1.8. Please, change your server version or wait for a fix for new versions.");
			this.setEnabled(false);
			return;
		}
		
		print("Plugin enabled.");
		File file = new File(getDataFolder(), "config.yml");
		disguiseManager = new DisguiseManager();
		
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				
			}
			
			getConfig().createSection("general");
			ConfigurationSection general = getConfig().getConfigurationSection("general");
			general.addDefault("prefix", prefix.replace("§", "&"));
			
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
			print("Config saved.");
		}
		else
		{
			reloadConfig();
			print("Config reloaded.");
		}
		
		prefix = getConfig().getConfigurationSection("general").getString("prefix").replace("&", "§");
		config = getConfig();
		plugin = this;
		
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
        {
        	@Override
            public void run()
            {
        		getDisguiseManager().updateDisguises();
            }
        }
        , 0L, 0L);
        
        getCommand("disguise").setExecutor(new DisguiseCmd());
        getCommand("d").setExecutor(new DisguiseCmd());
        getCommand("undisguise").setExecutor(new UndisguiseCmd());
        getCommand("und").setExecutor(new UndisguiseCmd());
        getServer().getPluginManager().registerEvents(new DisguiseListener(), (Plugin) this);
	}
	
	@Override
	public void onDisable()
	{
		print("Plugin disabled.");
	}
	
	public static void print(String line)
	{
		System.out.println("[Mob+] " + line);
	}
	
	public static void msg(Player p, String msg)
	{
		p.sendMessage(prefix + msg);
	}
	
	public static Plugin getPlugin()
	{
		return Bukkit.getServer().getPluginManager().getPlugin("MobPlus");
	}
	
	public static JavaPlugin getJavaPlugin()
	{
		return plugin;
	}
	
	public static String getPrefix()
	{
		return prefix;
	}
	
	public void addListener(Listener listener)
	{
		getServer().getPluginManager().registerEvents(listener, (Plugin) this);
	}
	
	public static void sendMessage(Player p, String msg)
	{
		if (!(msg == ""))
		{
			p.sendMessage(prefix + msg.replace("&", "§"));
		}
	}
	
	public static FileConfiguration getTConfig()
	{
		return config;
	}
	
	public static DisguiseManager getDisguiseManager()
	{
		return disguiseManager;
	}
}