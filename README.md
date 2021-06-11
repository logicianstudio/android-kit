# AndroidKit
One Module for all basic need of an android developer

Latest AndroidKit Version [![](https://jitpack.io/v/infinitechub/android-kit.svg)](https://jitpack.io/#infinitechub/android-kit)

## Usage

For integration, at this point latest version looks like

```groovy
implementation 'com.github.infinitechub:android-kit:x.y.z'
```

### ActivityKit.kt

Extend Application class with `ApplicationKit`
```kotlin
class Application : ApplicationKit(){
    override fun onCreate() {
        super.onCreate()
    }
}
```

`ActivityKit` has base implementation of data binding forcing to use data binding instead of deprecated kotlin synthatic. 

```kotlin
class MainActivity : ActivityKit<ActivityMainBinding>() {
    override fun onCreateBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // other code here...
    }
}
```

ActivityResult and Dynamic Permissions using `ActivityResultContract`. 
Extend activity with ActivityKit, To make good use of the kit. 

To ask single dynamic permission
```kotlin
requestPermission(READ_EXTERNAL_STORAGE){ isGranted ->  
    // code here
} 
```

To ask multiple dynamic permissions
```kotlin
requestPermission(arrayOf(READ_EXTERNAL_STORAGE, CAMERA)){ grantMap ->
    // code here
} 
```

[Android MVVM](https://github.com/MustahsanJunaid/AndroidMVVM)
