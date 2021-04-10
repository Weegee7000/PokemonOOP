/*
Game Basics (created in V1):
    - Randomly get one pokemon for you and one for the enemy
    - Pick one of the 2 moves to use
    - Keep going until one of you loses
    * Keep in mind that 100% accuracy is actually 255/256 chance to hit, while infinite never misses (256/256)
    * If you forgot pokemon stuff:
        - TYPE CHART: https://pokemondb.net/type
        - MOVE LIST: https://pokemondb.net/move/all
        - POKEMON LIST: https://pokemondb.net/pokedex/all

Changes from V1:
    - Added TYPES! this includes super/normal/not very/not at all effective mechanics, and STAB (same type attack bonus)
    - Added a random modifier that multiplies the attack by a number between 0.85 and 1.00 (RNG roll for moves)
    - Added a lot of pokemon + moves
    - Speed ties no longer always go to the player; it's a 50/50 between player and enemy
    - (code related change) Made moves separate objects rather than parts of the Pokemon object for general ease

Possible future changes:
    - Multiple pokemon + switching, rather than a strict 1v1 as the 1v1 seems to only last 1-3 turns
    - Chose your pokemon rather than random assignment (maybe a choice between choosing and random)
    - Special move effects, like: Priority moves (quick attack, extremespeed), status moves, flinch moves, multi-hit moves, heal/absorb, etc.
    - Pokemon Abilities
    - Possibly make the enemy pick the most damaging move rather than a random one (might make it too hard for player?)
    - Better formatting for the terminal text output
    ******(code) make each case only define the moves, do the rest outside the switch

To run, copy and paste the following into terminal:
    javac PokemonOOPV2.java
    java PokemonOOPV2

 */

import java.util.Scanner;
import java.util.Random;

public class PokemonOOPV2 {
    private final String name;
    private final String type;
    private final MovesOOPV2 move1;
    private final MovesOOPV2 move2;
    private final int health;
    private final int effAtk;
    private final int effDef;
    private final int effSpAtk;
    private final int effSpDef;
    private final int effSpeed;

    public PokemonOOPV2(String n, String t, MovesOOPV2 m1, MovesOOPV2 m2, int HP, int Atk, int Def, int SpAtk, int SpDef, int Speed) {
        name = n;
        type = t;
        move1 = m1;
        move2 = m2;
        health = (HP * 2) + 110;
        effAtk = (Atk * 2) + 5;
        effDef = (Def * 2) + 5;
        effSpAtk = (SpAtk * 2) + 5;
        effSpDef = (SpDef * 2) + 5;
        effSpeed = (Speed * 2) + 5;
    }

    public String toString() {
        return "(" + name + ", " + type + ", " + move1.name + ", " + move1.type + ", " + move1.power + ", " + move1.acc + ", " + move2 + ", " + move2.type + ", " + move2.power + ", " + move2.acc + ", " + health + ")";
    }

    public boolean isPhysical(MovesOOPV2 move) {
        return move.physpec.equalsIgnoreCase("Physical");
    }

    // TYPE CHART: https://pokemondb.net/type

    public int typeValue(String movetype) {
        if (movetype.equalsIgnoreCase("Normal")) {
            return 0;
        }
        else if (movetype.equalsIgnoreCase("Fire")) {
            return 1;
        }
        else if (movetype.equalsIgnoreCase("Water")) {
            return 2;
        }
        else if (movetype.equalsIgnoreCase("Electric")) {
            return 3;
        }
        else if (movetype.equalsIgnoreCase("Grass")) {
            return 4;
        }
        else if (movetype.equalsIgnoreCase("Ice")) {
            return 5;
        }
        else if (movetype.equalsIgnoreCase("Fighting")) {
            return 6;
        }
        else if (movetype.equalsIgnoreCase("Poison")) {
            return 7;
        }
        else if (movetype.equalsIgnoreCase("Ground")) {
            return 8;
        }
        else if (movetype.equalsIgnoreCase("Flying")) {
            return 9;
        }
        else if (movetype.equalsIgnoreCase("Psychic")) {
            return 10;
        }
        else if (movetype.equalsIgnoreCase("Bug")) {
            return 11;
        }
        else if (movetype.equalsIgnoreCase("Rock")) {
            return 12;
        }
        else if (movetype.equalsIgnoreCase("Ghost")) {
            return 13;
        }
        else if (movetype.equalsIgnoreCase("Dragon")) {
            return 14;
        }
        else if (movetype.equalsIgnoreCase("Dark")) {
            return 15;
        }
        else if (movetype.equalsIgnoreCase("Steel")) {
            return 16;
        }
        else if (movetype.equalsIgnoreCase("Fairy")) {
            return 17;
        }
        else {
            return -1;
        }
    }

