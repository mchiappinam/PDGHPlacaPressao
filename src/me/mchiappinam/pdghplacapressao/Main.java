package me.mchiappinam.pdghplacapressao;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import me.mchiappinam.pdghplacapressao.FireworkEffectPlayer;

public class Main extends JavaPlugin implements Listener {
	
	ArrayList<String> falldmg = new ArrayList();

	FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.YELLOW).with(Type.STAR).trail(false).build();
	FireworkEffect ffEffect = FireworkEffect.builder().flicker(false).withColor(Color.AQUA).withFade(Color.RED).with(Type.CREEPER).trail(false).build();
	
	
	  public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		File file = new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
				saveResource("config_template.yml",false);
				File file2 = new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			}
			catch(Exception e) {}
		}
		getServer().getConsoleSender().sendMessage("§3[PDGHPlacaPressao] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHPlacaPressao] §2Acesse: http://pdgh.net/");
	  }
	    
	  public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHPlacaPressao] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHPlacaPressao] §2Acesse: http://pdgh.net/");
	  }

	  @EventHandler
	  public void onPlayerDamage(EntityDamageEvent e) {
	    if ((e.getCause() == EntityDamageEvent.DamageCause.FALL) && ((e.getEntity() instanceof Player))) {
	      Player player = (Player)e.getEntity();
	      if (falldmg.contains(player.getName())) {
	        e.setCancelled(true);
	        falldmg.remove(player.getName());
	      }
	    }
	  }
	  
	  @EventHandler
	  public void onPlayerKick(PlayerKickEvent e) {
	      if (falldmg.contains(e.getPlayer().getName())) {
	        falldmg.remove(e.getPlayer().getName());
	      }
	  }
	  
	  @EventHandler
	  public void onPlayerLeave(PlayerQuitEvent e) {
	      if (falldmg.contains(e.getPlayer().getName())) {
	        falldmg.remove(e.getPlayer().getName());
	      }
	  }
	  
	  @EventHandler
	  public void onPressurePlateStep(PlayerInteractEvent e) {
	    if ((e.getAction().equals(Action.PHYSICAL)) && ((e.getClickedBlock().getType() == Material.STONE_PLATE) || (e.getClickedBlock().getType() == Material.WOOD_PLATE))) {
	      double strength = getConfig().getDouble("strength");
	      double up = getConfig().getDouble("up");
	        Vector v = e.getPlayer().getLocation().getDirection().multiply(strength).setY(up);
	        e.getPlayer().setVelocity(v);
	        
	        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 10.0F, 2.0F);
			try{FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(e.getPlayer().getWorld(), e.getPlayer().getLocation(), fEffect);FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(e.getPlayer().getWorld(), e.getPlayer().getLocation(), ffEffect);}catch(Exception e1){}
	        
	        e.setCancelled(true);
	        falldmg.add(e.getPlayer().getName());
	    }
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	}