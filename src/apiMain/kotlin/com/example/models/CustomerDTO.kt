package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDTO(val id: Long, val firstName: String, val lastName: String, val email: String)