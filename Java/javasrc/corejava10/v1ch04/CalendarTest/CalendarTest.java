import java.time.*;

/**
 * 显示当前月的日历
 * @version 1.0.1 2019-12-24
 * @author bruce
 */
public class CalendarTest {

    private static final String[] CALENDAR_TITLE = {
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    };
    private static final String CALENDAR_SPACE = "    ";

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        // 修改 date
//        date = date.minusDays(30);
        int year = date.getYear();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();
        System.out.printf("%4d年%02d月", year, month);
        System.out.println();
        System.out.println(String.join(" ",CALENDAR_TITLE));

        // 将 date 设置为当月第 1 天, 并得到这一天为星期几
        date = date.minusDays(today - 1);
        DayOfWeek weekday = date.getDayOfWeek();
        int weekdayValue = weekday.getValue(); // 1 = Mon, ... 7 = Sun

        // 日历的第 1 行是缩紧的
        for (int i = 1; i < weekdayValue; i++) {
            System.out.print(CALENDAR_SPACE);
        }

        // 打印日历主体
        while (date.getMonthValue() == month) {
            int currentDay = date.getDayOfMonth();
            System.out.printf("%3d", currentDay);
            if (currentDay == today) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() == 1) {
                System.out.println();
            }
        }

    }
}
