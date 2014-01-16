package com.activeandroid.preference.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

@Table(name = "Settings")
public class Setting extends Model {
    public static final String NAME = "name";
    public static final String VALUE = "value";

    // TODO
    public static final Map<String, String> defaultMap = new HashMap<String, String>();

    /**
     * CREATE TABLE global (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT UNIQUE ON CONFLICT REPLACE,value TEXT)
     */

    @Column(name = NAME, unique = true, onUniqueConflict = ConflictAction.REPLACE)
    public String name;

    @Column(name = VALUE)
    public String value;

    public Setting() {
        super();
    }

    public Setting(String name, String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public static long getLong(String string) {
        return Long.valueOf(string);
    }

    // TODO
    public static Date getDate(String string) {
        return null;
    }

    public static Setting getSetting(String name) {
        return new Select().from(Setting.class)
            .where(Setting.NAME + " = ?", name).executeSingle();
    }

    public static String getValue(String name) {
        return getValue(name, null);
    }

    public static String getValue(String name, String defValue) {
        Setting s = getSetting(name);
        if (s == null) return defValue;
        return s.value;
    }

    public static Setting setValue(String name, long value) {
        return setValue(name, "" + value); // FIXME
    }

    public static Setting setValue(String name, boolean value) {
        return setValue(name, "" + value); // FIXME
    }

    // TODO
    public static Setting setValue(String name, String value, String defValue) {
        return null;
    }

    public static Setting setValue(String name, String value) {
        Setting s = getSetting(name);
        if (s != null) {
            s.value = value;
        } else {
            s = new Setting(name, value);
        }
        if (s != null) s.save();
        return s;
    }

    // TODO
    public static void addAll(Map<String, String> map) {
        return;
    }

    public static boolean getBoolean(String name, boolean defValue) {
        if (name == null) return defValue;
        String value = getValue(name);
        if (value == null) return defValue;
        return toBoolean(value, defValue);
    }

    public static boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    public static boolean getBoolean(String name, String defValue) {
        return getBoolean(name, toBoolean(defValue));
    }

    public static boolean toBoolean(String string) {
        return toBoolean(string, false);
    }

    public static boolean toBoolean(String string, boolean defValue) {
        if (string == null) return defValue;
        try {
            long i = Long.valueOf(string.trim());
            return (i != 0L);
        } catch (java.lang.NumberFormatException e) {
            return "true".equalsIgnoreCase(string.trim());
        }
    }

    public static void setBoolean(String name, boolean bool) {
        setValue(name, bool);
    }

    public static void negative(String name) {
        setBoolean(name, false);
    }

    public static void passive(String name) {
        setBoolean(name, true);
    }
}