    public double effectiveness(String mtype, String ptype) {
        double tmult = 1.0;
        double[][] typeChart = {
                {1,1,1,1,1,1,1,1,1,1,1,1,0.5,0,1,1,0.5,1},
                {1,0.5,0.5,1,2,2,1,1,1,1,1,2,0.5,1,0.5,1,2,1},
                {1,2,0.5,1,0.5,1,1,1,2,1,1,1,2,1,0.5,1,1,1},
                {1,1,2,0.5,0.5,1,1,1,0,2,1,1,1,1,0.5,1,1,1},
                {1,0.5,2,1,0.5,1,1,0.5,2,0.5,1,0.5,2,1,0.5,1,0.5,1},
                {1,0.5,0.5,1,2,0.5,1,1,2,2,1,1,1,1,2,1,0.5,1},
                {2,1,1,1,1,2,1,0.5,1,0.5,0.5,0.5,2,0,1,2,2,0.5},
                {1,1,1,1,2,1,1,0.5,0.5,1,1,1,0.5,0.5,1,1,0,2},
                {1,2,1,2,0.5,1,1,2,1,0,1,0.5,2,1,1,1,2,1},
                {1,1,1,0.5,2,1,2,1,1,1,1,2,0.5,1,1,1,0.5,1},
                {1,1,1,1,1,1,2,2,1,1,0.5,1,1,1,1,0,0.5,1},
                {1,0.5,1,1,2,1,0.5,0.5,1,0.5,2,1,1,0.5,1,2,0.5,0.5},
                {1,2,1,1,1,2,0.5,1,0.5,2,1,2,1,1,1,1,0.5,1},
                {0,1,1,1,1,1,1,1,1,1,2,1,1,2,1,0.5,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,0.5,0},
                {1,1,1,1,1,1,0.5,1,1,1,2,1,1,2,1,0.5,1,0.5},
                {1,0.5,0.5,0.5,1,2,1,1,1,1,1,1,2,1,1,1,0.5,2},
                {1,0.5,1,1,1,1,2,0.5,1,1,1,1,1,1,2,2,0.5,1}};
        int atkMove = typeValue(mtype);
        String[] pkType = ptype.split("&");
        for (String s : pkType) {
            tmult *= typeChart[atkMove][typeValue(s)];
        }
        return tmult;
    }

