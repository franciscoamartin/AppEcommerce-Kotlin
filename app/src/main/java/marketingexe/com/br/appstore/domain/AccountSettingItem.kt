package marketingexe.com.br.appstore.domain

import androidx.appcompat.app.AppCompatActivity
import marketingexe.com.br.appstore.view.FormActivity

class AccountSettingItem (
    val label : String,
    val description : String,
    val activityClass: Class<out AppCompatActivity>)