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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.*
import android.text.style.ImageSpan
import android.util.Patterns
import android.view.KeyEvent
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.content_account_settings.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.info_block.*
import kotlinx.android.synthetic.main.proxy_screen.*
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*
import marketingexe.com.br.appstore.data.AccountSettingsItemsDataBase
import marketingexe.com.br.appstore.domain.User
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.isValidPassword
import marketingexe.com.br.appstore.util.validate

class AccountSettingsActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_account_settings)
       setSupportActionBar(toolbar)
       supportActionBar?.setDisplayHomeAsUpEnabled(true)
       supportActionBar?.setDisplayShowHomeEnabled(true)

       val user = getUser()

       tv_user_connected.text = String.format(
           "%s %s",
           getString(R.string.connected),
           user.name
       )

       initItems()
    }

    fun getUser() = intent.getParcelableExtra<User>(User.KEY)

    override fun onOptionsItemSelected( item: MenuItem): Boolean {
        if( item.itemId == android.R.id.home ){
            finish()
            return true
        }
        return super.onOptionsItemSelected( item )
    }

    private fun initItems(){
        rv_account_settings_items.setHasFixedSize(true)

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv_account_settings_items.layoutManager = layoutManager

        val divider = androidx.recyclerview.widget.DividerItemDecoration(
            this,
            layoutManager.orientation
        )
        divider.setDrawable(
            ContextCompat.getDrawable(this, R.drawable.light_grey_divider)!!
        )

        rv_account_settings_items.addItemDecoration( divider)

        rv_account_settings_items.adapter = AccountSettingsItemsAdapter(
            AccountSettingsItemsDataBase.getItems(this)
        )
    }

}





