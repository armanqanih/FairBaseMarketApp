package org.lotka.xenon.presentation.screen.my_card

import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Item

data class MyCardState(


   val itemCardList: List<CardModel> = emptyList()
)
