package ensyu2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine(1990); // maxAmount set to 1990
        Scanner sc = new Scanner(System.in);

        System.out.println("--------いらっしゃいませ--------");
        insertMoney(vm, sc);
        sc.nextLine();
        // Main loop
        while (true) {
             // Handle money insertion
            showMenu(sc);         // Show options menu

            int choice = getValidInteger(sc);
            if (!processMenuChoice(vm, sc, choice)) break;
        }

        sc.close();
    }

    // Show options menu
    static void showMenu(Scanner sc) {
        System.out.println("\nメニュー:");
        System.out.println("1. 飲み物を購入");
        System.out.println("2. 残高確認");
        System.out.println("3. 返金");
        System.out.print("Input> ");
    }

    // Process menu choice
    static boolean processMenuChoice(VendingMachine vm, Scanner sc, int choice) {
        switch (choice) {
            case 1:
                buyDrink(vm, sc);
                break;
            case 2:
                vm.showBalance();
                break;
            case 3:
                vm.refund();
                System.out.println("終了します。ご利用ありがとうございました!");
                return false;
            default:
                System.out.println("無効な選択です。もう一度選んでください。");
        }
        return true;
    }

    // Handle drink purchase
    static void buyDrink(VendingMachine vm, Scanner sc) {
        vm.showStock();
        System.out.print("購入したい飲み物の番号を選択してください: ");
        int drinkChoice = getValidInteger(sc);

        if (vm.buyDrink(drinkChoice)) {
            System.out.println("購入成功！");
        } else {
            System.out.println("購入できません。");
        }
    }

    // Handle money insertion
    static void insertMoney(VendingMachine vm, Scanner sc) {
        while (true) {
            System.out.println("お金を投入してください:");
            System.out.println("1. 紙幣 (1000円)");
            System.out.println("2. 硬貨 (10円, 50円, 100円, 500円)");
            System.out.println("3. 紙幣と硬貨両方");
            System.out.print("Input> ");
            int moneyChoice = getValidInteger(sc);

            if (handleMoneyChoice(vm, sc, moneyChoice)) break;
        }
    }

    // Handle the money input for different choices
    static boolean handleMoneyChoice(VendingMachine vm, Scanner sc, int choice) {
        switch (choice) {
            case 1:  // Banknotes
                return insertBanknotes(vm, sc);
            case 2:  // Coins
                return insertCoins(vm, sc);
            case 3:  // Both
                return insertBoth(vm, sc);
            default:
                System.out.println("無効な選択です。もう一度選んでください。");
                return false;
        }
    }

    // Insert banknotes
    static boolean insertBanknotes(VendingMachine vm, Scanner sc) {
        System.out.print("紙幣を入れてください (1000円): ");
        int banknote = getValidInteger(sc);
        if (vm.insertBanknote(banknote)) {
            System.out.println("紙幣が投入されました。");
            return true;
        } else {
            System.out.println("投入できません。最大金額を超えていませんか？\n最大金額：\u00a5" + vm.getMaxAmount());
            return false;
        }
    }

    // Insert coins
    static boolean insertCoins(VendingMachine vm, Scanner sc) {
        System.out.print("硬貨を入れてください (10, 50, 100, 500円): ");
        int coin = getValidInteger(sc);
        if (vm.insertCoin(coin)) {
            System.out.println("硬貨が投入されました。");
            return true;
        } else {
            System.out.println("投入できません。硬貨の金額が無効か、最大金額を超えていませんか？\n最大金額：\u00a5" + vm.getMaxAmount());
            return false;
        }
    }

    // Insert both banknotes and coins
    static boolean insertBoth(VendingMachine vm, Scanner sc) {
        if (!insertBanknotes(vm, sc)) return false;  // Insert banknote
        return insertCoins(vm, sc);                   // Insert coins
    }

    // Get a valid integer input from the user
    static int getValidInteger(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("無効な入力です。数字のみ入力してください。\nInput> ");
            sc.next(); // Consume the invalid input
        }
        return sc.nextInt();
    }
}
