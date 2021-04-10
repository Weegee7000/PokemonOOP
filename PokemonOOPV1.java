import java.util.Scanner;
import java.util.Random;

public class PokemonOOPV1 {
    private final String name;
    private final String type;
    private final String move1;
    private final String type1;
    private final int power1;
    private final int acc1;
    private final String move2;
    private final String type2;
    private final int power2;
    private final int acc2;
    private final int health;
    private final int effAtk;
    private final int effDef;
    private final int effSpAtk;
    private final int effSpDef;
    private final int effSpeed;

    public PokemonOOPV1(String n, String t, String m1, String t1, int p1, int a1, String m2, String t2, int p2, int a2, int HP, int Atk, int Def, int SpAtk, int SpDef, int Speed) {
        name = n;
        type = t;
        move1 = m1;
        type1 = t1;
        power1 = p1;
        acc1 = a1;
        move2 = m2;
        type2 = t2;
        power2 = p2;
        acc2 = a2;
        health = (HP * 2) + 110;
        effAtk = (Atk * 2) + 5;
        effDef = (Def * 2) + 5;
        effSpAtk = (SpAtk * 2) + 5;
        effSpDef = (SpDef * 2) + 5;
        effSpeed = (Speed * 2) + 5;
    }

    public String toString() {
        return "(" + name + ", " + type + ", " + move1 + ", " + type1 + ", " + power1 + ", " + acc1 + ", " + move2 + ", " + type2 + ", " + power2 + ", " + acc2 + ", " + health + ")";
    }

