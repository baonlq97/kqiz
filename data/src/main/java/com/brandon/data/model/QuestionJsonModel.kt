package com.brandon.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class QuestionJsonModel(
    @SerializedName("data")
    val data: List<QuestionJsonData>,
    @SerializedName("meta")
    val meta: Meta
)

data class QuestionJsonData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("attributes")
    val attributes: QuestionAttributes
)

data class QuestionAttributes(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("answerExplanation")
    val answerExplanation: String?,
    @SerializedName("options")
    val options: List<QuestionOptionJsonData>
)

data class QuestionOptionJsonData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("isValid")
    val isValid: Boolean
)

data class Meta(
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("name")
    val name: String
)

data class Pagination(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("total")
    val total: Int
)