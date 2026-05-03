package com.tantawi.tazkeer.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

// Helper gets the device location after permission is granted.
object LocationHelper {
    fun isLocationEnabled(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        activity: Activity,
        onSuccess: (Double, Double) -> Unit,
        onError: () -> Unit
    ) {
        if (!PermissionHelper.hasLocationPermission(activity)) {
            onError()
            return
        }

        val client = LocationServices.getFusedLocationProviderClient(activity)
        client.lastLocation
            .addOnSuccessListener { lastLocation ->
                if (lastLocation != null) {
                    onSuccess(lastLocation.latitude, lastLocation.longitude)
                } else {
                    client.getCurrentLocation(
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                        CancellationTokenSource().token
                    ).addOnSuccessListener { current ->
                        if (current != null) onSuccess(current.latitude, current.longitude) else onError()
                    }.addOnFailureListener { onError() }
                }
            }
            .addOnFailureListener { onError() }
    }
}
