package me.porge.android.cars.vehicles.layers.presentation.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun Drawable.toBitmapDescriptor(): BitmapDescriptor {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)

    return Bitmap
        .createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        .apply { draw(Canvas(this)) }
        .let(BitmapDescriptorFactory::fromBitmap)
}