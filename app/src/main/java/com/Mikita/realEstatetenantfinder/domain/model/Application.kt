package com.Mikita.realEstatetenantfinder.domain.model

data class Application(
    var propertyId: String="",
    var propertyName: String="",
    var status: String="", // e.g. "pending", "approved", "rejected"
    val landlordName: String="",
    val tenantName: String="",
    val id: String=""
)
