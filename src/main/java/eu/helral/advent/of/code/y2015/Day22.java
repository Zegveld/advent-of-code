package eu.helral.advent.of.code.y2015;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;

import eu.helral.advent.of.code.template.DayTemplate;

public class Day22 extends DayTemplate {

	/**
	 * <pre>
	 * --- Day 22: Wizard Simulator 20XX ---
	 * Little Henry Case decides that defeating bosses with swords and stuff is boring. Now he's playing
	 * the game with a wizard. Of course, he gets stuck on another boss and needs your help again.
	 *
	 * In this version, combat still proceeds with the player and the boss taking alternating turns. The
	 * player still goes first. Now, however, you don't get any equipment; instead, you must choose one
	 * of your spells to cast. The first character at or below 0 hit points loses.
	 *
	 * Since you're a wizard, you don't get to wear armor, and you can't attack normally. However, since
	 * you do magic damage, your opponent's armor is ignored, and so the boss effectively has zero armor
	 * as well. As before, if armor (from a spell, in this case) would reduce damage below 1, it becomes 1
	 * instead - that is, the boss' attacks always deal at least 1 damage.
	 *
	 * On each of your turns, you must select one of your spells to cast. If you cannot afford to cast any
	 * spell, you lose. Spells cost mana; you start with 500 mana, but have no maximum limit. You must have
	 * enough mana to cast a spell, and its cost is immediately deducted when you cast it. Your spells are
	 * Magic Missile, Drain, Shield, Poison, and Recharge.
	 *
	 * Magic Missile costs 53 mana. It instantly does 4 damage.
	 * Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
	 * Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is
	 * increased by 7.
	 * Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it
	 * is active, it deals the boss 3 damage.
	 * Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it
	 * is active, it gives you 101 new mana.
	 * Effects all work the same way. Effects apply at the start of both the player's turns and the boss' turns.
	 * Effects are created with a timer (the number of turns they last); at the start of each turn, after they
	 * apply any effect they have, their timer is decreased by one. If this decreases the timer to zero, the
	 * effect ends. You cannot cast a spell that would start an effect which is already active. However, effects
	 * can be started on the same turn they end.
	 *
	 * For example, suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage:
	 *
	 * -- Player turn --
	 * - Player has 10 hit points, 0 armor, 250 mana
	 * - Boss has 13 hit points
	 * Player casts Poison.
	 *
	 * -- Boss turn --
	 * - Player has 10 hit points, 0 armor, 77 mana
	 * - Boss has 13 hit points
	 * Poison deals 3 damage; its timer is now 5.
	 * Boss attacks for 8 damage.
	 *
	 * -- Player turn --
	 * - Player has 2 hit points, 0 armor, 77 mana
	 * - Boss has 10 hit points
	 * Poison deals 3 damage; its timer is now 4.
	 * Player casts Magic Missile, dealing 4 damage.
	 *
	 * -- Boss turn --
	 * - Player has 2 hit points, 0 armor, 24 mana
	 * - Boss has 3 hit points
	 * Poison deals 3 damage. This kills the boss, and the player wins.
	 *
	 * Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
	 *
	 * -- Player turn --
	 * - Player has 10 hit points, 0 armor, 250 mana
	 * - Boss has 14 hit points
	 * Player casts Recharge.
	 *
	 * -- Boss turn --
	 * - Player has 10 hit points, 0 armor, 21 mana
	 * - Boss has 14 hit points
	 * Recharge provides 101 mana; its timer is now 4.
	 * Boss attacks for 8 damage!
	 *
	 * -- Player turn --
	 * - Player has 2 hit points, 0 armor, 122 mana
	 * - Boss has 14 hit points
	 * Recharge provides 101 mana; its timer is now 3.
	 * Player casts Shield, increasing armor by 7.
	 *
	 * -- Boss turn --
	 * - Player has 2 hit points, 7 armor, 110 mana
	 * - Boss has 14 hit points
	 * Shield's timer is now 5.
	 * Recharge provides 101 mana; its timer is now 2.
	 * Boss attacks for 8 - 7 = 1 damage!
	 *
	 * -- Player turn --
	 * - Player has 1 hit point, 7 armor, 211 mana
	 * - Boss has 14 hit points
	 * Shield's timer is now 4.
	 * Recharge provides 101 mana; its timer is now 1.
	 * Player casts Drain, dealing 2 damage, and healing 2 hit points.
	 *
	 * -- Boss turn --
	 * - Player has 3 hit points, 7 armor, 239 mana
	 * - Boss has 12 hit points
	 * Shield's timer is now 3.
	 * Recharge provides 101 mana; its timer is now 0.
	 * Recharge wears off.
	 * Boss attacks for 8 - 7 = 1 damage!
	 *
	 * -- Player turn --
	 * - Player has 2 hit points, 7 armor, 340 mana
	 * - Boss has 12 hit points
	 * Shield's timer is now 2.
	 * Player casts Poison.
	 *
	 * -- Boss turn --
	 * - Player has 2 hit points, 7 armor, 167 mana
	 * - Boss has 12 hit points
	 * Shield's timer is now 1.
	 * Poison deals 3 damage; its timer is now 5.
	 * Boss attacks for 8 - 7 = 1 damage!
	 *
	 * -- Player turn --
	 * - Player has 1 hit point, 7 armor, 167 mana
	 * - Boss has 9 hit points
	 * Shield's timer is now 0.
	 * Shield wears off, decreasing armor by 7.
	 * Poison deals 3 damage; its timer is now 4.
	 * Player casts Magic Missile, dealing 4 damage.
	 *
	 * -- Boss turn --
	 * - Player has 1 hit point, 0 armor, 114 mana
	 * - Boss has 2 hit points
	 * Poison deals 3 damage. This kills the boss, and the player wins.
	 *
	 * You start with 50 hit points and 500 mana points. The boss's actual stats are in your puzzle input.
	 * What is the least amount of mana you can spend and still win the fight? (Do not include mana recharge
	 * effects as "spending" negative mana.)
	 *
	 * puzzle input:
	 * Hit Points: 71
	 * Damage: 10
	 * </pre>
	 */
	List<Predicate<Combat>> playerActions = List.of(Combat::castDrain, Combat::castMagicMissile, Combat::castPoison,
			Combat::castRecharge, Combat::castShield);

