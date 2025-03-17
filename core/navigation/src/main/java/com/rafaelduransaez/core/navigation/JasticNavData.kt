package com.rafaelduransaez.core.navigation

import kotlinx.serialization.Serializable


interface JasticNavData{
    @Serializable
    data object Empty : JasticNavData
}