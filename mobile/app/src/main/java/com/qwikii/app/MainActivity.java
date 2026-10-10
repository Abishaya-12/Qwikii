package com.qwikii.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private static final String PREFS = "qwikii_preferences";
    private static final String KEY_DARK = "dark_mode";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_NAME = "profile_name";
    private static final String KEY_EMAIL = "profile_email";
    private static final int INBOX = 0;
    private static final int CREATE_USER = 1;

    private final String[] languages = {"English", "Español", "Français"};
    private final Map<String, String[]> translations = new HashMap<>();
    private SharedPreferences preferences;
    private LinearLayout root;
    private LinearLayout page;
    private boolean darkMode;
    private int selectedLanguage;
    private int selectedPage = INBOX;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private String draftName = "";
    private String draftEmail = "";
    private String draftPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        darkMode = preferences.getBoolean(KEY_DARK, false);
        selectedLanguage = preferences.getInt(KEY_LANGUAGE, 0);
        initializeTranslations();
        render();
    }

    private void initializeTranslations() {
        translations.put("en", new String[]{
                "Inbox", "Create user", "Messages that matter, all in one place.",
                "Welcome to Qwikii", "Your inbox is ready when you are.",
                "Getting started", "Create a profile to make this app yours.",
                "New here?", "Create an account", "Your name", "Email address",
                "Password", "Create profile", "Please enter your name.",
                "Please enter a valid email address.", "Use at least 8 characters.",
                "Profile created on this device.", "Profile saved on this device.",
                "Appearance", "Light mode", "Dark mode", "Language",
                "Your messages", "Nothing to catch up on. Enjoy the calm.",
                "Sample message", "A little space for the things you care about.",
                "Account", "Your profile details stay on this device.",
                "Profile created", "Done", "Qwikii", "English", "Spanish",
                "French"
        });
        translations.put("es", new String[]{
                "Bandeja", "Crear usuario", "Tus mensajes importantes, en un solo lugar.",
                "Te damos la bienvenida a Qwikii", "Tu bandeja está lista cuando quieras.",
                "Comienza aquí", "Crea un perfil para personalizar la aplicación.",
                "¿Eres nuevo?", "Crear una cuenta", "Tu nombre", "Correo electrónico",
                "Contraseña", "Crear perfil", "Escribe tu nombre.",
                "Escribe un correo electrónico válido.", "Usa al menos 8 caracteres.",
                "Perfil creado en este dispositivo.", "Perfil guardado en este dispositivo.",
                "Apariencia", "Modo claro", "Modo oscuro", "Idioma",
                "Tus mensajes", "No hay novedades. Disfruta de la tranquilidad.",
                "Mensaje de ejemplo", "Un espacio para las cosas que te importan.",
                "Cuenta", "Los datos de tu perfil se guardan en este dispositivo.",
                "Perfil creado", "Listo", "Qwikii", "Inglés", "Español",
                "Francés"
        });
        translations.put("fr", new String[]{
                "Boîte de réception", "Créer un utilisateur", "Vos messages importants, au même endroit.",
                "Bienvenue sur Qwikii", "Votre boîte de réception est prête.",
                "Pour commencer", "Créez un profil pour personnaliser l’application.",
                "Nouveau ici ?", "Créer un compte", "Votre nom", "Adresse e-mail",
                "Mot de passe", "Créer le profil", "Veuillez saisir votre nom.",
                "Veuillez saisir une adresse e-mail valide.", "8 caractères minimum.",
                "Profil créé sur cet appareil.", "Profil enregistré sur cet appareil.",
                "Apparence", "Mode clair", "Mode sombre", "Langue",
                "Vos messages", "Rien de nouveau. Profitez de ce moment de calme.",
                "Message exemple", "Un espace pour ce qui compte pour vous.",
                "Compte", "Vos informations restent sur cet appareil.",
                "Profil créé", "Terminé", "Qwikii", "Anglais", "Espagnol",
                "Français"
        });
    }

    private String t(int index) {
        String language = selectedLanguage == 1 ? "es" : selectedLanguage == 2 ? "fr" : "en";
        return translations.get(language)[index];
    }

    private int backgroundColor() {
        return Color.parseColor(darkMode ? "#121820" : "#F5F7FA");
    }

    private int surfaceColor() {
        return Color.parseColor(darkMode ? "#1D2732" : "#FFFFFF");
    }

    private int textColor() {
        return Color.parseColor(darkMode ? "#F3F6FA" : "#17212B");
    }

    private int secondaryColor() {
        return Color.parseColor(darkMode ? "#B1BFCC" : "#596775");
    }

    private int borderColor() {
        return Color.parseColor(darkMode ? "#354352" : "#E0E6EC");
    }

    private int accentColor() {
        return Color.parseColor(darkMode ? "#65D5D2" : "#087F8C");
    }

    private void render() {
        getWindow().setStatusBarColor(backgroundColor());
        getWindow().setNavigationBarColor(surfaceColor());
        getWindow().getDecorView().setSystemUiVisibility(
                darkMode ? 0 : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            getWindow().getDecorView().setSystemUiVisibility(
                    darkMode ? 0 : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(backgroundColor());

        root.addView(createToolbar(), new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(68)));

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(24), dp(22), dp(24));
        scrollView.addView(page);
        root.addView(scrollView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        if (selectedPage == INBOX) {
            renderInbox();
        } else {
            renderCreateUser();
        }
        root.addView(createBottomNavigation(), new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(68)));
        setContentView(root);
    }

    private View createToolbar() {
        LinearLayout toolbar = new LinearLayout(this);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setPadding(dp(18), 0, dp(14), 0);
        toolbar.setBackgroundColor(surfaceColor());

        TextView brand = label(t(30), 20, textColor(), true);
        toolbar.addView(brand, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        Spinner languagePicker = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                new String[]{t(31), t(32), t(33)}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);
                item.setTextColor(textColor());
                return item;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getDropDownView(position, convertView, parent);
                item.setTextColor(textColor());
                item.setBackgroundColor(surfaceColor());
                item.setPadding(dp(12), dp(12), dp(12), dp(12));
                return item;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languagePicker.setAdapter(adapter);
        languagePicker.setSelection(selectedLanguage);
        languagePicker.setContentDescription(t(21));
        languagePicker.setBackgroundTintList(ColorStateList.valueOf(accentColor()));
        languagePicker.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            private boolean first = true;

            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (first) {
                    first = false;
                    return;
                }
                if (selectedLanguage != position) {
                    captureCreateUserDraft();
                    selectedLanguage = position;
                    preferences.edit().putInt(KEY_LANGUAGE, position).apply();
                    render();
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
        toolbar.addView(languagePicker, new LinearLayout.LayoutParams(dp(140), dp(48)));

        Button themeButton = new Button(this);
        themeButton.setText(darkMode ? t(19) : t(20));
        themeButton.setTextSize(12);
        themeButton.setTextColor(accentColor());
        themeButton.setAllCaps(false);
        themeButton.setMinWidth(0);
        themeButton.setPadding(dp(8), 0, dp(4), 0);
        themeButton.setBackgroundColor(Color.TRANSPARENT);
        themeButton.setOnClickListener(v -> {
            captureCreateUserDraft();
            darkMode = !darkMode;
            preferences.edit().putBoolean(KEY_DARK, darkMode).apply();
            render();
        });
        toolbar.addView(themeButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, dp(48)));
        return toolbar;
    }

    private void renderInbox() {
        TextView title = label(t(0), 30, textColor(), true);
        page.addView(title, bottomMargin(5));
        page.addView(label(t(2), 15, secondaryColor(), false), bottomMargin(24));

        String name = preferences.getString(KEY_NAME, "");
        if (!name.isEmpty()) {
            page.addView(card(t(3), t(17), name, 18), bottomMargin(18));
        } else {
            page.addView(card(t(4), t(5), t(6), 18), bottomMargin(18));
        }

        page.addView(label(t(22), 18, textColor(), true), bottomMargin(12));
        page.addView(card(t(24), t(25), t(23), 16), bottomMargin(14));
        if (!name.isEmpty()) {
            page.addView(card(t(26), t(3), name, 16), bottomMargin(14));
        }

        if (name.isEmpty()) {
            Button create = primaryButton(t(8));
            create.setOnClickListener(v -> {
                selectedPage = CREATE_USER;
                render();
            });
            page.addView(create, bottomMargin(12));
        }
    }

    private void renderCreateUser() {
        page.addView(label(t(1), 30, textColor(), true), bottomMargin(6));
        page.addView(label(t(7), 15, secondaryColor(), false), bottomMargin(4));
        page.addView(label(t(6), 14, secondaryColor(), false), bottomMargin(22));

        nameField = input(t(9), InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        emailField = input(t(10), InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        passwordField = input(t(11), InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        nameField.setText(draftName);
        emailField.setText(draftEmail);
        passwordField.setText(draftPassword);
        page.addView(nameField, bottomMargin(14));
        page.addView(emailField, bottomMargin(14));
        page.addView(passwordField, bottomMargin(18));

        Button submit = primaryButton(t(12));
        submit.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString();
            if (name.isEmpty()) {
                nameField.setError(t(13));
                nameField.requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.setError(t(14));
                emailField.requestFocus();
                return;
            }
            if (password.length() < 8) {
                passwordField.setError(t(15));
                passwordField.requestFocus();
                return;
            }

            preferences.edit().putString(KEY_NAME, name).putString(KEY_EMAIL, email).apply();
            draftName = "";
            draftEmail = "";
            draftPassword = "";
            Toast.makeText(this, t(16), Toast.LENGTH_LONG).show();
            selectedPage = INBOX;
            render();
        });
        page.addView(submit, bottomMargin(14));

        TextView note = label(t(27), 13, secondaryColor(), false);
        note.setGravity(Gravity.CENTER);
        page.addView(note, bottomMargin(8));
    }

    private View createBottomNavigation() {
        LinearLayout navigation = new LinearLayout(this);
        navigation.setGravity(Gravity.CENTER);
        navigation.setBackgroundColor(surfaceColor());
        navigation.setElevation(dp(8));
        navigation.addView(navButton(t(0), INBOX), new LinearLayout.LayoutParams(0, dp(58), 1));
        navigation.addView(navButton(t(1), CREATE_USER), new LinearLayout.LayoutParams(0, dp(58), 1));
        return navigation;
    }

    private Button navButton(String title, int destination) {
        Button button = new Button(this);
        button.setText(title);
        button.setAllCaps(false);
        button.setTextSize(14);
        button.setTypeface(null, selectedPage == destination ? Typeface.BOLD : Typeface.NORMAL);
        button.setTextColor(selectedPage == destination ? accentColor() : secondaryColor());
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setOnClickListener(v -> {
            captureCreateUserDraft();
            selectedPage = destination;
            render();
        });
        return button;
    }

    private void captureCreateUserDraft() {
        if (selectedPage == CREATE_USER && nameField != null) {
            draftName = nameField.getText().toString();
            draftEmail = emailField.getText().toString();
            draftPassword = passwordField.getText().toString();
        }
    }

    private View card(String heading, String subheading, String body, int padding) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(padding), dp(padding), dp(padding), dp(padding));
        card.setBackground(background(surfaceColor(), borderColor(), 16));
        card.addView(label(heading, 17, textColor(), true), bottomMargin(6));
        card.addView(label(subheading, 14, accentColor(), true), bottomMargin(8));
        card.addView(label(body, 14, secondaryColor(), false));
        return card;
    }

    private EditText input(String hint, int inputType) {
        EditText field = new EditText(this);
        field.setSingleLine(true);
        field.setTextSize(16);
        field.setHint(hint);
        field.setInputType(inputType);
        field.setTextColor(textColor());
        field.setHintTextColor(secondaryColor());
        field.setPadding(dp(14), 0, dp(14), 0);
        field.setBackground(background(surfaceColor(), borderColor(), 12));
        field.setMinHeight(dp(54));
        return field;
    }

    private Button primaryButton(String title) {
        Button button = new Button(this);
        button.setText(title);
        button.setAllCaps(false);
        button.setTextSize(16);
        button.setTypeface(null, Typeface.BOLD);
        button.setTextColor(Color.WHITE);
        button.setBackground(background(accentColor(), accentColor(), 12));
        button.setMinHeight(dp(54));
        return button;
    }

    private TextView label(String text, int size, int color, boolean bold) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(size);
        label.setTextColor(color);
        label.setLineSpacing(dp(2), 1f);
        if (bold) {
            label.setTypeface(null, Typeface.BOLD);
        }
        return label;
    }

    private GradientDrawable background(int fill, int stroke, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fill);
        drawable.setCornerRadius(dp(radius));
        drawable.setStroke(dp(1), stroke);
        return drawable;
    }

    private LinearLayout.LayoutParams bottomMargin(int margin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(margin);
        return params;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
