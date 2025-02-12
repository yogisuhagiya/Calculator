package com.example.calculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import com.example.calculator.Greeting
import com.example.shared.Calculator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorView()
                }
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CalculatorView() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("Add") }
    var expanded by remember { mutableStateOf(false) }
    val operations = listOf("Add", "Subtract", "Multiply", "Divide", "Square")
    val calculator = Calculator()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Math Solver",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Enter first number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (selectedOperation != "Square") {
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = num2,
                onValueChange = { num2 = it },
                label = { Text("Enter second number") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOperation,
                onValueChange = {},
                label = { Text("Select Operation") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                operations.forEach { operation ->
                    DropdownMenuItem(
                        text = { Text(operation) },
                        onClick = {
                            selectedOperation = operation
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val n1 = num1.toDoubleOrNull()
            val n2 = num2.toDoubleOrNull()

            result = when (selectedOperation) {
                "Add" -> n1?.let { calculator.add(it, n2 ?: 0.0).toString() } ?: "Invalid input"
                "Subtract" -> n1?.let { calculator.subtract(it, n2 ?: 0.0).toString() } ?: "Invalid input"
                "Multiply" -> n1?.let { calculator.multiply(it, n2 ?: 0.0).toString() } ?: "Invalid input"
                "Divide" -> n1?.let {
                    n2?.let { secondNum ->
                        calculator.divide(it, secondNum)?.toString() ?: "Cannot divide by zero"
                    } ?: "Invalid input"
                } ?: "Invalid input"
                "Square" -> n1?.let { calculator.square(it).toString() } ?: "Invalid input"
                else -> "Unknown operation"
            }
        }) {
            Text("Calculate")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Result: $result")
    }
}



@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        CalculatorView()
    }
}


