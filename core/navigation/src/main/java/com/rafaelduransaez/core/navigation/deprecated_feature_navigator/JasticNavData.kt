package com.rafaelduransaez.core.navigation.deprecated_feature_navigator

import kotlinx.serialization.Serializable


interface JasticNavData{
    @Serializable
    data object Empty : JasticNavData
}