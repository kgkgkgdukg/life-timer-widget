package com.lifetimer.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import java.util.Calendar

class LifeTimerWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            // Geburtsdatum: 31.01.2009
            val birthDate = Calendar.getInstance().apply {
                set(2009, Calendar.JANUARY, 31, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            // 90. Geburtstag: 31.01.2099
            val endDate = Calendar.getInstance().apply {
                set(2099, Calendar.JANUARY, 31, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val now = System.currentTimeMillis()
            val totalMs = (endDate - birthDate).toDouble()
            val remainingMs = (endDate - now).toDouble()

            // Prozent verbleibend: 100% = jetzt, 0% = 90. Geburtstag
            val percent = (remainingMs / totalMs * 100.0).coerceIn(0.0, 100.0)
            val percentText = "%.1f%%".format(percent)
            val progress = percent.toInt()

            val views = RemoteViews(context.packageName, R.layout.life_timer_widget)
            views.setTextViewText(R.id.tv_percent, percentText)
            views.setProgressBar(R.id.progress_bar, 100, progress, false)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
