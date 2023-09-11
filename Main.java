import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ganador = "";
        System.out.print("Ingrese el número de participantes: ");
        int numParticipants = scanner.nextInt();
        scanner.nextLine();


        if (!isPowerOfTwo(numParticipants)) {
            System.out.println("El número de participantes debe ser una potencia de 2 para un torneo de eliminación relampago.");
            return;
        }

        String[] participants = new String[numParticipants];

        for (int i = 0; i < numParticipants; i++) {
            System.out.print("Ingrese el nombre del participante No " + (i + 1) + ": ");
            participants[i] = scanner.nextLine();
        }

        Arrays.sort(participants, 0, numParticipants / 2);
        Arrays.sort(participants, numParticipants / 2, numParticipants, (a, b) -> -a.compareTo(b));

        int totalRounds = (int) (Math.log(numParticipants) / Math.log(2));

        String[][] brackets = new String[totalRounds][];

        brackets[0] = participants;

        for (int round = 0; round < totalRounds; round++) {
            String[] currentRound = brackets[round];
            int numMatches = currentRound.length / 2;
            String[] nextRound = (round == totalRounds - 1) ? null : new String[numMatches];

            for (int match = 0; match < numMatches; match++) {
                String team1 = currentRound[match];
                String team2 = currentRound[currentRound.length - match - 1];

                System.out.print("Ronda " + (round + 1) + ", Partido " + (match + 1) + ": " + team1 + " vs. " + team2 + " -> Ingrese el ganador (1 para " + team1 + " / 2 para " + team2 + "): ");
                int winnerChoice = scanner.nextInt();
                scanner.nextLine();

                String winner;
                if (winnerChoice == 1) {
                    winner = team1;
                } else if (winnerChoice == 2) {
                    winner = team2;
                } else {
                    System.out.println("Opción invalida por favor ingrese 1 o 2.");
                    match--;
                    continue;
                }

                if (round < totalRounds - 1) {
                    nextRound[match] = winner;
                }

                System.out.println("Ganador: " + winner);
                ganador = winner;
            }

            if (round < totalRounds - 1) {
                brackets[round + 1] = nextRound;
            }
        }

        System.out.println("\nGanador del torneo: " + ganador);

        // Print the tournament brackets as a tree
        System.out.println("\nBrackets del torneo:");
        printBracketTree(brackets);

        // Close the scanner
        scanner.close();
    }

    private static String simulateMatch(String team1, String team2) {
        // Simulate a random winner for the match
        return Math.random() < 0.5 ? team1 : team2;
    }

    private static boolean isPowerOfTwo(int number) {
        return (number & (number - 1)) == 0 && number > 0;
    }

    private static void printBracketTree(String[][] brackets) {
        for (int round = 0; round < brackets.length; round++) {
            System.out.println("Round " + (round + 1) + ":");
            String[] currentRound = brackets[round];

            for (int i = 0; i < currentRound.length / 2; i++) {
                String team1 = currentRound[i];
                String team2 = currentRound[currentRound.length - i - 1];
                System.out.println("   " + team1 + " vs. " + team2);
            }

            System.out.println();
        }
    }
}
