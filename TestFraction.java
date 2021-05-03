package work;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Scanner;
import java.lang.ArithmeticException;

/*
功能如下：1.通分
        2.约分
        3.整理分子为0显示整体为0，而不是0/x
        4.分子=分母显示为1,而不是1/1
        5.控制分母！=0
        6.比较
        7.加减乘除（除法可以控制分母为0）
ps:get和set未添加，大家可自行生成
*/

class Fraction {////////**此处记住，因为传给方法是引用，切记不能胡乱更改，原值，别用a += 某某 来进行计算，用其他局部变量c = a + 某某 来算**\\\\\\
    private int molecular;
    private int denominator;
    static Fraction c = new Fraction();//作为全局的临时变量，可随时被覆盖，但记住不可长期使用，更不可跨方法

    public Fraction() {
    }

    public Fraction(int mo, int de) {//统一规定负数在分子上
        if (mo < 0 && de < 0) {
            mo *= -1;
            de *= -1;
        } else if (mo < 0 || de < 0) {
            mo = -Math.abs(mo);
            de = Math.abs(de);
        } else if (de == 0) {
            System.out.println("The denominator cannot be zero!");
            System.exit(1);
            return /*"error"*/;
        }
        molecular = mo;
        denominator = de;
    }

    public static Fraction copy(Fraction a) { // 把a对象的数据传给一个新的对象，并返回新对象的引用
        Fraction b = new Fraction();
        b.denominator = a.denominator;
        b.molecular = a.molecular;
        return b;
    }

    public Fraction Reduce() throws ArithmeticException{//辗转相除法求最大公约数//得区分好已经约分的对象和没有约分的对象
        int max, min, remainder = 1;
        max = molecular;////备注：辗转相除法不用判断大小，因为  3%7=3
        min = denominator;//备注：辗转相除法不用判断大小，因为  3%7=3
        remainder = max % min;
        while (remainder != 0) {
            max = min;
            min = remainder;
            remainder = max % min;
        }
        denominator /= min;
        molecular /= min;
        return this;
    }

    public static void passPoint(Fraction a, Fraction b) {//通分
        a.molecular *= b.denominator;
        b.molecular *= a.denominator;
        int t;
        t = a.denominator;
        a.denominator *= b.denominator;
        b.denominator *= t;
    }

    public static Fraction Add(Fraction a, Fraction b) {//测试 this指的是 . 前面的对象，b指的是（）中的
        if (a.denominator != b.denominator) {
            passPoint(a, b);
        }//通分
        // c = a;/////////此处错误极大，c和a 都是引用，指针，而不是对象本身，这样只是使得c指向了a指向的对象，而不是c复制了a的数据
        c = copy(a);
        c.molecular += b.molecular;
        c.Reduce();
        return c;
    }

    public static Fraction Less(Fraction a, Fraction b) {//a为被减数，b为减数 a-b = return
        if (a.denominator != b.denominator) {
            passPoint(a, b);
        }//通分
        //c =a;
        c = copy(a);
        c.molecular -= b.molecular;
        return c.Reduce();
    }

    public static Fraction Multiply(Fraction a, Fraction b) {
        //c=a;
        c = copy(a);
        c.molecular *= b.molecular;
        c.denominator *= b.denominator;
        return c.Reduce();
    }

    public static Fraction Division(Fraction a, Fraction b) throws ArithmeticException{//表示 a / b= a////添加除数分数为0的情况
        //c =b;
        c = copy(b);
        if(c.molecular == 0)
            throw new ArithmeticException("分母不为0");
        int t = c.denominator;
        c.denominator = c.molecular;
        c.molecular = t;
        return Multiply(a, c);
    }

    public static boolean equales(Fraction a, Fraction b) {//比较
        if (a.molecular == b.molecular && a.denominator == b.denominator)
            return true;
        else
            return false;
    }

    public String toString() {//返回字符串 1/
        this.Reduce();
        if (molecular < 0 && denominator < 0) {
            molecular *= -1;
            denominator *= -1;
        } else if (molecular < 0 || denominator < 0) {
            molecular = -Math.abs(molecular);
            denominator = Math.abs(denominator);
        }
        String to = new String();
        if (this.molecular == 0)
            to = this.molecular + "";
        else if (this.molecular == this.denominator)
            to = "1";
        else
            to = this.molecular + "/" + this.denominator;
        return to;
    }
}

public class TestFraction {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter fraction f1:");
        int m = s.nextInt();
        int d = s.nextInt();
        Fraction a = new Fraction(m, d);
        System.out.println("Please enter fraction f2:");
        m = s.nextInt();
        d = s.nextInt();
        Fraction b = new Fraction(m, d);
        Fraction c = new Fraction();
        try {
            c = Fraction.Division(a, b);
        } catch (ArithmeticException exception) {
            System.out.println("Cannot be divided by zero!");
            System.exit(1);
        }
        c = Fraction.Add(a, b);
        System.out.println(a.toString() + " + " + b.toString() + "= " + c.toString());
        c = Fraction.Less(a, b);
        System.out.println(a.toString() + " - " + b.toString() + "= " + c.toString());
        c = Fraction.Multiply(a, b);
        System.out.println(a.toString() + " * " + b.toString() + "= " + c.toString());
/*        try{ c = Fraction.Division(a,b);}
        catch (ArithmeticException exception){
            System.out.println("Cannot be divided by zero!");
            System.exit(1);
        }*/
        System.out.println(a.toString() + " / " + b.toString() + "= " + c.toString());
        if (Fraction.equales(a, b))
            System.out.printf("%s equals %s ? true", a.toString(), b.toString());
        else
            System.out.printf("%s equals %s ? false", a.toString(), b.toString());
    }
}





