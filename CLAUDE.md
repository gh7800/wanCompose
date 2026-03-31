# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumented tests (requires emulator/device)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean
```

## Architecture Overview

### Stack
- **Jetpack Compose** for UI
- **Hilt** for dependency injection
- **Retrofit + OkHttp** for networking
- **Navigation Compose** for routing
- **Kotlin Coroutines + Channel** for async operations
- **Paging 3** for list pagination
- **DataStore** for cookie persistence

### Project Structure
```
app/src/main/java/cn/shineiot/wancompose/
├── MainActivity.kt          # Single activity, hosts NavHost
├── IApp.kt                  # Application singleton (holds static context)
├── route/                   # Navigation utilities
│   └── NavUtil.kt           # Singleton nav helper, uses composableX() extension
├── net/                      # Networking layer
│   ├── HttpService.kt       # Retrofit API interface
│   ├── RetrofitClient.kt    # OkHttp + Retrofit builder
│   ├── Resource.kt          # Result wrapper (Success/Error/Loading)
│   └── AddCookiesInterceptor.kt / SaveCookiesInterceptor.kt
├── ui/
│   ├── login/               # Login feature (ViewModel + Page)
│   ├── main/                # MainPage, SplashPage, HomePage, ProfilePage
│   └── theme/               # Colors, Typography, Shapes, Dimens
├── bean/                    # Data models (User, Message)
└── utils/                   # RouteConfig, SharePreferenceUtils, ToastUtil, LogUtil
```

### MVI Pattern (used in LoginViewModel)
- **State**: data class (e.g., `LoginState`) held via `mutableStateOf`
- **Intent**: sealed class for user actions (e.g., `LoginIntent`)
- **Event**: sealed class for one-time side effects (e.g., `LoginEvent` via Channel)
- ViewModel uses `dispatch(action: LoginIntent)` to handle intents and update state
- One-time events flow through a `Channel.receiveAsFlow()`

### Navigation
- Single `MainActivity` with one `NavHost` — no Fragment-based navigation
- Routes defined as string constants in `RouteConfig`
- `composableX()` custom extension on `NavGraphBuilder` (from `NavUtil.kt`) for declarative route registration
- Navigation via `NavUtil.get().navigation(route, params)` singleton

### Cookie / Session Management
- `AddCookiesInterceptor` and `SaveCookiesInterceptor` use DataStore to persist cookies across requests
- `IApp.CONTEXT` provides static application context for interceptors

### Base URL
- `HttpService.getBaseUrl()` provides the base URL — check `HttpService.kt` for the actual endpoint

### 忽略
- 不读取项目的build文件夹和app下的build文件夹和.git文件