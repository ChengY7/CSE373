import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class main {
    private static boolean[] allNumbers;
    private static int n;
    private static ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> currentSolution= new ArrayList<>();
    public static void main(String [] args) throws IOException {
        long startTime = System.nanoTime();
        BufferedReader br = new BufferedReader(new FileReader("s-rg-63-25"));
        n = Integer.parseInt(br.readLine());
        int numberSubsets = Integer.parseInt(br.readLine());
        allSubsets = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] strArr = line.split("\\s+");
            ArrayList<Integer> subset = new ArrayList<>();
            for (String strNumber : strArr) {
                if (!strNumber.isEmpty()) {
                    subset.add(Integer.parseInt(strNumber));
                }
            }

            allSubsets.add(subset);
        }
        ArrayList<ArrayList<Integer>> currentAnswer = new ArrayList<>();
        main test = new main();
        test.backTrack(currentAnswer);
        System.out.println("Minimum set cover:");
        for (ArrayList<Integer> subset : currentSolution) {
            System.out.println(Arrays.toString(subset.toArray()));
        }
        System.out.println("Size: " + currentSolution.size());


        long stopTime = System.nanoTime();
        System.out.println((stopTime - startTime)/1000000000.0);
    }
    public void backTrack(ArrayList<ArrayList<Integer>> currentAnswer) {
        this.allNumbers= new boolean[this.n+1];
        //boolean array to check if we have a solution the size is n+1 because were not using 0
        for (ArrayList<Integer> subset : currentAnswer) { //each subsets in currentAnswer
            for (Integer number : subset) { //each number in the subsets
                this.allNumbers[number]=true;
                //for each number inside each subsets in currentAnswer we updated allNumbers boolean array to true
            }
        }
        if (is_a_solution(this.allNumbers)) {
            //if we have a solution
            if (this.currentSolution.isEmpty() || currentAnswer.size()<this.currentSolution.size()) {
                //if current solution is empty or new solution is a smaller set then we update currentSolution
                this.currentSolution = (ArrayList<ArrayList<Integer>>) currentAnswer.clone();
            }
        } else {
            //This gets the index of the allSubsets were up to
            int counter = 0;
            if (!currentAnswer.isEmpty()) {
                //if currentAnswer is not empty
                if (this.allSubsets.contains(currentAnswer.get(currentAnswer.size() - 1))) {
                    //if last set of currentAnswer exist in allSubsets then counter = index of last set of sets in currentAnswer
                    counter=this.allSubsets.indexOf(currentAnswer.get(currentAnswer.size()-1));
                }
            }
            ArrayList<ArrayList<Integer>> candidates = new ArrayList<>();
            //contains potential candidates
            for (int i = counter; i<this.allSubsets.size(); i++) { //for allSubsets index at counter to last
                ArrayList<Integer> currentSubset = this.allSubsets.get(i);
                //if (!currentAnswer.contains(this.allSubsets.get(i))) { //if currentAnswer doesn't contain the allSubsets[i]
                    for (int number : currentSubset) { //for all numbers in the subset
                        if (!this.allNumbers[number]) { //if the number doesn't exist in allNumbers boolean array
                            candidates.add(currentSubset); //then add it as a candidates
                            break;                         //break and continue looking for candidates
                        }
                    }
                //}
            }
            for (ArrayList<Integer> subsetCandidates : candidates) { //for each candidates
                currentAnswer.add(subsetCandidates); //add it to currentAnswer
                backTrack(currentAnswer); //back track
                currentAnswer.remove(subsetCandidates); //remove it from currentAnswer and try next candidates
            }
            /**for (counter=0; counter<candidates.size(); counter++) {
                currentAnswer.add(candidates.get(counter));
                backTrack(currentAnswer);
                currentAnswer.remove(currentAnswer.size()-1);
            }*/
        }
    }
    public boolean is_a_solution(boolean[] solution) {
        for (int i=1; i<solution.length; i++) {
            if (!solution[i]) {
                return false; //if any index in the solution boolean is false then no solution
            }
        }
        return true;
    }
}
