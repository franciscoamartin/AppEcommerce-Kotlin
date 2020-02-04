package marketingexe.com.br.appstore.view

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.nguyenhoanglam.imagepicker.model.Config.EXTRA_IMAGES
import com.nguyenhoanglam.imagepicker.model.Config.RC_PICK_IMAGES
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import marketingexe.com.br.appstore.R

import kotlinx.android.synthetic.main.content_config_profile.*
import marketingexe.com.br.appstore.domain.User
import marketingexe.com.br.appstore.util.validate

class ConfigProfileActivity : FormActivity(),
    KeyboardUtils.OnSoftInputChangedListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        KeyboardUtils.registerSoftInputChangedListener(this, this)
        /*
         * Hackcode para que a imagem de background do layout não
         * se ajuste de acordo com a abertura do teclado de
         * digitação. Caso utilizando o atributo
         * android:background, o ajuste ocorre, desconfigurando o
         * layout.
         * */

        /*
      * Colocando configuração de validação de campo de email
      * para enquanto o usuário informa o conteúdo deste campo.
      * */
        et_name.validate(
            {
                it.length > 1
            },
            getString(R.string.invalid_name)
        )



        et_name.setOnEditorActionListener(this)

        val user = intent.getParcelableExtra<User>(User.KEY)
        et_name.setText( user.name)
        /*
 * Apresenta a tela de bloqueio que diz ao usuário que
 * algo está sendo processado em background e que ele
 * deve aguardar.
 * */

        riv_profile.setImageResource( user.image)
    }

    override fun getLayoutResourceID() = R.layout.content_config_profile



    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString(R.string.invalid_config_profile)
        )
    }

    override fun blockFields ( status: Boolean){
        riv_profile.isEnabled = !status
        et_name.isEnabled = !status
        bt_send_profile.isEnabled = !status
    }

    override fun isMainButtonSending (status: Boolean){
        bt_send_profile.text = if ( status)
            getString(R.string.config_profile_going)
        else
            getString(R.string.config_profile)

    }



    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }

    override fun onSoftInputChanged( height: Int ) {

        if (isAbleToCallChangeTargetViewConstraints()) {
            changeTargetViewConstraints(
                KeyboardUtils.isSoftInputVisible(this)
            )
        }
    }

    fun isAbleToCallChangeTargetViewConstraints()
            = true


    private fun changeTargetViewConstraints(
        isKeyBoardOpened: Boolean
    ){

        val photoProfileId = riv_profile.id
        val parent = riv_profile.parent as ConstraintLayout
        val constraintSet = ConstraintSet()
        val size = (108 * ScreenUtils.getScreenDensity()).toInt()


        /*
         * Definindo a largura e a altura da View em
         * mudança de constraints, caso contrário ela
         * fica com largura e altura em 0dp.
         * */
        constraintSet.constrainWidth(
            photoProfileId,
            size
        )
        constraintSet.constrainHeight(
            photoProfileId,
            size
        )

        /*
         * Centralizando a View horizontalmente no
         * ConstraintLayout.
         * */
        constraintSet.centerHorizontally(
            photoProfileId,
            ConstraintLayout.LayoutParams.PARENT_ID
        )

        if( isConstraintToSiblingView(isKeyBoardOpened) ){
            /*
             * Se o teclado virtual estiver aberto, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada a
             * View acima dela (tv_sign_up).
             * */

            setConstraintsRelativeToSiblingView(constraintSet, photoProfileId )
        }
        else{
            /*
             * Se o teclado virtual estiver fechado, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada ao
             * fundo do ConstraintLayout ancestral.
             * */
            constraintSet.connect(
                photoProfileId,
                ConstraintLayout.LayoutParams.TOP,
                ConstraintLayout.LayoutParams.PARENT_ID,
                ConstraintLayout.LayoutParams.TOP
            )
        }

        constraintSet.applyTo( parent )
    }

    fun isConstraintToSiblingView( isKeyBoardOpened : Boolean)
    = isKeyBoardOpened || ScreenUtils.isLandscape()

    fun setConstraintsRelativeToSiblingView(constraintSet : ConstraintSet, targetViewId : Int)
             {
        constraintSet.connect(
            targetViewId,
            ConstraintLayout.LayoutParams.BOTTOM,
            tv_name.id,
            ConstraintLayout.LayoutParams.TOP,
            (30 * ScreenUtils.getScreenDensity()).toInt()
        )
    }

    fun callGallery(view: View){
        val colorPrimary = ColorUtils.int2ArgbString(
            ColorUtils.getColor(R.color.colorPrimary)
        )
        val colorPrimaryDark = ColorUtils.int2ArgbString(
            ColorUtils.getColor(R.color.colorPrimaryDark)
        )
        val colorText = ColorUtils.int2ArgbString(
            ColorUtils.getColor(R.color.colorText)
        )
        val colorWhite = ColorUtils.int2ArgbString(
            Color.WHITE
        )

        ImagePicker
            .with( this ) /* Inicializa a ImagePicker API com um context (Activity ou Fragment) */
            .setToolbarColor( colorPrimary )
            .setStatusBarColor( colorPrimaryDark )
            .setToolbarTextColor( colorText )
            .setToolbarIconColor( colorText )
            .setProgressBarColor( colorPrimaryDark )
            .setBackgroundColor( colorWhite )
            .setMultipleMode( false )
            .setFolderMode( true )
            .setShowCamera( true )
            .setFolderTitle( getString(R.string.imagepicker_gallery_activity) ) /* Nome da tela de galeria da ImagePicker API (funciona quando FolderMode = true). */
            .setLimitMessage( getString(R.string.imagepicker_selection_limit) )
            .setSavePath( getString(R.string.imagepicker_cam_photos_activity) ) /* Folder das imagens de câmera, tiradas a partir da ImagePicker API. */
            .setKeepScreenOn( true ) /* Mantém a tela acionada enquanto a galeria estiver aberta. */
            .start()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent? ) {

        if( requestCode == RC_PICK_IMAGES
            && resultCode == Activity.RESULT_OK
            && data != null ){

            val images = data.getParcelableArrayListExtra<Image>( EXTRA_IMAGES )

            if( images.isNotEmpty() ){
                riv_profile.setImageURI(
                    Uri.parse( images.first().path )
                )
            }
        }

        /*
         * Note que em nossa lógica de negócio, se não houver imagem
         * selecionada, o que estiver atualmente presente como imagem
         * de perfil continua sendo a imagem de perfil.
         * */

        /*
         * A invocação a super.onActivityResult() tem que
         * vir após a verificação / obtenção da imagem.
         * */
        super.onActivityResult( requestCode, resultCode, data )
    }
}





