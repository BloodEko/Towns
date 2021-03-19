package de.bloodeko.towns;

// i<<4  works like i*16 (positive and negative)
// i>>4  works like i/16 (positive but with a wrong offset for negatives)
public class ConsoleDemo {
    
    public static void main(String[] args) {
        for (int i = -50; i < 50; i++) {
            int chunkX = i >> 4;
            System.out.println(i + " " + chunkX + " " + getMin(chunkX) + " " + getMax(chunkX));
        }
    }
    
    //done
    public static int getMin(int i) {
        return i << 4;
    }
    
    //done
    public static int getMax(int i) {
        return getMin(i+1) -1;
    }
    
}
