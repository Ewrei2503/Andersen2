import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TicketService {
    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        System.out.println("""
                        Welcome to the Ticket Service!
                        1.Create empty Ticket
                        2.Create limited ticket([Concert hall], [event code] and [time] required)
                        3.Create full ticket
                        0.Exit
                        """
                );
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while(running) {
            int option = validateInt(sc);
            switch (option){
                case 1:{
                        Ticket t = new Ticket();
                        System.out.println(t);
                        break;
                }
                case 2: {
                    System.out.println("Write Concert hall:");
                    String concertHall = validateStringSize(sc, 10);
                    String eventCode = validateEventCode(sc);
                    System.out.println("Write Event time:");
                    LocalDateTime time = validateTime(sc);
                    Ticket ticket = new Ticket(concertHall, eventCode, time);
                    System.out.println(ticket);
                    break;
                }
                case 3: {
                    System.out.println("Write full ticket:");
                    System.out.println("Write ID");
                    String ID = validateStringSize(sc, 4);
                    System.out.println("Write Concert hall:");
                    String concertHall = validateStringSize(sc, 10);
                    String eventCode = validateEventCode(sc);
                    System.out.println("Write Event time:");
                    LocalDateTime time = validateTime(sc);
                    boolean isPromo = validatePromo(sc);
                    char sector = validateSector(sc);
                    System.out.println("Write backpack weight:");
                    double weight = validateDouble(sc, 3).doubleValue();
                    System.out.println("Write price:");
                    BigDecimal price = validateDouble(sc, 2);
                    Ticket ticket = new Ticket(ID, concertHall, eventCode, time, isPromo, sector, weight, price);
                    System.out.println(ticket);
                    break;
                }
                case 0: {
                    running = false;
                    break;
                }
                default: {
                    System.out.println("Try again!");
                }
            }
        }
    }

    public static String validateStringSize(Scanner sc, int size) {
        String result = sc.nextLine();
        while(result.length()>size){
            System.out.println("Not valid! Must be size of " + size + " characters!");
            result = sc.nextLine();
        }
        return result;
    }


    public static String validateEventCode(Scanner sc) {
        System.out.println("Write Event code:");
        int eventCode = validateInt(sc);
        while(true) {
            if (eventCode > 0 & eventCode < 100) {
                return String.valueOf('0' + (char) (eventCode / 10) + (char) (eventCode % 10));
            } else if (eventCode > 100 & eventCode < 999) {
                return String.valueOf(((char) (eventCode / 100)) + ((char) ((eventCode % 100) / 10)) + ((char) (eventCode % 10)));
            } else {
                System.out.println("Try again!");
                eventCode = validateInt(sc);
            }
        }
    }
    public static LocalDateTime validateTime(Scanner sc) {
        System.out.println("Write Year:");
        int year = validateInt(sc);
        while(year<1970){
            System.out.println("Try again!");
            year = validateInt(sc);
        }
        System.out.println("Write Month Number:");
        int month = validateInt(sc);
        while(month>12 || month<1){
            System.out.println("Try again!");
            month = validateInt(sc);
        }
        System.out.println("Write Day Number:");
        int day = validateInt(sc);
        while(
                ((day<1 || day>31) && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) ||
                        ((day<1 || day>30) && (month == 4 || month == 6 || month == 9 || month == 11)) || ((day<1 || day>28) && month == 2)
        ){
            System.out.println("Try again!");
            day = validateInt(sc);
        }
        System.out.println("Write hour:");
        int hour = validateInt(sc);
        while(hour<0 || hour>23){
            System.out.println("Try again!");
            hour = validateInt(sc);
        }
        System.out.println("Write minutes:");
        int minutes = validateInt(sc);
        while(minutes<0 || minutes>59){
            System.out.println("Try again!");
            minutes = validateInt(sc);
        }
        return LocalDateTime.of(year, month, day, hour, minutes);
    }

    public static int validateInt(Scanner sc){
        String input = sc.nextLine();
        boolean validInput = true;
        do{
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                    System.out.println("Try again!");
                    validInput = false;
                    input = sc.nextLine();
                    break;
                } else validInput = true;
            }
        } while(!validInput);
        return Integer.parseInt(input);
    }

    public static char validateSector(Scanner sc){
        System.out.println("Write sector:");
        String input = sc.nextLine();
        while(true) {
            if ((input.length() != 1) | (!(input.charAt(0) >= 'A' & input.charAt(0) <= 'C') & !(input.charAt(0) >= 'a' & input.charAt(0) <= 'c'))) {
                System.out.println("Try again!");
                input = sc.nextLine();
            } else break;
        }
        return input.charAt(0);
    }

    public static boolean validatePromo(Scanner sc){
        System.out.println("Is it Promo?:\n1.True\n2.False");
        int input = validateInt(sc);
        while(true) {
            if (input < 1 || input > 2) {
                System.out.println("Try again!");
                input = validateInt(sc);
            } else return input == 1;
        }
    }

    public static BigDecimal validateDouble(Scanner sc, int scale) {
        String doubleValue = sc.nextLine();
        boolean validInput = true;
        int dotInput = 0;
        do{
            for (int i = 0; i < doubleValue.length(); i++) {
                if ((doubleValue.charAt(i) < '0' | doubleValue.charAt(i) > '9') | doubleValue.charAt(i) == '.') {
                    if(doubleValue.charAt(i)=='.' & dotInput == 1){
                        System.out.println("Try again!");
                        doubleValue = sc.nextLine();
                        validInput = false;
                        dotInput = 0;
                        break;
                    } else if(doubleValue.charAt(i)=='.') {
                        dotInput++;
                        continue;
                    }
                    System.out.println("Try again!");
                    doubleValue = sc.nextLine();
                    validInput = false;
                    dotInput = 0;
                    break;
                } else validInput = true;
            }
        } while(!validInput);
        return BigDecimal.valueOf(Double.parseDouble(doubleValue)).setScale(scale, RoundingMode.HALF_UP);
    }
}