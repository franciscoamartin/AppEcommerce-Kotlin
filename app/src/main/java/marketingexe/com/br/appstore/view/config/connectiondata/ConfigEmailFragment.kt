package marketingexe.com.br.appstore.view.config.connectiondata


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_config_email.*

import marketingexe.com.br.appstore.R
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.validate
import marketingexe.com.br.appstore.view.FormFragment

class ConfigEmailFragment :
    FormFragment() {

    companion object{
        const val TAB_TITLE = R.string.config_connection_data_tab_email
    }

    override fun getLayoutResourceID()
            = R.layout.fragment_config_email

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        bt_update_email_login.setOnClickListener{
            callPasswordDialog()
        }

        et_current_email.validate(
            {
                it.isValidEmail()
            },
            getString( R.string.invalid_email )
        )

        et_new_email.validate(
            {
                it.isValidEmail()
            },
            getString( R.string.invalid_email )
        )

        et_new_email_confirm.validate(
            {
                /*
                 * O toString() em et_new_email.text.toString() é
                 * necessário, caso contrário a validação falha
                 * mesmo quando é para ser ok.
                 * */
                (et_new_email.text.isNotEmpty()
                        && it.equals( et_new_email.text.toString() ))
                        || et_new_email.text.isEmpty()
            },
            getString( R.string.invalid_confirmed_email )
        )

        et_new_email_confirm.setOnEditorActionListener( this )
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_sign_up_email )
        )
    }

    override fun blockFields( status: Boolean ){
        et_current_email.isEnabled = !status
        et_new_email.isEnabled = !status
        et_new_email_confirm.isEnabled = !status
        bt_update_email_login.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_update_email_login.text =
            if( status )
                getString( R.string.update_email_login_going )
            else
                getString( R.string.update_email_login )
    }
}