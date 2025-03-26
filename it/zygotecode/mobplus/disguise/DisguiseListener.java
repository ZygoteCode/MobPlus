package it.zygotecode.mobplus.disguise;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import it.zygotecode.mobplus.Main;

public class DisguiseListener implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEventDeath(EntityDeathEvent e)
	{
		try
		{
			if (!(e.getEntity() instanceof Player))
			{
				if (Main.getDisguiseManager().hasDisguise((LivingEntity) e.getEntity()))
				{
					Main.getDisguiseManager().undisguisePlayer(Main.getDisguiseManager().getDisguise(e.getEntity()).getPlayer());
				}
			}
			else
			{
				if (Main.getDisguiseManager().hasDisguise((Player) e.getEntity()))
				{
					Main.getDisguiseManager().undisguisePlayer(Main.getDisguiseManager().getDisguise((Player) e.getEntity()).getPlayer());
				}
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		try
		{
			if (Main.getDisguiseManager().hasDisguise(e.getEntity()))
			{
				Main.getDisguiseManager().undisguisePlayer(e.getEntity());
			}
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent e)
	{
		try
		{
			if (Main.getDisguiseManager().hasDisguise(e.getPlayer()))
			{
				Player player = e.getPlayer();
				Disguise disguise = Main.getDisguiseManager().getDisguise(player);
				
				if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
				}
				
				Main.getDisguiseManager().updateDisguise(disguise);
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDamageEntity(EntityDamageByEntityEvent e)
	{
		try
		{
			if (e.getEntity() instanceof LivingEntity && !(e.getEntity() instanceof Player) && e.getDamager() instanceof Player)
			{
				if (Main.getDisguiseManager().hasDisguise((LivingEntity) e.getEntity()) && Main.getDisguiseManager().hasDisguise((Player) e.getDamager()))
				{
					e.setCancelled(true);
				}
			}
			else if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity && !(e.getDamager() instanceof Player))
			{
				if (Main.getDisguiseManager().hasDisguise((LivingEntity) e.getDamager()) && Main.getDisguiseManager().hasDisguise((Player) e.getEntity()))
				{
					e.setCancelled(true);
				}
			}
			
			if (e.getEntity() instanceof LivingEntity && !(e.getEntity() instanceof Player))
			{
				if (Main.getDisguiseManager().hasDisguise((LivingEntity) e.getEntity()))
				{
					Disguise disguise = Main.getDisguiseManager().getDisguise((LivingEntity) e.getEntity());
					Player player = disguise.getPlayer();
					
					if (Main.getDisguiseManager().hasDisguise(player) && (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)))
					{
						e.setCancelled(true);
					}
					else
					{
						player.damage(e.getDamage());
					}
				}
			}
			
			if (e.getEntity() instanceof Player && !e.isCancelled())
			{
				if (Main.getDisguiseManager().hasDisguise((Player) e.getEntity()) && !(e.getDamager().getType().equals(EntityType.WITHER_SKULL)))
				{
					Disguise disguise = Main.getDisguiseManager().getDisguise((Player) e.getEntity());
					disguise.getEntity().damage(e.getDamage());
				}
				else
				{
					((Player) e.getEntity()).damage(e.getDamage());
				}
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRegen(EntityRegainHealthEvent e)
	{
		if (e.getEntity() instanceof Player)
		{
			if (Main.getDisguiseManager().hasDisguise((Player) e.getEntity()))
			{
				e.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent e)
	{
		try
		{
			if (e.getEntity() instanceof LivingEntity && !(e.getEntity() instanceof Player))
			{
				if (Main.getDisguiseManager().hasDisguise((LivingEntity) e.getEntity()))
				{
					Disguise disguise = Main.getDisguiseManager().getDisguise((LivingEntity) e.getEntity());
					Player player = disguise.getPlayer();
					
					if (Main.getDisguiseManager().hasDisguise(player) && (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)))
					{
						e.setCancelled(true);
					}
					else
					{
						player.damage(e.getDamage());
					}
				}
			}
			
			if (e.getEntityType().equals(EntityType.WITHER) && (e.getCause().equals(DamageCause.BLOCK_EXPLOSION) || e.getCause().equals(DamageCause.ENTITY_EXPLOSION)) && Main.getDisguiseManager().hasDisguise((LivingEntity) e.getEntity()))
			{
				e.setCancelled(true);
			}	
			
			if (e.getEntity().equals(EntityType.CHICKEN) && e.getCause().equals(DamageCause.FALL))
			{
				e.setCancelled(true);
			}
			
			if (e.getEntity() instanceof Player && !e.isCancelled())
			{
				if (Main.getDisguiseManager().hasDisguise((Player) e.getEntity()))
				{
					Disguise disguise = Main.getDisguiseManager().getDisguise((Player) e.getEntity());
					disguise.getEntity().damage(e.getDamage());
				}
			}
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent e)
	{
		try
		{
			if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && Main.getDisguiseManager().hasDisguise(e.getPlayer()))
			{
				onRightClickPower(e, null);
			}
			else if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && Main.getDisguiseManager().hasDisguise(e.getPlayer()))
			{
				onLeftClickPower(e);
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityInteract(PlayerInteractEntityEvent e)
	{
		try
		{
			if (Main.getDisguiseManager().hasDisguise(e.getPlayer()))
			{
				onRightClickPower(null, e);
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void onRightClickPower(PlayerInteractEvent e, PlayerInteractEntityEvent e2)
	{
		try
		{
			Player player = null;
			
			if (e != null)
			{
				player = e.getPlayer();
			}
			else if (e2 != null)
			{
				player = e2.getPlayer();
			}
			
			if (player == null)
			{
				return;
			}
			
			EntityType type = Main.getDisguiseManager().getDisguise(player).getEntityType();
			ItemStack item = e.getItem();
			
			if (item.hasItemMeta())
			{
				ItemMeta itemMeta = item.getItemMeta();
				
				if (type.equals(EntityType.CREEPER))
				{
					if (item.getType().equals(Material.STICK) && itemMeta.getDisplayName().equalsIgnoreCase("§a§lCreeper explosion") && itemMeta.getLore().get(0).equals("§2Right click this on a block or on a mob to explode!"))
					{
						player.getWorld().createExplosion(player.getLocation(), 3.0F);
					}
				}
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void onLeftClickPower(PlayerInteractEvent e)
	{
		try
		{
			if (e != null)
			{
				Player player = e.getPlayer();
				
				if (player == null)
				{
					return;
				}
				
				EntityType type = Main.getDisguiseManager().getDisguise(player).getEntityType();
				ItemStack item = e.getItem();
				
				if (item.hasItemMeta())
				{
					ItemMeta itemMeta = item.getItemMeta();
					
					if (type.equals(EntityType.WITHER))
					{
						if (item.getType().equals(Material.STICK) && itemMeta.getDisplayName().equalsIgnoreCase("§9§lLaunch wither skull") && itemMeta.getLore().get(0).equals("§dLeft click this on air or on a block to launch a witherskull!"))
						{
							player.launchProjectile(WitherSkull.class);
						}
					}
					else if (type.equals(EntityType.BLAZE))
					{
						if (item.getType().equals(Material.STICK) && itemMeta.getDisplayName().equalsIgnoreCase("§4§lLaunch fireball") && itemMeta.getLore().get(0).equals("§cLeft click this on air or on a block to launch a fireball!"))
						{
							player.launchProjectile(Fireball.class);
						}
					}
					else if (type.equals(EntityType.SNOWMAN))
					{
						if (item.getType().equals(Material.STICK) && itemMeta.getDisplayName().equalsIgnoreCase("§7§lLaunch snowball") && itemMeta.getLore().get(0).equals("§fLeft click this on air or on a block to launch a snowball!"))
						{
							player.launchProjectile(Snowball.class);
						}
					}
				}
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(EntityExplodeEvent e)
	{
		try
		{
			if (e.getEntityType().equals(EntityType.WITHER_SKULL))
			{
				for (Entity entity2: e.getEntity().getWorld().getEntities())
				{
					if (entity2 instanceof LivingEntity && !(entity2 == e.getEntity()))
					{
						LivingEntity entity = (LivingEntity) entity2;
						
						if (entity.getType().equals(EntityType.WITHER) && e.getEntity().getLocation().distance(entity.getLocation()) <= 5.0D)
						{
							e.setCancelled(true);
							return;
						}
					}
				}
				
				e.setCancelled(true);
				e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 1.0F);
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e)
	{
		try
		{
			onJoinQuitKick(e.getPlayer());
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerKickEvent e)
	{
		try
		{
			onJoinQuitKick(e.getPlayer());
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e)
	{
		try
		{
			onJoinQuitKick(e.getPlayer());
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void onJoinQuitKick(Player player)
	{
		try
		{
			if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
			{
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
			}
			
			for (Player p : Bukkit.getOnlinePlayers())
			{
				if (player != p)
				{
					p.showPlayer(player);
				}
			}
			
			if (Main.getDisguiseManager().hasDisguise(player))
			{
				Main.getDisguiseManager().undisguisePlayer(player);
			}
			
			player.setMaxHealth(20);
		}
		catch (Exception ex)
		{
			
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onTeleport(PlayerTeleportEvent e)
	{
		try
		{
			if (Main.getDisguiseManager().hasDisguise(e.getPlayer()))
			{
				Player player = e.getPlayer();
				Disguise disguise = Main.getDisguiseManager().getDisguise(player);
				
				if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
				}
				
				Main.getDisguiseManager().updateDisguise(disguise);
			}	
		}
		catch (Exception ex)
		{
			
		}
	}
}