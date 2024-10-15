package org.lotka.xenon.presentation.util

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.yalantis.ucrop.UCrop
import org.lotka.xenon.domain.util.getFileName
import java.io.File


class CropActivityResultContract(
    private val AspectRatioX: Float,
    private val AspectRatioY: Float,
) :ActivityResultContract<Uri,Uri?>() {

    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(input,
            Uri.fromFile(
                File(context.cacheDir,
                context.contentResolver.getFileName(input)
                )
            )
        )
            .withAspectRatio(AspectRatioX, AspectRatioY)
            .getIntent(context)

    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == RESULT_OK && intent != null) {
            UCrop.getOutput(intent)
        } else {
            null
        }
    }

}

