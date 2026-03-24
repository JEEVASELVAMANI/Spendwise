SpendWise Android app scaffold

This scaffold creates a minimal Android Kotlin + Jetpack Compose app that:
- Listens for notifications (NotificationListenerService) to capture bank messages
- Parses messages to extract transaction data (amount, merchant, type)
- Stores transactions in Room database
- Shows a simple dashboard with balance and recent transactions

How to build
1. Open the `spendwise` folder in Android Studio.
2. Let Gradle sync. You may need to install the Kotlin and Android plugins if prompted.
3. Enable Notification access on the device/emulator for the app (Settings > Notification access) or run on a physical Android device and enable access.
4. Run the app.

Notes
- For SMS BroadcastReceiver you must request `RECEIVE_SMS` and `READ_SMS` runtime permissions and the Play Store restricts these permissions. Using NotificationListener avoids SMS permissions.
- This scaffold is minimal and intended as a starting point. Add tests and secure backups before production use.
\n