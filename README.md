# AndroidKit
All boiler-plate at one place for developing an Android Application

## Usage
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```


Add the dependency with latest version code [![](https://jitpack.io/v/logicianstudio/android-kit.svg)](https://jitpack.io/#logicianstudio/android-kit)

```groovy
implementation 'com.github.logicianstudio:android-kit:x.y.z'
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
