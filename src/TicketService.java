import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class TicketService {
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        while(true) {
            System.out.println("""
                        Welcome to the Ticket Service!
                        1.Create empty Ticket
                        2.Create limited ticket([Concert hall], [event code] and [time] required)
                        3.Create full ticket
                        0.Exit
                        """
            );
            switch (inputInt(sc.nextLine())){
                case 1:{
                        Ticket t = new Ticket();
                        System.out.println(t);
                        break;
                }
                case 2: {
                    System.out.println("Input Concert hall:");
                    String concertHall = inputString(validateStringLimits(sc.nextLine(),new char[][]{{'A','Z'},{'a','z'}}), 10);
                    System.out.println("Input Event code:");
                    String eventCode = validateEventCode(inputInt(sc.nextLine()));
                    System.out.println("Input Event time:");
                    LocalDateTime time = inputTime();
                    Ticket ticket = new Ticket(concertHall, eventCode, time.toEpochSecond(OffsetDateTime.now().getOffset()));
                    System.out.println(ticket);
                    break;
                }
                case 3: {
                    System.out.println("Writing full ticket:");
                    System.out.println("Input ID");
                    String ID = inputString(sc.nextLine(), 4);
                    System.out.println("Input Concert hall:");
                    String concertHall = inputString(validateStringLimits(sc.nextLine(),new char[][]{{'A','Z'},{'a','z'}}), 10);
                    System.out.println("Input Event code:");
                    String eventCode = validateEventCode(inputInt(sc.nextLine()));
                    System.out.println("Input Event time:");
                    LocalDateTime time = inputTime();
                    System.out.println("Is it Promo?:\n1.True\n2.False");
                    boolean isPromo = isPromo(inputInt(sc.nextLine()));
                    System.out.println("Input sector:");
                    char sector = inputString(validateStringLimits(sc.nextLine(),new char[][]{{'A','C'}, {'a','c'}}), 1).charAt(0);
                    System.out.println("Input allowed backpack weight:");
                    double weight = inputDouble(sc.nextLine(),3).doubleValue();
                    System.out.println("Input price:");
                    BigDecimal price = inputDouble(sc.nextLine(),2);
                    Ticket ticket = new Ticket(ID, concertHall, eventCode, time.toEpochSecond(OffsetDateTime.now().getOffset()), isPromo, sector, weight, price);
                    System.out.println(ticket);
                    break;
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.println("Try again!");
                }
            }
        }
    }

    public static String inputString(String result, int size) {
        if(result.length()>size || result.isEmpty()){
            System.out.println("Not valid! Must be size of " + size + " characters!");
            return inputString(sc.nextLine(), size);
        } else {
            return result;
        }
    }


    public static int inputInt(String input){
        if(input.isEmpty()){
            System.out.println("Not valid! Must contain digits!");
            return inputInt(sc.nextLine());
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) < '0' || input.charAt(i) > '9' ) {
                System.out.println("Not valid! Must contain digits!");
                return inputInt(sc.nextLine());
            }
        }
        return Integer.parseInt(input);
    }


    public static BigDecimal inputDouble(String input, int scale) {
        if(input.isEmpty()){
            System.out.println("Not valid! Must contain digits or/and '.'");
            return inputDouble(sc.nextLine(),scale);
        }
        int dotInput = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) < '0' | input.charAt(i) > '9') | input.charAt(i) == '.') {
                if(input.charAt(i)=='.' & dotInput == 1){
                    System.out.println("Not valid! Only one '.' allowed!");
                    return inputDouble(sc.nextLine(),scale);
                } else if(input.charAt(i)=='.') {
                    dotInput++;
                    continue;
                }
                System.out.println("Not valid! Must contain digits or/and '.'");
                return inputDouble(sc.nextLine(),scale);
            }
        }
        return BigDecimal.valueOf(Double.parseDouble(input)).setScale(scale, RoundingMode.HALF_UP);
    }


    public static String validateEventCode(int eventCode) {
        if (eventCode>0 & eventCode<10){
            return "00" + eventCode;
        }else if (eventCode > 9 & eventCode < 100) {
            return "0" + eventCode;
        } else if (eventCode > 100 & eventCode < 999) {
            return String.valueOf(eventCode);
        } else {
            System.out.println("Not valid! Must be digits between 0 and 999!");
            return validateEventCode(inputInt(sc.nextLine()));
        }
    }


    public static LocalDateTime inputTime() {
        System.out.println("Input Year:");
        int year = inputInt(sc.nextLine());
        while(year<1970){
            System.out.println("Try again!");
            year = inputInt(sc.nextLine());
        }
        System.out.println("Input Month Number:");
        int month = inputInt(sc.nextLine());
        while(month>12 || month<1){
            System.out.println("Try again!");
            month = inputInt(sc.nextLine());
        }
        System.out.println("Input Day Number:");
        int day = inputInt(sc.nextLine());
        while(
                ((day<1 || day>31) && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) ||
                        ((day<1 || day>30) && (month == 4 || month == 6 || month == 9 || month == 11)) || ((day<1 || day>28) && month == 2)
        ){
            System.out.println("Try again!");
            day = inputInt(sc.nextLine());
        }
        System.out.println("Input hour:");
        int hour = inputInt(sc.nextLine());
        while(hour<0 || hour>23){
            System.out.println("Try again!");
            hour = inputInt(sc.nextLine());
        }
        System.out.println("Input minutes:");
        int minutes = inputInt(sc.nextLine());
        while(minutes<0 || minutes>59){
            System.out.println("Try again!");
            minutes = inputInt(sc.nextLine());
        }
        return LocalDateTime.of(year, month, day, hour, minutes);
    }


    public static String validateStringLimits(String input,char[][] limits){
        int lim=0;
        if(input.isEmpty()){
            System.out.println("Not valid! Must contain characters!");
            return validateStringLimits(sc.nextLine(),limits);
        }
        for(int str = 0;str<input.length();str++){
            for (char[] limit : limits) {
                if (input.charAt(str) >= limit[0] & input.charAt(str) <= limit[1]) {
                    break;
                } else lim++;
            }
            if(lim> limits.length-1){
                System.out.println("Not valid! Must be between " + Arrays.deepToString(limits));
                return validateStringLimits(sc.nextLine(),limits);
            } else lim=0;
        }
        return input;
    }


    public static boolean isPromo(int input){
        if (input < 1 || input > 2) {
            System.out.println("Not valid! Input 1(True) or 2(False)");
            return isPromo(inputInt(sc.nextLine()));
        } else return input == 1;
    }

}