    public boolean isPhysical(String move) {
        return move.equalsIgnoreCase("Slash") || move.equalsIgnoreCase("Ice Punch") || move.equalsIgnoreCase("Earthquake");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        PokemonOOPV1 Charizard = new PokemonOOPV1("Charizard", "Fire & Flying", "Flamethrower", "Fire", 90, 255, "Slash", "Normal", 70, 255, 78, 84, 78, 109, 85, 100);
        PokemonOOPV1 Blastoise = new PokemonOOPV1("Blastoise", "Water", "Hydro Pump", "Water", 110, 205, "Ice Punch", "Ice", 75, 255, 79, 83, 100, 85, 105, 78);
        PokemonOOPV1 Venusaur = new PokemonOOPV1("Venusaur", "Grass & Poison", "Energy Ball", "Grass", 90, 255, "Earthquake", "Ground", 100, 255, 80, 82, 83, 100, 100, 80);
        PokemonOOPV1[] pkmn = {Charizard, Blastoise, Venusaur};

        int num1 = (int) (pkmn.length * Math.random());
        PokemonOOPV1 playerMon = pkmn[num1];
        int playerHP = playerMon.health;
        int num2 = (int) (pkmn.length * Math.random());
        PokemonOOPV1 enemyMon = pkmn[num2];
        int enemyHP = enemyMon.health;
        boolean running = true;
        System.out.println("* Welcome to the Pokemon Fighting Simulator! *");
        System.out.println("Press Enter to begin.");
        String i = in.nextLine();
        label:
        while (running) {
            System.out.println("-----------------------------------");
            System.out.println("Your pokemon is " + playerMon.name);
            System.out.println("Your opponent's pokemon is " + enemyMon.name);
            while (enemyHP > 0) {
                int enemyMNo = (int) (2 * Math.random());
                System.out.println("-----------------------------------");
                System.out.println("\tYour HP: " + playerHP);
                System.out.println("\tEnemy HP: " + enemyHP);
                System.out.println("> What move would you like to use?");
                System.out.println("\t1) " + playerMon.move1);
                System.out.println("\t2) " + playerMon.move2);
                String input = in.nextLine();

                int playerPower;
                int playerAcc;
                int playerM = 1;  //will work on modifiers later (stab, types, etc.)
                int dmgDealt;
                String enemyMove = "";
                int enemyPower = 0;
                int enemyAcc = 0;
                int enemyM = 1;
                int dmgTaken;

                switch (input) {
                    case "1":
                        playerPower = playerMon.power1;
                        playerAcc = playerMon.acc1;

                        if (enemyMNo == 0) {
                            enemyMove = enemyMon.move1;
                            enemyPower = enemyMon.power1;
                            enemyAcc = enemyMon.acc1;
                        }
                        else if (enemyMNo == 1) {
                            enemyMove = enemyMon.move2;
                            enemyPower = enemyMon.power2;
                            enemyAcc = enemyMon.acc2;
                        }

                        if (playerMon.isPhysical(playerMon.move1)) {
                            dmgDealt = (int) (((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effAtk / enemyMon.effDef)) + 2) * playerM;
                        }
                        else {
                            dmgDealt = (int) (((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effSpAtk / enemyMon.effSpDef)) + 2) * playerM;
                        }

                        if (enemyMon.isPhysical(enemyMove)) {
                            dmgTaken = (int) (((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effAtk / playerMon.effDef)) + 2) * enemyM;
                        }
                        else {
                            dmgTaken = (int) (((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effSpAtk / playerMon.effSpDef)) + 2) * enemyM;
                        }

                        if (playerMon.effSpeed >= enemyMon.effSpeed) {   // Player is faster
                            System.out.println("> Your " + playerMon.name + " used " + playerMon.move1 + ".");
                            if (rand.nextInt(256) < playerMon.acc1) {    // Player's attack lands
                                enemyHP -= dmgDealt;
                                System.out.println("Your " + playerMon.name + " hit the enemy " + enemyMon.name + " for " + dmgDealt + ".");
                                if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                    System.out.println("The enemy " + enemyMon.name + " fainted.");
                                    break;
                                }
                                else {                      // Enemy survives attack
                                    System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                                    if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                        playerHP -= dmgTaken;
                                        System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                        if (playerHP < 1) {                 // You die
                                            System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                            break label;
                                        }
                                    }
                                    else {                                  // Enemy misses
                                        System.out.println("The enemy " + enemyMon.name + "'s " + enemyMove + " missed!");
                                    }
                                }

                            }
                            else {              // player misses
                                System.out.println(playerMon.name + "'s attack missed!");
                                System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                                if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                    playerHP -= dmgTaken;
                                    System.out.println("Your " + playerMon.name + "takes " + dmgTaken + " from " + enemyMon.name + ".");
                                    if (playerHP < 1) {                 // You die
                                        System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                        break label;
                                    }
                                }
                                else {                                  // Enemy misses
                                    System.out.println("The enemy " + enemyMon.name + "'s " + enemyMove + " missed!");
                                }
                            }
                        }
                        else {     // Enemy is faster
                            System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                            if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                playerHP -= dmgTaken;
                                System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                if (playerHP < 1) {                 // You die
                                    System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                    break label;
                                }
                                else {                  // You survive the attack
                                    System.out.println("t> Your " + playerMon.name + " used " + playerMon.move1 + ".");
                                    if (rand.nextInt(256) < playerMon.acc1) {    // Player's attack lands
                                        enemyHP -= dmgDealt;
                                        System.out.println("Your " + playerMon.name + " hit the enemy " + enemyMon.name + " for " + dmgDealt + ".");
                                        if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                            System.out.println("The enemy " + enemyMon.name + " fainted.");
                                            break;
                                        }
                                    }
                                    else {           // Player misses
                                        System.out.println(playerMon.name + "'s attack missed!");
                                    }
                                }
                            }
                            else {                                  // Enemy misses
                                System.out.println("The enemy " + enemyMon.name + "'s " + enemyMove + " missed!");
                                System.out.println("> Your " + playerMon.name + " used " + playerMon.move1 + ".");
                                if (rand.nextInt(256) < playerMon.acc1) {    // Player's attack lands
                                    enemyHP -= dmgDealt;
                                    System.out.println("Your " + playerMon.name + " hit " + enemyMon.name + " for " + dmgDealt + ".");
                                    if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                        System.out.println("The enemy " + enemyMon.name + " fainted.");
                                        break;
                                    }
                                } else {           // Player misses
                                    System.out.println("Your " + playerMon.name + "'s attack missed!");
                                }
                            }
                        }
                        break;
                    case "2":
                        playerPower = playerMon.power2;
                        playerAcc = playerMon.acc2;

                        if (enemyMNo == 0) {
                            enemyMove = enemyMon.move1;
                            enemyPower = enemyMon.power1;
                            enemyAcc = enemyMon.acc1;
                        }
                        else if (enemyMNo == 1) {
                            enemyMove = enemyMon.move2;
                            enemyPower = enemyMon.power2;
                            enemyAcc = enemyMon.acc2;
                        }

                        if (playerMon.isPhysical(playerMon.move2)) {
                            dmgDealt = (int) (((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effAtk / enemyMon.effDef)) + 2) * playerM;
                        }
                        else {
                            dmgDealt = (int) (((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effSpAtk / enemyMon.effSpDef)) + 2) * playerM;
                        }

                        if (enemyMon.isPhysical(enemyMove)) {
                            dmgTaken = (int) (((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effAtk / playerMon.effDef)) + 2) * enemyM;
                        }
                        else {
                            dmgTaken = (int) (((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effSpAtk / playerMon.effSpDef)) + 2) * enemyM;
                        }

                        if (playerMon.effSpeed >= enemyMon.effSpeed) {   // Player is faster
                            System.out.println("> Your " + playerMon.name + " used " + playerMon.move2 + ".");
                            if (rand.nextInt(256) < playerMon.acc2) {    // Player's attack lands
                                enemyHP -= dmgDealt;
                                System.out.println("Your " + playerMon.name + " hit " + enemyMon.name + " for " + dmgDealt + ".");
                                if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                    System.out.println("The enemy " + enemyMon.name + " fainted.");
                                    break;
                                }
                                else {                      // Enemy survives attack
                                    System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                                    if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                        playerHP -= dmgTaken;
                                        System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                        if (playerHP < 1) {                 // You die
                                            System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                            break label;
                                        }
                                    }
                                    else {                                  // Enemy misses
                                        System.out.println("The enemy " + enemyMon.name + "'s " + enemyMove + " missed!");
                                    }
                                }

                            }
                            else {              // player misses
                                System.out.println("Your " + playerMon.name + "'s attack missed!");
                                System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                                if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                    playerHP -= dmgTaken;
                                    System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                    if (playerHP < 1) {                 // You die
                                        System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                        break label;
                                    }
                                }
                                else {                                  // Enemy misses
                                    System.out.println("The enemy " + enemyMon.name + "'s " + enemyMove + " missed!");
                                }
                            }
                        }
                        else {     // Enemy is faster
                            System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                            if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                playerHP -= dmgTaken;
                                System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                if (playerHP < 1) {                 // You die
                                    System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                    break label;
                                }
                                else {                  // You survive the attack
                                    System.out.println("> Your " + playerMon.name + " used " + playerMon.move2 + ".");
                                    if (rand.nextInt(256) < playerMon.acc2) {    // Player's attack lands
                                        enemyHP -= dmgDealt;
                                        System.out.println("Your " + playerMon.name + " hit the enemy " + enemyMon.name + " for " + dmgDealt + ".");
                                        if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                            System.out.println("The enemy " + enemyMon.name + " fainted.");
                                            break;
                                        }
                                    }
                                    else {           // Player misses
                                        System.out.println("Your " + playerMon.name + "'s attack missed!");
                                    }
                                }
                            }
                            else {                                  // Enemy misses
                                System.out.println("The enemy " + enemyMon.name + "'s " + enemyMove + " missed!");
                                System.out.println("> Your" + playerMon.name + " used " + playerMon.move2 + ".");
                                if (rand.nextInt(256) < playerMon.acc2) {    // Player's attack lands
                                    enemyHP -= dmgDealt;
                                    System.out.println("Your " + playerMon.name + " hit " + enemyMon.name + " for " + dmgDealt + ".");
                                    if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                        System.out.println("The enemy " + enemyMon.name + " fainted.");
                                        break;
                                    }
                                } else {           // Player misses
                                    System.out.println("Your " + playerMon.name + "'s attack missed!");
                                }
                            }
                        }
                        break;
                    default:                        // Didn't chose any of the given options
                        System.out.println("Invalid command!");
                        break;
                }
                if (enemyHP < 1 || playerHP < 1) {
                    break label;
                }
            }
        }
        if (enemyHP < 1) {
            System.out.println("You won!");
        }
        else {
            System.out.println("You lost!");
        }
        System.out.println("* Thanks for playing! *");
    }
}
