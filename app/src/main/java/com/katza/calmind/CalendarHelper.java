package com.katza.calmind;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.javanet.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.*;

import java.util.*;

public class CalendarHelper {

    public static void addEvent(Context ctx, String title, Date date) throws Exception {

        GoogleAccountCredential cred =
                GoogleAccountCredential.usingOAuth2(
                        ctx,
                        Collections.singleton("https://www.googleapis.com/auth/calendar"));

        cred.setSelectedAccount(GoogleSignIn.getLastSignedInAccount(ctx).getAccount());

        Calendar service = new Calendar.Builder(
                AndroidHttp.newCompatibleTransport(),
                GsonFactory.getDefaultInstance(),
                cred
        ).setApplicationName("CalMind").build();

        Event event = new Event()
                .setSummary(title)
                .setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(date)))
                .setEnd(new EventDateTime().setDateTime(
                        new com.google.api.client.util.DateTime(date.getTime() + 3600000)));

        service.events().insert("primary", event).execute();
    }
}
