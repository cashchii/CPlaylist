package com.cashchii.production.cplaylist.manager

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by Panc. on 8/11/2018 AD.
 */
class SelfSigningClientBuilder {
    companion object {
        fun getUnsafeOkHttpClient(context: Context?, callback: ((String) -> Unit)?): OkHttpClient {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }
                builder.addInterceptor {
                    val original = it.request()
                    val response = it.proceed(original)
                    if (response.code() != 200) {
                        callback?.invoke(response.message())
                    }
                    return@addInterceptor response
                }
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)

                val okHttpClient: OkHttpClient = builder
                        .retryOnConnectionFailure(true)
                        .readTimeout(180, TimeUnit.SECONDS)
                        .connectTimeout(180, TimeUnit.SECONDS)
                        .build()
                return okHttpClient
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}