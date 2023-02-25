# MVVM-Core
>**Core library for all of my new android applications.**

Available now:
- DebounceFlows. Simplify your instant search implementation.
- BaseViewModels for collecting your ui states and single events.
- BaseFragment with the viewModel and the viewBinding.

Will be released soon:
- BaseActivity, ModelMappers, etc.

**Dependencies**:<br>
Add it in your root <I>build.gradle</I> at the end of repositories:
```groovy
allprojects {
  repositories {
    google()
    mavenCentral()
    maven { url "https://jipack.io" }
    }
}
```
Add the dependency in your app or other android module <I>build.gradle</I> file.
Take a version from this repository's releases.
```groovy
dependencies {
  implementation 'com.github.Alexander1245:MVVMCore:$RELEASE_VERSION'
}
```


