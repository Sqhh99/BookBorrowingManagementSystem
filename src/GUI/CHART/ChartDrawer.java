package GUI.CHART;

import SQL.JdbcUtils;
import org.knowm.xchart.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.knowm.xchart.style.Styler;

public class ChartDrawer {
    public static JPanel drawBarChart(String chartTitle, String xAxisTitle, String yAxisTitle, List<String> categories, List<Double> values,String series) {
        // 创建一个柱状图
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title(chartTitle)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // 添加数据
        chart.addSeries(series, categories, values);

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        // 创建一个 JPanel
        JPanel panel = new XChartPanel<>(chart);

        // 返回 JPanel
        return panel;
    }
    public static JPanel drawLineChart(String chartTitle, String xAxisTitle, String yAxisTitle, List<Double> xData, List<Double> yData) {
        // 创建一个折线图
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(chartTitle)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // 添加数据
        chart.addSeries("Series 1", xData, yData);

        // 创建一个 JPanel
        JPanel panel = new XChartPanel<>(chart);

        // 返回 JPanel
        return panel;
    }
    public static JPanel getXBookPriceStatisticsChartPanel() {
        List<String> bookNameLists = new ArrayList<>();
        List<Double> bookStockLists = new ArrayList<>();

        JPanel jPanel;
        try {
            Vector<Vector> lists = JdbcUtils.tableResultList("select name, price from books");
            for (Vector item : lists) {
                bookNameLists.add(item.get(0).toString());
                bookStockLists.add(Double.parseDouble(item.get(1).toString()));
            }

            jPanel = ChartDrawer.drawBarChart("书籍价格统计图","书名","价格",bookNameLists,bookStockLists,"价格");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jPanel;
    }
    public static JPanel getXBookInventoryBarChartPanel() {
        List<String> bookNameLists = new ArrayList<>();
        List<Double> bookStockLists = new ArrayList<>();

        JPanel jPanel;
        try {
            Vector<Vector> lists = JdbcUtils.tableResultList("select name, stock from books");
            for (Vector item : lists) {
                bookNameLists.add(item.get(0).toString());
                bookStockLists.add(Double.parseDouble(item.get(1).toString()));
            }

            jPanel = ChartDrawer.drawBarChart("书籍库存统计图","书名","库存",bookNameLists,bookStockLists,"库存数量");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jPanel;
    }
    public static JPanel getXBorrowingStatisticsChartPanel() {

        List<String> bookNameLists = new ArrayList<>();
        List<Double> bookStockLists = new ArrayList<>();

        JPanel jPanel;
        try {
            Vector<Vector> lists = JdbcUtils.tableResultList("select username,count(username) from borrowuserbook group by username");
            for (Vector item : lists) {
                bookNameLists.add(item.get(0).toString());
                bookStockLists.add(Double.parseDouble(item.get(1).toString()));
            }
            jPanel = ChartDrawer.drawBarChart("书籍借阅统计图","书名","价格",bookNameLists,bookStockLists,"借阅数量");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jPanel;
    }
}

