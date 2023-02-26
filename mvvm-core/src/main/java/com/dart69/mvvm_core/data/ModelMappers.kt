package com.dart69.mvvm_core.data

import com.dart69.mvvm_core.domain.DomainModel

interface ModelMapper<I : DataModel, O : DomainModel> {
    fun toDomainModel(dataModel: I): O
}

interface DataModelMapper<I : DomainModel, O : DataModel> {
    fun toEntityModel(domainModel: I): O
}

interface BidirectionalModelMapper<I : DataModel, O : DomainModel> : ModelMapper<I, O>,
    DataModelMapper<O, I>