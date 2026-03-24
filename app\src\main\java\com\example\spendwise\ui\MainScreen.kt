package com.example.spendwise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.spendwise.data.AppDatabase
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var recent by remember { mutableStateOf(listOf<com.example.spendwise.data.Transaction>()) }
    var totalDebits by remember { mutableStateOf(0.0) }
    var totalCredits by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            recent = db.transactionDao().recent(10)
            totalDebits = db.transactionDao().totalDebits() ?: 0.0
            totalCredits = db.transactionDao().totalCredits() ?: 0.0
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("SpendWise") }) }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Balance: ${"%.2f".format(totalCredits - totalDebits)}", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Total Credits: ${"%.2f".format(totalCredits)}")
            Text("Total Debits: ${"%.2f".format(totalDebits)}")
            Spacer(modifier = Modifier.height(12.dp))
            Text("Recent transactions:")
            Spacer(modifier = Modifier.height(8.dp))
            for (t in recent) {
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(t.merchant ?: "Unknown merchant")
                        Text("${t.type} - ${"%.2f".format(t.amount)}")
                        Text(t.rawText, maxLines = 2)
                    }
                }
            }
        }
    }
}
