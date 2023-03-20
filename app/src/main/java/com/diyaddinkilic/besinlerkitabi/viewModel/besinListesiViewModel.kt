package com.diyaddinkilic.besinlerkitabi.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.diyaddinkilic.besinlerkitabi.model.Besin
import com.diyaddinkilic.besinlerkitabi.servis.BesinAPIServis
import com.diyaddinkilic.besinlerkitabi.servis.BesinDataBase
import com.diyaddinkilic.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class besinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    private val besinApiServis = BesinAPIServis()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refleshData() {
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani) {
            //Sqlite an AL
            verileriSQLitetanAl()
        } else {
            verileriInternettenAl()
        }
    }
    fun refreshFromInternet() {
        verileriInternettenAl()
    }
    private fun verileriSQLitetanAl() {
        besinYukleniyor.value = true

        launch {
            val besinListesi = BesinDataBase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(), "Besinleri Roomdan ALdık", Toast.LENGTH_LONG).show()
        }
    }

    private fun verileriInternettenAl() {
        besinYukleniyor.value = true
        //IO, Default, UI
        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                    override fun onSuccess(t: List<Besin>) {
                        //Başarılı Olursak
                        sqliteSakla(t)
                        Toast.makeText(
                            getApplication(),
                            "Besinleri Internetten ALdık",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        //Hata Alırsak
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun besinleriGoster(besinlerListesi: List<Besin>) {
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinListesi: List<Besin>) {
        launch {
            val dao = BesinDataBase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i < besinListesi.size) {
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}