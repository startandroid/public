package ru.startandroid.domain.model


data class Word (val id: Int, val value: String, val translate: String, val sample: String,
                        val definition: String, val rememberedCount: Int, val added: Long)