package com.example.andrew.timetracker.utils;

import android.content.Context;

import com.example.andrew.timetracker.R;
import com.example.andrew.timetracker.data.models.Task;

import java.util.List;

/**
 * Created by andrew on 3/11/18.
 */

public class TasksToHtmlParser {

    private static final String ROW_START = "<tr>";
    private static final String ROW_END = "</tr>";
    private static final String COLUMN_START = "<td>";
    private static final String COLUMN_END = "</td>";

    public static String createHtml(List<Task> tasks, Context context) {
        StringBuilder table = new StringBuilder();

        table.append("<html><body>");
        table.append("<meta charset=\"utf-8\">");
        table.append("<table border=\"1\">");

        appendHeader(table, context);

        for (int position = 0; position < tasks.size(); position++) {
            Task task = tasks.get(position);

            table.append(ROW_START);

            appendTaskRow(table, task);

            table.append(ROW_END);
        }

        table.append("</table>");
        table.append("</body></html>");

        return table.toString();
    }

    private static void appendTaskRow(StringBuilder table, Task task) {
        appendCell(table, task.getName());
        appendCell(table, task.getDescription());
        appendCell(table, Utils.getTimeByTimestamp(task.getTime()));
    }

    private static void appendCell(StringBuilder table, String data) {
        table.append(COLUMN_START)
                .append(data)
                .append(COLUMN_END);
    }

    private static void appendHeader(StringBuilder table, Context context) {
        appendCell(table, context.getString(R.string.task));
        appendCell(table, context.getString(R.string.description));
        appendCell(table, context.getString(R.string.time));
    }
}
