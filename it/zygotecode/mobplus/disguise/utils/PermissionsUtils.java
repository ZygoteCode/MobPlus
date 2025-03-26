package it.zygotecode.mobplus.disguise.utils;

import org.bukkit.entity.Player;

public class PermissionsUtils
{
	public static boolean hasPerm(Player player, String perm)
	{
		if (player.hasPermission("mobplus.*") || player.hasPermission(perm) || player.isOp() || player.hasPermission("*") || player.hasPermission("mobplus." + perm))
		{
			return true;
		}
		
		return false;
	}
}