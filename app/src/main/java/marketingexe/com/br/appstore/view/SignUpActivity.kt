package marketingexe.com.br.appstore.view

import android.app.Activity
import android.content.Intent
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
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import marketingexe.com.br.appstore.R

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.content_sign_up.*
import kotlinx.android.synthetic.main.content_sign_up.et_email
import kotlinx.android.synthetic.main.content_sign_up.et_password
import kotlinx.android.synthetic.main.proxy_screen.*
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.isValidPassword
import marketingexe.com.br.appstore.util.validate

class SignUpActivity : FormEmailAndPasswordActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*
         * Hackcode para que a imagem de background do layout não
         * se ajuste de acordo com a abertura do teclado de
         * digitação. Caso utilizando o atributo
         * android:background, o ajuste ocorre, desconfigurando o
         * layout.
         * */


        /*
      * Colocando configuração de validação de campo de email
      * para enquanto o usuário informa o conteúdo deste campo.
      * */
        et_email.validate(
            {
                it.isValidEmail()
            },
            getString(R.string.invalid_email)
        )

        /*
         * Colocando configuração de validação de campo de senha
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_password.validate(
            {
                it.isValidPassword()
            },
            getString(R.string.invalid_password)
        )

        et_confirm_password.validate(
            {
                (et_password.text.isNotEmpty() && it.equals(et_password.text.toString()))
                        || et_password.text.isEmpty()
            },
            getString(R.string.invalid_confirmed_password)
        )

        et_confirm_password.setOnEditorActionListener(this)
        /*
 * Apresenta a tela de bloqueio que diz ao usuário que
 * algo está sendo processado em background e que ele
 * deve aguardar.
 * */
    }

   override fun getLayoutResourceID() = R.layout.content_sign_up


        override fun blockFields ( status: Boolean){
        et_email.isEnabled = !status
        et_password.isEnabled = !status
        et_confirm_password.isEnabled = !status
        bt_sign_up.isEnabled = !status
    }

    override fun isMainButtonSending (status: Boolean){
        bt_sign_up.text = if ( status)
            getString(R.string.sign_up_going)
        else
            getString(R.string.sign_up)

    }



    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString(R.string.invalid_sign_up_email)
        )
    }

    override fun isConstraintToSiblingView( isKeyBoardOpened : Boolean)
            = isKeyBoardOpened || ScreenUtils.isLandscape()

   override fun setConstraintsRelativeToSiblingView(constraintSet : ConstraintSet, privacyId : Int){
        constraintSet.connect(
            privacyId,
            ConstraintLayout.LayoutParams.TOP,
            bt_sign_up.id,
            ConstraintLayout.LayoutParams.BOTTOM,
            (12 * ScreenUtils.getScreenDensity()).toInt()
        )
    }

    fun callLoginActivity( view: View ){

        if (ActivityUtils.isActivityExistsInStack(LoginActivity::class.java)) {
            finish()
        } else {

            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            startActivity(intent)
        }
    }
}