	public int part1() {
		TreeSet<Combat> simulations = simulateCombatForLeastAmountOfMana(new Combat(false));
		return simulations.first().getManaSpent();
	}

	private TreeSet<Combat> simulateCombatForLeastAmountOfMana(Combat initialCombat) {
		TreeSet<Combat> simulations = new TreeSet<>();
		simulations.add(initialCombat);
		while (!simulations.first().playerWon()) {
			Combat combat = simulations.pollFirst();
			for (Predicate<Combat> action : playerActions) {
				Combat nextCombatRound = combat.turn(action);
				if (nextCombatRound == null) {
					continue;
				}
				if (!nextCombatRound.playerLost()) {
					simulations.add(nextCombatRound);
				}
			}
		}
		return simulations;
	}

	private class Combat implements Comparable<Combat> {
		private int mana = 500;
		private int playerHp = 50;
		private int bossHp = 71;
		private final int bossDamage = 10;
		private int poisonActive = 0;
		private int shieldActive = 0;
		private int rechargeActive = 0;
		private int manaSpent = 0;
		private final List<String> log;
		private final String combatCode;
		private final boolean hardMode;

		public Combat(boolean hardMode) {
			this.hardMode = hardMode;
			log = new ArrayList<>();
			log.add("-- Player turn --");
			log.add(getPlayerStats());
			log.add(getBossStats());
			combatCode = "";
		}

		private Combat(Combat combat, Predicate<Combat> action) {
			hardMode = combat.hardMode;
			mana = combat.mana;
			playerHp = combat.playerHp;
			bossHp = combat.bossHp;
			poisonActive = combat.poisonActive;
			shieldActive = combat.shieldActive;
			rechargeActive = combat.rechargeActive;
			manaSpent = combat.manaSpent;
			log = new ArrayList<>(combat.log);
			combatCode = combat.combatCode + playerActions.indexOf(action);
		}

