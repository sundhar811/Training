package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputClass {

    static boolean isInputStringNull(String name){
        if(name.isEmpty()){
            System.out.println("The input cannot be null.");
            return true;
        }
        else{
            return false;
        }
    }

    static boolean isNumberGreaterThanZero(int number){
        if(number>0){
            return true;
        }
        else{
            System.out.println("The input be zero or negative");
            return false;
        }
    }

    static boolean isNumberPositive(float number){
        if(number>=0){
            return true;
        }
        else{
            System.out.println("The input cannot be negative");
            return false;
        }
    }

    static int getIdFromConsole() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Id: ");
        int id = Integer.parseInt(br.readLine());

        return id;
    }

    static String getLoginNameFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Username: ");
        String uname = br.readLine();

        return uname;
    }

    static String getPasswordFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter password: ");
        String pwd = br.readLine();

        return pwd;
    }

    static String getNameFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Name: ");
        String name = br.readLine();

        return name;
    }

    static String getMailFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Email: ");
        String email = br.readLine();

        return email;
    }

    static String getYesOrNoFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Repeat process?: y/n");
        String choice = br.readLine();

        return choice;
    }

    static long getPhoneNoFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter phone number: ");
        long ph = Long.parseLong(br.readLine());

        return ph;
    }

    static String getAddressFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter address: ");
        String ad = br.readLine();

        return ad;
    }

    static int getPinCodeFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter pincode: ");
        int pc = Integer.parseInt(br.readLine());

        return pc;
    }

    static String getProductNameFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Product Name: ");
        String name = br.readLine();

        return name;
    }

    static int getProductStockFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Product Stock: ");
        int ps = Integer.parseInt(br.readLine());

        return ps;
    }

    static float getProductPriceFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Product Price: ");
        float pp = Float.parseFloat(br.readLine());

        return pp;
    }

    static String getChoiceFromConsole() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your choice: ");
        String choice = br.readLine();

        return choice;
    }

}
