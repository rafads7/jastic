package com.rafaelduransaez.core.permissions

data class PermissionsRequestHolder(
    val permissions: List<String>,
    val onAllGranted: () -> Unit
) {
    companion object {

        fun Companion.empty() = PermissionsRequestHolder(
            permissions = emptyList(), onAllGranted = {}
        )

        fun Companion.fromJasticPermission(
            permissionList: JasticPermission,
            onAllGranted: () -> Unit
        ) = fromJasticPermissions(listOf(permissionList), onAllGranted)

        private fun Companion.fromJasticPermissions(
            permissionList: List<JasticPermission>,
            onAllGranted: () -> Unit
        ) = PermissionsRequestHolder(
            permissions = permissionList.flatMap { it.toAndroidPermissions() },
            onAllGranted = onAllGranted
        )
    }
}