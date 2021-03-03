package hu.laszloba.fortnightly.data.mapper.api.todatabase

import hu.laszloba.fortnightly.data.api.BaseApiModel
import hu.laszloba.fortnightly.data.database.BaseDataModel
import hu.laszloba.fortnightly.data.mapper.BaseMapper

abstract class ApiModelToDataModelMapper<T : BaseApiModel, R : BaseDataModel> : BaseMapper<T, R>()
