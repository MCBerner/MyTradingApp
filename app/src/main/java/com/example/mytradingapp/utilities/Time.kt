package com.example.mytradingapp.utilities

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
fun Long.ToDateTimeString():String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(this*1000))
}