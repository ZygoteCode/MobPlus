package it.zygotecode.mobplus.disguise;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Disguise
{
	private Player player;
	private LivingEntity entity;
	private EntityType entityType;
	private String inventory;
	private double oldHealth;
	private DisguiseType disguiseType;
	
	public Disguise(Player player, LivingEntity entity, EntityType entityType, String inventory, double oldHealth, DisguiseType disguiseType)
	{
		this.player = player;
		this.entity = entity;
		this.entityType = entityType;
		this.inventory = inventory;
		this.oldHealth = oldHealth;
		this.disguiseType = disguiseType;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public LivingEntity getEntity()
	{
		return entity;
	}

	public void setEntity(LivingEntity entity)
	{
		this.entity = entity;
	}

	public EntityType getEntityType()
	{
		return entityType;
	}

	public void setEntityType(EntityType entityType)
	{
		this.entityType = entityType;
	}

	public String getInventory()
	{
		return inventory;
	}

	public void setInventory(String inventory)
	{
		this.inventory = inventory;
	}

	public double getOldHealth()
	{
		return oldHealth;
	}

	public void setOldHealth(double oldHealth)
	{
		this.oldHealth = oldHealth;
	}

	public DisguiseType getDisguiseType()
	{
		return disguiseType;
	}

	public void setDisguiseType(DisguiseType disguiseType)
	{
		this.disguiseType = disguiseType;
	}
}