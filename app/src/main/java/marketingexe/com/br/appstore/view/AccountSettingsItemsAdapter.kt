package marketingexe.com.br.appstore.view

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import marketingexe.com.br.appstore.R
import marketingexe.com.br.appstore.domain.AccountSettingItem
import marketingexe.com.br.appstore.domain.User
import java.security.AccessControlException

class AccountSettingsItemsAdapter(
    private val items: List<AccountSettingItem>
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AccountSettingsItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        type: Int ): ViewHolder {

        val layout = LayoutInflater
            .from( parent.context )
            .inflate(
                R.layout.account_settings_item,
                parent,
                false
            )

        return ViewHolder( layout )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int ) {

        holder.setData( items[ position ] )
    }

    override fun getItemCount() = items.size

    inner class ViewHolder( itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder( itemView ),
        View.OnClickListener
    {

        private val tvLabel : TextView
        private val tvDescription : TextView

        init{
            itemView.setOnClickListener(this)
            tvLabel = itemView.findViewById( R.id.tv_label )
            tvDescription = itemView.findViewById( R.id.tv_description )
        }

        fun setData( item: AccountSettingItem ){
            tvLabel.text = item.label
            tvDescription.text = item.description
        }

        override fun onClick(view: View) {
            val activity = view.context as AccountSettingsActivity
            val intent = Intent(activity, items[adapterPosition].activityClass)

            intent.putExtra( User.KEY, activity.getUser())

            activity.startActivity(intent)
        }
    }
}