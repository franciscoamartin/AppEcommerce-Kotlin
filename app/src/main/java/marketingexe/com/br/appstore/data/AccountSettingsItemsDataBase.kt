package marketingexe.com.br.appstore.data

import android.content.Context
import marketingexe.com.br.appstore.R
import marketingexe.com.br.appstore.domain.AccountSettingItem
import marketingexe.com.br.appstore.view.ConfigProfileActivity
import marketingexe.com.br.appstore.view.config.connectiondata.ConfigConnectionDataActivity
import marketingexe.com.br.appstore.view.config.creditcards.ConfigCreditCardsActivity

class AccountSettingsItemsDataBase {

    companion object{

        fun getItems( context: Context)
                = listOf(
            AccountSettingItem(
                context.getString( R.string.setting_item_profile ),
                context.getString( R.string.setting_item_profile_desc ),
                ConfigProfileActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_login ),
                context.getString( R.string.setting_item_login_desc ),
                ConfigConnectionDataActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_address ),
                context.getString( R.string.setting_item_address_desc ),
                ConfigProfileActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_credit_cards ),
                context.getString( R.string.setting_item_credit_cards_desc ),
                ConfigCreditCardsActivity::class.java
            )
        )
    }
}