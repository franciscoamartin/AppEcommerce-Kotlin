package marketingexe.com.br.appstore.view

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.SystemClock
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.app.AppCompatActivity;
import android.text.*
import android.text.style.ImageSpan
import android.util.Patterns
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import marketingexe.com.br.appstore.R

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.proxy_screen.*
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*

abstract class FormActivity : AppCompatActivity(), TextView.OnEditorActionListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        /*
         * Hackcode para que a imagem de background do layout não
         * se ajuste de acordo com a abertura do teclado de
         * digitação. Caso utilizando o atributo
         * android:background, o ajuste ocorre, desconfigurando o
         * layout.
         * */
        window.setBackgroundDrawableResource(R.drawable.bg_activity)


        View.inflate(this, getLayoutResourceID(), fl_form)
    }

        abstract fun getLayoutResourceID() : Int
        /*
          * Para permitir que o back button tenha a ação de volta para
          * a atividade anterior.
          * */
        override fun onOptionsItemSelected( item: MenuItem): Boolean {
            if( item.itemId == android.R.id.home ){
                finish()
                return true
            }
            return super.onOptionsItemSelected( item )
        }

    override fun onEditorAction(view: TextView, actionId: Int, event: KeyEvent?): Boolean {

        mainAction()
        return false
    }

        protected fun showProxy( status: Boolean ){
            fl_proxy_container.visibility = if( status )
                View.VISIBLE
            else
                View.GONE
        }

    protected fun snackBarFeedback(
        viewContainer: View,
        status: Boolean,
        message: String
    ){
        val snackbar = Snackbar.make(
            viewContainer,message, Snackbar.LENGTH_LONG
        )

        val iconResource = if (status)
            R.drawable.ic_check_black_18dp
        else
            R.drawable.ic_close_black_18dp

        val img = ResourcesCompat.getDrawable(
            resources,
            iconResource,
            null
        )

        img!!.setBounds(0,0,img.intrinsicWidth, img.intrinsicHeight)

        val iconColor = if (status)
            ContextCompat.getColor( this,R.color.colorNavButton)
        else
            Color.RED

        img!!.setColorFilter( iconColor, PorterDuff.Mode.SRC_ATOP)

        val spannedText = SpannableString("     $message")
        spannedText.setSpan(
            ImageSpan(img, ImageSpan.ALIGN_BOTTOM),
            0,
            1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

        textView.setText( spannedText, TextView.BufferType.SPANNABLE)

        snackbar.show()
        }

    abstract fun blockFields ( status: Boolean)

    abstract fun isMainButtonSending (status: Boolean)

    fun mainAction ( view:View? = null){
        blockFields(true)
        isMainButtonSending(true)
        showProxy(true)

        backEndFakeDelay()
    }

    abstract fun backEndFakeDelay(): Unit

    protected fun backEndFakeDelay( statusAction: Boolean, feedbackMessage: String){
        Thread{
            kotlin.run {
                SystemClock.sleep(1000)

                runOnUiThread {
                    blockFields(false)
                    isMainButtonSending(false)
                    showProxy(false)

                    snackBarFeedback(
                        fl_form_container,
                        statusAction,
                        feedbackMessage
                    )
                }
            }
        }.start()
    }



}





