package com.scribblex.tutorial.inheritance

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import java.io.File
import javax.sql.DataSource

class Inheritance {

    open class Base(p: Int)
    class Derived(p: Int) : Base(p)

    class MyView : View {
        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    }

    // ===================== OVERRIDING METHODS  ========================

    open class Shape {
        open val vertexCount: Int = 0
        open fun draw() {}
        fun fill() {}
    }

    class Circle : Shape() {
        override fun draw() {
            // do something
        }
    }

    open class Rectangle : Shape() {
        override val vertexCount: Int
            get() = 4

        final override fun draw() {
            // do something
        }
    }

    // ===================== OVERRIDING PROPERTIES  ========================

    open class Triangle : Shape() {
        override var vertexCount = 4
    }

    interface SomeShape {
        val vertexCount: Int
    }

    class SomeRectangle(override val vertexCount: Int = 4) : SomeShape

    class SomePolygon : SomeShape {
        override var vertexCount: Int = 0
    }

    // ================ CALLING THE SUPERCLASS IMPLEMENTATION ================

    open class AnotherRectangle {
        open fun draw() {
            println("Drawing a rectangle")
        }

        val borderColor: String get() = "black"
    }

    class FilledRectangle : AnotherRectangle() {
        override fun draw() {
            super.draw()
            println("Filling the rectangle")
        }

        val fillColor: String get() = super.borderColor
    }

    class AnotherFilledRectangle : AnotherRectangle() {
        override fun draw() {
            val filler = Filler()
            filler.drawAndFill()
        }

        inner class Filler {
            private fun fill() {
                println("Filling")
            }

            fun drawAndFill() {
                super@AnotherFilledRectangle.draw() // Calls Rectangle's implementation of draw()
                fill()
                println("Drawn a filled rectangle with color ${super@AnotherFilledRectangle.borderColor}") // Uses Rectangle's implementation of borderColor's get()
            }
        }
    }

    fun main() {
        val fr = AnotherFilledRectangle()
        fr.draw()
    }

    // ===================== OVERRIDING RULES ========================

    open class BaseRectangle {
        open fun draw() {}
    }

    interface Polygon {
        fun draw() {} // interface members are 'open' by default
    }

    class Square() : BaseRectangle(), Polygon {
        // The compiler requires draw() to be overridden:
        override fun draw() {
            super<BaseRectangle>.draw() // call to Rectangle.draw()
            super<Polygon>.draw() // call to Polygon.draw()
        }

    }

    // ===================== SEALED CLASSES ========================

    sealed interface Error // has implementations only in same package and module

    sealed class IOError() : Error  // extended only in same package and module

    class FileReaderError(f: File) : IOError()
    class DatabaseError(source: DataSource) : IOError()

    object RuntimeError : Error

    sealed class AnotherIOError {
        constructor()
        private constructor(description: String) : this()  // private is OK
        //public constructor(code: Int) : this() // Error: public and internal are not allowed
    }

    // ===================== Location of Direct Subclasses ==========

    open class CustomError() : Error // can be extended wherever it's visible

    // =====================  ENUM CLASSES  ==========================
    enum class Direction {
        NORTH, EAST, SOUTH, WEST
    }

    enum class Color(rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
    }

    // =====================  OBJECT EXPRESSIONS  ==========================

    val helloWorld = object {
        val hello = "Hello"
        val world = "World"

        // object expressions extend Any, so `override` is required on `toString()`
        override fun toString() = "$hello $world"
    }

    // =====================  SINGLETON  ==========================

    object DataProviderManager {
        fun registerDataProvider() {
            // ...
        }
    }

    class MyClass {
        companion object Factory {
            fun create(): MyClass = MyClass()
        }
    }
    val instance = MyClass.create()
}