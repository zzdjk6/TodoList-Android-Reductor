---
presentation:
    theme: beige.css
---

<!-- slide -->

# Overview

* Introduction of Android Development Basics
  * Activity
  * Fragment
  * RecyclerView
* Introduction of Android App Architecture
* Small Tips

<!-- slide -->

# Notice

All of contents are based on my own experience and opinions, so:

* you don't have to understand everything.
* you don't have to follow everything.
* just grab a big picture then dig deeper if you have interest.
* let me know if you have a question anytime.

<!-- slide -->

## Android Basics

### Activity

* Just like a `window` in desktop apps
* We build UI inside `Activity`
* We can have multiple `Activities`
* Start another `Activity` by `Intent`
* `Main Activity` is the `Home Page`

<!-- slide -->

![](/images/screenshot-1.png) ![](/images/screenshot-2.png)

<!-- slide -->

![](/images/activity-lifecycle.png)

<!-- slide -->

### Fragment

* You can think of a `Fragment` as a modular section of an activity
* `Fragment` has its own lifecycle, receives its own input events
* You can add or remove `Fragments` while the activity is running 
* It's sort of like a `Sub Activity` that you can reuse in different activities

<!-- slide -->

### RecyclerView

* `List` is the most important UI element in mobile apps
* `RecyclerView` is the most recommended way to build `List` in Android app

![](/images/recyclerview.png)

<!-- slide -->

### RecyclerView

* Each item will be reused for performance
* UI element is not always be `Created` or `Destroyed`
* `RecyclerView` only instantiate enough `Item` to draw the UI
* We `Bind` the data to `Item` when we need to show a `Data Record`

<!-- slide -->

<div style="display: flex; align-items: center">
  <div style="flex: 1">
    <img src="/images/recyclerview-item.png" />
  </div>
  <div style="flex: 2">
  Assume 8 items per screen: only 9 items will be created, and the one out of screen will be recycled.
  </div>
</div>

<!-- slide -->

### Congratulations!

**You can start build android apps now**

_But build a maintainable app is not that easy_

<!-- slide -->

## Architecture

* Why _architecture_ matters?
  * _maintain_ problems
  * _collaborate_ problems
  * reduce _bugs_
* But what is a good _Architecture_?
  * It's a matter of _choice_

<!-- slide -->

### Vanilla

* Write code to any place you want
* Super easy to start
* Get stuck soon
* Bugs are everywhere and hard to debug
* e.g write everything into a single `Activity`

<!-- slide -->

### MVVM

![](/images/mvvm.png)

* Comes from `Microsoft`
* Widely-used in different context
* View is for render UI, its state are holded by ViewModel, when state changes automatically via `Binding`, view changes. ViewModel also talks to Model for data logic.

<!-- slide -->

### MVVM Benefits & Drawbacks

* Benefits
  * Seperated UI / Business Logic
  * Reusable `ViewModel` with different UI
  * Easy-to-test `ViewModel` and `Model` rather than unit test the whole `Acivity` or `Fragment`
* Drawbacks
  * More like a `Concept` rather than `Concrete Framework`
  * Everyone has its own implementation

<!-- slide -->

### Redux

* Enhanced version of `Facebook's Flux`
* Widely-used in Javascript world

<!-- slide -->

* __Three Principles__:
  * __Single source of truth__: The state of your whole application is stored in an object tree within a single store.
  * __State is read-only__: The only way to change the state is to emit an action, an object describing what happened.
  * __Changes are made with pure functions__: To specify how the state tree is transformed by actions, you write pure reducers.
* __Pure Function__: f(x) = y, given certain input, always give certain output, no side-effects.
* __Data Flow__: strict unidirectional (one-way)
  * old state --> actions --> reducers --> new state

<!-- slide -->

### Redux Benefits & Drawbacks

* __Benefits__:
  * Make state mutations __Predictable__: any time, the state of your app is __certain__
  * Easy-to-test __Reducers__: they are `pure functions`
  * __Snapshoot__ states and state histories for debug and `Time Travel`
* __Drawbacks__:
  * boilerplate code

<!-- slide -->

## Small Tips

* Saving Data: simply use `SharedPreferences`
* Consume Restful API: simply use `OkHttp`
* Observer pattern & Asynchronize: `RxJava`
  * `Rx` stands for `ReactiveX`, used for `FRP`
  * `Rx` is cross-language and cross-platform: `RxJava`, `RxJS`, `RxSwift`, etc
  * `Rx` allows composable and flatten `signal flow`: you will see a small example in demo

![](/images/rxjava.png)

<!-- slide -->

# Demo Time!

<!-- slide -->

# Thanks!

Feel free to follow me on:

* Medium: https://medium.com/@zzdjk6
* Github: https://github.com/zzdjk6