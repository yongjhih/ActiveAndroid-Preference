ActiveAndroidPreference
=======================

Allow Preference using ActiveAndroid ORM to persist settings

Getting Start
=============

Override `getSharedPreferences()` in PreferenceActivity or PreferenceFragment:

```java
    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return new com.activeandroid.app.Settings();
    }
```
