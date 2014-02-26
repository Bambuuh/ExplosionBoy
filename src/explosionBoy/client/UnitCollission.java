package explosionBoy.client;

import org.newdawn.slick.geom.Rectangle;

public class UnitCollission {
	
	//Takes in two rectangles to check if the are colliding with each other.
	public static boolean isColliding(Rectangle rect1, Rectangle rect2){
		return (rect1.intersects(rect2));
	}

}
