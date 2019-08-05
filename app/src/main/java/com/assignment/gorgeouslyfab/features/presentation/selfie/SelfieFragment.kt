package com.assignment.gorgeouslyfab.features.presentation.selfie

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.extension.gone
import com.assignment.gorgeouslyfab.core.extension.visible
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_selfie.*
import timber.log.Timber
import java.io.InputStream

/**
 * Created by danieh on 04/08/2019.
 */
class SelfieFragment : BaseFragment() {

    companion object {
        private val TAG = SelfieFragment::class.java.simpleName

        private const val REQUEST_CAPTURE_IMAGE = 1234
    }

    override fun layoutId() = R.layout.fragment_selfie

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.tag(TAG).d("backStackEntryCount: ${fragmentManager?.backStackEntryCount}")
        Timber.tag(TAG).d("fragments: ${fragmentManager?.fragments}")

        if (isFirstTime) {
            if (!isTablet) {
                selfie_create_review.visible()
                selfie_root.apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                }
            } else {
                selfie_create_review.gone()
                selfie_root.apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                }
            }

        }

        if (imageUri != null) {
            context?.let {
                Glide.with(it)
                        .load(imageUri)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                        .error(Glide.with(it).load(R.mipmap.ic_review_viewholder))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(selfi_image_container)
            }
        } else {
            context?.let {
                Glide.with(it)
                        .load(R.mipmap.ic_selfie_time)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                        .error(Glide.with(it).load(R.mipmap.ic_review_viewholder))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(selfi_image_container)
            }
        }

        selfie_button.setOnClickListener {
            openCameraIntent()
        }

        selfie_create_review.isEnabled = imageUri != null
        selfie_create_review.setOnClickListener {
            findNavController().navigate(SelfieFragmentDirections.actionSelfieFragmentToReviewsFragment())
        }
    }

    private fun openCameraIntent() {
        val filename = "Gorgeous-${System.currentTimeMillis()}.jpg"

        val values = ContentValues()
        values.put(MediaStore.MediaColumns.TITLE, filename)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        imageUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.tag(TAG).d("onActivityResult: requestCode=$requestCode resultCode=$resultCode data=${data?.extras.toString()}")

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {

            if (imageUri == null) {
                notify(getString(R.string.selfie_error))
                selfie_create_review.isEnabled = false
                return
            }
            val uri: Uri = imageUri!!
            val ins: InputStream? = activity?.contentResolver?.openInputStream(uri)
            val img: Bitmap? = BitmapFactory.decodeStream(ins)
            ins?.close()
            if (img != null) {
                val bitmap = pictureTurn(img, uri)
                context?.let {
                    Glide.with(it)
                            .load(bitmap)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
                            .error(Glide.with(it).load(R.mipmap.ic_review_viewholder))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(selfi_image_container)
                    selfie_create_review.isEnabled = true
                }
            } else {
                notify(getString(R.string.selfie_error))
                selfie_create_review.isEnabled = false
            }
        }
    }

    private fun pictureTurn(img: Bitmap, uri: Uri): Bitmap {
        val columns = arrayOf(MediaStore.MediaColumns.DATA)
        val c = activity?.contentResolver?.query(uri, columns, null, null, null)
        if (c == null) {
            Timber.tag(TAG).d("Could not get cursor")
            return img;
        }

        c.moveToFirst()
        val str = c.getString(0)
        if (str == null) {
            Timber.tag(TAG).d("Could not get exif")
            return img
        }

        val exifInterface = ExifInterface(c.getString(0)!!)
        val orientation: Float =
                when (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                    else -> 0f
                }
        c.close()

        val mat: Matrix? = Matrix()
        mat?.postRotate(orientation)
        return Bitmap.createBitmap(img, 0, 0, img.width, img.height, mat, true)
    }
}