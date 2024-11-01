package uz.gita.entity.locale

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.common.data.CardData
import uz.gita.common.data.RecentTransferData
import javax.inject.Inject


class LastTransfersPref @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LastTransfersPref", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getCards(): List<RecentTransferData> {
        val json = sharedPreferences.getString("cards", null)
        val type = object : TypeToken<ArrayList<RecentTransferData>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun saveCards(list: ArrayList<RecentTransferData>) {
        val json = gson.toJson(list)
        sharedPreferences.edit().putString("cards", json).apply()
    }
}