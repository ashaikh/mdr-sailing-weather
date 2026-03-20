# MDR Sailing Weather — Project History

## Project Overview
- **App Name:** Marina Del Rey Sailing Weather (launcher: "MDR Sailing Weather")
- **Package:** com.sailingweather
- **Repo:** https://github.com/ashaikh/mdr-sailing-weather
- **Publisher:** Foley Mobile Apps
- **Publisher Email:** foleymobileapps@gmail.com
- **Privacy Policy:** https://ashaikh.github.io/mdr-sailing-weather/privacy-policy.html
- **Current Version:** v0.1 (Initial Release)
- **Version Code:** 1 / Version Name: 1.0

## Architecture
- Single-screen MVVM app
- Kotlin + Jetpack Compose + Material 3
- OkHttp for HTTP networking
- Kotlin Coroutines + StateFlow for async/state
- Firebase Analytics + Crashlytics for telemetry
- Two data sources:
  - MAC weather station (HTTP, cleartext): http://www.marinaaquaticcenter.org/weather/MAC_Conditions.htm
  - NWS forecast API (HTTPS, no key needed): https://api.weather.gov/gridpoints/LOX/148,43/forecast

## File Structure
```
app/src/main/java/com/sailingweather/
├── MainActivity.kt                     # Entry point, Firebase init, theme detection
├── model/
│   ├── WeatherConditions.kt            # 14 weather fields + isDinghySailing (≤8 kts), windDirectionCompass
│   └── ForecastPeriod.kt               # NWS forecast period (name, temp, wind, forecast)
├── data/
│   ├── WeatherParser.kt                # Regex HTML parser for MAC station data
│   ├── WeatherDataSource.kt            # OkHttp fetcher for MAC station
│   ├── ForecastDataSource.kt           # OkHttp fetcher for NWS API (JSON)
│   └── Analytics.kt                    # Firebase custom events wrapper
├── viewmodel/
│   └── WeatherViewModel.kt             # Sealed UI state, 10-min auto-refresh, manual/pull refresh
└── ui/
    ├── theme/
    │   ├── Color.kt                    # Nautical palette (navy, ocean blue, sunset, sailing status)
    │   ├── Type.kt                     # SailingTypography
    │   └── Theme.kt                    # SailingWeatherTheme (dark + light)
    ├── screens/
    │   └── WeatherScreen.kt            # Main screen: Scaffold, PullToRefreshBox, 3 states
    └── components/
        ├── WindCard.kt                 # Hero wind card (48sp speed, compass, stats row)
        ├── ConditionsCard.kt           # "CURRENT CONDITIONS" — temp, humidity, barometer
        ├── SunMoonCard.kt              # Sunrise, sunset, moon phase with colored icons
        └── ForecastCard.kt             # NWS forecast periods (Tonight, Tomorrow, etc.)
```

## Key Configuration
- **Network security:** `res/xml/network_security_config.xml` allows cleartext HTTP to marinaaquaticcenter.org only
- **Min SDK:** 26 (Android 8.0)
- **Target/Compile SDK:** 35
- **Compose BOM:** 2024.12.01
- **OkHttp:** 4.12.0
- **Firebase BOM:** 33.7.0
- **Kotlin:** 2.1.0, AGP: 8.7.3

## Signing
- **Keystore file:** `release-keystore.jks` (in project root, gitignored)
- **Keystore password:** SailingWeather2026
- **Key alias:** sailing-weather
- **Key password:** SailingWeather2026
- **CRITICAL:** Back up this keystore. Without it you cannot update the app on the Play Store.

## Firebase
- **Project:** mdr-sailing-weather
- **Account:** foleymobileapps@gmail.com
- **Config file:** `app/google-services.json` (gitignored)
- **Services:** Analytics (unlimited free), Crashlytics (unlimited free)
- **Custom events tracked:**
  - `weather_loaded` — wind speed, direction, temp, humidity, barometer, dinghy status
  - `weather_refresh` — trigger type (app_open, manual, pull_to_refresh, auto)
  - `weather_error` — error message on fetch failure
  - `forecast_loaded` — period count, tonight/tomorrow temp and wind
  - `app_theme` — dark vs light mode

