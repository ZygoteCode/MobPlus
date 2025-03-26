package it.zygotecode.mobplus.disguise;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import it.zygotecode.mobplus.Main;
import it.zygotecode.mobplus.disguise.utils.InventoryUtils;
import it.zygotecode.mobplus.disguise.utils.NoAI;

public class DisguiseManager
{
	private ArrayList<Disguise> disguises;
	
	public DisguiseManager()
	{
		disguises = new ArrayList<Disguise>();
		Main.print("Initialized disguises.");
	}
	
	public ArrayList<Disguise> getDisguises()
	{
		return disguises;
	}
	
	public boolean hasDisguise(Player player)
	{
		if (getDisguises().size() <= 0)
		{
			return false;
		}
		
		for (Disguise disguise: getDisguises())
		{
			if (disguise.getPlayer().equals(player) && disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasDisguise(Player player, DisguiseType disguiseType)
	{
		if (getDisguises().size() <= 0)
		{
			return false;
		}
		
		for (Disguise disguise: getDisguises())
		{
			if (disguise.getDisguiseType().equals(disguiseType) && disguise.getPlayer().equals(player) && disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasDisguise(LivingEntity entity)
	{
		if (getDisguises().size() <= 0)
		{
			return false;
		}
		
		for (Disguise disguise: getDisguises())
		{
			if (disguise.getEntity().equals(entity) && disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null && disguise.getDisguiseType().equals(DisguiseType.ENTITY))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void addDisguise(Disguise disguise)
	{
		if (disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
		{
			disguises.add(disguise);
		}
	}
	
	public void disguisePlayer(Player player, EntityType type)
	{
		try
		{
			if (hasDisguise(player))
			{
				return;
			}
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (player != p)
				{
					p.hidePlayer(player);
				}
			}
			
			if (type.equals(EntityType.BAT) || type.equals(EntityType.BLAZE) || type.equals(EntityType.ENDER_DRAGON) || type.equals(EntityType.WITHER))
			{
				player.setFlying(true);
				player.setAllowFlight(true);
			}
			else if (player.getGameMode().equals(GameMode.CREATIVE))
			{
				player.setFlying(true);
				player.setAllowFlight(true);
			}
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
			LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), type);
			NoAI.setAiEnabled(entity, false);
			Disguise disguise = new Disguise(player, entity, type, InventoryUtils.InventoryToString(player.getInventory()), player.getHealth(), DisguiseType.ENTITY);
			player.getInventory().clear();
			player.setMaxHealth(entity.getMaxHealth());
			player.setHealth(entity.getHealth());
			affectMobInitialization(player, type);
			giveMobUtilities(player, type);
			addDisguise(disguise);	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void giveMobUtilities(Player player, EntityType type)
	{
		try
		{
			if (type.equals(EntityType.CREEPER))
			{
				ItemStack explosion = new ItemStack(Material.STICK);
				ItemMeta explosionData = explosion.getItemMeta();
				explosionData.setDisplayName("§a§lCreeper explosion");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§2Right click this on a block or on a mob to explode!");
				explosionData.setLore(lore);
				explosion.setItemMeta(explosionData);
				player.getInventory().addItem(explosion);
			}
			else if (type.equals(EntityType.WITHER))
			{
				ItemStack explosion = new ItemStack(Material.STICK);
				ItemMeta explosionData = explosion.getItemMeta();
				explosionData.setDisplayName("§9§lLaunch wither skull");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§dLeft click this on air or on a block to launch a witherskull!");
				explosionData.setLore(lore);
				explosion.setItemMeta(explosionData);
				player.getInventory().addItem(explosion);
			}
			else if (type.equals(EntityType.BLAZE))
			{
				ItemStack explosion = new ItemStack(Material.STICK);
				ItemMeta explosionData = explosion.getItemMeta();
				explosionData.setDisplayName("§4§lLaunch fireball");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§cLeft click this on air or on a block to launch a fireball!");
				explosionData.setLore(lore);
				explosion.setItemMeta(explosionData);
				player.getInventory().addItem(explosion);
			}
			else if (type.equals(EntityType.SNOWMAN))
			{
				ItemStack explosion = new ItemStack(Material.STICK);
				ItemMeta explosionData = explosion.getItemMeta();
				explosionData.setDisplayName("§7§lLaunch snowball");
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§fLeft click this on air or on a block to launch a snowball!");
				explosionData.setLore(lore);
				explosion.setItemMeta(explosionData);
				player.getInventory().addItem(explosion);
			}
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void affectMobInitialization(Player player, EntityType type)
	{
		try
		{
			if (type.equals(EntityType.WITHER))
			{
				player.getWorld().createExplosion(player.getLocation(), 7.0F);
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void removeDisguise(Disguise disguise)
	{
		disguises.remove(disguise);
	}
	
	public Disguise getDisguise(Player player)
	{
		for (Disguise disguise: getDisguises())
		{
			if (disguise.getPlayer().equals(player) && disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
			{
				return disguise;
			}
		}
		
		return null;
	}
	
	public Disguise getDisguise(LivingEntity entity)
	{
		for (Disguise disguise: getDisguises())
		{
			if (disguise.getEntity().equals(entity) && disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
			{
				return disguise;
			}
		}
		
		return null;
	}
	
	public void undisguisePlayer(Player player)
	{
		try
		{
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (player != p)
				{
					p.showPlayer(player);
				}
			}
			
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.setMaxHealth(20);
			player.setFlying(false);
			player.setAllowFlight(false);
			
			if (hasDisguise(player))
			{
				Disguise disguise = getDisguise(player);
				
				if (disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
				{
					if (!disguise.getEntity().isDead())
					{
						disguise.getEntity().setHealth(0);
					}
					
					player.getInventory().clear();
					Inventory i = InventoryUtils.StringToInventory(disguise.getInventory());
					player.getInventory().setContents(i.getContents());
					removeDisguise(disguise);
				}
			}
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void updateDisguise(Disguise disguise)
	{
		try
		{
			Player player = disguise.getPlayer();
			LivingEntity entity = disguise.getEntity();
			EntityType type = disguise.getEntityType();
			
			if (entity.isDead())
			{
				undisguisePlayer(player);
				return;
			}
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (player != p)
				{
					p.hidePlayer(player);
				}
			}
			
			NoAI.setAiEnabled(entity, false);
			player.setFoodLevel(20);	
			entity.setCanPickupItems(player.getCanPickupItems());
			entity.setFallDistance(player.getFallDistance());
			entity.setLastDamage(player.getLastDamage());
			entity.setLastDamageCause(player.getLastDamageCause());
			entity.setVelocity(player.getVelocity());
			entity.setRemoveWhenFarAway(false);
			player.setMaximumAir(entity.getMaximumAir());
			player.setMaximumNoDamageTicks(entity.getMaximumNoDamageTicks());
			entity.teleport(player.getLocation());
			entity.teleport(player);
			player.setFireTicks(entity.getFireTicks());
			entity.setFireTicks(player.getFireTicks());
			entity.getLocation().setX(player.getLocation().getX());
			entity.getLocation().setY(player.getLocation().getY());
			entity.getLocation().setZ(player.getLocation().getZ());
			entity.getLocation().setYaw(player.getLocation().getYaw());
			entity.getLocation().setPitch(player.getLocation().getPitch());
			entity.getLocation().setWorld(player.getLocation().getWorld());
			entity.getLocation().setDirection(player.getLocation().getDirection());
			
			if (type.equals(EntityType.BAT) || type.equals(EntityType.BLAZE) || type.equals(EntityType.ENDER_DRAGON) || type.equals(EntityType.WITHER))
			{
				player.setFlying(true);
				player.setAllowFlight(true);
			}
			else if (player.getGameMode().equals(GameMode.CREATIVE))
			{
				player.setFlying(true);
				player.setAllowFlight(true);
			}
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void updateDisguises()
	{
		for (Disguise disguise: getDisguises())
		{
			if (disguise != null && disguise.getEntity() != null && disguise.getPlayer() != null && disguise.getEntityType() != null && disguise.getInventory() != null)
			{
				try
				{
					updateDisguise(disguise);
				}
				catch (Exception ex)
				{
					
				}
			}
			else
			{
				if (disguise.getPlayer() != null)
				{
					Player player = disguise.getPlayer();
					undisguisePlayer(player);
				}
			}
		}
	}
}