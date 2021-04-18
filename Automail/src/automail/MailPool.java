package automail;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public class MailPool {

	private class Item {
		int destination;
		boolean priority;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			priority = false;
			destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
		}
	}

	// Default comparator
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}

	// Prior comparator
	public class ItemComparatorPriority implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {

			int order = 0;

			if (i1.priority && !i2.priority) {
				order = -1;
			} else if (!i1.priority && i2.priority) {
				order = 1;
			} else {
				if (i1.destination < i2.destination) {
					order = 1;
				} else if (i1.destination > i2.destination) {
					order = -1;
				}
			}
			return order;
		}
	}
	
	private LinkedList<Item> pool;
	private LinkedList<Robot> robots;
	private Charger centralCharger;
	private double chargeThreshold;

	public MailPool(int nrobots, double chargeThreshold){
		// Start empty
		pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		this.chargeThreshold = chargeThreshold;

		// The new feature would be applied
		this.centralCharger = new Charger();
	}

	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
	public void addToPool(MailItem mailItem) {

		Item item = new Item(mailItem);

		double estimateCharge = centralCharger.initialCharge(mailItem);
		if (estimateCharge >= chargeThreshold) {
			item.priority = true;
		}

		pool.add(item);

		// Sorted by different comparators depending on chargeThreshold
		if (chargeThreshold > 0){
			pool.sort(new ItemComparatorPriority());
		} else {
			pool.sort(new ItemComparator());
		}
	}
	
	
	
	/**
     * load up any waiting robots with mailItems, if any.
     */
	public void loadItemsToRobot() throws ItemTooHeavyException {
		//List available robots
		ListIterator<Robot> i = robots.listIterator();
		while (i.hasNext()) loadItem(i);
	}
	
	//load items to the robot
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		Robot robot = i.next();
		assert(robot.isEmpty());
		// System.out.printf("P: %3d%n", pool.size());
		ListIterator<Item> j = pool.listIterator();
		if (pool.size() > 0) {
			try {
			robot.addToHand(j.next().mailItem); // hand first as we want higher priority delivered first
			j.remove();
			if (pool.size() > 0) {
				robot.addToTube(j.next().mailItem);
				j.remove();
			}
			robot.dispatch(); // send the robot off if it has any items to deliver
			i.remove();       // remove from mailPool queue
			} catch (Exception e) { 
	            throw e; 
	        } 
		}
	}

	/**
     * @param robot refers to a robot which has arrived back ready for more mailItems to deliver
     */	
	public void registerWaiting(Robot robot) { // assumes won't be there already
		robots.add(robot);
	}

	public Charger getCharger() {
		return centralCharger;
	}
}
