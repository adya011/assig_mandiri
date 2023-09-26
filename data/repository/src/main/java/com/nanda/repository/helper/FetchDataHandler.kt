package com.nanda.repository.helper

import com.nanda.repository.model.DataResult
import com.nanda.repository.model.DataType
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import java.util.concurrent.TimeoutException

suspend fun <Source, Result> dataSourceHandling(
    networkCall: () -> Deferred<Response<Source>>,
    mapper: DataMapper<Source, Result>
): DataResult<Result> {

    try {
        val response = withTimeout(7000) { networkCall().await() }

        if (response.isSuccessful) {
            val body = response.body()
            val mappedData = mapper.mapDataModel(body)

            if (body != null) {
                return DataResult.Success(mappedData, DataType.DATA_TYPE_API)
            }
        }
        return DataResult.Error(
            response.message(),
            response.code()
        )
    } catch (e: TimeoutCancellationException) {
        return DataResult.Error(e.message.toString(), 0)
    } catch (e: TimeoutException) {
        return DataResult.Error(e.message.toString(), 0)
    } catch (e: Exception) {
        return DataResult.Error(e.message.toString(), 0)
    }
}