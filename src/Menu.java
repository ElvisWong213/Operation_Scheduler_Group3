import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public void run() {
        login();
    }

    private void login() {
        System.out.println("Enter User ID: ");
        String userID = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
    }
}
