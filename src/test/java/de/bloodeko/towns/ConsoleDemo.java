package de.bloodeko.towns;

public class ConsoleDemo {
    
    public static void main(String[] args) {
        testMod(-0.7d, 2);
        testMod(-3.4d, 2);
        testMod(-4d, 2);
        testMod(0.1d, 1d);     //returns: 0.1
        testMod(1.1d, 1d);     //returns: 0.10000000000000009 aka 0.1 + 0.00000000000000009
        testMod(2.1d, 1d);     //returns: 0.10000000000000009 aka 0.1 + 0.00000000000000009
        testMod(10000.1d, 1d); //returns: 0.10000000000036380 aka 0.1 - 0.00000000000036380
    }
    
    public static void testMod(double a, double b) {
        System.out.println(floatMod1(a, b));
        System.out.println(floatMod2(a, b));
        System.out.println(floatMod3(a, b));
        System.out.println("");
    }
    
    //fastest, precise
    public static double floatMod1(double x, double y){
        return (x - Math.floor(x/y) * y);
    }
    
    //slow, precise
    public static double floatMod2(double a, double b) {
        return (a %= b) >= 0 ? a : (a + b);
    }
    
    //slowest
    public static double floatMod3(double a, double b) {
        return (a % b + b) % b;
    }
    
}
