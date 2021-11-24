import java.sql.*;
import java.util.Scanner;
// В базе данных postgres таблица employee, в ней имеются поля id - primary key, first_name - varchar(20), last_name - varchar(20).
// На локальной субд PostgreSQL
public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:7272/postgres";

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        Scanner scanner3 = new Scanner(System.in);
        Scanner scanner4 = new Scanner(System.in);
        Scanner scanner5 = new Scanner(System.in);
        Scanner scanner6 = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        Statement createStatement = connection.createStatement();
        String SQL_CREATETABLE = "CREATE TABLE employee (id SERIAL PRIMARY KEY,first_name varchar(20),last_name varchar(20));";
        createStatement.executeUpdate(SQL_CREATETABLE);
        while(true){
            System.out.println("1. Создать сотрудника");
            System.out.println("2. Просмотреть список сотрудников");
            System.out.println("3. Обновить сотрудника");
            System.out.println("4. Удалить сотрудника");
            System.out.println("5. Выйти");

            int command = scanner.nextInt();

            if (command == 2) { //view
                Statement statement = connection.createStatement();
                String SQL_SELECT = "SELECT * FROM employee ORDER BY id asc;";
                ResultSet result = statement.executeQuery(SQL_SELECT);

                while (result.next()) {

                    System.out.println(result.getInt("id")+" "+ result.getString("last_name")+" " + result.getString("first_name"));

                }
            } else if (command == 5) { //exit

                System.exit(0);

            } else if (command == 3) { //update

                String SQL_UPDATE = "UPDATE employee SET last_name = ?,first_name = ? WHERE id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
                System.out.println("Введите фамилию нового работника:");
                String lastname = scanner1.nextLine();
                System.out.println("Введите имя нового работника:");
                String name = scanner2.nextLine();
                System.out.println("Введите номер работника:");
                int num = scanner3.nextInt();
                preparedStatement.setString(1,lastname);
                preparedStatement.setString(2,name);
                preparedStatement.setInt(3,num);
                preparedStatement.executeUpdate();

            } else if (command == 4) { //delete

                System.out.println("Введите номер сотрудника, которого следует удалить:");
                int num = scanner3.nextInt();
                String SQL_DELETE = "DELETE FROM employee WHERE id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
                preparedStatement.setInt(1,num);
                preparedStatement.executeUpdate();


            } else if (command == 1) { //create


                String SQL_CREATE = "INSERT INTO employee (last_name, first_name) VALUES (?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE);
                System.out.println("Введите фамилию нового работника:");
                String lastname = scanner5.nextLine();
                System.out.println("Введите имя нового работника:");
                String firstname = scanner6.nextLine();
                preparedStatement.setString(1,lastname);
                preparedStatement.setString(2,firstname);
                preparedStatement.executeUpdate();

            }
            else {

                System.err.println("Команда не распознана!");

            }
        }
    }
}
