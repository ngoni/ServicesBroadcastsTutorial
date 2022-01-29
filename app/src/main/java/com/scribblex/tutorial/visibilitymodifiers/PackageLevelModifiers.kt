package com.scribblex.tutorial.visibilitymodifiers

private fun foo() {} // visible inside PackageLevelModifiers.kt

public var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in PackageLevelModifiers.kt

internal val baz = 6    // visible inside the same module