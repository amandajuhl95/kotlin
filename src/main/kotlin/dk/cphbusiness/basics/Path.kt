package dk.cphbusiness.basics

// Object is a singleton

// this is a way to not ask if something is null
sealed class Path<T> : Iterable<T> {

    class ValuePath<T>(val value: T, val rest : Path<T>) : Path<T>() {
        override fun iterator(): Iterator<T> = iterator {
         yield(value)
         yieldAll(rest)
        }
    }
    // this class prevents a nullpointer exception
    class EmptyPath<T> : Path<T>() {
        override fun iterator(): Iterator<T> = iterator { }
    }
}

fun main() {
    val names = Path.ValuePath("Anders", Path.ValuePath("Bente", Path.ValuePath("Christine", Path.EmptyPath())))
    for (name in names) println(name)
}


// here you have to ask if something is null

/*
class Path<T>(
    val value: T,
    val rest: Path<T>? = null ) : Iterable<T> {

    // (2) if we want to print what's inside the path, this is all you need
    fun print() {
        println(value)
        rest?.print()
    }

    // Hvad var det nu det her var? Virker både for (1) (2) og (3)
    override fun iterator(): Iterator<T> = iterator {
        yield(value)
        if (rest != null) yieldAll(rest!!)
    }
/*
    // (1) our own iterator
    override fun iterator(): Iterator<T> {
        return PathIterator(this)
    }

    class PathIterator<T>(var step: Path<T>?): Iterator<T> {
        override fun hasNext(): Boolean {
            return step != null
        }

        fun nextLocal(t: Path<T>?) : T {
            if ( t == null ) throw RuntimeException("Hovsa")
            val result = t.value
            step = t.rest
            return result
        }

        override fun next(): T {
            // return nextLocal(step)

            // this will overlook if result is null, DON'T USE THIS
            // val result = step!!.value

            // result gets the value null, if step is or is not null
            val result = step?.value

            // step either has the value null, or gets the value of rest
            step = step?.rest

            return result!!
        }

    }
 */
}

// (3)
fun <T>print(path: Path<T>?) {
    if(path == null) return
    println(path.value)
    print(path.rest)
}



fun main() {
    val names = Path("Anders", Path("Bente", Path("Christine")))

    // if we want to loop through the list, we need to create our own iterator (1)
    for (name in names) println(name)

    // this calls the method print (2)
    names.print()

    // (3)
    print(names)
}

 */