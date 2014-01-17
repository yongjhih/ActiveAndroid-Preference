package com.activeandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ConflictAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

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

    public Setting(String name) {
        this(name, (String) null);
    }

    public Setting(String name, String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public Setting(Setting setting) {
        this(setting.name, setting.value);
    }

    public Setting(String name, int value) {
        this(name, "" + value);
    }

    public Setting(String name, long value) {
        this(name, "" + value);
    }

    public Setting(String name, float value) {
        this(name, "" + value);
    }

    public Setting(String name, double value) {
        this(name, "" + value);
    }

    public Setting(String name, boolean value) {
        this(name, "" + value);
    }

    public Setting(String name, Set<String> values) {
        this(name, android.text.TextUtils.join(", ", new ArrayList<String>(values)));
    }

    /** @deprecated */
    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static Date getDate(String key, Date defValue) {
        // TODO
        return null;
    }

    public static Setting getSetting(String name) {
        if (android.text.TextUtils.isEmpty(name)) return null;
        return new Select().from(Setting.class)
            .where(Setting.NAME + " = ?", name).executeSingle();
    }

    public static String get(String name) {
        return get(name, null);
    }

    public static String get(String name, String defValue) {
        Setting s = getSetting(name);
        if (s == null) return defValue;
        return s.value;
    }

    public static Setting set(String name, int value) {
        return set(name, "" + value); // FIXME
    }

    public static Setting set(String name, long value) {
        return set(name, "" + value); // FIXME
    }

    public static Setting set(String name, float value) {
        return set(name, "" + value); // FIXME
    }

    public static Setting set(String name, double value) {
        return set(name, "" + value); // FIXME
    }

    public static Setting set(String name, boolean value) {
        return set(name, "" + value); // FIXME
    }

    public static Setting set(String name, Set<String> values) {
        return set(name, android.text.TextUtils.join(", ", new ArrayList<String>(values)));
    }

    /** @deprecated */
    public static Setting set(String name, String value, String defValue) {
        // TODO
        return null;
    }

    public static Setting set(String name, String value) {
        Setting s = getSetting(name);
        if (s != null) {
            s.value = value;
        } else {
            s = new Setting(name, value);
        }
        if (s != null) s.save();
        return s;
    }

    public static void addAll(Map<String, String> map) {
        // TODO
        return;
    }

    public static boolean getBoolean(String name, boolean defValue) {
        if (name == null) return defValue;
        String value = get(name);
        if (value == null) return defValue;
        return toBoolean(value, defValue);
    }

    /** @deprecated */
    public static boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    public static boolean getBoolean(String name, String defValue) {
        return getBoolean(name, toBoolean(defValue));
    }

    /** @deprecated */
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

    public static void negative(String name) {
        set(name, false);
    }

    public static void passive(String name) {
        set(name, true);
    }

    public static Map<String, ?> getAll() {
        List<Setting> settings = new Select().from(Setting.class).execute();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Setting s : settings) {
            map.put(s.name, s.value);
        }
        return map;
    }

    public static String getString(String key, String defValue) {
        return get(key, defValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defValues) {
        String v = get(key);
        if (v == null) return defValues;
        return new HashSet<String>(Arrays.asList(v.split(", ")));
    }

    public static int getInt(String key, int defValue) {
        String v = get(key);
        try {
            return Integer.valueOf(v);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public static long getLong(String key, long defValue) {
        String v = get(key);
        try {
            return Long.valueOf(v);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public static float getFloat(String key, float defValue) {
        String v = get(key);
        try {
            return Float.valueOf(v);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public static boolean contains(String key) {
        return getSetting(key) != null;
    }

    public static void remove(String name) {
        Setting s = getSetting(name);
        if (s != null) s.delete();
    }

    public static void clear() {
        new Delete().from(Setting.class).executeSingle();
    }
}
