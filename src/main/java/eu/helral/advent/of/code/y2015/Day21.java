package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day21 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 21: RPG Simulator 20XX ---
	 * Little Henry Case got a new video game for Christmas. It's an RPG, and he's stuck on a boss. He needs to know what equipment to buy at the shop.
	 * He hands you the controller.
	 *
	 * In this game, the player (you) and the enemy (the boss) take turns attacking. The player always goes first. Each attack reduces the opponent's
	 * hit points by at least 1. The first character at or below 0 hit points loses.
	 *
	 * Damage dealt by an attacker each turn is equal to the attacker's damage score minus the defender's armor score. An attacker always does at least
	 * 1 damage. So, if the attacker has a damage score of 8, and the defender has an armor score of 3, the defender loses 5 hit points. If the defender
	 * had an armor score of 300, the defender would still lose 1 hit point.
	 *
	 * Your damage score and armor score both start at zero. They can be increased by buying items in exchange for gold. You start with no items and have
	 * as much gold as you need. Your total damage or armor is equal to the sum of those stats from all of your items. You have 100 hit points.
	 *
	 * Here is what the item shop is selling:
	 *
	 * Weapons:    Cost  Damage  Armor
	 * Dagger        8     4       0
	 * Shortsword   10     5       0
	 * Warhammer    25     6       0
	 * Longsword    40     7       0
	 * Greataxe     74     8       0
	 * </pre>
	 */
	List<Item> weapons = List.of(//
			new Item("Dagger", 8, 4, 0), //
			new Item("ShortSwod", 10, 5, 0), //
			new Item("Warhammer", 25, 6, 0), //
			new Item("Longsword", 40, 7, 0), //
			new Item("Greataxe", 74, 8, 0));

	/**
	 * <pre>
	 * Armor:      Cost  Damage  Armor
	 * Leather      13     0       1
	 * Chainmail    31     0       2
	 * Splintmail   53     0       3
	 * Bandedmail   75     0       4
	 * Platemail   102     0       5
	 * </pre>
	 */
	List<Item> armors = List.of(//
			new Item("None", 0, 0, 0), //
			new Item("Leather", 13, 0, 1), //
			new Item("Chainmail", 31, 0, 2), //
			new Item("Splintmail", 53, 0, 3), //
			new Item("Bandedmail", 75, 0, 4), //
			new Item("Platemail", 102, 0, 5));

	/**
	 * <pre>
	 *
	 * Rings:      Cost  Damage  Armor
	 * Damage +1    25     1       0
	 * Damage +2    50     2       0
	 * Damage +3   100     3       0
	 * Defense +1   20     0       1
	 * Defense +2   40     0       2
	 * Defense +3   80     0       3
	 * </pre>
	 */
	List<Item> rings = List.of(//
			new Item("None1", 0, 0, 0), //
			new Item("None2", 0, 0, 0), //
			new Item("Damage +1", 25, 1, 0), //
			new Item("Damage +2", 50, 2, 0), //
			new Item("Damage +3", 100, 3, 0), //
			new Item("Defense +1", 20, 0, 1), //
			new Item("Defense +2", 40, 0, 2), //
			new Item("Defense +3", 80, 0, 3));

	/**
	 * <pre>
	 * You must buy exactly one weapon; no dual-wielding. Armor is optional, but you can't use more than one. You can buy 0-2 rings (at most one for
	 * each hand). You must use any items you buy. The shop only has one of each item, so you can't buy, for example, two rings of Damage +3.
	 *
	 * For example, suppose you have 8 hit points, 5 damage, and 5 armor, and that the boss has 12 hit points, 7 damage, and 2 armor:
	 *
	 * The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
	 * The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
	 * The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
	 * The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
	 * The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
	 * The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
	 * The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.
	 * In this scenario, the player wins! (Barely.)
	 *
	 * You have 100 hit points. The boss's actual stats are in your puzzle input. What is the least amount of gold you can spend and still win the fight?
	 *
	 * puzzle input:
	 * Hit Points: 100
	 * Damage: 8
	 * Armor: 2
	 * </pre>
	 */
	public int part1() {
		List<Player> allVariations = getAllVariations();
		allVariations.sort(Comparator.comparing(Player::gearValue));
		for (Player player : allVariations) {
			if (playerWinsCombat(player)) {
				return player.gearValue();
			}
		}
		return -1;
	}

	private List<Player> getAllVariations() {
		List<Player> allVariations = new ArrayList<>();
		for (Item weapon : weapons) {
			for (Item armor : armors) {
				for (Item ring1 : rings) {
					for (Item ring2 : rings) {
						if (rings.indexOf(ring1) <= rings.indexOf(ring2)) {
							continue;
						}
						allVariations.add(new Player(weapon, armor, ring1, ring2));
					}
				}
			}
		}
		return allVariations;
	}

	private boolean playerWinsCombat(Player player) {
		int hpBoss = 100;
		int bossDamage = 8;
		int bossArmor = 2;
		int hpPlayer = 100;
		boolean playerTurn = true;
		while (hpPlayer > 0 && hpBoss > 0) {
			if (playerTurn) {
				int damage = player.damageValue() - bossArmor;
				if (damage <= 0) {
					damage = 1;
				}
				hpBoss -= damage;
			} else {
				int damage = bossDamage - player.armorValue();
				if (damage <= 0) {
					damage = 1;
				}
				hpPlayer -= damage;
			}
			playerTurn = !playerTurn;
		}
		return hpPlayer > 0;
	}

	private record Item(String name, int cost, int damage, int armor) {
	}

	private record Player(Item weapon, Item armor, Item ring1, Item ring2) {
		int gearValue() {
			return weapon.cost() + armor.cost() + ring1.cost() + ring2.cost();
		}

		int damageValue() {
			return weapon.damage() + armor.damage() + ring1.damage() + ring2.damage();
		}

		int armorValue() {
			return weapon.armor() + armor.armor() + ring1.armor() + ring2.armor();
		}
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * Turns out the shopkeeper is working with the boss, and can persuade you to
	 * buy whatever items he wants. The other rules still apply, and he still only
	 * has one of each item.
	 *
	 * What is the most amount of gold you can spend and still lose the fight?
	 * </pre>
	 */
	public int part2() {
		List<Player> allVariations = getAllVariations();
		allVariations.sort(Comparator.comparing(Player::gearValue, Comparator.reverseOrder()));
		for (Player player : allVariations) {
			if (!playerWinsCombat(player)) {
				return player.gearValue();
			}
		}
		return -1;
	}
}
