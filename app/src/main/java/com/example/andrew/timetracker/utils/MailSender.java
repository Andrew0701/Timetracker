package com.example.andrew.timetracker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.andrew.timetracker.BuildConfig;
import com.example.andrew.timetracker.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by andrew on 3/11/18.
 */

public class MailSender {

    public static void send(String content, Context context) {
        String tasks = context.getString(R.string.tasks);
        File htmlFile = createTempTextFile(context, tasks + ".html", content);

        if (htmlFile == null) {
            Toast.makeText(context, R.string.cant_create_file, Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uriToFile = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, htmlFile);

        Intent shareIntent = ShareCompat.IntentBuilder.from((Activity) context)
                .setType("text/plain")
                .setText(tasks)
                .setStream(uriToFile)
                .setSubject(tasks)
                .getIntent();

        context.startActivity(android.content.Intent.createChooser(shareIntent, context.getString(R.string.send_email)));
    }

    private static File createTempTextFile(Context context, String fileName, String body) {
        File result = null;

        try {
            File root = context.getExternalCacheDir();

            result = new File(root, fileName);
            FileWriter writer = new FileWriter(result);
            writer.append(body);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
