package me.sokolenko.microservice.util

import com.google.common.reflect.TypeToken
import com.netflix.client.http.AsyncHttpClient
import com.netflix.client.http.HttpRequest
import com.netflix.client.http.HttpResponse
import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommand.Setter

/**
 * Created by galak on 9/30/14.
 */
@Grab(group = 'com.netflix.hystrix', module = 'hystrix-core', version = '1.3.18')
@Grab(group='com.google.guava', module='guava', version='18.0')
abstract class RestCommand<T> extends HystrixCommand<T> {

    final String clientNamespace

    final TypeToken<T> type

    protected RestCommand(Setter setter, String clientNamespace, TypeToken<T> type = null) {
        super(setter)

        this.clientNamespace = clientNamespace
        this.type = type
    }

    abstract HttpRequest.Builder buildRequest()

    @Override
    protected run() {
        def requestBuilder = buildRequest()
            .header('content-type', 'application/json')
            .header('accept', 'application/json')

        def future = restClient.execute(requestBuilder.build())
        HttpResponse resp = future.get()

        if (!resp.success) {
            throw new RestException(resp)
        } else if (type) {
            return resp.getEntity(type)
        }
    }

    protected AsyncHttpClient getRestClient() {
        ClientHolder.get(clientNamespace)
    }
}
