package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.AsteroidsAdapter
import com.udacity.asteroidradar.model.Asteroid

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("contentDescriptionByHazard")
fun setContentDescByHazard(imgView: ImageView, isHazardous: Boolean){
    if (isHazardous){
        imgView.contentDescription = imgView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else{
        imgView.contentDescription = imgView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("contentDescription")
fun setContentDescToTitle(imgView: ImageView, pod: PictureOfDay){
    if(pod.mediaType == "image"){
        Glide.with(imgView.context).load(pod.url).into(imgView)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

//@BindingAdapter("listData")
//fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
//    val adapter = recyclerView.adapter as AsteroidsAdapter
//    adapter.submitList(data)
//}
