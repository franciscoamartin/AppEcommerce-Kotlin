package marketingexe.com.br.appstore.view.config.connectiondata

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import marketingexe.com.br.appstore.view.config.connectiondata.ConfigEmailFragment
import marketingexe.com.br.appstore.view.config.connectiondata.ConfigPasswordFragment

class ConfigConnectionDataSectionsAdapter(
    val context: Context,
    fm: FragmentManager ) : FragmentPagerAdapter( fm ) {

    companion object{
        const val TOTAL_PAGES = 2
        const val EMAIL_PAGE_POS = 0
    }

    /*
     * getItem() é invocado para devolver uma instância do
     * fragmento correspondendo a posição (seção/página)
     * informada.
     * */
    override fun getItem( position: Int )
            = when( position ){
        EMAIL_PAGE_POS -> ConfigEmailFragment()
        else -> ConfigPasswordFragment()
    }

    override fun getPageTitle( position: Int )
            = context.getString(
        when( position ){
            EMAIL_PAGE_POS -> ConfigEmailFragment.TAB_TITLE
            else -> ConfigPasswordFragment.TAB_TITLE
        }
    )

    override fun getCount()
            = TOTAL_PAGES
}