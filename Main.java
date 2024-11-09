import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static boolean sumsAreEqual(int[] A, int[] B){ // to check if problem is balanced
        int A_sum=0;
        int B_sum=0;
        for (int i=0; i<3; i++) A_sum+=A[i];
        for (int i=0; i<4; i++) B_sum+=B[i];
        return (A_sum==B_sum);
    }

    public static int differenceOf2Minimums(int A, int B, int C){ // for vogel's approximation
        ArrayList<Integer> initialNumbers = new ArrayList<>();
        initialNumbers.add(A);
        initialNumbers.add(B);
        initialNumbers.add(C);
        ArrayList<Integer> minimums = new ArrayList<>();
        for (int i=0; i<3; i++) minimums.add(0);

        int minimumNumber = 0;
        int minimumValue = 1000000;
        for (int i=0; i<3; i++){
            if (minimumValue>initialNumbers.get(i)){
                minimumNumber=i;
                minimumValue=initialNumbers.get(i);
            }
        }

        minimums.set(minimumNumber, 1);

        minimumNumber = 0;
        minimumValue = 1000000;
        for (int i=0; i<3; i++){
            if (minimumValue>initialNumbers.get(i) && minimums.get(i)!=1){
                minimumNumber=i;
                minimumValue=initialNumbers.get(i);
            }
        }

        minimums.set(minimumNumber, 2);

        int second = initialNumbers.get(minimums.indexOf(2));
        int first = initialNumbers.get(minimums.indexOf(1));

        return second-first;
    }

    public static int differenceOf2Minimums(int A, int B, int C, int D){ // same as previous, but for 4 numbers
        ArrayList<Integer> initialNumbers = new ArrayList<>();
        initialNumbers.add(A);
        initialNumbers.add(B);
        initialNumbers.add(C);
        initialNumbers.add(D);
        ArrayList<Integer> minimums = new ArrayList<>();
        for (int i=0; i<4; i++) minimums.add(0);

        int minimumNumber = 0;
        int minimumValue = 1000000;
        for (int i=0; i<4; i++){
            if (minimumValue>initialNumbers.get(i)){
                minimumNumber=i;
                minimumValue=initialNumbers.get(i);
            }
        }

        minimums.set(minimumNumber, 1);

        minimumNumber = 0;
        minimumValue = 1000000;
        for (int i=0; i<4; i++){
            if (minimumValue>initialNumbers.get(i) && minimums.get(i)!=1){
                minimumNumber=i;
                minimumValue=initialNumbers.get(i);
            }
        }

        minimums.set(minimumNumber, 2);

        int second = initialNumbers.get(minimums.indexOf(2));
        int first = initialNumbers.get(minimums.indexOf(1));

        return second-first;
    }


    public static int[][] NorthWest(int[] S_init, int[][] C, int[] D_init){ // finds  NorthWest initial solution
        int[] S = new int[3];
        int[] D = new int[4];
        for (int i=0; i<3; i++) S[i] = S_init[i];
        for (int i=0; i<4; i++) D[i] = D_init[i];
        int[][] NW = new int[3][4];
        for (int i=0; i<3; i++) for (int j=0; j<4; j++){
            NW[i][j] = 0;
        }


        for (int j=0; j<4; j++) for (int i=0; i<3; i++){
            int amount = Math.min(S[i], D[j]);
            NW[i][j]+=amount;
            S[i]-=amount;
            D[j]-=amount;
        }

        return NW;
    }


    public static int[][] Vogel(int[] S_init, int[][] C, int[] D_init){ // returns Vogel's approximation
        int[] S = new int[3];
        int[] D = new int[4];
        for (int i=0; i<3; i++) S[i] = S_init[i];
        for (int i=0; i<4; i++) D[i] = D_init[i];
        int[][] VA = new int[3][4];
        for (int i=0; i<3; i++) for (int j=0; j<4; j++){
            VA[i][j] = 0;
        }

        while (S[0]!=0 || S[1]!=0 || S[2]!=0){ // while not all sources are empty
            int[] rows = new int[3];
            int[] columns = new int[4];
            for (int i=0; i<3; i++) rows[i] = differenceOf2Minimums(C[i][0], C[i][1], C[i][2], C[i][3]);
            for (int i=0; i<4; i++) columns[i] = differenceOf2Minimums(C[0][i], C[1][i], C[2][i]);
            int maxRowNumber = 0;
            int maxColumnNumber = 0;
            int maxRowValue = -1000;
            int maxColumnValue = -1000;

            for (int i=0; i<3; i++){
                if (maxRowValue<rows[i] && S[i]!=0){
                    maxRowValue=rows[i];
                    maxRowNumber=i;
                }
            }
            for (int i=0; i<4; i++){
                if (maxColumnValue<columns[i] && D[i]!=0){
                    maxColumnValue=columns[i];
                    maxColumnNumber=i;
                }
            }

            int chosenRow;
            int chosenColumn;

            if (maxRowValue>maxColumnValue){
                chosenRow=maxRowNumber;
                int minNumber = 0;
                int minValue = 100000;
                for (int i=0; i<4; i++){
                    if (minValue>C[chosenRow][i] && D[i]!=0){
                        minNumber=i;
                        minValue=C[chosenRow][i];
                    }
                }
                chosenColumn=minNumber;
            } else {
                chosenColumn=maxColumnNumber;
                int minNumber = 0;
                int minValue = 100000;
                for (int i=0; i<3; i++){
                    if (minValue>C[i][chosenColumn] && S[i]!=0){
                        minNumber=i;
                        minValue=C[i][chosenColumn];
                    }
                }
                chosenRow=minNumber;
            }

            int amount = Math.min(S[chosenRow], D[chosenColumn]);
            VA[chosenRow][chosenColumn]+=amount;
            S[chosenRow]-=amount;
            D[chosenColumn]-=amount;


        }

        return VA;
    }


    public static int[][] Russel(int[] S_init, int[][] C, int[] D_init){ // returns Russel's approximation
        int[] S = new int[3];
        int[] D = new int[4];
        for (int i=0; i<3; i++) S[i] = S_init[i];
        for (int i=0; i<4; i++) D[i] = D_init[i];
        int[][] RA = new int[3][4];
        for (int i=0; i<3; i++) for (int j=0; j<4; j++){
            RA[i][j] = 0;
        }

        while (S[0]!=0 || S[1]!=0 || S[2]!=0){
            int[] rows = new int[3];
            int[] columns = new int[4];
            for (int i=0; i<3; i++) rows[i] = -1000;
            for (int i=0; i<4; i++) columns[i] = -1000;

            for (int i=0; i<3; i++) for (int j=0; j<4; j++){
                if (C[i][j]>rows[i]) rows[i] = C[i][j];
                if (C[i][j]>columns[j]) columns[j] = C[i][j];
            }

            int mostNegI = 0;
            int mostNegJ = 0;
            int mostNegValue = 100000;

            for (int i=0; i<3; i++) for (int j=0; j<4; j++){
                if (S[i]!=0 && D[j]!=0) if (mostNegValue > C[i][j] - rows[i] - columns[j]) {
                    mostNegValue = C[i][j] - rows[i] - columns[j];
                    mostNegI=i;
                    mostNegJ=j;
                }
            }

            int amount = Math.min(S[mostNegI], D[mostNegJ]);
            RA[mostNegI][mostNegJ]+=amount;
            S[mostNegI]-=amount;
            D[mostNegJ]-=amount;

        }


        return RA;
    }


    public static void output(int[][] matrix){ // outputs 3x4 matrix of ints
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args){

        //input
        Scanner scanner = new Scanner(System.in);
        System.out.println("A vector of coefficients of supply:");
        int[] S = new int[3];
        for (int i=0; i<3; i++) S[i] = scanner.nextInt();

        System.out.println("A matrix of coefficients of costs:");
        int[][] C = new int[3][4];
        for (int i=0; i<3; i++) for (int j=0; j<4; j++) C[i][j] = scanner.nextInt();

        System.out.println("A vector of coefficients of demand");
        int[] D = new int[4];
        for (int i=0; i<4; i++) D[i] = scanner.nextInt();

        if (!sumsAreEqual(S, D)) { // Check balance
            System.out.println("The problem is not balanced!");
            return;
        }


        int[][] NW = NorthWest(S, C, D); // stores initial solution for NorthWest method
        int[][] VA = Vogel(S, C, D);     // stores initial solution for Vogel's Approximation
        int[][] RA = Russel(S, C, D);    // stores initial solution for Russel's Approximation

        System.out.println("Initial table:"); // output of initial table
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++){
                System.out.print(C[i][j] + " ");
            }
            System.out.println(S[i]);
        }
        for (int j=0; j<4; j++){
            System.out.print(D[j] + " ");
        }
        System.out.println();

        // output of initial solutions

        System.out.println("NorthWest method initial solution:");
        output(NW);
        System.out.println("Vogel's approximation initial solution:");
        output(VA);
        System.out.println("Russel's approximation initial solution:");
        output(RA);

    }

}
