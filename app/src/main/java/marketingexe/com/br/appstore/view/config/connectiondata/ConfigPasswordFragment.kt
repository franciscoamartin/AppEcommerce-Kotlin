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
import kotlinx.android.synthetic.main.fragment_config_password.*

import marketingexe.com.br.appstore.R
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.isValidPassword
import marketingexe.com.br.appstore.util.validate
import marketingexe.com.br.appstore.view.FormFragment

class ConfigPasswordFragment :
    FormFragment() {

    companion object{
        const val TAB_TITLE = R.string.config_connection_data_tab_password
    }

    override fun getLayoutResourceID()
            = R.layout.fragment_config_password

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        bt_update_password.setOnClickListener{
            callPasswordDialog()
        }


        et_new_password.validate(
            {
                it.isValidPassword()
            },
            getString( R.string.invalid_password )
        )

        et_new_password_confirm.validate(
            {
                /*
                 * O toString() em et_new_email.text.toString() é
                 * necessário, caso contrário a validação falha
                 * mesmo quando é para ser ok.
                 * */
                (et_new_password.text.isNotEmpty()
                        && it.equals( et_new_password.text.toString() ))
                        || et_new_password.text.isEmpty()
            },
            getString( R.string.invalid_confirmed_password)
        )

        et_new_password_confirm.setOnEditorActionListener( this )
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_password )
        )
    }

    override fun blockFields( status: Boolean ){
        et_new_password.isEnabled = !status
        et_new_password_confirm.isEnabled = !status
        bt_update_password.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_update_password.text =
            if( status )
                getString( R.string.update_password_going)
            else
                getString( R.string.update_password )
    }
}