## App Behavior
- Auto-refreshes every **10 minutes** (matches MAC station update interval)
- Manual refresh via toolbar button or pull-to-refresh gesture
- Shows "Good for Dinghy Sailing" badge when wind ≤ 8 knots
- Wind speed color-coded: <5 gray, 5-15 green, 15-25 yellow, >25 red
- Navigation icon rotates to show wind direction
- Forecast shows 4 NWS periods (Tonight, Tomorrow, Tomorrow Night, day after)
- Dark/light theme follows system setting

## Build Artifacts
- **Debug APK:** `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK:** `app/build/outputs/apk/release/app-release.apk` (signed, 13MB)
- **Release AAB:** `app/build/outputs/bundle/release/app-release.aab` (signed, 13MB)

## Play Store Status
- **Developer account:** foleymobileapps@gmail.com — approved, $25 paid
- **Identity verification:** Approved (March 2026)
- **Store listing status:** Complete and submitted for review
- **Current track:** Closed testing — submitted for Google review (March 19, 2026)
- **Version uploaded:** versionCode 2, versionName 1.0
- **Production access:** Requires 20 opted-in testers for 14 days (Google policy for new accounts)
- **Internal test link:** https://play.google.com/apps/internaltest/4701244707637686545

## Play Store Completed
- [x] Developer account approved
- [x] App created in Play Console
- [x] Release AAB (signed, versionCode 2)
- [x] App icon (adaptive + 512x512 PNG: play_store_icon_512.png)
- [x] Feature graphic (1024x500: play_store_feature_graphic.png)
- [x] Privacy policy (live: https://ashaikh.github.io/mdr-sailing-weather/privacy-policy.html)
- [x] Screenshots (4 total: 2 dark mode + 2 light mode)
- [x] Store listing text (short + full description)
- [x] Content rating questionnaire (Rated Everyone)
- [x] Data safety form (Firebase Analytics + Crashlytics declared)
- [x] Advertising ID declaration (Yes — Analytics)
- [x] AD_ID permission removed from manifest (tools:node="remove")
- [x] Closed testing release submitted for review
- [x] Firebase Analytics + Crashlytics integrated

## What's Left for Production
- [ ] Google review of closed testing release (1-3 days)
- [ ] Recruit 20 testers to opt in via closed testing link
- [ ] 14-day closed testing period
- [ ] Promote to Production

## Keystore
- **Keystore file:** `release-keystore.jks` (in project root, gitignored)
- **Backup:** `MDR Sailing Apps Files/release-keystore.jks`
- **Keystore password:** SailingWeather2026
- **Key alias:** sailing-weather
- **Key password:** SailingWeather2026
- **CRITICAL:** Without this keystore you cannot update the app on the Play Store

## Git History
```
396bdff feat: add adaptive app icon, release signing config
c9b85a8 feat: add custom Firebase analytics events
11f1c47 feat: add Firebase Analytics and Crashlytics
1bf6de9 docs: add screenshots to README
f08058d docs: add README with features, tech stack, and build instructions
7a04d6f feat: rename to Marina Del Rey Sailing Weather, add NWS forecast, 10min refresh
65c9535 fix: resolve build issues from integration
7166740 feat: add WeatherScreen with pull-to-refresh and MainActivity entry point
0f1fc0f feat: add WeatherViewModel with loading/success/error states and pull-to-refresh
459e163 feat: add WindCard, ConditionsCard, SunMoonCard, and ForecastCard composables
f615c23 feat: add nautical-themed Material 3 color scheme and typography
e35605b feat: add OkHttp weather data source with HTTP cleartext support
92fe271 feat: add weather HTML parser with regex extraction and unit tests
83a62d2 feat: add WeatherConditions data model with compass conversion
1e5495c chore: scaffold Android project with Compose, OkHttp, and network config
```

## Build Commands
```bash
# Debug build + install
./gradlew assembleDebug && adb install -r app/build/outputs/apk/debug/app-debug.apk

# Release APK
./gradlew assembleRelease

# Release AAB (for Play Store upload)
./gradlew bundleRelease

# Run unit tests
./gradlew :app:testDebugUnitTest

# Launch on device
adb shell am start -n com.sailingweather/.MainActivity
```

## Target Devices
- Samsung Galaxy phones
- Google Pixel phones
- Any Android 8.0+ device
