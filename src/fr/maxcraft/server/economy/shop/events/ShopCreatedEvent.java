package fr.maxcraft.server.economy.shop.events;

import fr.maxcraft.player.User;
import fr.maxcraft.server.economy.shop.Shop;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;



public class ShopCreatedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    
    private Shop shop;
    private User user;
 
    public ShopCreatedEvent(Shop shop, User u) {
    	this.shop = shop;
    	this.user = u;
    }
 




	public Shop getShop() {
		return shop;
	}


	public void setShop(Shop shop) {
		this.shop = shop;
	}




	public User getUser() {
		return user;
	}





	public void setUser(User user) {
		this.user = user;
	}





	public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}