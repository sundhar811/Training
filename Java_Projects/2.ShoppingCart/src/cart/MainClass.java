package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

class MainClass {
    BufferedReader br;
    MainClass(){
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    void mainPage() throws IOException, SQLException, ClassNotFoundException {
        String choice;

        do{
            System.out.println("===================================WELCOME====================================");
            System.out.println("1.Seller Login \n2.User Login.\n3.Display Product list." +
                    "\n4.Exit the application.");
            System.out.print("Enter your choice: ");
            choice = br.readLine();

            switch (choice){
                case "1":
                    SellerClass sc = new SellerClass();
                    sc.frontPage();
                    break;
                case "2":
                    UserClass uc = new UserClass();
                    uc.frontPage();
                    break;
                case "3":
                    ProductClass.productFrontPage();
                    break;
                case "4":
                    System.out.print("Thank you.");
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }
        }while(!choice.equals("4"));
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException{
        MainClass mc = new MainClass();
        mc.mainPage();
    }
}
