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
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import marketingexe.com.br.appstore.R

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.info_block.*
import kotlinx.android.synthetic.main.proxy_screen.*
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.isValidPassword
import marketingexe.com.br.appstore.util.validate

class ForgotPasswordActivity : FormActivity() {



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



        et_email.setOnEditorActionListener(this)

        tv_info_block.text = getString(R.string.forgot_password_info)
        /*
 * Apresenta a tela de bloqueio que diz ao usuário que
 * algo está sendo processado em background e que ele
 * deve aguardar.
 * */
    }

    override fun getLayoutResourceID() = R.layout.content_forgot_password


    override fun blockFields ( status: Boolean){
        et_email.isEnabled = !status
        bt_recover_password.isEnabled = !status
    }

    override fun isMainButtonSending (status: Boolean){
        bt_recover_password.text = if ( status)
            getString(R.string.recover_password_going)
        else
            getString(R.string.recover_password)

    }


    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString(R.string.invalid_login_email)
        )
    }


    fun callForgotPasswordActivity( view: View ){
        Toast
            .makeText(
                this,
                "TODO: callForgotPasswordActivity()",
                Toast.LENGTH_SHORT
            )
            .show()
    }

    }





