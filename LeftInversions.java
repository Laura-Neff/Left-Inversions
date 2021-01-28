// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - LAURA NEFF

// As given, this is a correct but slow solution to the "Left
// Inversions" challenge on hackerrank. You want to replace method
// computeL() with something faster, based on mergesort.  See the
// Canvas assignment for some further advice.

// TODO:
//  1. add your name in the SPCA statement at top
//  2. replace the slow computeL method with something faster
//  3. test your code at https://www.hackerrank.com/cs253f20
//     (the "Left Inversions" challenge)
//  4. submit your best solution (as a link) on Canvas

// Requirements:
//   * pass the hackerrank tests with no timeouts (it gives you a score)
//   * solve this in O(N lg N) time and O(N) space
//   * do not use java.util (except StringTokenizer)

import java.io.*;
import java.util.StringTokenizer;

public class LeftInversions
{
    // You should replace this method!
    //
    // Given the input array A, computeL(A) return the output array L.
    // This does insertion sort, using worst-case quadratic time and
    // linear space.  It also sorts A, but that is not required.





    static public int[] computeL(int[] array) {


            int[] helper = new int[array.length]; //Create the auxiliary array to have the same size as the array you want to sort

            int[] positions = new int[array.length];

            int[] inversions = new int [array.length];


            for(int i = 0; i < positions.length; i++) { //Snapshot the original positions relative to the array at the very beginning before mergesort
                positions[i] = i;

            }


            int endIndex = array.length - 1;


            order(array, helper, positions, inversions, 0, endIndex); //Call our sorting function, which will include the merge function




            return inversions;

        }


    private static void order(int[] array, int[] helper, int[] positions, int[] inversions, int firstIndex, int lastIndex) { //Here we will do implementation of sorting function





        if(firstIndex >= lastIndex) return; //Base-case is when all the array elements are in subarrays of length 1 or for malformed input (insufficient endIndex since
        //why would you sort with an array of length 1 and how would it work if your first index was bigger than your last?

        int midpoint = firstIndex + (lastIndex - firstIndex)/2; //adding our first index to our last index may lead to overflow. That's why we divide the distance
        //between our last index and our first index by 2 to show us half of the distance between the first and last index
        //and then we add it back to the first index to show us the midpoint
        // lastIndex - firstIndex = distance between the 2 points
        // (lastIndex - firstIndex)/2 = half of distance between 2 points
        // firstIndex + (lastIndex - firstIndex)/2 = midpoint


        order(array,helper, positions, inversions, firstIndex, midpoint);       //We are dividing our array in half. To do this, we will make our midpoint the lastIndex of our first half.

        order(array, helper, positions, inversions,midpoint+1, lastIndex);     //Now sort for the second half, starting after the input.




        merge(array, helper, positions, inversions, firstIndex, midpoint, lastIndex);




    }

    private static int[] merge(int[] array, int[] helper, int[] positions, int[] inversions, int firstIndex, int midpoint, int lastIndex) { //This is our real sorting method

        for (int k = firstIndex; k <= lastIndex; k++) { //first, copy the parameter array into our helper
            helper[k] = positions[k];/**Fill helper array with original positions --> will change after recursive calls as you shift positions around
             on behalf of comparing the original array for sorting. We are not sorting the actual array now. We are looking
             at the array at different parts of the recursive call and moving our positions array elements to reflect that. For example,
             with array "8 12 2 0", you'd get "3 2 0 1" after shifting the position array because you're tracing where the original
             indexes went. For example, 0, which was at position 3, is now at the first index. So the 3 reflects 0's original position.
             That's how we trace our positions.**/
        }


        // merge from aux[] back to a[]
        int firstHalfIndex = firstIndex; //Tell merge where we'll be iterating. This is where we will compare the values in each half
        // so that we know what to change in original array from our helper array.

        int secondHalfIndex = midpoint+1;

        for (int k = firstIndex; k <= lastIndex; k++) { //This tells merge to recognize these 2 halves are part of one unit
            //by linking the firstIndex to the lastIndex

            if  (midpoint < firstHalfIndex) {           //If midpoint < firstHalfIndex, we're done with the first half.
                positions[k] = helper[secondHalfIndex];     //so then just input elements from sorted second half.
                secondHalfIndex++;                      //Now move onto next element in second half by incrementing the index.
            }
            else if (lastIndex < secondHalfIndex)  {    //If the lastIndex < secondHalfIndex, we're done with the second half.
                positions[k] = helper[firstHalfIndex];      //so then just input elements from sorted first half.
                firstHalfIndex++;                       //Now move onto next element in first half by incrementing the index.
            }
            else if (array[helper[firstHalfIndex]] > array[helper[secondHalfIndex]]) //if helper[firstHalfIndex] > helper[secondHalfIndex], we must choose helper[secondHalfIndex]
            {                                                          //to put back into our original array
                positions[k] = helper[secondHalfIndex];
                secondHalfIndex++;                      //now that we chose one from the second half, we will have to increment secondHalfIndex by 1 to compare to current firstHalfIndex
                inversions[positions[k]] += (midpoint - firstHalfIndex + 1); //We calculate the inversions here.
                                                                            /** Inversions and array have same index system. Positions are moving around, so say you want to set the
                                                                             * number of inversions at a certain position to something. Because the positions are moving, you want to
                                                                             * jump to that position with it to set the number of inversions in each recursive call.
                                                                             *
                                                                             * The reason we increase inversions by (midpoint - firstHalfIndex + 1) is because this is mergesort
                                                                             * rather than insertion sort. We do this formula to find out how many it moved to the left according to mergesort.
                                                                             * We increment by 1 to make up for the fact that our midpoint is AT one of the numbers you have to move through
                                                                             * for the swapping process.
                                                                             * **/

            }

            else    {
                positions[k] = helper[firstHalfIndex];
                firstHalfIndex++;
            }
        }

        return positions;


        //return inversions;



    }




    // This main() handles the input and output in a fast buffered
    // way, you do not need to modify main().
    public static void main(String[] args) throws IOException
    {
        // Read input array A. We avoid java.util.Scanner, for speed.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // first line
        int[] A = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine()); // second line
        for (int i=0; i<N; ++i)
            A[i] = Integer.parseInt(st.nextToken());

        // Solve the problem!
        int[] L = computeL(A);

        // Print the output array L.
        PrintWriter out = new PrintWriter(System.out);
        out.print(L[0]);
        for (int i=1; i<N; ++i)
            out.print(" " + L[i]);
        out.close();
    }
}
