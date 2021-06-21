package meetingrooms.controller;

import meetingrooms.repository.InMemoryMeetingRoomsRepository;
import meetingrooms.repository.MariaDbMeetingRoomsRepository;
import meetingrooms.service.ListType;
import meetingrooms.service.MeetingRoomsService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MeetingRoomsController {

    private Scanner scanner = new Scanner(System.in);
    private MeetingRoomsService mrs = new MeetingRoomsService(new MariaDbMeetingRoomsRepository());

    public static void main(String[] args) {
        MeetingRoomsController mrc = new MeetingRoomsController();

        mrc.start();
    }

    private void start() {
        this.printMenu();

        String numberString;
        while ((numberString = this.getNumber()).length() == 0) {
            this.printMenu();
        }

        this.doProperActionByNumber(numberString);
        performAnotherOperation();
    }

    private void printMenu() {
        System.out.println("0. Tárgyaló rögzítése");
        System.out.println("1. Tárgyalók névsorrendben");
        System.out.println("2. Tárgyalók név alapján visszafele sorrendben");
        System.out.println("3. Minden második tárgyaló");
        System.out.println("4. Területek");
        System.out.println("5. Keresés pontos név alapján");
        System.out.println("6. Keresés névtöredék alapján");
        System.out.println("7. Keresés terület alapján");
        System.out.println("8. Kilépés");
    }

    private String getNumber() {
        String numberString = scanner.nextLine();
        List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
        if (numbers.contains(numberString)) {
            return numberString;
        } else {
            System.out.println("Nincs ilyen menüpont.");
            System.out.println();
            return "";
        }
    }

    private void doProperActionByNumber(String numberString) {
        switch (numberString) {
            case "0":
                saveMeetingRoom();
                break;
            case "1":
                writeMeetingRoomsOrderedByName(OrderingType.ABC);
                break;
            case "2":
                writeMeetingRoomsOrderedByName(OrderingType.BACK);
                break;
            case "3":
                writeEverySecondMeetingRoom();
                break;
            case "4":
                writeAreas();
                break;
            case "5":
                findByExactName();
                break;
            case "6":
                findByPrefix();
                break;
            case "7":
                writeMeetingRoomsAreaGreaterThan();
                break;
            case "8":
                exit();
        }
    }

    private void performAnotherOperation() {
        System.out.println("Kíván még más műveletet elvégezni? (i/n)");
        String answer = scanner.nextLine();
        if (answer.equals("i")) {
            start();
        } else {
            exit();
        }
    }

    private void saveMeetingRoom() {
        System.out.println("Kérem, adja meg a tárgyaló nevét, melyet el kíván menteni:");
        String name = scanner.nextLine();
        System.out.println();

        int width = getSizeOfMeetingRoom("szélességét");
        System.out.println();

        int length = getSizeOfMeetingRoom("hosszát");
        System.out.println();

        mrs.save(name, width, length);
        System.out.println("A tárgyaló adatai mentésre kerültek.");
        System.out.println();
    }

    private int getSizeOfMeetingRoom(String word) {
        int size = -1;
        while (size < 0) {
            try {
                System.out.println("Kérem, adja meg a tárgyaló " + word + " méterben:");
                size = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Nem megfelelő számérték.");
            }
        }
        return size;
    }

    private void writeMeetingRoomsOrderedByName(OrderingType type) {
        System.out.println("A tárgyalók nevei " + type.getWord() + "sorrendben:");
        System.out.println();
        if (type == OrderingType.ABC) {
            System.out.println(mrs.getMeetingroomsOrderedByName());
        } else if (type == OrderingType.BACK) {
            List<String> names = mrs.getMeetingroomsOrderedByName();
            Collections.reverse(names);
            System.out.println(names);
        } else {
            System.out.println("Ilyen művelet nem található a menürendszerben.");
        }
        System.out.println();
    }

    private void writeEverySecondMeetingRoom() {
        System.out.println("Minden második tárgyaló:");
        System.out.println();
        System.out.println(mrs.getEverySecondMeetingRoom());
    }

    private void writeAreas() {
        System.out.println("A tárgyalók területe csökkenő sorrendben:");
        System.out.println();
        List<String> dataOfMeetingRooms = mrs.getMeetingRoomsInString(ListType.AREAS);
        Collections.reverse(dataOfMeetingRooms);
        System.out.println(dataOfMeetingRooms);
    }

    private void findByExactName() {
        System.out.println("Adja meg a tárgyaló nevét, amelyről többet szeretne megtudni:");
        String name = scanner.nextLine();
        System.out.println();

        List<String> dataOfMeetingRoom = mrs.getMeetingRoomsInString(ListType.EXACT, name);
        if (dataOfMeetingRoom.size() == 0) {
            System.out.println("Sajnos nem található tárgyaló ilyen néven.");
        } else {
            System.out.println("A keresett tárgyaló adatai:");
            System.out.println(dataOfMeetingRoom.get(0));
            System.out.println();
        }
    }

    private void findByPrefix() {
        System.out.println("Adja meg a névtöredéket, ami alapján keresi a tárgyaló(ka)t:");
        String prefix = scanner.nextLine();
        System.out.println();

        List<String> dataOfMeetingRooms = mrs.getMeetingRoomsInString(ListType.PREFIX, prefix);
        if (dataOfMeetingRooms.size() == 0) {
            System.out.println("Sajnos nem található tárgyaló ilyen néven.");
        } else {
            System.out.println("A keresett tárgyalók adatai:");
            System.out.println(dataOfMeetingRooms);
            System.out.println();
        }
    }

    private void writeMeetingRoomsAreaGreaterThan() {
        System.out.println("Adjon meg egy területet, amelynél nagyobb tárgyalókat keres:");
        int area = -1;
        while (area < 0) {
            try {
                area = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Nem megfelelő számérték.");
            }
        }
        List<String> dataOfMeetingRooms = mrs.getMeetingRoomsInString(ListType.AREA_GREATER, area);
        System.out.println("A keresett tárgyalók adatai:");
        System.out.println(dataOfMeetingRooms);
        System.out.println();
    }

    private void exit() {
        System.out.println("Viszontlátásra!");
    }
}