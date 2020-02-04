package marketingexe.com.br.appstore.view


import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.Annotation
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

import marketingexe.com.br.appstore.R
import marketingexe.com.br.appstore.util.CustomTypefaceSpan

class PrivacyPolicyFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false)
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        /*
         * Vamos manter a invocação do método privacyPolicyLoad()
         * em um método que vem depois do método onCreateActivity()
         * para assim podermos manter a sintaxe
         * kotlin-android-extensions de acesso às Views do layout
         * do fragmento.
         * */
        privacyPolicyLoad()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity)
            .updateToolbarTitleInFragment(R.string.privacy_policy_frag_title)
    }

    private fun privacyPolicyLoad(){
        val text = getText( R.string.privacy_policy_content ) as SpannedString

        val annotations = text.getSpans(
            0,
            text.length,
            Annotation::class.java
        )

        val spannedText = SpannableString( text )

        for ( annotation in  annotations){

            val textStartPos = text.getSpanStart( annotation)
            val textEndPos = text.getSpanEnd( annotation)
            val spanFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

            if( annotation.key.equals("title") ){
                val typeFace = ResourcesCompat.getFont(activity!!, R.font.pathway_gothic_one_regular)
                spannedText.setSpan(
                    CustomTypefaceSpan( typeFace!!),
                    textStartPos,
                    textEndPos,
                    spanFlag
                )

                spannedText.setSpan(
                    StyleSpan(Typeface.BOLD),
                    textStartPos,
                    textEndPos,
                    spanFlag
                )

                val textSizeSpan = when( annotation.value){
                    "main" -> RelativeSizeSpan(1.5F)
                    "sub" -> RelativeSizeSpan(1.3F)
                    else -> RelativeSizeSpan(1.1F)

                }
                spannedText.setSpan(
                textSizeSpan,
                    textStartPos,
                    textEndPos,
                    spanFlag
                )

            }
            else if( annotation.key.equals("link")){

                spannedText.setSpan(
                    URLSpan(annotation.value),
                    textStartPos+1,
                    textEndPos-1,
                    spanFlag
                )

                spannedText.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(activity!!, R.color.colorLink)),
                    textStartPos+1,
                    textEndPos-1,
                    spanFlag
                )
            }
        }
        tv_privacy_policy_content.movementMethod = LinkMovementMethod.getInstance()
        tv_privacy_policy_content.text = spannedText.trimStart()
    }



}
