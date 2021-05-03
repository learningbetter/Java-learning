package work;

import java.awt.geom.Point2D;
import java.util.Scanner;
import java.awt.geom.Point2D.Double;

class Cylinder{
    private Point2D.Double p;
    private double z;
    private double radius;
    private double height;

    private final static double PI = Math.PI;

    public Cylinder(){ }

    public Cylinder(Point2D.Double p, double z, double radius, double height) {
        this.p = p;
        this.z = z;
        this.radius = radius;
        this.height = height;
    }

    public double getX(){ return this.p.x; }
    
    public double getY(){ return this.p.y; }
    
    public double floorage(){ return PI*radius*radius; }

    public double superficialArea(){ return 2*floorage()+2*radius*PI*height; }

    public double volume(){ return this.floorage()*height; }

    public /*本想用double但是Point都是int*/double cylinderCenter(){ return z+height/2.0; }
}


public class CylinderTest {
    public static void main(String[] arge){
        System.out.println("Please enter the x, y and z coordinates of the floor circle center, the radius and height of the cylinder:");
        Scanner s = new Scanner(System.in);
        double x = s.nextDouble();
        double y = s.nextDouble();
        double z = s.nextDouble();
        double r = s.nextDouble();
        double h = s.nextDouble();
        Cylinder C = new Cylinder(new Point2D.Double(x,y),z,r,h);
        System.out.printf("Floorage=%.3f\n",C.floorage());
        System.out.printf("Superficial area=%.3f\n",C.superficialArea());
        System.out.printf("Volume=%.3f\n",C.volume());
        System.out.printf("Cylinder center coordinates are (%.3f,%.3f,%.3f)\n",C.getX(),C.getY(),C.cylinderCenter());
    }
}
