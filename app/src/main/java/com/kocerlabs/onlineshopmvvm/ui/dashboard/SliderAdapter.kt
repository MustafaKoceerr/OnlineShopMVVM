package com.kocerlabs.onlineshopmvvm.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.kocerlabs.onlineshopmvvm.data.network.model.SliderModel
import com.kocerlabs.onlineshopmvvm.databinding.SliderItemContainerBinding

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private val TAG = "SliderAdapter"

//    private val runnable = Runnable {
//        sliderItems = sliderItems
//        notifyDataSetChanged()
//    }
    /**
     * runnable burada bir Runnable nesnesidir ve bir tür işlem planlayıcısı olarak işlev görür.
     * Runnable sınıfı, içinde yazılmış olan bir kod bloğunun (işlemin) çalıştırılmak üzere bir işlem kuyruğuna eklenmesini sağlar.
     *
     *
     * Eğer son sayfada iseniz, runnable'ı post() ederek UI'nin güncellenmesi sağlanabilir.
     * Bu, örneğin bir "sonsuz kaydırma" yapıldığında veya yeni verilerin yüklenmesi gerektiğinde kullanılır.
     */

    inner class SliderViewHolder(private val sliderItemContainerBinding: SliderItemContainerBinding) :
        RecyclerView.ViewHolder(sliderItemContainerBinding.root) {

        fun setImage(sliderItems: SliderModel) {

            Glide.with(itemView.context)
                .load(sliderItems.url)
                .apply {
                    RequestOptions().transform(CenterInside())
                    // Glide kütüphanesinde resim yüklerken görsel üzerinde transformasyon (dönüşüm) uygulamak için kullanılan bir yapıdır.
                // Bu özel kod, Glide ile yüklenen resmin boyutlandırılmasını ve hizalanmasını kontrol eder.
                    /**
                     * transform(), Glide'da bir veya daha fazla dönüşüm uygulamak için kullanılan bir metoddur. Bu dönüşümler, örneğin resmin boyutunu değiştirme, kırpma, döndürme gibi işlemleri içerir.
                     *
                     * CenterInside(): Bu, Glide'ın sağladığı bir dönüşümdür ve resmin içerik merkezini koruyarak boyutlandırma işlemi yapar. Yani, resmin orijinal en-boy oranını bozmadan, mümkün olduğunca küçük bir alana sığdırır.

                     * Resmin boyutlarını, hedef alanın boyutlarına uygun şekilde küçültür, ancak en-boy oranını bozmadan.
                     * Eğer resim, hedef alanın boyutlarından küçükse, resim olduğu gibi yerleştirilir.
                     * Eğer resim, hedef alanın boyutlarından büyükse, resim sadece küçültülür ve merkeze yerleştirilir, böylece resmin tamamı görünür.
                     */
                }.into(sliderItemContainerBinding.imageSlide)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderAdapter.SliderViewHolder = SliderViewHolder(
        SliderItemContainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SliderAdapter.SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])

//        if (position == sliderItems.lastIndex - 1){
//            Log.d(TAG,"#$sliderItems.lastIndex")
//            viewPager2.post(runnable)
//        }
    }

    override fun getItemCount(): Int = sliderItems.size

}
