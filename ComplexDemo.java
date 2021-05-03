package work;

import java.math.RoundingMode;
import java.util.Scanner;
import java.math.BigDecimal;
import java.lang.NumberFormatException;//**警告：**\\
//**1.浮点数陷阱**\\
//**2. 0.0/0.0 浮点数不出错,但是为NaN,即无穷大,此时若使用BigDecimal**\\
//**  则会出现Exception in thread "main" java.lang.NumberFormatException: Infinite or NaN**\\

/*复数类：
    1.除数不能为0
    2.a+-bi改为a-bi
    3.b=0显示为a，而不是a+0i
    4.a=0显示为bi,而不是0+bi
    5.a=0,b=0显示为0,而不是0+0i
*/
class Complex {
    private double realNumber, imaginaryNumber;

    public Complex(double real, double img) {
        this.realNumber = real;
        this.imaginaryNumber = img;
    }

    public Complex() { this(0,0); }

    public double getRealNumber() { return realNumber;  }

    public double getImaginaryNumber() { return imaginaryNumber;  }

    public void setRealNumber(double realNumber) { this.realNumber = realNumber; }

    public void setImaginaryNumber(double imaginaryNumber) { this.imaginaryNumber = imaginaryNumber;  }

    public void reset(double real, double img) {
        this.realNumber = real;
        this.imaginaryNumber = img;
    }


    // (a+bi)+(c+di)=(a+c)+(b+d)i
    public Complex add(Complex c) {
        double real = this.realNumber + c.realNumber;
        double img = this.imaginaryNumber + c.imaginaryNumber;
        return new Complex(real, img);
    }

    // (a+bi)-(c+di)=(a-c)+(b-d)i
    public Complex sub(Complex c) {
        double real = this.realNumber - c.realNumber;
        double img = this.imaginaryNumber - c.imaginaryNumber;
        return new Complex(real, img);
    }

    // (a+bi)*(c+di)=(a*c-b*d)+(b*c+a*d)i
    public Complex mul(Complex c) {
        double r = this.realNumber * c.realNumber - this.imaginaryNumber * c.imaginaryNumber;
        double i = this.imaginaryNumber * c.realNumber + this.realNumber * c.imaginaryNumber;
        return new Complex(r,i);
    }

    // (a+bi)/(c+di)=(a*c+b*d)/(c*c+d*d) +((b*c-a*d)/(c*c+d*d))i
    public Complex div(Complex c) throws/* ArithmeticException*/NumberFormatException{
        Complex ret = new Complex();
        ret.realNumber = this.realNumber * c.realNumber + this.imaginaryNumber * c.imaginaryNumber;
        ret.imaginaryNumber = this.imaginaryNumber * c.realNumber - this.realNumber * c.imaginaryNumber;
/*        if(Math.abs(c.getModulus()-0)<1e-6)
            throw new ArithmeticException();*/
//        System.out.println(ret.imaginaryNumber+" "+ret.realNumber+" "+c.getModulus());
        ret.realNumber /= Math.pow(c.getModulus(), 2);
        ret.imaginaryNumber /= Math.pow(c.getModulus(), 2);
        if(Math.abs(c.getModulus()-0)<1e-6)
            throw new NumberFormatException();
//        System.out.println(ret.imaginaryNumber+" "+ret.realNumber+" "+c.getModulus());
        return ret;
    }

    // get |a+bi|
    public double getModulus() {
        double m = Math.sqrt(this.imaginaryNumber*this.imaginaryNumber + this.realNumber * this.realNumber);
        return m;
    }

    @Override
    public String toString() {//应该将所有的格式整理放到toString
        String to = new String();
        if(Math.abs(this.imaginaryNumber - 0)<1e-6 && Math.abs(this.realNumber - 0)<1e-6)
            to = "(0.0)";
        else if(Math.abs(this.realNumber - 0)<1e-6)
            to = "("+this.imaginaryNumber + "i)";
        else if(Math.abs(this.imaginaryNumber - 0)<1e-6)
            to ="("+ this.realNumber+")";
        else if(this.imaginaryNumber < 0)
            to = "("+this.realNumber +"" +this.imaginaryNumber+")";
        else
            to = "(" + this.realNumber +"+" + this.imaginaryNumber +"i)";
        return to;
    }

    public String toString(int a) {//可选保留小数点后几位//取舍方式为四舍五入
        String to = new String();
        BigDecimal i = new BigDecimal(this.imaginaryNumber);
        BigDecimal r = new BigDecimal(this.realNumber);
        BigDecimal zero = new BigDecimal(0.0);
        i = i.setScale(a,RoundingMode.HALF_UP);
        r = r.setScale(a,RoundingMode.HALF_UP);//不改变本身，是返回对象
        zero = zero.setScale(a,RoundingMode.HALF_UP);
        if(Math.abs(this.imaginaryNumber - 0)<1e-6 && Math.abs(this.realNumber - 0)<1e-6)
            to = "("+zero+")";
        else if(Math.abs(this.realNumber - 0)<1e-6)
            to ="("+ i + "i)";
        else if(Math.abs(this.imaginaryNumber - 0)<1e-6)
            to ="("+ r + ")";
        else if(this.imaginaryNumber < 0)
            to ="("+ r +"" + i+"i)";
        else
            to = "(" + r+ "+" + i +"i)";
        return to;
    }
    /*@Override*/
    public boolean equals(Complex a) {
        if(Math.abs(this.realNumber - a.realNumber)<1e-6 && Math.sqrt(this.imaginaryNumber - this.imaginaryNumber)<1e-6)
            return true;
        else
            return false;
    }
}

public class ComplexDemo {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Complex c1, c2, c3, ret;
        c1 = new Complex(0, 0);
        c2 = new Complex(0, 0);

        double r1, i1, r2, i2, r3, i3;
        System.out.println("Enter complex c1:");
        r1 = sc.nextDouble();
        i1 = sc.nextDouble();
        c1.reset(r1, i1);

        System.out.println("Enter complex c2:");
        r2 = sc.nextDouble();
        i2 = sc.nextDouble();
        c2.reset(r2, i2);

        System.out.println("Enter complex c3:");
        r3 = sc.nextDouble();
        i3 = sc.nextDouble();
        c3 = new Complex(r3, i3);

        ret = c1.add(c2);
        System.out.println(c1 + " + " + c2 + " = " + ret.toString(3));

        ret = c1.sub(c2);
        System.out.println(c1 + " - " + c2 + " = " + ret.toString(3));

        ret = c1.mul(c2);
        System.out.println(c1 + " * " + c2 + " = " + ret.toString(3));

        try{
            ret = c1.div(c2);
            System.out.println(c1 + " / " + c2 + " = " + ret.toString(3));
        }
        catch(NumberFormatException e){
            System.out.println(c1 + " / " + c2 + " = (NaN)");
        }

        System.out.println(c1 + " == " + c2 + " ? " + c1.equals(c2));
        System.out.println(c1 + " == " + c3 + " ? " + c1.equals(c3));
        System.out.println(c2 + " == " + c3 + " ? " + c2.equals(c3));

        sc.close();
    }
}
