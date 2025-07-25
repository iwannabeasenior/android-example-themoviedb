package com.example.movie.base

class Queue<T> (list:MutableList<T>){

    var items: MutableList<T> = list

    fun isEmpty():Boolean = items.isEmpty()

    fun count():Int = items.count()

    override  fun toString() = items.toString()

    fun add(element: T){
        items.add(element)
    }

    @Throws(Exception::class)
    fun remove(): T {
        if (this.isEmpty()){
            throw Exception("fun 'remove' threw an exception: Nothing to remove from the queue.")
        } else {
            return items.removeAt(0)
        }
    }

    @Throws(Exception::class)
    fun removePop(): T {
        if (this.isEmpty()){
            throw Exception("fun 'remove' threw an exception: Nothing to remove from the queue.")
        } else {
            return items.removeAt(items.count()-1)
        }
    }

    fun remove(item: T): Boolean {
        return items.remove(item)
    }

    @Throws(Exception::class)
    fun element(): T {
        if(this.isEmpty()){
            throw Exception("fun 'element' threw an exception: Nothing in the queue.")
        }
        return items[0]
    }

    fun offer(element: T): Boolean{
        try{
            items.add(element)
        }catch (e: Exception){
            return false
        }
        return true
    }

    fun poll(): T?{
        if(this.isEmpty()) return null
        return items.removeAt(0)
    }

    fun peek():T?{
        if(this.isEmpty()) return null
        return items[0]
    }

    fun pop():T?{
        if(this.isEmpty()) return null
        return items[items.count()-1]
    }

    fun addAll(queue: Queue<T>){
        this.items.addAll(queue.items)
    }

    fun clear(){
        items.removeAll { true }
//        items.clear()
    }
    fun copy(element: T): Queue<T> {
        return Queue(items.plus(element).toMutableList())
    }

}
