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
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.proxy_screen.*
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.isValidPassword
import marketingexe.com.br.appstore.util.validate

abstract class FormEmailAndPasswordActivity : FormActivity(),  KeyboardUtils.OnSoftInputChangedListener {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KeyboardUtils.registerSoftInputChangedListener(this, this)

    }




    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }


    fun callPrivacyPolicyFragment( view: View ){
        val intent = Intent(
            this,
            MainActivity::class.java
        )

        /*
         * Para saber qual fragmento abrir quando a
         * MainActivity voltar ao foreground.
         * */
        intent.putExtra(
            MainActivity.FRAGMENT_ID,
            R.id.item_privacy_policy
        )

        /*
         * Removendo da pilha de atividades a primeira
         * MainActivity aberta (e a LoginActivity), para
         * deixar somente a nova MainActivity com uma nova
         * configuração de fragmento aberto.
         * */
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        startActivity( intent )
    }


    override fun onSoftInputChanged( height: Int ) {

        if (isAbleToCallChangePrivacyPolicyConstraints()) {
            changePrivacyPolicyConstraints(
                KeyboardUtils.isSoftInputVisible(this)
            )
        }
    }

    open fun isAbleToCallChangePrivacyPolicyConstraints()
            = true


    private fun changePrivacyPolicyConstraints(
        isKeyBoardOpened: Boolean
    ){

        val privacyId = tv_privacy_policy.id
        val parent = tv_privacy_policy.parent as ConstraintLayout
        val constraintSet = ConstraintSet()

        /*
         * Definindo a largura e a altura da View em
         * mudança de constraints, caso contrário ela
         * fica com largura e altura em 0dp.
         * */
        constraintSet.constrainWidth(
            privacyId,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        constraintSet.constrainHeight(
            privacyId,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        /*
         * Centralizando a View horizontalmente no
         * ConstraintLayout.
         * */
        constraintSet.centerHorizontally(
            privacyId,
            ConstraintLayout.LayoutParams.PARENT_ID
        )

        if( isConstraintToSiblingView(isKeyBoardOpened) ){
            /*
             * Se o teclado virtual estiver aberto, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada a
             * View acima dela (tv_sign_up).
             * */

            setConstraintsRelativeToSiblingView(constraintSet, privacyId )
        }
        else{
            /*
             * Se o teclado virtual estiver fechado, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada ao
             * fundo do ConstraintLayout ancestral.
             * */
            constraintSet.connect(
                privacyId,
                ConstraintLayout.LayoutParams.BOTTOM,
                ConstraintLayout.LayoutParams.PARENT_ID,
                ConstraintLayout.LayoutParams.BOTTOM
            )
        }

        constraintSet.applyTo( parent )
    }

    abstract fun isConstraintToSiblingView( isKeyBoardOpened : Boolean): Boolean

    abstract fun setConstraintsRelativeToSiblingView(constraintSet : ConstraintSet, privacyId : Int)
       : Unit

    }






