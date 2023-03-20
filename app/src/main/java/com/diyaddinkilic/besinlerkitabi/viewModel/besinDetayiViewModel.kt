package com.diyaddinkilic.besinlerkitabi.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.diyaddinkilic.besinlerkitabi.model.Besin
import com.diyaddinkilic.besinlerkitabi.servis.BesinDataBase
import kotlinx.coroutines.launch

class besinDetayiViewModel(application: Application) : BaseViewModel(application) {
    val besinLiveData = MutableLiveData<Besin>()
    fun roomVerisiniAl(uuid: Int) {
        launch {
            val dao = BesinDataBase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }
}
