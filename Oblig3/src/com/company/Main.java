package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Take initial program parameters
        Scanner scanner = new Scanner(System.in);
        System.out.println("Specify the amount of numbers you want sorted:");
        int length = scanner.nextInt();
        System.out.println("Specify the method you want to use\n1)Insertion sort\n2)Quick sort\n3)Merge sort\n4)Radix sort");
        int method = scanner.nextInt();
        System.out.println("What type of test? \n0)Sort with timer\n1) Estimate an approximation of C over 10 iterations");
        int sortTest = scanner.nextInt();


        //If sortTest == 0 sort a random array of size n and print the runtime.
        if(sortTest == 0) {
            int[] unSortedSequence = new int[length];
            int[] sortedSequence = new int[length];
            for(int i = 0; i < length; i++) {
                Random rand = new Random();
                unSortedSequence[i] = rand.nextInt(length*2);
            }
            long time = System.currentTimeMillis();
            //Sort based on the entered method
            switch (method) {
                case 1 -> sortedSequence = insertSort(unSortedSequence);
                case 2 -> sortedSequence = quickSort(unSortedSequence, 0, unSortedSequence.length - 1);
                case 3 -> sortedSequence = mergeSort(unSortedSequence, 0, unSortedSequence.length - 1);
                case 4 -> sortedSequence = radixSort(unSortedSequence);
            }
            time = System.currentTimeMillis() - time;
            System.out.println("Sorting done in: " + time + " ms");
        }

        /*
        If sortTest == 1 calculate a approximation of C over 10 iterations.

        I found the assignment text a bit vague on how to calculate an appropriate C but this is my best guestimation on how to approach the issue.
         */
        if(sortTest == 1){
            long[] timers = new long[10];
            // Sort 10 random arrays and average out the sorting time. (Basically just run the sorting code from above 10 times and store the sort time)
            for(int i = 0; i < 10; i++){
                int[] unSortedSequence = new int[length];
                int[] sortedSequence = new int[length];
                for(int j = 0; j < length; j++) {
                    Random rand = new Random();
                    unSortedSequence[j] = rand.nextInt(length*2);
                }
                long time = System.currentTimeMillis();
                switch (method) {
                    case 1 -> sortedSequence = insertSort(unSortedSequence);
                    case 2 -> sortedSequence = quickSort(unSortedSequence, 0, unSortedSequence.length - 1);
                    case 3 -> sortedSequence = mergeSort(unSortedSequence, 0, unSortedSequence.length - 1);
                    case 4 -> sortedSequence = radixSort(unSortedSequence);
                }
                time = System.currentTimeMillis() - time;
                timers[i] = time;
            }
            //T is the average of the above iterations
            double T = 0;
            for(long time : timers ) T +=time;
            T /= timers.length;

            double C  = 0;
            //Calculate C based on the sorting method used (This is where im the most uncertain if my approach is correct)
            switch(method) {
                //C = T/n² for insert sort
                case 1 -> C = (T/(Math.exp(length)));
                //C = T/(n * log(n))
                case 2, 3 -> C = (T/(length * Math.log(length)));
                //C = T/(m*n)
                case 4 -> {
                    //Not a 100% sure about this but from my understanding m is the maximum amount of possible digits ?
                    long m = String.valueOf(length*2).length();
                    C = (T/(length * m));
                }
            }
            System.out.println("T for the sorting method is:" +T + " ms" );
            System.out.println("Giving a C of: " + C );
        }
    }
    /*
    Sorting algorithms are inspired™ by the ones from geeksforgeeks.org
     */


    public static int[] insertSort(int[] unSorted) {
        int n = unSorted.length;
        for(int i =  1; i <n; i++){
            int k = unSorted[i];
            int j = i -1;

            while(j >= 0 && unSorted[j] > k){
                unSorted[j + 1] = unSorted[j];
                j = j-1;
            }
            unSorted[j+1] = k;
        }
        return unSorted;
    }

    public static int[] quickSort(int[] unSorted,int low, int high){
        if(low < high){
            int part = partition(unSorted, low, high);
            quickSort(unSorted,low,part-1);
            quickSort(unSorted, part+1, high);
        }

        return unSorted;
    }
    //Used together with the QuickSort algorithm
    public static int partition(int[] arr, int low, int high){
        int pivot = arr[high];
        int i = (low -1);

        for(int j = low; j <= high-1; j++){
            if(arr[j] < pivot){
                i++;
                //Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        //Swap arr[i+1] and arr[high]
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return (i+1);
    }
    public static int[] mergeSort(int[] unSorted,int l ,int r){
        if(l < r){
            int middle = l + (r-l)/2;

            mergeSort(unSorted, l, middle);
            mergeSort(unSorted, middle+1, r);

            merge(unSorted, l, middle, r);
        }

        return unSorted;
    }
    //Used together with the mergeSort algorithm
    public static void merge(int[] toMerge, int l, int middle, int r){
        int size1 = middle-l+1;
        int size2 = r-middle;

        int[] L = new int[size1];
        int[] R = new int[size2];

        for (int i = 0; i < size1; ++i)
            L[i] = toMerge[l+i];
        for(int i = 0; i <size2; ++i)
            R[i] = toMerge[middle+1+i];

        int i = 0, j = 0;
        int k=l;
        while(i < size1 && j < size2){
            if(L[i] <= R[j]){
                toMerge[k] = L[i];
                i++;
            }else {
                toMerge[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < size1){
            toMerge[k] = L[i];
            i++;
            k++;
        }

        while (j < size2){
            toMerge[k] = R[j];
            j++;
            k++;
        }
    }

    public static int[] radixSort(int[] unSorted){
        int n = unSorted.length;
        int m = unSorted[0];
        //Find the max value in unSorted
        for(int i : unSorted) {
            if (i > m)
                m = i;
        }
        for(int exp = 1; m/exp > 0; exp *=10)
            countSort(unSorted,n,exp);

        return unSorted;
    }
    //Function used together
    public static void countSort(int[] unSorted, int n, int exp){
        int[] out = new int[n];
        int[] cnt = new int[10];

        Arrays.fill(cnt, 0);

        for(int i = 0; i<n; i++)
            cnt[(unSorted[i]/exp) % 10]++;

        for(int i = 1; i <10; i++)
            cnt[i] += cnt[i-1];

        for(int i = n - 1; i >= 0; i--){
            out[cnt[(unSorted[i]/exp)%10] -1] = unSorted[i];
            cnt[(unSorted[i]/exp) % 10]--;
        }

        for(int i=0;i<n;i++)
            unSorted[i] = out[i];

    }
}

