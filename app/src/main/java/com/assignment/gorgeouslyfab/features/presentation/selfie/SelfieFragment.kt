package com.assignment.gorgeouslyfab.features.presentation.selfie

import android.Manifest
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
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.assignment.gorgeouslyfab.R
import com.assignment.gorgeouslyfab.core.exception.Failure
import com.assignment.gorgeouslyfab.core.extension.*
import com.assignment.gorgeouslyfab.core.platform.BaseFragment
import com.assignment.gorgeouslyfab.features.presentation.createreview.CreateReviewListener
import com.assignment.gorgeouslyfab.features.presentation.selfie.SelfieFragmentArgs.fromBundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_selfie.*
import timber.log.Timber
import java.io.InputStream

/**
 * Created by danieh on 04/08/2019.
 */
class SelfieFragment : BaseFragment(), CreateReviewListener {

    companion object {
        private val TAG = SelfieFragment::class.java.simpleName

        private const val REQUEST_CAPTURE_IMAGE = 1234
        private const val PICTURE_URI = "picture_uri"
    }

    override fun getData() = ""

    override fun getUri() = imageUri

    override fun layoutId() = R.layout.fragment_selfie

    private lateinit var viewModel: SelfieViewModel

    private val review by lazy {
        arguments?.let { fromBundle(it).review }
    }

    private var isWriteExternalStoragePermissionGranted = false

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        savedInstanceState?.let {
            imageUri = savedInstanceState.getParcelable(PICTURE_URI)
        }

        viewModel = viewModel(viewModelFactory) {
            observe(reviewCreated, ::showReviewCreated)
            failure(failure, ::showError)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            isTablet -> {
                selfie_create_review.gone()
                selfie_root.apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                }
            }
            else -> {
                selfie_create_review.visible()
                selfie_root.apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                }
            }
        }

        if (imageUri != null) {
            context?.let {
                val requestOptions = RequestOptions()
                requestOptions.transform(CenterCrop(), RoundedCorners(24))
                Glide.with(it)
                        .load(imageUri)
                        .apply(requestOptions)
                        .error(Glide.with(it).load(R.mipmap.ic_selfie_time_viewholder))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(selfi_image_container)
            }
        } else {
            context?.let {
                val requestOptions = RequestOptions()
                requestOptions.transform(CenterCrop(), RoundedCorners(24))
                Glide.with(it)
                        .load(R.mipmap.ic_selfie_time)
                        .apply(requestOptions)
                        .error(Glide.with(it).load(R.mipmap.ic_selfie_time_viewholder))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(selfi_image_container)
            }
        }

        selfie_button.setOnClickListener {
            openCamera()
        }

        selfie_create_review.setOnClickListener {
            review?.let {
                if (Uri.EMPTY != it.picture) {
                    viewModel.createReview(it)
                } else {
                    notify("Please complete your review before creating it!")
                }
            }
        }

        selfie_scroll_root.postDelayed({
            selfie_scroll_root.scrollToBottom()
        }, 300L)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PICTURE_URI, imageUri)
        Timber.tag(TAG).d("onSaveInstanceState: $outState")
    }

    private fun openCamera() {
        Dexter.withActivity(activity).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(object :
                PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                isWriteExternalStoragePermissionGranted = true
                openCameraIntent()
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                Timber.tag(TAG).d("onPermissionRationaleShouldBeShown")
                token?.continuePermissionRequest()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                Timber.tag(TAG).d("onPermissionDenied")
                isWriteExternalStoragePermissionGranted = false
                response?.let { deniedResponse ->
                    if (deniedResponse.isPermanentlyDenied) {
                        context?.let { context ->
                            val alertDialog = AlertDialog.Builder(context).apply {
                                setTitle(R.string.app_name)
                                setMessage(R.string.selfie_permissions_write_external_storage)
                                setPositiveButton(R.string.selfie_permissions_open_settings) { _, _ -> context.openAppSystemSettings() }
                            }.create()
                            alertDialog.show()
                        }
                    }
                }
            }
        }).check()
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
        Timber.tag(TAG)
                .d("onActivityResult: requestCode=$requestCode resultCode=$resultCode data=${data?.extras.toString()}")

        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {

            if (imageUri == null) {
                notify(getString(R.string.selfie_error))
                return
            }
            val uri: Uri = imageUri!!
            review?.picture = uri
            val ins: InputStream? = activity?.contentResolver?.openInputStream(uri)
            val img: Bitmap? = BitmapFactory.decodeStream(ins)
            ins?.close()
            if (img != null) {
                val bitmap = pictureTurn(img, uri)
                context?.let {
                    val requestOptions = RequestOptions()
                    requestOptions.transform(CenterCrop(), RoundedCorners(24))
                    Glide.with(it)
                            .load(bitmap)
                            .apply(requestOptions)
                            .error(Glide.with(it).load(R.mipmap.ic_selfie_time_viewholder))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(selfi_image_container)
                }
            } else {
                notify(getString(R.string.selfie_error))
            }
        }
    }

    private fun pictureTurn(img: Bitmap, uri: Uri): Bitmap {
        val columns = arrayOf(MediaStore.MediaColumns.DATA)
        val c = activity?.contentResolver?.query(uri, columns, null, null, null)
        if (c == null) {
            Timber.tag(TAG).d("Could not get cursor")
            return img
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

    private fun showReviewCreated(isCreated: Boolean?) {
        isCreated?.let {
            findNavController().navigate(SelfieFragmentDirections.actionSelfieFragmentToReviewsFragment())
        } ?: Timber.tag(TAG).d("showReviewCreatedError")
    }

    private fun showError(failure: Failure?) {
        //progress_reviews.gone()
        when (failure) {
            is Failure.BaseFailure -> {
                Timber.tag(TAG).d("showError")
            }
        }
    }
}

fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}
