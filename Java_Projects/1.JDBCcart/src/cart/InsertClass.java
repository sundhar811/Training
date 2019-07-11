package cart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class InsertClass {
    private BufferedReader br;
    private String line_from_file;

    void userCredentialFile() throws SQLException, IOException, ClassNotFoundException {
        br = new BufferedReader(new FileReader("D:\\Training\\Files\\db_records\\user_details.csv"));

        String query1 = "INSERT INTO users(uname, umail) VALUES(?, ?)";
        String query2 = "INSERT INTO user_credentials(loginname, password) VALUES(?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps1 = DatabaseClass.connection.prepareStatement(query1);
        PreparedStatement ps2 = DatabaseClass.connection.prepareStatement(query2);

        while ((line_from_file=br.readLine())!=null){
            String[] temp = line_from_file.split(",");

            ps1.setString(1, temp[0]);
            ps1.setString(2, temp[1]);
            ps1.addBatch();

            ps2.setString(1, temp[2]);
            ps2.setString(2, temp[3]);
            ps2.addBatch();
        }

        ps1.executeBatch();
        ps2.executeBatch();

        System.out.println("Insertion Complete");

        DatabaseClass.terminateConnection();
        br.close();
    }

    void sellerCredentialFile() throws SQLException, IOException, ClassNotFoundException {
        br = new BufferedReader(new FileReader("D:\\Training\\Files\\db_records\\seller_details.csv"));

        String query1 = "INSERT INTO sellers(sname, smail) VALUES(?, ?)";
        String query2 = "INSERT INTO seller_credentials(loginname, password) VALUES(?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps1 = DatabaseClass.connection.prepareStatement(query1);
        PreparedStatement ps2 = DatabaseClass.connection.prepareStatement(query2);

        while ((line_from_file=br.readLine())!=null){
            String[] temp = line_from_file.split(",");

            ps1.setString(1, temp[0]);
            ps1.setString(2, temp[1]);
            ps1.addBatch();

            ps2.setString(1, temp[2]);
            ps2.setString(2, temp[3]);
            ps2.addBatch();
        }

        ps1.executeBatch();
        ps2.executeBatch();

        System.out.println("Insertion Complete");

        DatabaseClass.terminateConnection();
        br.close();
    }

    void userInfoFile() throws SQLException, IOException, ClassNotFoundException {
        br = new BufferedReader(new FileReader("D:\\Training\\Files\\db_records\\user_info.csv"));

        String query = "INSERT INTO user_details(uid, p_no, addr, pincode) VALUES(?, ?, ?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);

        while ((line_from_file = br.readLine()) != null) {
            String[] temp = line_from_file.split(",");

            ps.setInt(1, Integer.parseInt(temp[0]));
            ps.setLong(2, Long.parseLong(temp[1]));
            ps.setString(3, temp[2]);
            ps.setInt(4, Integer.parseInt(temp[3]));
            ps.addBatch();
        }

        ps.executeBatch();

        System.out.println("Insertion Complete");

        DatabaseClass.terminateConnection();
        br.close();
    }

    void sellerInfoFile() throws SQLException, IOException, ClassNotFoundException {
        br = new BufferedReader(new FileReader("D:\\Training\\Files\\db_records\\seller_info.csv"));

        String query = "INSERT INTO seller_details(sid, p_no, addr, pincode) VALUES(?, ?, ?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);

        while ((line_from_file = br.readLine()) != null) {
            String[] temp = line_from_file.split(",");

            ps.setInt(1, Integer.parseInt(temp[0]));
            ps.setLong(2, Long.parseLong(temp[1]));
            ps.setString(3, temp[2]);
            ps.setInt(4, Integer.parseInt(temp[3]));
            ps.addBatch();
        }

        ps.executeBatch();

        System.out.println("Insertion Complete");

        DatabaseClass.terminateConnection();
        br.close();
    }

    void productInfoFile() throws IOException, SQLException, ClassNotFoundException{
        br = new BufferedReader(new FileReader("D:\\Training\\Files\\db_records\\product_info.csv"));

        String query = "INSERT INTO product(sid, pname, stock, price) VALUES(?, ?, ?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);

        while ((line_from_file = br.readLine()) != null) {
            String[] temp = line_from_file.split(",");

            ps.setInt(1, Integer.parseInt(temp[0]));
            ps.setString(2, temp[1]);
            ps.setInt(3, Integer.parseInt(temp[2]));
            ps.setFloat(4, Float.parseFloat(temp[3]));
            ps.addBatch();
        }

        ps.executeBatch();

        System.out.println("Insertion Complete");

        DatabaseClass.terminateConnection();
        br.close();
    }

    void startFunction() throws SQLException, IOException, ClassNotFoundException{
        //userCredentialFile();
        //sellerCredentialFile();
        //userInfoFile();
        //sellerInfoFile();
        //productInfoFile();

    }

    /*
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException{
        InsertClass ic = new InsertClass();
        ic.startFunction();
    }
    */
}
