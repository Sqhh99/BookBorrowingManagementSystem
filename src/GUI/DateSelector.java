package GUI;
import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;
public class DateSelector extends Box {
    private final JComboBox<Integer> yearComboBox;
    private final JComboBox<Integer> monthComboBox;
    private final JComboBox<Integer> dayComboBox;
    public DateSelector() {
        super(BoxLayout.X_AXIS);

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // 月份是0-11
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建年份下拉框
        yearComboBox = new JComboBox<>();
        for (int i = currentYear; i <= currentYear + 5; i++) {
            yearComboBox.addItem(i);
        }
        yearComboBox.setSelectedItem(currentYear);

        // 创建月份下拉框
        monthComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        monthComboBox.setSelectedItem(currentMonth);

        // 创建日期下拉框
        dayComboBox = new JComboBox<>();
        updateDayComboBox(currentYear, currentMonth);
        dayComboBox.setSelectedItem(currentDay);

        // 添加事件监听器以更新日期下拉框
        yearComboBox.addActionListener(e -> updateDayComboBox((Integer) yearComboBox.getSelectedItem(), (Integer) monthComboBox.getSelectedItem()));
        monthComboBox.addActionListener(e -> updateDayComboBox((Integer) yearComboBox.getSelectedItem(), (Integer) monthComboBox.getSelectedItem()));

        JLabel yearLabel = new JLabel("年: ");
        JLabel monthLabel = new JLabel("月: ");
        JLabel dayLabel = new JLabel("日: ");

        // 添加组件到容器
        GUIUtils.addComponents(this, yearLabel, yearComboBox,createHorizontalStrut(10),
                monthLabel,monthComboBox,createHorizontalStrut(10),
                dayLabel,dayComboBox);
    }
    private void updateDayComboBox(int year, int month) {
        int daysInMonth = getDaysInMonth(year, month);
        dayComboBox.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            dayComboBox.addItem(i);
        }
    }
    private int getDaysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public Timestamp getSelectedTimestamp() {
        int year = (int) yearComboBox.getSelectedItem();
        int month = (int) monthComboBox.getSelectedItem() - 1; // 月份是0-11
        int day = (int) dayComboBox.getSelectedItem();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0); // 将时间部分设为 00:00:00
        calendar.set(Calendar.MILLISECOND, 0); // 将毫秒部分设为 0

        Date date = calendar.getTime();
        return new Timestamp(date.getTime());
    }
}
