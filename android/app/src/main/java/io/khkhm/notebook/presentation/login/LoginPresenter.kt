package io.khkhm.notebook.presentation.login

import android.os.Handler
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.IOException


/**
 * Created by khashayar on 7/17/18.
 */
class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {


    override fun start() {
        view.setLoadingIndicator(false)
    }

    override fun onLoginBtnClick(username: String, password: String) {
        view.setLoadingIndicator(true)

        if (username != "" && password != "") {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            val url = "http://198.143.181.203:4000/login/$username/$password"

            Timber.e(url)

            val request = Request.Builder()
                    .url(url)
                    .build()

            client.newCall(request).enqueue(object : Callback {

                var mainHandler = Handler(view.getContext().getMainLooper())


                override fun onFailure(call: Call?, e: IOException?) {
                    mainHandler.post {
                        view.showLoginAttemptResult(false)
                        view.setLoadingIndicator(false)
                        Timber.e(e)
                        Timber.e("error")
                    }
                }

                override fun onResponse(call: Call?, response: Response) {
                    mainHandler.post {
                        if (response.body()?.string() == "true") {
                            view.showLoginAttemptResult(true)
                            view.gotoNotebooksList()
                            view.setLoadingIndicator(false)
                            Timber.e("success")
                        } else {
                            view.showLoginAttemptResult(false)
                            view.setLoadingIndicator(false)
                        }
                    }
                }
            })
        } else{
            view.showLoginAttemptResult(false)
            view.setLoadingIndicator(false)
        }
    }
}