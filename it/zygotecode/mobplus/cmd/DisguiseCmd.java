package it.zygotecode.mobplus.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import it.zygotecode.mobplus.Main;
import it.zygotecode.mobplus.disguise.utils.PermissionsUtils;

public class DisguiseCmd implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (PermissionsUtils.hasPerm(player, "disguise") || PermissionsUtils.hasPerm(player, "d"))
			{
				try
				{
					if (args.length == 1)
					{	
						String strType = args[0].toUpperCase();
						EntityType type = EntityType.valueOf(strType);
						
						if (type != null)
						{
							if (Main.getDisguiseManager().hasDisguise(player))
							{
								Main.getDisguiseManager().undisguisePlayer(player);
							}
							
							Main.getDisguiseManager().disguisePlayer(player, type);
							Main.msg(player, "You have been disguised into a " + strType.toLowerCase() + ".");
							return true;
						}
						else
						{
							Main.msg(player, "Invalid mob type.");
							return true;
						}
					}
					else if (args.length == 2)
					{
						Player p = Bukkit.getPlayer(args[0]);
						
						if (p != null)
						{
							String strType = args[1].toUpperCase();
							EntityType type = EntityType.valueOf(strType);
							
							if (type != null)
							{
								if (Main.getDisguiseManager().hasDisguise(player))
								{
									Main.getDisguiseManager().undisguisePlayer(player);
								}
								
								Main.getDisguiseManager().disguisePlayer(p, type);
								Main.msg(player, "You have been disguised into a " + strType.toLowerCase() + ".");
								return true;
							}
							else
							{
								Main.msg(player, "Invalid mob type.");
								return true;
							}
						}
						else
						{
							Main.msg(player, "Can not find the player " + args[0].toLowerCase() + ".");
							return true;
						}
					}
					else
					{
						Main.msg(player, "Invalid command usage.");
						return true;
					}
				}
				catch (Exception ex)
				{
					Main.msg(player, "Invalid command usage.");
					ex.printStackTrace();
					return true;
				}
			}
			else
			{
				Main.msg(player, "You do not have permission to do this command.");
			}
		}
		else
		{
			sender.sendMessage("You must need to be a player to execute this command.");
		}
		
		return true;
	}
}