package com.example.models

import com.example.Customer
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDTO(val id: Long = 0, val firstName: String, val lastName: String, val email: String)

fun CustomerDTO.toEntity() = Customer(this.id, this.firstName, this.lastName, this.email)