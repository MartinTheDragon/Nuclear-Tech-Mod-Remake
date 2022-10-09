## Kotlin

### Don't...

#### Self-reference with a backing field

When you need a subclass to implement a reference to itself (e.g. because of generics), make it an abstract function, not a property.

In testing, an equivalent of the following code broke sometimes because the reference became a **null pointer**, probably after garbage collection.

```kotlin
abstract class Base<T : Base> {
  protected abstract val self: T
}

class Sub : Base<Sub> {
  override val self = this
}
```

Instead, implement it as a function, which will always return a fresh pointer.

```kotlin
abstract class Base<T : Base> {
  protected abstract fun self(): T
}

class Sub : Base<Sub> {
  override fun self() = this
}
```
