package com.activeandroid.app;

//import com.google.android.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.SharedPreferences;

import  com.activeandroid.model.Setting;

public class Settings implements SharedPreferences {

    public static class Maps {
        public static <K, V> HashMap<K, V> newHashMap() {
            return new HashMap<K, V>();
        }
    }

    private final List<OnSharedPreferenceChangeListener> mListeners =
            new ArrayList<OnSharedPreferenceChangeListener>();


    public final class EditorImpl implements Editor {
        private final Map<String, Setting> mModified = Maps.newHashMap();
        private boolean mClear = false;

        @Override
        public Editor putString(String key, String value) {
            synchronized (this) {
                mModified.put(key, new Setting(key, value));
                return this;
            }
        }

        @Override
        public Editor putStringSet(String key, Set<String> values) {
            synchronized (this) {
                mModified.put(key, new Setting(key, values));
                return this;
            }
        }

        @Override
        public Editor putInt(String key, int value) {
            synchronized (this) {
                mModified.put(key, new Setting(key, value));
                return this;
            }
        }

        @Override
        public Editor putLong(String key, long value) {
            synchronized (this) {
                mModified.put(key, new Setting(key, value));
                return this;
            }
        }

        @Override
        public Editor putFloat(String key, float value) {
            synchronized (this) {
                mModified.put(key, new Setting(key, value));
                return this;
            }
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            synchronized (this) {
                mModified.put(key, new Setting(key, value));
                return this;
            }
        }

        @Override
        public Editor remove(String key) {
            synchronized (this) {
                mModified.put(key, null);
                return this;
            }
        }

        @Override
        public Editor clear() {
            synchronized (this) {
                mClear = true;
                return this;
            }
        }

        @Override
        public boolean commit() {
            synchronized (this) {
                if (mClear) {
                    Setting.clear();
                    mClear = false;
                }

                for (Map.Entry<String, Setting> e : mModified.entrySet()) {
                    String k = e.getKey();
                    Setting setting = e.getValue();
                    if (setting == null) {
                        Setting.remove(k);
                    } else {
                        String value = Setting.get(k);
                        if (value != null && value.equals(setting.value)) continue;
                        setting.save();
                    }
                    for (OnSharedPreferenceChangeListener listener : mListeners) {
                        if (listener != null) {
                            listener.onSharedPreferenceChanged(Settings.this, k);
                        }
                    }
                }

                mModified.clear();
                return true;
            }
        }

        @Override
        public void apply() {
            commit();
        }
    }

    @Override
    public Map<String, ?> getAll() {
        return Setting.getAll();
    }

    @Override
    public String getString(String key, String defValue) {
        return Setting.getString(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return Setting.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return Setting.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return Setting.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return Setting.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return Setting.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return Setting.contains(key);
    }

    @Override
    public Editor edit() {
        return new EditorImpl();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        synchronized(this) {
            mListeners.add(listener);
        }
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        synchronized(this) {
            mListeners.remove(listener);
        }
    }
}
