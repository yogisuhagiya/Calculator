package com.example.shared  // Change package name as per your project

class Calculator {


    fun add(num1: Double, num2: Double): Double{
        return num1 + num2
    }
    fun subtract(num1: Double, num2: Double): Double{
        return num1 - num2
    }
    fun divide(num1: Double, num2: Double): Double?{
        if (num2 !=0.0){
            return num1/num2
        }
        else {
            return null
        }
    }

    fun multiply(num1: Double, num2: Double): Double{
        return num1 * num2
    }
    fun square(num1: Double): Double{
        return num1 * num1;
    }
}