    public double stab(String mtype, String ptype) {
        String[] pkType = ptype.split("&");
        double stb;
        if (pkType.length == 1) {
            if (mtype.equalsIgnoreCase(pkType[0])) {
                stb = 1.5;
            }
            else {
                stb = 1.0;
            }
        }
        else {
            if (mtype.equalsIgnoreCase(pkType[0]) || mtype.equalsIgnoreCase(pkType[1])) {
                stb = 1.5;
            }
            else {
                stb = 1.0;
            }
        }
        return stb;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        // MovesOOP requires: String name, String move type, String physical/special, int damage, int accuracy (out of 256), String shown accuracy
        MovesOOPV2 Flamethrower = new MovesOOPV2("Flamethrower", "Fire", "Special", 90, 255, "100%");
        MovesOOPV2 Slash = new MovesOOPV2("Slash", "Normal", "Physical", 70, 255, "100%");
        MovesOOPV2 HydroPump = new MovesOOPV2("Hydro Pump", "Water", "Special", 110, 205, "80%");
        MovesOOPV2 IcePunch = new MovesOOPV2("Ice Punch", "Ice", "Physical", 75, 255, "100%");
        MovesOOPV2 EnergyBall = new MovesOOPV2("Energy Ball", "Grass", "Special", 90, 255, "100%");
        MovesOOPV2 Earthquake = new MovesOOPV2("Earthquake", "Ground", "Physical", 100, 255, "100%");
        MovesOOPV2 IceBeam = new MovesOOPV2("Ice Beam", "Ice", "Special", 90, 255, "100%");
        MovesOOPV2 BlazeKick = new MovesOOPV2("Blaze Kick", "Fire", "Physical", 85, 230, "90%");
        MovesOOPV2 Crunch = new MovesOOPV2("Crunch", "Dark", "Physical", 80, 255, "100%");
        MovesOOPV2 DarkPulse = new MovesOOPV2("Dark Pulse", "Dark", "Special", 80, 255, "100%");
        MovesOOPV2 DragonClaw = new MovesOOPV2("Dragon Claw", "Dragon", "Physical", 80, 255, "100%");
        MovesOOPV2 DragonPulse = new MovesOOPV2("Dragon Pulse", "Dragon", "Special", 85, 255, "100%");
        MovesOOPV2 DrillPeck = new MovesOOPV2("Drill Peck", "Flying", "Physical", 80, 255, "100%");
        MovesOOPV2 EarthPower = new MovesOOPV2("Earth Power", "Ground", "Special", 90, 255, "100%");
        MovesOOPV2 FireBlast = new MovesOOPV2("Fire Blast", "Fire", "Special", 110, 218, "85%");
        MovesOOPV2 GunkShot = new MovesOOPV2("Gunk Shot", "Poison", "Physical", 120, 205, "80%");
        MovesOOPV2 IronHead = new MovesOOPV2("Iron Head", "Steel", "Physical", 80, 255, "100%");
        MovesOOPV2 LeafBlade = new MovesOOPV2("Leaf Blade", "Grass", "Physical", 90, 255, "100%");
        MovesOOPV2 FlashCannon = new MovesOOPV2("Flash Cannon", "Steel", "Special", 80, 255, "100%");
        MovesOOPV2 MeteorMash = new MovesOOPV2("Meteor Mash", "Steel", "Physical", 90, 230, "90%");
        MovesOOPV2 Moonblast = new MovesOOPV2("Moonblast", "Fairy", "Special", 95, 255, "100%");
        MovesOOPV2 PoisonJab = new MovesOOPV2("Poison Jab", "Poison", "Physical", 80, 255, "100%");
        MovesOOPV2 PowerGem = new MovesOOPV2("Power Gem", "Rock", "Special", 80, 255, "100%");
        MovesOOPV2 Megahorn = new MovesOOPV2("Megahorn", "Bug", "Physical", 120, 218, "85%");
        MovesOOPV2 Psychic = new MovesOOPV2("Psychic", "Psychic", "Special", 90, 255, "100%");
        MovesOOPV2 RockTomb = new MovesOOPV2("Rock Tomb", "Rock", "Physical", 60, 243, "95%");
        MovesOOPV2 RockSlide = new MovesOOPV2("Rock Slide", "Rock", "Physical", 75, 230, "90%");
        MovesOOPV2 ShadowBall = new MovesOOPV2("Shadow Ball", "Ghost", "Special", 80, 255, "100%");
        MovesOOPV2 ShadowClaw = new MovesOOPV2("Shadow Claw", "Ghost", "Physical", 70, 255, "100%");
        MovesOOPV2 ShockWave = new MovesOOPV2("Shock Wave", "Electric", "Special", 60, 256, "infinite");
        MovesOOPV2 SkyUppercut = new MovesOOPV2("Sky Uppercut", "Fighting", "Physical", 85, 230, "90%");
        MovesOOPV2 SludgeBomb = new MovesOOPV2("Sludge Bomb", "Poison", "Special", 90, 255, "100%");
        MovesOOPV2 StoneEdge = new MovesOOPV2("Stone Edge", "Rock", "Physical", 100, 205, "80%");
        MovesOOPV2 Thunder = new MovesOOPV2("Thunder", "Electric", "Special", 110, 179, "75%");
        MovesOOPV2 ThunderPunch = new MovesOOPV2("Thunder Punch", "Electric", "Physical", 75, 255, "100%");
        MovesOOPV2 Thunderbolt = new MovesOOPV2("Thunderbolt", "Electric", "Special", 90, 255, "100%");
        MovesOOPV2 TriAttack = new MovesOOPV2("Tri Attack", "Normal", "Special", 80, 255, "100%");
        MovesOOPV2 Waterfall = new MovesOOPV2("Waterfall", "Water", "Physical", 80, 255, "100%");
        MovesOOPV2 XScissor = new MovesOOPV2("X-Scissor", "Bug", "Physical", 80, 255, "100%");
        MovesOOPV2 ZenHeadbutt = new MovesOOPV2("Zen Headbutt", "Psychic", "Physical", 80, 230, "90%");

        // Pokemon require: String name, String type, MovesOOP move1, MovesOOP move2, hp, atk, def, spatk, spdef, speed
        PokemonOOPV2 Charizard = new PokemonOOPV2("Charizard", "Fire&Flying", Flamethrower, Slash, 78, 84, 78, 109, 85, 100);
        PokemonOOPV2 Blastoise = new PokemonOOPV2("Blastoise", "Water", HydroPump, IcePunch, 79, 83, 100, 85, 105, 78);
        PokemonOOPV2 Venusaur = new PokemonOOPV2("Venusaur", "Grass&Poison", EnergyBall, Earthquake, 80, 82, 83, 100, 100, 80);
        PokemonOOPV2 Beedrill = new PokemonOOPV2("Beedrill", "Bug&Poison", Megahorn, PoisonJab, 65, 90, 40, 45, 80, 75);
        PokemonOOPV2 Nidoking = new PokemonOOPV2("Nidoking", "Ground&Poison", Earthquake, PoisonJab, 81, 102, 77, 85, 75, 85);
        PokemonOOPV2 Alakzam = new PokemonOOPV2("Alakazam", "Psychic", Psychic, ShadowBall, 55, 50, 45, 135, 95, 120);
        PokemonOOPV2 Golem = new PokemonOOPV2("Golem", "Rock&Ground", Earthquake, StoneEdge, 80, 120, 130, 55, 65, 45);
        PokemonOOPV2 Magneton = new PokemonOOPV2("Magneton", "Electric&Steel", FlashCannon, Thunderbolt, 50, 60, 95, 120, 70, 70);
        PokemonOOPV2 Cloyster = new PokemonOOPV2("Cloyster", "Water&Ice", IceBeam, HydroPump, 50, 95, 180, 85, 45, 70);
        PokemonOOPV2 Gengar = new PokemonOOPV2("Gengar", "Ghost&Poison", ShadowBall, SludgeBomb, 60, 65, 60, 130, 75, 110);
        PokemonOOPV2 Exeggutor = new PokemonOOPV2("Exeggutor", "Grass&Psychic", Psychic, EnergyBall, 95, 95, 85, 125, 75, 55);
        PokemonOOPV2 Electabuzz = new PokemonOOPV2("Electabuzz", "Electric", Thunder, Thunderbolt, 65, 84, 57, 95, 85, 105);
        PokemonOOPV2 Houndoom = new PokemonOOPV2("Houndoom", "Fire&Dark", DarkPulse, FireBlast, 75, 90, 50, 110, 80, 95);
        PokemonOOPV2 Tyranitar = new PokemonOOPV2("Tyranitar", "Rock&Dark", Crunch, StoneEdge, 100, 134, 110, 95, 100, 61);
        PokemonOOPV2 Sceptile = new PokemonOOPV2("Sceptile", "Grass", LeafBlade, DragonPulse, 70, 85, 65, 105, 85, 120);
        PokemonOOPV2 Blaziken = new PokemonOOPV2("Blaziken", "Fire&Fighting", SkyUppercut, BlazeKick, 80, 120, 70, 110, 70, 80);
        PokemonOOPV2 Swampert = new PokemonOOPV2("Swampert", "Water&Ground", Earthquake, Waterfall, 100, 110, 90, 85, 90, 60);
        PokemonOOPV2 Breloom = new PokemonOOPV2("Breloom", "Grass&Fighting", SkyUppercut, EnergyBall, 60, 130, 80, 60, 60, 70);
        PokemonOOPV2 Aggron = new PokemonOOPV2("Aggron", "Steel&Rock", IronHead, StoneEdge, 70, 110, 180, 60, 60, 50);
        PokemonOOPV2 Sharpedo = new PokemonOOPV2("Sharpedo", "Water&Dark", Crunch, Waterfall, 70, 120, 40, 95, 40, 95);
        PokemonOOPV2 Flygon = new PokemonOOPV2("Flygon", "Dragon&Ground", DragonClaw, Earthquake, 80, 100, 80, 80, 80, 100);
        PokemonOOPV2 Cacturne = new PokemonOOPV2("Cacturne", "Grass&Dark", EnergyBall, Crunch, 70, 115, 60, 115, 60, 55);
        PokemonOOPV2 Manectric = new PokemonOOPV2("Manectric", "Electric", ShockWave, Thunderbolt, 70, 75, 60, 105, 60, 105);
        PokemonOOPV2 Banette = new PokemonOOPV2("Banette", "Ghost", ShadowClaw, DarkPulse, 64, 115, 65, 83, 63, 65);
        PokemonOOPV2 Relicanth = new PokemonOOPV2("Relicanth", "Water&Rock", RockSlide, Waterfall, 100, 90, 130, 45, 65, 55);
        PokemonOOPV2 Salamence = new PokemonOOPV2("Salamence", "Dragon&Flying", DragonClaw, DrillPeck, 95, 135, 80, 110, 80, 100);
        PokemonOOPV2 Metagross = new PokemonOOPV2("Metagross", "Steel&Psychic", ZenHeadbutt, MeteorMash, 80, 135, 130, 95, 90, 70);

        PokemonOOPV2[] pkmn = {Charizard, Blastoise, Venusaur, Beedrill, Nidoking, Alakzam, Golem, Magneton, Cloyster, Gengar, Exeggutor,
        Electabuzz, Houndoom, Tyranitar, Sceptile, Blaziken, Swampert, Breloom, Aggron, Sharpedo, Flygon, Cacturne, Manectric, Banette,
        Relicanth, Salamence, Metagross};

        int num1 = (int) (pkmn.length * Math.random());
        PokemonOOPV2 playerMon = pkmn[num1];
        int playerHP = playerMon.health;
        int num2 = (int) (pkmn.length * Math.random());
        PokemonOOPV2 enemyMon = pkmn[num2];
        int enemyHP = enemyMon.health;
        boolean running = true;
        System.out.println("* Welcome to the Pokemon Fighting Simulator! *");
        System.out.println("Press Enter to begin.");
        String i = in.nextLine();

        label:
        while (running) {
            System.out.println("-----------------------------------");
            System.out.println("Your pokemon is " + playerMon.name + " (" + playerMon.type + ")");
            System.out.println("Your opponent's pokemon is " + enemyMon.name + " (" + enemyMon.type + ")");
            while (enemyHP > 0) {
                int enemyMNo = (int) (2 * Math.random());
                System.out.println("-----------------------------------");
                System.out.println("\tYour HP: " + playerHP);
                System.out.println("\tEnemy HP: " + enemyHP);
                System.out.println("> What move would you like to use?");
                System.out.println("\t1) " + playerMon.move1.name + " (" + playerMon.move1.type + " Type, " + playerMon.move1.power + " Power, " + playerMon.move1.realacc + " Accuracy)");
                System.out.println("\t2) " + playerMon.move2.name + " (" + playerMon.move2.type + " Type, " + playerMon.move2.power + " Power, " + playerMon.move2.realacc + " Accuracy)");
                String input = in.nextLine();

                int playerPower;
                int playerAcc;
                double playerEff;
                double playerStab;
                double playerM = 1.0;
                double playerRand = ((rand.nextDouble() * 15.0) + 85.0) / 100.0;
                int dmgDealt;
                MovesOOPV2 enemyMoveOOP = new MovesOOPV2("", "", "", 0, 0, "");
                String enemyMove = "";
                String enemyMoveType = "";
                int enemyPower = 0;
                int enemyAcc = 0;
                double enemyEff;
                double enemyStab;
                double enemyM = 1.0;
                double enemyRand = ((rand.nextDouble() * 15.0) + 85.0) / 100.0;
                double speedTie = (rand.nextDouble());
                int dmgTaken;

                switch (input) {
                    case "1":
                        playerPower = playerMon.move1.power;
                        playerAcc = playerMon.move1.acc;

                        if (enemyMNo == 0) {
                            enemyMoveOOP = enemyMon.move1;
                            enemyMoveType = enemyMon.move1.type;
                            enemyMove = enemyMon.move1.name;
                            enemyPower = enemyMon.move1.power;
                            enemyAcc = enemyMon.move1.acc;
                        }
                        else if (enemyMNo == 1) {
                            enemyMoveOOP = enemyMon.move2;
                            enemyMoveType = enemyMon.move2.type;
                            enemyMove = enemyMon.move2.name;
                            enemyPower = enemyMon.move2.power;
                            enemyAcc = enemyMon.move2.acc;
                        }
                        playerEff = playerMon.effectiveness(playerMon.move1.type, enemyMon.type);
                        playerStab = playerMon.stab(playerMon.move1.type, playerMon.type);
                        playerM *= playerEff * playerStab * playerRand;

                        enemyEff = enemyMon.effectiveness(enemyMoveType, playerMon.type);
                        enemyStab = enemyMon.stab(enemyMoveType, enemyMon.type);
                        enemyM *= enemyEff * enemyStab * enemyRand;
                        if (playerMon.isPhysical(playerMon.move1)) {
                            dmgDealt = (int) ((((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effAtk / enemyMon.effDef)) + 2) * playerM);
                        }
                        else {
                            dmgDealt = (int) ((((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effSpAtk / enemyMon.effSpDef)) + 2) * playerM);
                        }

                        if (enemyMon.isPhysical(enemyMoveOOP)) {
                            dmgTaken = (int) ((((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effAtk / playerMon.effDef)) + 2) * enemyM);
                        }
                        else {
                            dmgTaken = (int) ((((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effSpAtk / playerMon.effSpDef)) + 2) * enemyM);
                        }

                        if (playerMon.effSpeed > enemyMon.effSpeed || (playerMon.effSpeed == enemyMon.effSpeed && speedTie < 0.5)) {   // Player is faster or wins speed tie 50/50
                            System.out.println("> Your " + playerMon.name + " used " + playerMon.move1.name + ".");
                            if (rand.nextInt(256) < playerAcc) {    // Player's attack lands
                                enemyHP -= dmgDealt;
                                if (playerEff < 1.0 && playerEff > 0.0) {
                                    System.out.println("It was not very effective.");
                                }
                                else if (playerEff > 1.0) {
                                    System.out.println("It was super effective!");
                                }
                                else if (playerEff == 0.0) {
                                    System.out.println("It doesn't effect the pokemon");
                                }
                                System.out.println("Your " + playerMon.name + " hit the enemy " + enemyMon.name + " for " + dmgDealt + ".");
                                if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                    System.out.println("The enemy " + enemyMon.name + " fainted.");
                                    break;
                                }
                                else {                      // Enemy survives attack
                                    System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                                    if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                        playerHP -= dmgTaken;
                                        if (enemyEff < 1.0 && enemyEff > 0.0) {
                                            System.out.println("It was not very effective.");
                                        }
                                        else if (enemyEff > 1.0) {
                                            System.out.println("It was super effective!");
                                        }
                                        else if (enemyEff == 0.0) {
                                            System.out.println("It doesn't effect the pokemon");
                                        }
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
                                    if (enemyEff < 1.0 && enemyEff > 0.0) {
                                        System.out.println("It was not very effective.");
                                    }
                                    else if (enemyEff > 1.0) {
                                        System.out.println("It was super effective!");
                                    }
                                    else if (enemyEff == 0.0) {
                                        System.out.println("It doesn't effect the pokemon");
                                    }
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
                                if (enemyEff < 1.0 && enemyEff > 0.0) {
                                    System.out.println("It was not very effective.");
                                }
                                else if (enemyEff > 1.0) {
                                    System.out.println("It was super effective!");
                                }
                                else if (enemyEff == 0.0) {
                                    System.out.println("It doesn't effect the pokemon");
                                }
                                System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                if (playerHP < 1) {                 // You die
                                    System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                    break label;
                                }
                                else {                  // You survive the attack
                                    System.out.println("> Your " + playerMon.name + " used " + playerMon.move1.name + ".");
                                    if (rand.nextInt(256) < playerAcc) {    // Player's attack lands
                                        enemyHP -= dmgDealt;
                                        if (playerEff < 1.0 && playerEff > 0.0) {
                                            System.out.println("It was not very effective.");
                                        }
                                        else if (playerEff > 1.0) {
                                            System.out.println("It was super effective!");
                                        }
                                        else if (playerEff == 0.0) {
                                            System.out.println("It doesn't effect the pokemon");
                                        }
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
                                System.out.println("> Your " + playerMon.name + " used " + playerMon.move1.name + ".");
                                if (rand.nextInt(256) < playerAcc) {    // Player's attack lands
                                    enemyHP -= dmgDealt;
                                    if (playerEff < 1.0 && playerEff > 0.0) {
                                        System.out.println("It was not very effective.");
                                    }
                                    else if (playerEff > 1.0) {
                                        System.out.println("It was super effective!");
                                    }
                                    else if (playerEff == 0.0) {
                                        System.out.println("It doesn't effect the pokemon");
                                    }
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
                        playerPower = playerMon.move2.power;
                        playerAcc = playerMon.move2.acc;

                        if (enemyMNo == 0) {
                            enemyMoveOOP = enemyMon.move1;
                            enemyMoveType = enemyMon.move1.type;
                            enemyMove = enemyMon.move1.name;
                            enemyPower = enemyMon.move1.power;
                            enemyAcc = enemyMon.move1.acc;
                        }
                        else if (enemyMNo == 1) {
                            enemyMoveOOP = enemyMon.move2;
                            enemyMoveType = enemyMon.move2.type;
                            enemyMove = enemyMon.move2.name;
                            enemyPower = enemyMon.move2.power;
                            enemyAcc = enemyMon.move2.acc;
                        }

                        playerEff = playerMon.effectiveness(playerMon.move2.type, enemyMon.type);
                        playerStab = playerMon.stab(playerMon.move2.type, playerMon.type);
                        playerM *= playerEff * playerStab * playerRand;

                        enemyEff = enemyMon.effectiveness(enemyMoveType, playerMon.type);
                        enemyStab = enemyMon.stab(enemyMoveType, enemyMon.type);
                        enemyM *= enemyEff * enemyStab * enemyRand;

                        if (playerMon.isPhysical(playerMon.move2)) {
                            dmgDealt = (int) ((((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effAtk / enemyMon.effDef)) + 2) * playerM);
                        }
                        else {
                            dmgDealt = (int) ((((42.0 / 50.0) * (playerPower) * (1.0 * playerMon.effSpAtk / enemyMon.effSpDef)) + 2) * playerM);
                        }

                        if (enemyMon.isPhysical(enemyMoveOOP)) {
                            dmgTaken = (int) ((((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effAtk / playerMon.effDef)) + 2) * enemyM);
                        }
                        else {
                            dmgTaken = (int) ((((42.0 / 50.0) * (enemyPower) * (1.0 * enemyMon.effSpAtk / playerMon.effSpDef)) + 2) * enemyM);
                        }

                        if (playerMon.effSpeed >= enemyMon.effSpeed) {   // Player is faster
                            System.out.println("> Your " + playerMon.name + " used " + playerMon.move2.name + ".");
                            if (rand.nextInt(256) < playerAcc) {    // Player's attack lands
                                enemyHP -= dmgDealt;
                                if (playerEff < 1.0 && playerEff > 0.0) {
                                    System.out.println("It was not very effective.");
                                }
                                else if (playerEff > 1.0) {
                                    System.out.println("It was super effective!");
                                }
                                else if (playerEff == 0.0) {
                                    System.out.println("It doesn't effect the pokemon");
                                }
                                System.out.println("Your " + playerMon.name + " hit " + enemyMon.name + " for " + dmgDealt + ".");
                                if (enemyHP < 1) {         // Enemy dead, they can't attack you anymore!
                                    System.out.println("The enemy " + enemyMon.name + " fainted.");
                                    break;
                                }
                                else {                      // Enemy survives attack
                                    System.out.println("> The enemy " + enemyMon.name + " used " + enemyMove + ".");
                                    if (rand.nextInt(256) < enemyAcc) {     // Enemy attack lands
                                        playerHP -= dmgTaken;
                                        if (enemyEff < 1.0 && enemyEff > 0.0) {
                                            System.out.println("It was not very effective.");
                                        }
                                        else if (enemyEff > 1.0) {
                                            System.out.println("It was super effective!");
                                        }
                                        else if (enemyEff == 0.0) {
                                            System.out.println("It doesn't effect the pokemon");
                                        }
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
                                    if (enemyEff < 1.0 && enemyEff > 0.0) {
                                        System.out.println("It was not very effective.");
                                    }
                                    else if (enemyEff > 1.0) {
                                        System.out.println("It was super effective!");
                                    }
                                    else if (enemyEff == 0.0) {
                                        System.out.println("It doesn't effect the pokemon");
                                    }
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
                                if (enemyEff < 1.0 && enemyEff > 0.0) {
                                    System.out.println("It was not very effective.");
                                }
                                else if (enemyEff > 1.0) {
                                    System.out.println("It was super effective!");
                                }
                                else if (enemyEff == 0.0) {
                                    System.out.println("It doesn't effect the pokemon");
                                }
                                System.out.println("Your " + playerMon.name + " takes " + dmgTaken + " from " + enemyMon.name + ".");
                                if (playerHP < 1) {                 // You die
                                    System.out.println("Your " + playerMon.name + " has taken too much damage. " + playerMon.name + " fainted.");
                                    break label;
                                }
                                else {                  // You survive the attack
                                    System.out.println("> Your " + playerMon.name + " used " + playerMon.move2.name + ".");
                                    if (rand.nextInt(256) < playerAcc) {    // Player's attack lands
                                        enemyHP -= dmgDealt;
                                        if (playerEff < 1.0 && playerEff > 0.0) {
                                            System.out.println("It was not very effective.");
                                        }
                                        else if (playerEff > 1.0) {
                                            System.out.println("It was super effective!");
                                        }
                                        else if (playerEff == 0.0) {
                                            System.out.println("It doesn't effect the pokemon");
                                        }
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
                                System.out.println("> Your " + playerMon.name + " used " + playerMon.move2.name + ".");
                                if (rand.nextInt(256) < playerAcc) {    // Player's attack lands
                                    enemyHP -= dmgDealt;
                                    if (playerEff < 1.0 && playerEff > 0.0) {
                                        System.out.println("It was not very effective.");
                                    }
                                    else if (playerEff > 1.0) {
                                        System.out.println("It was super effective!");
                                    }
                                    else if (playerEff == 0.0) {
                                        System.out.println("It doesn't effect the pokemon");
                                    }
                                    System.out.println("Your " + playerMon.name + " hit " + enemyMon.name + " for " + dmgDealt + ".");
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
        System.out.println();
        if (enemyHP < 1) {
            System.out.println("You won!");
        }
        else {
            System.out.println("You lost!");
        }
        System.out.println("* Thanks for playing! *");
    }
}

