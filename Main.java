import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserDataApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Map<String, BufferedWriter> fileWriters = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            while (true) {
                System.out.println("Введите данные (Фамилия Имя Отчество ДатаРождения НомерТелефона Пол):");
                String input = scanner.nextLine();

                String[] data = input.split(" ");
                if (data.length != 6) {
                    System.out.println("Неправильное количество данных. Введите 6 значений.");
                    continue;
                }

                String lastName = data[0];
                String fileName = lastName + ".txt";
                BufferedWriter fileWriter = fileWriters.get(fileName);

                if (fileWriter == null) {
                    fileWriter = new BufferedWriter(new FileWriter(fileName, true));
                    fileWriters.put(fileName, fileWriter);
                }

                String firstName = data[1];
                String middleName = data[2];
                String birthDateStr = data[3];
                String phoneNumberStr = data[4];
                String gender = data[5];

                try {
                    String formattedDate = dateFormat.format(dateFormat.parse(birthDateStr));
                    long phoneNumber = Long.parseLong(phoneNumberStr);

                    if (!gender.equals("f") && !gender.equals("m")) {
                        throw new IllegalArgumentException("Пол должен быть 'f' или 'm'.");
                    }

                    fileWriter.write(lastName + firstName + middleName + formattedDate + " " + phoneNumber + gender);
                    fileWriter.newLine();
                    fileWriter.flush();
                } catch (ParseException e) {
                    System.out.println("Ошибка: Неверный формат даты рождения.");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Неверный формат номера телефона.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
