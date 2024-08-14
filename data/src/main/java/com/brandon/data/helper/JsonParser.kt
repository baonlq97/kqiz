package com.brandon.data.helper

import android.content.Context
import com.brandon.data.model.Meta
import com.brandon.data.model.Pagination
import com.brandon.data.model.QuestionJsonModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject

class JsonLoader @Inject constructor(
    private val context: Context
) {
    suspend fun loadAllJsonFromRaw(resourceId: Int): QuestionJsonModel {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                val reader = InputStreamReader(inputStream)
                val type = object : TypeToken<QuestionJsonModel>() {}.type
                Gson().fromJson(reader, type)
            }
            catch (ex: Exception) {
                QuestionJsonModel(data = emptyList(), meta = Meta(pagination = Pagination(0,0,0,0), name = ""))
            }
        }
    }
}