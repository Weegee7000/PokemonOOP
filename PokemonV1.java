import java.util.Scanner;
import java.util.Random;

public class PokemonV1 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        String[] pokemon = {"Charizard", "Blastoise", "Venusaur"};
        String[] types = {"Fire & Flying", "Water", "Grass & Poison"};

        String[] m1 = {"Flamethrower", "Hydro Pump", "Energy Ball"};
        int[] p1 = {90, 110, 90};
        int[] a1 = {255, 205, 255};
        String[] m2 = {"Aerial Ace", "Aqua Tail", "Sludge Bomb"};
        int[] p2 = {60, 90, 90};
        int[] a2 = {256, 230, 255};
        String[] m3 = {"Slash", "Ice Punch", "Earthquake"};
        int[] p3 = {70, 75, 100};
        int[] a3 = {255, 255, 255};

        int[] HP = {78, 79, 80};
        int[] Atk = {84, 83, 82};
        int[] Def = {78, 100, 83};
        int[] SpAtk = {109, 85, 100};
        int[] SpDef = {85, 105, 100};
        int[] Speed = {100, 78, 80};

        int[] effHP = new int[pokemon.length];
        int[] effAtk = new int[pokemon.length];
        int[] effDef = new int[pokemon.length];
        int[] effSpAtk = new int[pokemon.length];
        int[] effSpDef = new int[pokemon.length];
        int[] effSpeed = new int[pokemon.length];

        for (int i = 0; i < pokemon.length; i++) {
            effHP[i] = (HP[i] * 2) + 110;
            effAtk[i] = (Atk[i] * 2) + 5;
            effDef[i] = (Def[i] * 2) + 5;
            effSpAtk[i] = (SpAtk[i] * 2) + 5;
            effSpDef[i] = (SpDef[i] * 2) + 5;
            effSpeed[i] = (Speed[i] * 2) + 5;
        }

        Double[] typechart = {};

        int num1 = (int) (pokemon.length*Math.random());
        int num2 = (int) (pokemon.length*Math.random());
        boolean running = true;

        label:
        while (running) {
            int playerHP = effHP[num1];
            int enemyHP = effHP[num2];
            while (enemyHP > 0) {
                int enemyMove = (int) (3 * Math.random());
                System.out.println("Your pokemon is " + pokemon[num1] + ". HP: " + playerHP);
                System.out.println("Your opponent's pokemon is " + pokemon[num2] + ". HP: " + enemyHP);
                System.out.println("What move would you like to use?");
                System.out.println("1) " + m1[num1]);
                System.out.println("2) " + m2[num1]);
                System.out.println("3) " + m3[num1]);

                String input = in.nextLine();
                int power;
                int acc;
                int m;
                int dmgDealt;
                int dmgTaken;
                int epower = 0;
                int eacc = 0;

                switch (input) {
                    case "1":
                        power = p1[num1];
                        acc = a1[num1];

                        if (enemyMove == 0) {
                            epower = p1[num2];
                            eacc = a1[num2];
                        }
                        else if (enemyMove == 1) {
                            epower = p2[num2];
                            eacc = a2[num2];
                        }
                        else if (enemyMove == 2) {
                            epower = p3[num2];
                            eacc = a3[num2];
                        }
                        m = 1;
                        dmgDealt = (int) ((((42) * power * ((1.0 * effSpAtk[num1]) / effSpDef[num2])) / 50) + 2) * m;
                        dmgTaken = (int) ((((42) * epower * ((1.0 * effSpAtk[num2]) / effSpDef[num1])) / 50) + 2) * m;
                        if (rand.nextInt(256) < acc) {
                            enemyHP -= dmgDealt;
                            System.out.println("You dealt " + dmgDealt + " damage!");
                        }
                        else {
                            System.out.println("Your attack missed!");
                        }
                        break;
                    case "2":
                        power = p2[num1];
                        acc = a2[num1];

                        if (enemyMove == 0) {
                            epower = p1[num2];
                            eacc = a1[num2];
                        }
                        else if (enemyMove == 1) {
                            epower = p2[num2];
                            eacc = a2[num2];
                        }
                        else if (enemyMove == 2) {
                            epower = p3[num2];
                            eacc = a3[num2];
                        }

                        m = 1;
                        dmgDealt = (int) ((((42) * power * (1.0 * effAtk[num1] / effDef[num2])) / 50) + 2) * m;
                        dmgTaken = (int) ((((42) * epower * ((1.0 * effAtk[num2]) / effDef[num1])) / 50) + 2) * m;
                        enemyHP -= dmgDealt;
                        if (rand.nextInt(256) < acc) {
                            System.out.println("You dealt " + dmgDealt + " damage!");
                        }
                        else {
                            System.out.println("Your attack missed!");
                        }
                        break;
                    case "3":
                        power = p3[num1];
                        acc = a3[num1];

                        if (enemyMove == 0) {
                            epower = p1[num2];
                            eacc = a1[num2];
                        }
                        else if (enemyMove == 1) {
                            epower = p2[num2];
                            eacc = a2[num2];
                        }
                        else if (enemyMove == 2) {
                            epower = p3[num2];
                            eacc = a3[num2];
                        }

                        m = 1;
                        dmgDealt = (int) ((((42) * power * (1.0 * effAtk[num1] / effDef[num2])) / 50) + 2) * m;
                        dmgTaken = (int) ((((42) * epower * ((1.0 * effAtk[num2]) / effDef[num1])) / 50) + 2) * m;
                        if (rand.nextInt(256) < acc) {
                            enemyHP -= dmgDealt;
                            System.out.println("You dealt " + dmgDealt + " damage!");
                        }
                        else {
                            System.out.println("Your attack missed!");
                        }
                        break;
                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            }
            if (playerHP < 1) {
                System.out.println("Your Pokemon fainted.");
                break;
            }
            System.out.println("You win!");
            break;
        }
    }
}
