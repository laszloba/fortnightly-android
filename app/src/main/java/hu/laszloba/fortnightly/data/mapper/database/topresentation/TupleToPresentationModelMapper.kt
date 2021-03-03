package hu.laszloba.fortnightly.data.mapper.database.topresentation

import hu.laszloba.fortnightly.data.database.BaseTuple
import hu.laszloba.fortnightly.data.mapper.BaseMapper
import hu.laszloba.fortnightly.model.BasePresentationModel

abstract class TupleToPresentationModelMapper<T : BaseTuple, R : BasePresentationModel> :
    BaseMapper<T, R>()
