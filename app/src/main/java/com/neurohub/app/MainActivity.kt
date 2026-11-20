package com.neurohub.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF0A0D1C)
private val Panel = Color(0xFF151B35)
private val Violet = Color(0xFF7657F6)
private val Blue = Color(0xFF367BFF)
private val SoftText = Color(0xFFB9C4E8)

data class ChatMessage(val text: String, val fromUser: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NeuroHubApp() }
    }
}

@Composable
fun NeuroHubApp() {
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Привет! Я НейроХаб — твой AI-помощник. Пока я работаю в демо-режиме. Скоро подключим настоящую нейросеть!", false)
        )
    }

    MaterialTheme(colorScheme = darkColorScheme(
        background = Navy, surface = Panel, primary = Violet,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Column(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color(0xFF111936), Navy, Color(0xFF090B16)))
            ).padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(48.dp).background(
                        Brush.linearGradient(listOf(Blue, Violet)), RoundedCornerShape(16.dp)
                    ),
                    contentAlignment = Alignment.Center
                ) { Text("✦", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("НЕЙРОХАБ", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("Твой личный искусственный интеллект", fontSize = 11.sp, color = SoftText)
                }
                Surface(color = Color(0xFF193A38), shape = CircleShape) {
                    Text("● ONLINE", Modifier.padding(horizontal = 10.dp, vertical = 6.dp), color = Color(0xFF71E0B2), fontSize = 10.sp)
                }
            }

            Spacer(Modifier.height(22.dp))
            Text("ТВОЙ AI-ПРОСТРАНСТВО", color = SoftText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(messages) { message ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = if (message.fromUser) Arrangement.End else Arrangement.Start
                    ) {
                        Surface(
                            color = if (message.fromUser) Color(0xFF353078) else Panel,
                            shape = RoundedCornerShape(
                                topStart = 18.dp, topEnd = 18.dp,
                                bottomEnd = if (message.fromUser) 5.dp else 18.dp,
                                bottomStart = if (message.fromUser) 18.dp else 5.dp
                            ),
                            modifier = Modifier.widthIn(max = 300.dp)
                        ) {
                            Text(message.text, Modifier.padding(14.dp), color = Color.White, fontSize = 14.sp, lineHeight = 20.sp)
                        }
                    }
                }
            }

            Surface(color = Panel, shape = RoundedCornerShape(22.dp)) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить", tint = SoftText)
                    Spacer(Modifier.width(8.dp))
                    BasicTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.weight(1f),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 14.sp),
                        singleLine = true,
                        decorationBox = { inner ->
                            if (input.isEmpty()) Text("Напиши сообщение...", color = SoftText, fontSize = 14.sp)
                            inner()
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            val text = input.trim()
                            if (text.isNotEmpty()) {
                                messages.add(ChatMessage(text, true))
                                messages.add(ChatMessage("Я получил твоё сообщение: «$text». Это пока демонстрационный режим. Подключение настоящего ИИ добавим следующим шагом.", false))
                                input = ""
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Violet)
                    ) { Icon(Icons.Default.Send, contentDescription = "Отправить", tint = Color.White) }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("NEUROHUB • BUILD 1.0 • DEMO", Modifier.align(Alignment.CenterHorizontally), color = Color(0xFF7884AA), fontSize = 9.sp)
        }
    }
}
