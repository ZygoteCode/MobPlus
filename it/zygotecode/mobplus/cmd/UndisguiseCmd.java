package it.zygotecode.mobplus.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.zygotecode.mobplus.Main;
import it.zygotecode.mobplus.disguise.utils.PermissionsUtils;

public class UndisguiseCmd implements CommandExecutor
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
						Player p = Bukkit.getPlayer(args[0]);
						
						if (p != null)
						{
							if (Main.getDisguiseManager().hasDisguise(p))
							{
								Main.getDisguiseManager().undisguisePlayer(p);
								Main.msg(player, "Succesfully undisguised " + args[0].toLowerCase() + ".");
								Main.msg(p, "Your are not more disguised.");
								return true;
							}
							else
							{
								Main.msg(player, "This player is not disguised.");
								return true;
							}
						}
					}
					else if (args.length == 0)
					{
						if (Main.getDisguiseManager().hasDisguise(player))
						{
							Main.getDisguiseManager().undisguisePlayer(player);
							Main.msg(player, "You are not more disguised.");
							return true;
						}
						else
						{
							Main.msg(player, "You are not disguised.");
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