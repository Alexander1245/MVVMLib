# MVVM-Core
>**Core library for all of my new android applications.**

Available now:
- DebounceFlows. Simplify your instant search implementation.
- BaseViewModels for collecting your ui states and single events.
- BaseFragment with the viewModel and the viewBinding.

Will be released soon:
- BaseActivity, ModelMappers, etc.

**Dependencies**:<br>
Add it in your <I>settings.gradle</I> file.
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```
Add the dependency in your app or other android module <I>build.gradle</I> file.
Take a version from this repository's releases.
```groovy
dependencies {
  def LIB_VERSION = "1.0"
  
  implementation "com.github.Alexander1245.MVVMLib:coroutines:$LIB_VERSION"
  implementation "com.github.Alexander1245.MVVMLib:mvvm-core:$LIB_VERSION"
}
```


