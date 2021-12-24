package com.archetype.manaslu.entity

data class Mountain(
    private val name: String,
    private val country: String,
    private val mountainSystem: String,
    private val highestPoint: HighestPoint
)

data class HighestPoint(
    private val elevation: String,
    private val prominence: String,
    private val isolation: String,
    private val listing: List<String>,
    private val coordinate: String
)
