package marketingexe.com.br.appstore.view.config.creditcards


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_account_settings.*
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_config_credit_cards_list.*
import kotlinx.android.synthetic.main.fragment_config_email.*

import marketingexe.com.br.appstore.R
import marketingexe.com.br.appstore.data.AccountSettingsItemsDataBase
import marketingexe.com.br.appstore.data.CreditCardsDataBase
import marketingexe.com.br.appstore.domain.CreditCard
import marketingexe.com.br.appstore.util.isValidEmail
import marketingexe.com.br.appstore.util.validate
import marketingexe.com.br.appstore.view.AccountSettingsItemsAdapter
import marketingexe.com.br.appstore.view.FormFragment

class ConfigCreditCardsListFragment :
    FormFragment() {

    companion object{
        const val TAB_TITLE = R.string.config_credit_cards_tab_list
    }

    var callbackMainButtonUpdate : (Boolean)->Unit = {}
    var callbackBlockFields : (Boolean)->Unit = {}
    var callbackRemoveItem : (Boolean)->Unit = {}

    override fun getLayoutResourceID()
            = R.layout.fragment_config_credit_cards_list

    override fun backEndFakeDelay() {
        backEndFakeDelay(
            true,
            getString( R.string.credit_card_removed )
        )
    }

    override fun blockFields(status: Boolean) {
        callbackBlockFields( status )
    }

    override fun isMainButtonSending( status: Boolean ) {
        callbackMainButtonUpdate( status )
        callbackRemoveItem( status )
    }

    /*
     * Método utilizado para receber os callbacks do adapter
     * do RecyclerView para assim poder atualizar os itens
     * de adapter.
     * */
    fun callbacksToUpdateItem(
        mainButtonUpdate: (Boolean)->Unit,
        blockFields: (Boolean)->Unit,
        removeItem: (Boolean)->Unit ){

        callbackMainButtonUpdate = mainButtonUpdate
        callbackBlockFields = blockFields
        callbackRemoveItem = removeItem
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        updateFlFormToFullFreeScreen()
        initItems()
    }

    /*
     * Método que inicializa a lista de cartões de crédito.
     * */
    private fun initItems(){
        rv_credit_cards.setHasFixedSize( false )

        val layoutManager = LinearLayoutManager( activity )
        rv_credit_cards.layoutManager = layoutManager

        val adapter = ConfigCreditCardsListItemsAdapter(
            this,
            CreditCardsDataBase.getItems()
        )
        adapter.registerAdapterDataObserver( RecyclerViewObserver() )
        rv_credit_cards.adapter = adapter
    }

    /*
     * Com o RecyclerView.AdapterDataObserver é possível
     * escutar o tamanho atual da lista de itens vinculada
     * ao RecyclerView e caso essa lista esteja vazia, então
     * podemos apresentar uma mensagem ao usuário informando
     * sobre a lista vazia.
     * */
    inner class RecyclerViewObserver :
        RecyclerView.AdapterDataObserver() {

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)

            tv_empty_list.visibility =
                if( rv_credit_cards.adapter!!.itemCount == 0 )
                    View.VISIBLE
                else
                    View.GONE
        }
    }
}