		Combat turn(Predicate<Combat> action) {
			Combat combat = new Combat(this, action);
			if (!action.test(combat)) {
				return null;
			}
			combat.nonPlayerEvents();
			return combat;
		}

		private void nonPlayerEvents() {
			if (!isFinished()) {
				log.add("");
				log.add("-- Boss turn --");
				log.add(getPlayerStats());
				log.add(getBossStats());
				triggerEffects();
			}
			if (!isFinished()) {
				bossTurn();
			}
			if (!isFinished()) {
				log.add("");
				log.add("-- Player turn --");
				log.add(getPlayerStats());
				log.add(getBossStats());
				triggerEffects();
				if (hardMode) {
					log.add("Hard mode: you lose 1 hp.");
					playerHp -= 1;
				}
			}
		}

		private String getBossStats() {
			return String.format("- Boss has %d hit points", bossHp);
		}

		private String getPlayerStats() {
			return String.format("- Player has %d hit points, %d armor, %d mana", playerHp, shieldActive > 0 ? 7 : 0,
					mana);
		}

		public int getManaSpent() {
			return manaSpent;
		}

		public boolean isFinished() {
			return playerHp <= 0 || bossHp <= 0;
		}

		public boolean playerWon() {
			return bossHp <= 0;
		}

		public boolean playerLost() {
			return playerHp <= 0;
		}

		void bossTurn() {
			if (bossHp < 0) {
				return;
			}
			int armor = shieldActive > 0 ? 7 : 0;
			int damage = bossDamage - armor;
			playerHp -= damage;
			log.add(String.format("Boss attacks for %d - %d = %d damage", bossDamage, armor, damage));
		}

		boolean castMagicMissile() {
			if (mana < 53) {
				return false;
			}
			spendMana(53);
			bossHp -= 4;
			log.add("Player casts Magic Missile, dealing 4 damage.");
			return true;
		}

		boolean castDrain() {
			if (mana < 73) {
				return false;
			}
			spendMana(73);
			bossHp -= 2;
			playerHp += 2;
			log.add("Player casts Drain, dealing 2 damage, and healing 2 hit points.");
			return true;
		}

		boolean castPoison() {
			if (mana < 173 || poisonActive > 0) {
				return false;
			}
			spendMana(173);
			poisonActive = 6;
			log.add("Player casts Poison.");
			return true;
		}

		boolean castShield() {
			if (mana < 113 || shieldActive > 0) {
				return false;
			}
			spendMana(113);
			shieldActive = 6;
			log.add("Player casts Shield, increasing armor by 7.");
			return true;
		}

		boolean castRecharge() {
			if (mana < 229 || rechargeActive > 0) {
				return false;
			}
			spendMana(229);
			rechargeActive = 5;
			log.add("Player casts Recharge.");
			return true;
		}

		private void triggerEffects() {
			if (poisonActive > 0) {
				bossHp -= 3;
				poisonActive -= 1;
				log.add(String.format("Poison deals 3 damage; its timer is now %d.", poisonActive));
			}
			if (shieldActive > 0) {
				shieldActive -= 1;
				log.add(String.format("Shield's timer is now %d.", shieldActive));
			}
			if (rechargeActive > 0) {
				mana += 101;
				rechargeActive -= 1;
				log.add(String.format("Recharge provides 101 mana; its timer is now %d.", rechargeActive));
			}
		}

		private void spendMana(int value) {
			mana -= value;
			manaSpent += value;
		}

		@Override
		public int compareTo(Combat o) {
			int difference = manaSpent - o.manaSpent;
			if (difference == 0) {
				return combatCode.compareTo(o.combatCode);
			}
			return difference;
		}
	}

	/**
	 * <pre>
	 * --- Part Two ---
	 * On the next run through the game, you increase the difficulty to hard.
	 *
	 * At the start of each player turn (before any other effects apply), you lose 1 hit point. If this brings you to or below 0 hit points, you lose.
	 *
	 * With the same starting stats for you and the boss, what is the least amount of mana you can spend and still win the fight?
	 *
	 * </pre>
	 */
	public int part2() {
		TreeSet<Combat> simulations = simulateCombatForLeastAmountOfMana(new Combat(true));
		return simulations.first().getManaSpent();
	}
}
