/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mobile.accounts;

import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static com.github.mobile.accounts.AccountConstants.ACCOUNT_TYPE;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AccountsException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;

import com.github.mobile.R;

import java.io.IOException;

/**
 * GitHub account model
 */
public class GitHubAccount {

    private static final String TAG = "GitHubAccount";

    private final Account account;

    private final AccountManager manager;

    /**
     * Create account wrapper
     *
     * @param account
     * @param manager
     */
    public GitHubAccount(final Account account, final AccountManager manager) {
        this.account = account;
        this.manager = manager;
    }

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return account.name;
    }

    /**
     * Get password
     *
     * @return password
     */
    public String getPassword() {
        return manager.getPassword(account);
    }

    /**
     * Get auth token
     *
     * @return token
     */
    public String getAuthToken() {
        AccountManagerFuture<Bundle> future = manager.getAuthToken(account,
                ACCOUNT_TYPE, false, null, null);

        try {
            Bundle result = future.getResult();
            return result != null ? result.getString(KEY_AUTHTOKEN) : null;
        } catch (AccountsException e) {
            Log.e(TAG, "Auth token lookup failed", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Auth token lookup failed", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + account.name + ']';
    }
}

// prepare intent which is triggered if the
// notification is selected

Intent intent = new Intent(this, NotificationReceiver.class);
PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
Notification n = new Notification.Builder(this)
        .setContentTitle("A pull request " + "has been merged")
        .setContentText("Github")
        .setSmallIcon(R.drawable.ic_stat_github)
        .setContentIntent(pIntent)
        .setAutoCancel(true)
         .build();


NotificationManager notificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

private class Intent {
    public Intent(GitHubAccount gitHubAccount, Class<NotificationReceiver> notificationReceiverClass) {
    }
}

notificationManager.notify(0, n);