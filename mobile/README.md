# Qwikii Android app

This is a native Android starter app implemented in Java. Open the `mobile` folder in Android Studio and let Gradle sync, or build a debug APK from that folder in PowerShell:

```text
.\gradlew.bat assembleDebug
```

The app includes an inbox screen, a create-profile form, an English/Spanish/French language picker available on both screens, and a persisted light/dark appearance switch. Profiles are stored locally on the device; this starter app does not connect to an authentication service or inbox backend, and it never stores the password.
