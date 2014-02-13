///////////////////////////////////////////////////////////////////////////////
// Main Class File:  TestSorts.java
// File:             ComparisonSort.java
// Semester:         Fall 2011
//
// Author:           Saul Laufer, slaufer@wisc.edu
// CS Login:         saul
// Lecturer's Name:  Beck Hasti
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class implements seven different comparison sorts:
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort using median-of-three partitioning</li>
 * <li>heap sort</li>
 * <li>shaker sort</li>
 * <li>two-way insertion sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {

	private static int dataMoves; //tracks number of data moves made by a sort
	/**
	 * Sorts the given array using the selection sort algorithm.Note: after
	 * this method finishes the array is in sorted order.
	 * 
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 **/
	public static <E extends Comparable<E>> void selectionSort(E[] A) {
		dataMoves = 0;
		int j;
		int k;
		int minIndex; //location of smallest value
		E min; //holds smallest value
		int N = A.length; //length of array

		//selects smallest value
		for (k = 0; k < N; k++) {
			dataMoves += 3;
			min = A[k];
			minIndex = k;
			//places smallest value in correct location
			for (j = k+1; j < N; j++) {
				if (A[j].compareTo(min) < 0) {
					dataMoves++;
					min = A[j];
					minIndex = j;
				}
			}
			A[minIndex] = A[k];
			A[k] = min;
		}
	}

	/**
	 * Sorts the given array using the insertion sort algorithm. Note: after
	 * this method finishes the array is in sorted order.
	 * 
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 **/
	public static <E extends Comparable<E>> void insertionSort(E[] A) {
		dataMoves = 0;
		int j;
		E temp; //holds currently sorted element
		int N = A.length;

		//places sorted element in correct location within sorted area of arary
		for (int k = 1; k < N; k++) {
			dataMoves += 2;
			temp = A[k];
			j = k - 1;
			while ((j >= 0) && (A[j].compareTo(temp) > 0)) {
				dataMoves ++;
				A[j+1] = A[j];
				j--;
			}
			A[j+1] = temp;
		}
	}

	/**
	 * Sorts the given array using the merge sort algorithm. Note: after this
	 * method finishes the array is in sorted order.
	 * 
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 **/
	public static <E extends Comparable<E>> void mergeSort(E[] A) {
		dataMoves = 0;
		mergeAux(A, 0, A.length - 1);
	}

	/**
	 * Helper method for mergeSort.
	 *
	 * @param <E> the type of values to be sorted
	 * @param A the array to sort
	 * @param low location of first value to be sorted
	 * @param high location of last value to be sorted
	 */
	private static <E extends Comparable<E>> void mergeAux(E[] A, int low, int high) {
		// base case
		if (low == high) return;

		// recursive case
		// location of middle of array
		int mid = (low + high) / 2;

		// sorts halves split at "mid"
		mergeAux(A, low, mid);
		mergeAux(A, mid+1, high);

		//Merge sorted halves into an auxiliary array
		E[] tmp = (E[])(new Comparable[high-low+1]);
		//holds sorted values of partition

		int left = low;    // index into left half
		int right = mid+1; // index into right half
		int pos = 0;       // index into tmp

		//copies smallest of left and right and adds to tmp
		while ((left <= mid) && (right <= high)) {

			if (A[left].compareTo(A[right]) < 0) {
				dataMoves++;
				tmp[pos] = A[left];
				left++;   		
			}
			else {
				dataMoves++;
				tmp[pos] = A[right];
				right++;
			}
			pos++;
		}

		//when one half is done but other has values to be sorted
		while (left <= mid) {
			dataMoves++;
			tmp[pos] = A[left];
			left++;
			pos++;
		}
		while (right <= high) {
			dataMoves++;
			tmp[pos] = A[right];
			right++;
			pos++;
		}

		//copies values from tmp into array
		for (int i = 0; i < tmp.length; i++) {
			dataMoves++;
			A[low + i] = tmp[i];
		}
	}

	/**
	 * Sorts the given array using the quick sort algorithm, using the median of
	 * the first, last, and middle values in each segment of the array as the
	 * pivot value. Note: after this method finishes the array is in sorted
	 * order.
	 * 
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 **/
	public static <E extends Comparable<E>> void quickSort(E[] A) {
		dataMoves = 0;
		quickSortAux(A, 0, (A.length - 1));
	}

	/**
	 * Helper method for quickSort.
	 *
	 * @param <E> the type of values to be sorted
	 * @param A the array to sort
	 * @param low location of first value to be sorted
	 * @param high location of last value to be sorted
	 */
	private static <E extends Comparable<E>> void quickSortAux(E[] A, int low, int high) {
		//current values to be sorted are 2 or 1
		if ((high - low) < 2) {
			if ((high - low) == 1) {
				if (A[low].compareTo(A[high]) > 0) {
					swap(A, low, high);
				}
			}
		}
		else {
			//partition and sort partitions
			int right = partition(A, low, high);
			quickSortAux(A, low, right);
			quickSortAux(A, right+2, high);
		}
	}

	/**
	 * Sorts current partition and determines next partitioning.
	 *
	 * @param <E> the type of values to be sorted
	 * @param A the array to sort
	 * @param low location of first value to be sorted
	 * @param high location of last value to be sorted
	 */
	private static <E extends Comparable<E>> int partition(E[] A, int low, int high) {
		E pivot = medianOfThree(A, low, high); //pivot value
		int left = low+1; //left most unsorted value
		int right = high-2; //right most unsorted value

		//swaps values in comparison with pivot
		//if no swap necessary increment pointer
		while ( left <= right ) {
			while (A[left].compareTo(pivot) < 0) left++;
			while (A[right].compareTo(pivot) > 0) right--;

			if (left <= right) {
				swap(A, left, right);
				left++;
				right--;
			}
		}
		//puts pivot in proper place
		swap(A, left, high-1);
		return right;
	}

	/**
	 * Finds the pivot for current partition.
	 *
	 * @param <E> the type of values to be sorted
	 * @param A the array to sort
	 * @param low first value in partition
	 * @param high last value in partition
	 */
	private static <E extends Comparable<E>> E medianOfThree(E[] A, int low, int high) {
		//value in middle position of partition
		int mid = (low + high) / 2;

		//places values in locations at high, low and mid of array into correct 
		//locations, finds pivot (median of three values)

		if (A[low].compareTo(A[mid]) > 0) {
			swap(A, low, mid);
		}
		if (A[low].compareTo(A[high]) > 0) {
			swap(A, low, high);
		}
		if (A[mid].compareTo(A[high]) > 0) {
			swap(A, mid, high);
		}

		swap(A, mid, (high -  1));
		mid = high - 1;

		return A[mid];
	}

	/**
	 * Swaps values in the array at the parameter locations.
	 *
	 * @param <E> the type of values to be sorted
	 * @param A the array to sort
	 * @param val1 location of one value to swap
	 * @param val2 location of second value to swap
	 */
	private static <E extends Comparable<E>> void swap(E[] A, int val1, int val2) {
		dataMoves += 3;
		E tmp = A[val1];
		A[val1] = A[val2];
		A[val2] =tmp;
	}

	/**
	 * Sorts the given array using the heap sort algorithm by inserting values
	 * into a heap and then adding removeMax at the end of the array. Note: 
	 * after this method finishes the array is in sorted order.
	 * 
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 **/
	public static <E extends Comparable<E>> void heapSort(E[] A) {
		dataMoves = 0;;

		//heap used in sorting array
		@SuppressWarnings("unchecked")
		E[] heap = (E[])(new Comparable[(A.length) * 2]);

		//inserts values into heap
		for (int i = 0; i < A.length; i++) {
			insert(heap, A[i], (i + 1));
		}

		//removes values in heap in descending order, places
		//into array in ascending order
		for (int i = A.length - 1; i >= 0; i--) {
			dataMoves++;
			A[i] = removeMax(heap, (i + 1));
		} 	
	}

	/**
	 * Adds the given item to the heap.
	 * 
	 * @param <E> the type of values to be sorted
	 * @param tmp the heap of values currently being sorted
	 * @param nextLoc the next open location in the heap
	 */
	private static <E extends Comparable<E>> void insert(E[] tmp, E item, int nextLoc) {

		dataMoves++;
		tmp[nextLoc] = item;
		//the location of the currently assessed value
		int newValLoc = nextLoc;
		//to determine if current value's location is correct
		boolean done = false;
		while (!done) {
			//location of current value's parent
			int parentLoc = newValLoc/2;
			if (parentLoc == 0) {
				done = true;
			}
			else if (tmp[newValLoc].compareTo(tmp[parentLoc]) <= 0) {
				done = true;
			}
			else {
				dataMoves += 3;
				E parentHolder = tmp[parentLoc];
				tmp[parentLoc] = tmp[newValLoc];
				tmp[newValLoc] = parentHolder;
				newValLoc = parentLoc;
			}
		}
	}

	/**
	 * Returns and removes the largest item in the heap.
	 *
	 * @param <E> type of values to be sorted
	 * @param tmp the heap of values currently being sorted
	 * @param lastLoc the location of the last value in the heap
	 * @return the largest value in the heap
	 */
	private static <E extends Comparable<E>> E removeMax(E[] tmp, int lastLoc) {
		if (tmp.length < 1) {
			throw new NoSuchElementException();
		}
		dataMoves += 2;
		E oldMax = tmp[1];
		tmp[1] = tmp[lastLoc];
		tmp[lastLoc] = null;
		//location of current value
		int currentValLoc = 1;
		boolean done = false;
		while (!done) {
			//location of left child
			int childOne = currentValLoc * 2;
			//location of right child
			int childTwo = currentValLoc * 2 + 1;
			//to hold location of largest child
			int largeChildLoc = 0;
			//determines which child (if any) is largest
			if (!(childOne > tmp.length || childTwo > tmp.length)) {
				if (tmp[childOne] != null && tmp[childTwo] != null) {
					if (tmp[childOne].compareTo(tmp[childTwo]) > 0) {
						largeChildLoc = childOne;
					}
					else {
						largeChildLoc = childTwo;
					}
				}
				else if (tmp[childOne] == null && tmp[childTwo] != null) {
					largeChildLoc = childTwo;
				}
				else if (tmp[childTwo] == null & tmp[childOne] != null) {
					largeChildLoc = childOne;
				}
			}
			else {
				largeChildLoc = 0;
			}

			//changes locations of values if necessary
			if (tmp[largeChildLoc] == null) {
				done = true;
			}
			else if (tmp[largeChildLoc].compareTo(tmp[currentValLoc]) <= 0) {
				done = true;
			}
			else {
				dataMoves += 3;
				E largeChildHolder = tmp[largeChildLoc];
				tmp[largeChildLoc] = tmp[currentValLoc];
				tmp[currentValLoc] = largeChildHolder;
				currentValLoc = largeChildLoc;
			}
		}
		return oldMax;
	}

	/**
	 * Sorts the given array using the shaker sort algorithm.
	 * Note: after this method finishes the array is in sorted order.
	 *
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 **/
	public static <E extends Comparable<E>> void shakerSort(E[] A) {
		dataMoves = 0;
		int begin = 0; //first value in array
		int end = A.length - 1; //last value in array
		//to determine if a swap has occured in first loop
		boolean swapOccured = false;

		//while array is unsorted
		do {
			swapOccured = false;

			//compares (and swaps if necessary) values from right and lower
			for (int i = begin; i < end; i++) {
				if (A[i].compareTo(A[i+1]) > 0) {
					swap(A, i, (i+1));
					swapOccured = true;
				}
			}

			//if no swaps occured, array is sorted
			if (!swapOccured) break;

			//compares (and swaps if necessary) values from left and up
			if (swapOccured) {
				for (int i = end; i > begin; i--) {
					if (A[i].compareTo(A[i-1]) < 0) {
						swap(A, i, (i - 1));
					}
				}
				begin++;
				end--;
			}
		} while (swapOccured || (begin >= end));
	}

	/**
	 * Sorts the given array using the two-way insertion sort algorithm 
	 * outlined below.
	 * Note: after this method finishes the array is in sorted order.
	 * <p>
	 * This sorting algorithm described above only works on arrays of even 
	 * length.  If the array passed in as a parameter is not even, the method 
	 * throws an IllegalArgumentException
	 * </p>
	 *
	 * @param <E>  the type of values to be sorted
	 * @param A    the array to sort
	 * @throws IllegalArgumentException if the length or A is not even
	 **/    
	public static <E extends Comparable<E>> void twoWayInsertSort(E[] A) { 
		dataMoves = 0;

		if (A.length % 2 != 0) {
			throw new IllegalArgumentException();
		}

		int right = (A.length / 2); //right of center index
		int left = right - 1; //left of center index

		if (A[left].compareTo(A[right]) > 0) {
			swap(A, left, right);
		}
		left--;
		right++;

		//while array is unsorted, swaps values moving towards ends of array
		do {
			if (A[left].compareTo(A[right]) > 0) {
				swap(A, left, right);
			}

			dataMoves += 2;
			E temp= A[right];
			int j = right - 1;
			while ((j >= left) && (A[j].compareTo(temp) > 0)) {
				dataMoves ++;
				A[j+1] = A[j];
				j--;
			}
			A[j+1] = temp;

			dataMoves += 2;
			temp = A[left];
			j = left + 1;
			while ((j < right) && (A[j].compareTo(temp) < 0)) {
				dataMoves ++;
				A[j-1] = A[j];
				j++;
			}
			A[j-1] = temp;

			left--;
			right++;
		}while (left >= 0  && right < A.length);
	}

	/**
	 * Internal helper for printing rows of the output table.
	 * 
	 * @param sort name of the sorting algorithm
	 * @param compares number of comparisons performed during sort
	 * @param moves number of data moves performed during sort
	 * @param milliseconds time taken to sort, in milliseconds
	 */
	private static void printStatistics(String sort, int compares, int moves, 
			long milliseconds) {
		System.out.format("%-15s%,15d%,15d%,15d\n", 
				sort, compares, moves, milliseconds);
	}

	/**
	 * Sorts the given array using the seven different sorting algorithms and
	 * prints out statistics. The sorts performed are:
	 * <ul>
	 * <li>selection sort</li>
	 * <li>insertion sort</li>
	 * <li>merge sort</li>
	 * <li>quick sort using median-of-three partitioning</li>
	 * <li>heap sort</li>
	 * <li>shaker sort</li>
	 * <li>two-way insertion sort</li>
	 * </ul>
	 * <p>
	 * The statistics displayed for each sort are: number of comparisons, number
	 * of data moves, and time (in milliseconds).
	 * </p>
	 * <p>
	 * Note: each sort is given the same array (i.e., in the original order) and
	 * the input array A is not changed by this method.
	 * </p>
	 * 
	 * @param A  the array to sort
	 **/
	static public void runAllSorts(InstrumentedInt[] A) {
		System.out.format("%-15s%15s%15s%15s\n", "algorithm", "data compares", 
				"data moves", "milliseconds");
		System.out.format("%-15s%15s%15s%15s\n", "---------", "-------------", 
				"----------", "------------");

		//to hold original version of array (unsorted)
		InstrumentedInt [] original = new InstrumentedInt[A.length];
		for (int i = 0; i < A.length; i++) {
			original[i] = A[i];
		}


		/*
		 * For every sort, a variable is initialized with the system time prior
		 * to and after the sort has occurred. Another variable is initialized
		 * with the difference between the two. New variables for each sort were
		 * used to decrease time anomalies. The number of comparisons each data
		 * member has made are recorded into a variable totalCompares.
		 */
		long beforeSelec = System.currentTimeMillis();
		selectionSort(A);
		long afterSelec = System.currentTimeMillis();
		long selecTime = afterSelec - beforeSelec;

		int totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("selection", totalCompares, dataMoves, selecTime);


		for (int i = 0; i < A.length; i++) {
			A[i] = original[i];
		}
		long beforeInsert = System.currentTimeMillis();
		insertionSort(A);
		long afterInsert = System.currentTimeMillis();
		long insertTime = afterInsert - beforeInsert;

		totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("insertion", totalCompares, dataMoves, insertTime);


		for (int i = 0; i < A.length; i++) {
			A[i] = original[i];
		}
		long beforeMerge = System.currentTimeMillis();
		mergeSort(A);
		long afterMerge = System.currentTimeMillis();
		long mergeTime = afterMerge - beforeMerge;

		totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("merge", totalCompares, dataMoves, mergeTime);


		for (int i = 0; i < A.length; i++) {
			A[i] = original[i];
		}
		long beforeQuick = System.currentTimeMillis();
		quickSort(A);
		long afterQuick = System.currentTimeMillis();
		long quickTime = afterQuick - beforeQuick;

		totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("quick", totalCompares, dataMoves, quickTime);


		for (int i = 0; i < A.length; i++) {
			A[i] = original[i];
		}
		long beforeHeap = System.currentTimeMillis();
		heapSort(A);
		long afterHeap = System.currentTimeMillis();
		long heapTime = afterHeap - beforeHeap;

		totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("heap", totalCompares, dataMoves, heapTime);


		for (int i = 0; i < A.length; i++) {
			A[i] = original[i];
		}
		long beforeShake = System.currentTimeMillis();
		shakerSort(A);
		long afterShake = System.currentTimeMillis();
		long shakeTime = afterShake - beforeShake;
		totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("shaker", totalCompares, dataMoves, shakeTime);


		for (int i = 0; i < A.length; i++) {
			A[i] = original[i];
		}
		long beforeTwoWay = System.currentTimeMillis();
		twoWayInsertSort(A);
		long afterTwoWay = System.currentTimeMillis();
		long twoWayTime = afterTwoWay - beforeTwoWay;
		totalCompares = 0;
		for (int i = 0; i < A.length; i++) {
			totalCompares += A[i].getCompares();
			A[i].resetCompares();
		}
		printStatistics("2-way insertion", totalCompares, dataMoves, twoWayTime);
	}
}
