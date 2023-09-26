package com.nanda.repository.helper

interface DataMapper<RemoteDataModel, DomainModel> {
    fun mapDataModel(dataModel: RemoteDataModel?): DomainModel
}